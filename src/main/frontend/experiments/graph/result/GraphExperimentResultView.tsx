import React, {useState, useEffect} from 'react'
import { useParams } from 'react-router'

import { GraphDoneView } from './GraphDoneView'
import { GraphNotDoneView } from './GraphNotDoneView'
import { GraphExperimentsResult } from '../Graph.interface'
import { fetchGraphExperiments, deleteGraphExperiment } from '../GraphServices'

import styles from './GraphNotDoneView.module.scss'

export const GraphExperimentResultView = () =>{
    const { id } = useParams<string>()
    const [experimentsResults, setExperimentsResults] = useState<GraphExperimentsResult>(null)
    const [intervalV, setIntervalV] = useState<NodeJS.Timer>();

    const getExperimentsResult = () => {
        if(experimentsResults===null || experimentsResults.status === 'QUEUED' || experimentsResults.status === 'CALCULATING')
            fetchGraphExperiments(id, setExperimentsResults)
    }

    useEffect(() => {
        let intervalId = setInterval(getExperimentsResult, 1000)
        setIntervalV(intervalId)
        return () => {
            clearInterval(intervalId)
            deleteGraphExperiment(id);
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
                    <GraphNotDoneView
                        queuePosition={experimentsResults.queuePosition}
                        errorCause={experimentsResults.errorCause}
                        status={experimentsResults.status}
                        results={experimentsResults.results}
                        id={id}
                    /> : 
                    <GraphDoneView
                        queuePosition={experimentsResults.queuePosition}
                        errorCause={experimentsResults.errorCause}
                        status={experimentsResults.status}
                        results={experimentsResults.results}
                    />
        }
        </>
    )
}