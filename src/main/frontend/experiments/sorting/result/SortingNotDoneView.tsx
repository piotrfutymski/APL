import React, { useState } from "react"
import { SortingExperimentsResult } from "../Sorting.interface"
import { deleteSortingExperiment } from "../SortingServices"
import { Navigate } from 'react-router-dom'


import styles from './SortingNotDoneView.module.scss'

export const SortingNotDoneView = (props: SortingExperimentsResult) => {

    const NoExperiment = () => {
        return (
            <div className={styles.Message}>
                Experiment doesn't exists
            </div>
        )
    }

    const ExperimentQueued = () => {
        return (
            <div className={styles.Message}>
                {`Experiment queued at position: ${props.queuePosition}`}
            </div>
        )
    }

    const ExperimentCalculating = () => {
        return (
            <div className={styles.Message}>
                Calculating your experiment
            </div>
        )
    }

    const ExperimentExpired = () => {
        return (
            <div className={styles.Message}>
                Experiment too long - check option for longer experiments or try to change parameters for shorter calculation time
            </div>
        )
    }

    const ExperimentError = () => {
        return (
            <div className={styles.Message}>
                {`There was an exception while calculating your experiment: ${props.errorCause}`}
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
        {props.donePercent ? <>Done in {(props.donePercent * 100).toFixed(0)}%</> : <></>}
        {['CALCULATING', 'QUEUED'].includes(props.status) ? <button type="button" onClick={() => cancel()}>Cancel Experiment</button> : ""}
    </div>)
}