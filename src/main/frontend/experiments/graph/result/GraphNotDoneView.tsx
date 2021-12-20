import React from "react"
import { GraphExperimentsResult } from "../Graph.interface"

import styles from './GraphNotDoneView.module.scss'

export const GraphNotDoneView = (props: GraphExperimentsResult) => {

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

    return (
    <div className={styles.Container}>
        {props.status === 'CALCULATING' && ExperimentCalculating()}
        {props.status === 'QUEUED' && ExperimentQueued()}
        {props.status === 'ERROR' && ExperimentError()}
        {props.status === 'EXPIRED' && ExperimentExpired()}
        {props.status === 'REMOVED' && NoExperiment()}
    </div>)
}