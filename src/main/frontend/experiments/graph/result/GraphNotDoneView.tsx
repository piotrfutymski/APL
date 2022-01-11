import classNames from "classnames"
import React, { useEffect, useState } from "react"
import { Navigate } from "react-router-dom"
import { deleteSortingExperiment } from "../../sorting/SortingServices"
import { GraphExperimentsResult } from "../Graph.interface"
import { deleteGraphExperiment } from "../GraphServices"

import styles from './GraphNotDoneView.module.scss'

export const GraphNotDoneView = (props: GraphExperimentsResult) => {
    const [time, setTime] = useState<number>(0)
    const [intervalV, setIntervalV] = useState<NodeJS.Timer>();

    const updateTime=()=>{
        setTime(oldTime => oldTime+1)
    }
    useEffect(()=>{
        let intervalId = setInterval(updateTime, 1000)
        setIntervalV(intervalId)
    }, [])
    useEffect(()=>{
        if(props.status==="CALCULATING"){
            setTime(0)
        }
    }, [props.status])

    const NoExperiment = () => {
        return (
            <div className={styles.Message}>
                <h1>Experiment doesn't exist</h1>
            </div>
        )
    }

    const ExperimentQueued = () => {
        return (
            <div className={styles.Message}>
                <h1>{`Experiment queued at position: ${props.queuePosition}`}</h1>
            </div>
        )
    }

    const ExperimentCalculating = () => {
        let minutes = ((time/60) | 0).toString().padStart(2, '0')
        let seconds = (time%60).toString().padStart(2, '0')
        return (
            <div className={styles.Message}>
                <h1>Calculating your experiment</h1>
                <p>{`Elapsed time: ${minutes}:${seconds}`}</p>
            </div>
        )
    }

    const ExperimentExpired = () => {
        return (
            <div className={styles.Message}>
                <h1>Experiment took too much time to calculate</h1>
                <p>Change configuration and try again</p>
            </div>
        )
    }

    const ExperimentError = () => {
        return (
            <div className={styles.Message}>
                <h1>
                    There was an unexpected error while calculating your experiment
                </h1>
                <p>
                    Please check experiment configuration and try again
                </p>
            </div>
        )
    }

    const [canceled, setCanceled] = useState(false);

    const cancel = () => {
        deleteGraphExperiment(props.id);
        setCanceled(true);
    };

    return (
    <div className={styles.Container}>
        {canceled ? <Navigate to={`/experiments/graph/`} /> : ""}
        {props.status === 'CALCULATING' && ExperimentCalculating()}
        {props.status === 'QUEUED' && ExperimentQueued()}
        {props.status === 'ERROR' && ExperimentError()}
        {props.status === 'EXPIRED' && ExperimentExpired()}
        {props.status === 'REMOVED' && NoExperiment()}
        {props.donePercent ? 
        <div className={classNames(styles.progressBar, "progressBarStyling")}>
            <div className="text">
                {(props.donePercent * 100).toFixed(0)}%
            </div>
            <div className="progress" style={{width: `${(props.donePercent * 100).toFixed(0)}%`}}>
            </div>
        </div>:<></>}
        {['CALCULATING', 'QUEUED'].includes(props.status) ? <button type="button" onClick={() => cancel()}>Cancel Experiment</button> : ""}
    </div>)
}