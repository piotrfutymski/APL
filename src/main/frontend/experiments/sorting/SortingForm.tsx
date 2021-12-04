import React, {useState, useEffect} from 'react'
import { useCookies } from 'react-cookie'
import { fetchSortingDataDistributions, fetchSortingAlgorithms, startSortingExperiments } from './Requests'

import { SortingExperimentCard, SortingExperiment } from './SortingExperimentCard'
import { paramInfo } from './SortingExperimentCard.interface'
import styles from './SortingForm.module.scss'
import { SortingHeader } from './SortingHeader'
import { SortingConfig } from './SortingHeader.interface'

export const SortingForm = () =>{
    const [id, setId] = useState<string>("");
    //============================= fetch data =============================
    const [algorithmOptions, setAlgorithmOptions] = useState<string[]>([])
    const [dataOptions, setDataOptions] = useState<string[]>([])
    useEffect(() => {
        fetchSortingAlgorithms((options)=> setAlgorithmOptions(options))
        fetchSortingDataDistributions((options)=> setDataOptions(options))
    }, [])
    //==========================================================
    //============================= handle cookies =============================
    const [cookies, setCookie] = useCookies(['experiments', 'config']);

    const experiments = (cookies.experiments || []).map( (e: any)=> { return {...e, algorithmParams: new Map(Object.entries(e.algorithmParams))} }) as SortingExperiment[] 
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
        setExperiments([...experiments, {algorithm: "", dataDistribution: "", algorithmParams: new Map<string, string>(), maxVal: defMaxVal,check: false}])
    }
    const checkExperiment = (experiment: SortingExperiment): boolean =>{
        return experiment.algorithm !== "" && experiment.dataDistribution !== ""
    }
    const updateExperiment = (key: number, newExperiment: SortingExperiment) =>{
        newExperiment.check = checkExperiment(newExperiment)
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
            let newExperiments = experiments.map(e => {return {...e, maxVal: e.maxVal*tmp}})
            setExperiments(newExperiments)
        }
        setConfig(newConfig)
    }
    //==========================================================
    //============================= submitting =============================
    const submit = () =>{
        let res: any[] = []
        experiments.forEach(element => {
            for(let i = 0; i < config.measureSeries; i++){
                res.push({
                    algorithmName: element.algorithm,
                    dataDistribution: element.dataDistribution,
                    algorithmParams: Object.fromEntries(element.algorithmParams),
                    maxValue: config.maxValAsPercent ? element.maxVal/100 * config.n : element.maxVal,
                    n: (config.n * (i+1)) / config.measureSeries
                })
            }
        });
        startSortingExperiments(experiments, true, (id: string) => {setId(id)})
    }
    //==========================================================
    return (
        <>
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