import React, {useState, useEffect} from 'react'
import { ControlForm } from './ControlForm'
import { fetchDataDistributions, fetchSortingAlgorithms } from './Requests'

import { SortingExperimentCard, SortingExperiment } from './SortingExperimentCard'
import styles from './SortingForm.module.scss'

export const SortingForm = () =>{
    //============================= fetch available options =============================
    const [algorithmOptions, setAlgorithmOptions] = useState<string[]>([])
    const [dataOptions, setDataOptions] = useState<string[]>([])
    useEffect(() => {
        fetchSortingAlgorithms((options)=> setAlgorithmOptions(options))
        fetchDataDistributions((options)=> setDataOptions(options))
    }, [])
    //==========================================================
    //============================= experiments =============================
    const [experiments, setExperiments] = useState<SortingExperiment[]>([])
    const addExperiment = () =>{
        setExperiments(prevExperiments =>{
            return [...prevExperiments, {algorithm: "", dataDistribution: "", algorithmParams: new Map<string, string>(), check: false}]
        })
    }
    const checkExperiment = (experiment: SortingExperiment): boolean =>{
        return experiment.algorithm !== "" && experiment.dataDistribution !== ""
    }
    const updateExperiment = (key: number, newExperiment: SortingExperiment) =>{
        newExperiment.check = checkExperiment(newExperiment)
        setExperiments(prevExperiments =>{
            return prevExperiments.map((experiment, i) => {
                if(i === key){
                    return newExperiment
                }
                return experiment
            })
        })
    }
    const removeExperiment = (key: number) =>{
        setExperiments(prevExperiments =>{
            return prevExperiments.filter((_, i) => i !== key)
        })
    }

    const createExperimentsFromTemplates = () => {
        let res: SortingExperiment[] = []
        experiments.forEach(element => {
            for(let i = 0; i<seriesSize; i++){
                res.push({
                    algorithmName: element.algorithmName,
                    dataDistribution: element.dataDistribution,
                    maxValue: maxValueAsPercentOfN ? element.maxValue * N : element.maxValue,
                    n: (N * (i+1)) / seriesSize
                })
            }
        });
        return res;
    }
    //==========================================================
    //============================= result =============================
    const [id, setId] = useState<string>("");
    //==========================================================
    return (
        <>
        <div className={styles.ControlContainer}>
            <ControlForm />
        </div>
        <div className={styles.FormContainer}>
            <div className={styles.ExperimentContainer}>
                {
                    experiments.map((experiment, index) => {
                        return <SortingExperimentCard experiment={experiment} 
                            updateExperiment={(experiment)=>updateExperiment(index, experiment)} 
                            removeExperiment={()=>removeExperiment(index)} 
                            algorithmOptions={algorithmOptions}
                            dataOptions={dataOptions}/>
                    })
                }
                <button className={styles.AddButton} onClick={addExperiment}>Add Experiment</button>
            </div>
            <div className={styles.ControlContainerSpace}></div>
        </div>
        </>
    )
}