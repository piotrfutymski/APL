import { Button } from "@material-ui/core"
import React from "react"
import { ButtonStylizer, ExperimentTextWrapper, ExperimentWrapper } from "../Utility/Forms.styled"
import { SortingExperiment, SortingExperimentListProps } from "./Sorting.interface"

export const SortingExperimentList = (props: SortingExperimentListProps) => {

    const deleteExperiment = (experiment: SortingExperiment) => {
        let experiments = props.experiments.filter((value, index, arr) => {
            return value !== experiment;
        });
        props.setExperiments(experiments)
    }

    const itemElement = (experiment: SortingExperiment, id: number) => {
        return (
            <ExperimentWrapper>
                <ExperimentTextWrapper>
                    <span>{`Algorithm:  ${experiment.algorithmName}`}</span>
                    <span>{`Data distribution:  ${experiment.dataDistribution}`}</span>
                    <span>{`Max value:  ${experiment.maxValue} ${props.asAPercent ? "N" : ""}`}</span>
                </ExperimentTextWrapper>
                <ButtonStylizer>
                    <Button
                        onClick={() => deleteExperiment(experiment)}
                        variant="contained"
                        color="secondary"
                    >
                        Delete
                    </Button>
                </ButtonStylizer>
            </ExperimentWrapper>
        )
    }


    return (
        <>
            {props.experiments.map((value, index) =>
                itemElement(value, index)
            )}
        </>
    )
}