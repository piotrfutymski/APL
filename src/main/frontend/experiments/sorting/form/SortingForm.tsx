import React, {useState, useEffect} from 'react'
import { useCookies } from 'react-cookie'
import { Navigate } from 'react-router-dom'

import { fetchAlgorithms, fetchDataDistributions, getParamInfos, submitExperiments } from '../SortingServices'

import { SortingExperimentCard} from './SortingExperimentCard'
import { SortingHeader } from './SortingHeader'

import styles from './SortingForm.module.scss'
import { SortingConfig, SortingExperiment } from '../Sorting.interface'

export const SortingForm = () =>{
    const [id, setId] = useState<string>("");
    //============================= fetch data =============================
    const [algorithmOptions, setAlgorithmOptions] = useState<string[]>([])
    const [dataOptions, setDataOptions] = useState<string[]>([])
    useEffect(() => {
        fetchAlgorithms((options)=> setAlgorithmOptions(options))
        fetchDataDistributions((options)=> setDataOptions(options))
    }, [])
    //==========================================================
    //============================= handle cookies =============================
    const [cookies, setCookie] = useCookies(['SortingExperiments', 'SortingConfig']);

    const experiments = (cookies.SortingExperiments || []).map( (e: any)=> { return {...e, algorithmParams: new Map<string,string>(Object.entries(e.algorithmParams))} }) as SortingExperiment[] 
    const setExperiments = (newExperiments: SortingExperiment[]) =>{
        setCookie('SortingExperiments', newExperiments.map( (e: any)=> { return {...e, algorithmParams: Object.fromEntries(e.algorithmParams)} }), {path: '/'})
    }

    const config = (cookies.SortingConfig || {n: 1000, measureSeries: 10, maxValAsPercent: true}) as SortingConfig
    const setConfig = (newConfig: SortingConfig) =>{
        setCookie('SortingConfig', newConfig, {path: '/'})
    }
    //==========================================================
    //============================= experiments =============================
    const addExperiment = () =>{
        let defMaxVal = config.n
        if(config.maxValAsPercent){
            defMaxVal = 100
        }
        let newExperiment: SortingExperiment = {algorithmName: algorithmOptions[0], dataDistribution: dataOptions[0], algorithmParams: new Map<string, string>(), maxValue: defMaxVal, check: false}
        if(experiments.length > 0){
            newExperiment = experiments.at(experiments.length-1)
        }
        prepareExperimentParams(newExperiment)
        setExperiments([...experiments, newExperiment])
    }
    const prepareExperimentParams = (experiment: SortingExperiment) =>{
        const paramInfos = getParamInfos(experiment)
        let newParams = new Map<string, string>()
        paramInfos.forEach(param => newParams.set(param.name, param.isSelect === true ? param.options.at(0) : "") )

        experiment.algorithmParams.forEach( (value, name) => {
            if(newParams.has(name)){
                newParams.set(name, value)
            }
        })
        experiment.algorithmParams = newParams
    }
    const checkExperiment = (experiment: SortingExperiment): boolean =>{
        return experiment.algorithmName !== "" && experiment.dataDistribution !== ""
    }
    const updateExperiment = (key: number, newExperiment: SortingExperiment) =>{
        newExperiment.check = checkExperiment(newExperiment)
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
    const updateConfig = (newConfig: SortingConfig) =>{
        if(config.maxValAsPercent !== newConfig.maxValAsPercent){
            let tmp=0
            if(newConfig.maxValAsPercent){
                tmp=100/newConfig.n
            }else{
                tmp=newConfig.n/100
            }
            let newExperiments = experiments.map(e => {return {...e, maxValue: e.maxValue*tmp}})
            setExperiments(newExperiments)
        }
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
        { id !== "" ? <Navigate to={`/experiments/sorting/${id}`} /> : <></>}
        <SortingHeader config={config} updateConfig={updateConfig} submit={submit}/>
        <div className={styles.ExperimentList}>
            {
                experiments.map((experiment, index) => {
                    return <SortingExperimentCard experiment={experiment} 
                        updateExperiment={(experiment)=>updateExperiment(index, experiment)} 
                        removeExperiment={()=>removeExperiment(index)} 
                        algorithmOptions={algorithmOptions}
                        dataOptions={dataOptions}
                        maxValAsPercents={config.maxValAsPercent}/>
                })
            }
            <button className={styles.AddButton} onClick={addExperiment}>Add Experiment</button>
        </div>
        </>
    )
}