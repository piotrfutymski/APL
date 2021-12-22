import React, {useState, useEffect} from 'react'
import { useParams } from 'react-router'

import { SortingDoneView } from './SortingDoneView'
import { SortingNotDoneView } from './SortingNotDoneView'
import { SortingExperimentsResult } from '../Sorting.interface'
import { fetchSortingExperiments, deleteSortingExperiment } from '../SortingServices'

import styles from './SortingNotDoneView.module.scss'

export const SortingExperimentResultView = () =>{
    const { id } = useParams<string>()
    const [experimentsResults, setExperimentsResults] = useState<SortingExperimentsResult>(null)
    const [intervalV, setIntervalV] = useState<NodeJS.Timer>();

    const getExperimentsResult = () => {
        if(experimentsResults===null || experimentsResults.status === 'QUEUED' || experimentsResults.status === 'CALCULATING')
            fetchSortingExperiments(id, setExperimentsResults)
    }

    useEffect(() => {
        let intervalId = setInterval(getExperimentsResult, 1000)
        setIntervalV(intervalId)
        return () => {
            clearInterval(intervalId)
        }
    }, [id])

    useEffect(() => {
        if(experimentsResults!==null ){
            if(experimentsResults.status !== 'QUEUED' && experimentsResults.status !== 'CALCULATING')
                clearInterval(intervalV)
        }
    }, [experimentsResults])


    
    return (
        <>
        {
            experimentsResults === null ? 
                <div className={styles.Container}><div className={styles.Message}> Waiting for server to respond... </div></div> :
                experimentsResults.status !== "DONE" ? 
                    <SortingNotDoneView
                        queuePosition={experimentsResults.queuePosition}
                        errorCause={experimentsResults.errorCause}
                        status={experimentsResults.status}
                        results={experimentsResults.results}
                        id={id}
                    /> : 
                    <SortingDoneView
                        queuePosition={experimentsResults.queuePosition}
                        errorCause={experimentsResults.errorCause}
                        status={experimentsResults.status}
                        results={experimentsResults.results}
                    />
        }
        </>
    )
}