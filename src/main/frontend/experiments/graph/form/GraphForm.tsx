import React, {useState, useEffect} from 'react'
import { useCookies } from 'react-cookie'
import { Navigate } from 'react-router-dom'

import { fetchAlgorithms, fetchDataGenerators, fetchRepresentations, getParamInfos, submitExperiments } from '../GraphServices'

import { GraphExperimentCard} from './GraphExperimentCard'
import { GraphHeader } from './GraphHeader'

import styles from './GraphForm.module.scss'
import { GraphConfig, GraphExperiment } from '../Graph.interface'

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
        setCookie('GraphExperiments', newExperiments.map( (e: any)=> { return {...e, algorithmParams: Object.fromEntries(e.algorithmParams)} }), {path: '/'})
    }

    const config = (cookies.GraphConfig || {measureSeries: 10}) as GraphConfig
    const setConfig = (newConfig: GraphConfig) =>{
        setCookie('GraphConfig', newConfig, {path: '/'})
    }
    //==========================================================
    //============================= experiments =============================
    const addExperiment = () =>{
        setExperiments([...experiments, {algorithmName: algorithmOptions[0], dataGenerator: dataOptions[0], representation: representationOptions[0], numberOfVertices: 5, density: 90, algorithmParams: new Map<string, string>(), check: false}])
    }
    const checkExperiment = (experiment: GraphExperiment): boolean =>{
        return experiment.algorithmName !== "" && experiment.dataGenerator !== "" && experiment.representation !== ""
    }
    const updateExperiment = (key: number, newExperiment: GraphExperiment) =>{
        newExperiment.check = checkExperiment(newExperiment)
        if(experiments.at(key).algorithmName !== newExperiment.algorithmName){
            const paramInfos = getParamInfos(newExperiment)
            if(paramInfos.length > 0)
            {
                newExperiment.algorithmParams.clear()
                paramInfos.forEach(param => newExperiment.algorithmParams.set(param.name, param.isSelect === true ? param.options.at(0) : "") )
            }
        }
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
        setConfig(newConfig)
    }
    //==========================================================
    //============================= submitting =============================
    const submit = () =>{
        submitExperiments(experiments, config, true, (id: string) => {setId(id)})
    }
    //==========================================================
    return (
        <>
        { id !== "" ? <Navigate to={`/experiments/graph/${id}`} /> : <></>}
        <GraphHeader config={config} updateConfig={updateConfig} submit={submit}/>
        <div className={styles.ExperimentList}>
            {
                experiments.map((experiment, index) => {
                    return <GraphExperimentCard experiment={experiment} 
                        updateExperiment={(experiment)=>updateExperiment(index, experiment)} 
                        removeExperiment={()=>removeExperiment(index)} 
                        algorithmOptions={algorithmOptions}
                        dataOptions={dataOptions}
                        representationOptions={representationOptions}/>
                })
            }
            <button className={styles.AddButton} onClick={addExperiment}>Add Experiment</button>
        </div>
        </>
    )
}