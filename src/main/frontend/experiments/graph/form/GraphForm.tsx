import React, {useState, useEffect} from 'react'
import { useCookies } from 'react-cookie'
import { Navigate } from 'react-router-dom'

import { checkConfig, checkExperiment, fetchAlgorithms, fetchDataGenerators, fetchRepresentations, getParamInfos, reducePossibleGenerators, reducePossibleRepresentations, submitExperiments } from '../GraphServices'

import { GraphExperimentCard} from './GraphExperimentCard'
import { GraphHeader } from './GraphHeader'

import styles from './GraphForm.module.scss'
import { CheckResult, GraphConfig, GraphExperiment } from '../Graph.interface'

export const GraphForm = () =>{
    const [id, setId] = useState<string>("");
    //============================= fetch data =============================
    const [algorithmOptions, setAlgorithmOptions] = useState<string[]>([])
    const [dataOptions, setDataOptions] = useState<string[]>([])
    const [representationOptions, setRepresentationOptions] = useState<string[]>([])
    useEffect(() => {
        fetchAlgorithms((options)=> setAlgorithmOptions(options))
        fetchDataGenerators((options)=> setDataOptions(options))
        fetchRepresentations((options)=> setRepresentationOptions(options))
    }, [])
    //==========================================================
    //============================= handle cookies =============================
    const [cookies, setCookie] = useCookies(['GraphExperiments', 'GraphConfig']);

    const experiments = (cookies.GraphExperiments || []).map( (e: any)=> { return {...e, algorithmParams: new Map<string,string>(Object.entries(e.algorithmParams))} }) as GraphExperiment[] 
    const setExperiments = (newExperiments: GraphExperiment[]) =>{
        setCookie('GraphExperiments', newExperiments.map( (e: any)=> { return {...e, algorithmParams: Object.fromEntries(e.algorithmParams)} }), {path: '/', sameSite: true, maxAge: 36288000})
    }

    const config = (cookies.GraphConfig || {measureSeries: 10, densityOrVertices: 50, measureByDensity: false}) as GraphConfig
    const setConfig = (newConfig: GraphConfig) =>{
        setCookie('GraphConfig', newConfig, {path: '/', sameSite: true, maxAge: 36288000})
    }
    //==========================================================
    //============================= experiments =============================
    const addExperiment = () =>{
        const defAlgorithm = algorithmOptions[0]
        const defGenerator = reducePossibleGenerators(defAlgorithm, dataOptions)[0]
        const defRepresentation=reducePossibleRepresentations(defAlgorithm, defGenerator, representationOptions)[0]
        let newExperiment: GraphExperiment = {algorithmName: defAlgorithm,
            dataGenerator: defGenerator, 
            representation: defRepresentation,
            numberOfVertices: config.measureByDensity ? config.measureSeries * 5 : config.densityOrVertices,
            density: config.measureByDensity ? config.densityOrVertices : 90, 
            algorithmParams: new Map<string, string>(), 
            check: false}
        if(experiments.length > 0){
            newExperiment = experiments.at(experiments.length-1)
        }
        prepareExperimentParams(newExperiment)
        setExperiments([...experiments, newExperiment])
    }
    const prepareExperimentParams = (experiment: GraphExperiment) =>{
        const paramInfos = getParamInfos(experiment)
        let newParams = new Map<string, string>()
        paramInfos.forEach(param => newParams.set(param.name, param.isSelect === true ? param.options.at(0) : "") )

        experiment.algorithmParams.forEach( (value, name) => {
            if(newParams.has(name)){
                newParams.set(name, value)
            }
        })
        experiment.algorithmParams = newParams
        if(getParamInfos(experiment).length !== paramInfos.length)
            prepareExperimentParams(experiment)
    }

    const updateExperiment = (key: number, newExperiment: GraphExperiment) =>{
        if(newExperiment.algorithmName !== experiments.at(key).algorithmName || newExperiment.dataGenerator !== experiments.at(key).dataGenerator)
        {
            const posGens = reducePossibleGenerators(newExperiment.algorithmName, dataOptions)
            if(!posGens.includes(newExperiment.dataGenerator)){
                newExperiment.dataGenerator=posGens[0]
            }
            const posReprs = reducePossibleRepresentations(newExperiment.algorithmName, newExperiment.dataGenerator, representationOptions)
            if(!posReprs.includes(newExperiment.representation)){
                newExperiment.representation=posReprs[0]
            }
        }
        prepareExperimentParams(newExperiment)
        setExperiments(experiments.map((experiment, i) => {
                if(i === key){
                    return newExperiment
                }
                return experiment
            }))
    }
    const removeExperiment = (key: number) =>{
        setExperiments(experiments.filter((_, i) => i !== key))
    }
    //==========================================================
    //============================= config =============================
    const updateConfig = (newConfig: GraphConfig) =>{
        setExperiments(experiments.map(v => {
            let tmp = {...v};
            if(newConfig.measureByDensity === true) {
                tmp.density = newConfig.densityOrVertices;
            } else {
                tmp.numberOfVertices = newConfig.densityOrVertices;
            }
            return tmp;
        }))
        setConfig(newConfig)
    }
    //==========================================================
    //============================= check =============================
    const experimentCheckInfos = experiments.map(e=> checkExperiment(e, config))
    const configCheckInfo = checkConfig(config, experiments)
    let allowSubmit=true
    experimentCheckInfos.forEach(e=>e.errorFlag? allowSubmit=false:"")
    //==========================================================
    //============================= submitting =============================
    const submit = () =>{
        submitExperiments(experiments, config, true, (id: string) => {setId(id)})
    }
    //==========================================================
    return (
        <>
        { id !== "" ? <Navigate to={`/experiments/graph/${id}`} /> : <></>}
        <GraphHeader allowSubmit={allowSubmit} configCheckInfo={configCheckInfo} config={config} updateConfig={updateConfig} submit={submit}/>
        <div className={styles.ExperimentList}>
            {
                experiments.map((experiment, index) => {
                    return <GraphExperimentCard key={index} experiment={experiment} config={config}
                        updateExperiment={(experiment)=>updateExperiment(index, experiment)} 
                        removeExperiment={()=>removeExperiment(index)} 
                        algorithmOptions={algorithmOptions}
                        dataOptions={dataOptions}
                        representationOptions={representationOptions}
                        experimentCheckInfo={experimentCheckInfos[index]}/>

                })
            }
            <button className={styles.AddButton} onClick={addExperiment}>Add Experiment</button>
        </div>
        </>
    )
}