import React, { useState } from "react"
import { SortingExperimentsResult } from "../Sorting.interface"
import { deleteSortingExperiment } from "../SortingServices"
import { Navigate } from 'react-router-dom'


import styles from './SortingNotDoneView.module.scss'
import classNames from "classnames"

export const SortingNotDoneView = (props: SortingExperimentsResult) => {

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
        return (
            <div className={styles.Message}>
                <h1>Calculating your experiment</h1>
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
        deleteSortingExperiment(props.id);
        setCanceled(true);
    };

    return (
    <div className={styles.Container}>
        {canceled ? <Navigate to={`/experiments/sorting/`} /> : ""}
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