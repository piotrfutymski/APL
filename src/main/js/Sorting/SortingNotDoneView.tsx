import React from "react"
import { SortingExperimentsResult } from "./Sorting.interface"
import { ResultViewTextContainer } from "./Sorting.styled"

export const SortingNotDoneView = (props: SortingExperimentsResult) => {

    const NoExperiment = () => {
        return (
            <ResultViewTextContainer>
                Experiment doesn't exists
            </ResultViewTextContainer>
        )
    }

    const ExperimentQueued = () => {
        return (
            <ResultViewTextContainer>
                {`Experiment queued at position: ${props.queuePosition}`}
            </ResultViewTextContainer>
        )
    }

    const ExperimentCalculating = () => {
        return (
            <ResultViewTextContainer>
                Calculating your experiment
            </ResultViewTextContainer>
        )
    }

    const ExperimentExpired = () => {
        return (
            <ResultViewTextContainer>
                Experiment too long - check option for longer experiments or try to change parameters for shorter calculation time
            </ResultViewTextContainer>
        )
    }

    const ExperimentError = () => {
        return (
            <ResultViewTextContainer>
                {`There was an exception while calculating your experiment: ${props.errorCause}`}
            </ResultViewTextContainer>
        )
    }

    return (<>
        {props.status === 'CALCULATING' && ExperimentCalculating()}
        {props.status === 'QUEUED' && ExperimentQueued()}
        {props.status === 'ERROR' && ExperimentError()}
        {props.status === 'EXPIRED' && ExperimentExpired()}
        {props.status === 'REMOVED' && NoExperiment()}
    </>)
}