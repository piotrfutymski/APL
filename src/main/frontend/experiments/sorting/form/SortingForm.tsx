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
    const [cookies, setCookie] = useCookies(['experiments', 'config']);

    const experiments = (cookies.experiments || []).map( (e: any)=> { return {...e, algorithmParams: new Map<string,string>(Object.entries(e.algorithmParams))} }) as SortingExperiment[] 
    const setExperiments = (newExperiments: SortingExperiment[]) =>{
        setCookie('experiments', newExperiments.map( (e: any)=> { return {...e, algorithmParams: Object.fromEntries(e.algorithmParams)} }), {path: '/'})
    }

    const config = (cookies.config || {n: 1000, measureSeries: 10, maxValAsPercent: true}) as SortingConfig
    const setConfig = (newConfig: SortingConfig) =>{
        setCookie('config', newConfig, {path: '/'})
    }
    //==========================================================
    //============================= experiments =============================
    const addExperiment = () =>{
        let defMaxVal = config.n
        if(config.maxValAsPercent){
            defMaxVal = 100
        }
        setExperiments([...experiments, {algorithmName: algorithmOptions[0], dataDistribution: dataOptions[0], algorithmParams: new Map<string, string>(), maxValue: defMaxVal, check: false}])
    }
    const checkExperiment = (experiment: SortingExperiment): boolean =>{
        return experiment.algorithmName !== "" && experiment.dataDistribution !== ""
    }
    const updateExperiment = (key: number, newExperiment: SortingExperiment) =>{
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
    const updateConfig = (newConfig: SortingConfig) =>{
        if(config.maxValAsPercent !== newConfig.maxValAsPercent){
            let tmp=0
            if(newConfig.maxValAsPercent){
                tmp=100/newConfig.n
            }else{
                tmp=newConfig.n/100
            }
            let newExperiments = experiments.map(e => {return {...e, maxVal: e.maxValue*tmp}})
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