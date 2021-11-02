import { useEffect, useState } from "react";
import { getSortingExperiments } from "../Utility/Requests";
import { SortingExperimentsResult, SortingResultViewProps } from "./Sorting.interface";
import React from "react"
import { SortingNotDoneView } from "./SortingNotDoneView";
import { SortingDoneView } from "./SortingDoneView";


export const SortingResultView = (props: SortingResultViewProps) => {

    const [experimentsResults, setExperimentsResults] = useState<SortingExperimentsResult>(null)
    const [intervalV, setIntervalV] = useState<NodeJS.Timer>();


    useEffect(() => {
        setIntervalV(setInterval(getExperimentsResult, 500))
        return () => {
            clearInterval(intervalV)
        }
    }, [props.experimentId])

    useEffect(() => {
        if(experimentsResults!==null ){
            if(experimentsResults.status !== 'QUEUED' && experimentsResults.status !== 'CALCULATING')
                clearInterval(intervalV)
        }
    }, [experimentsResults])

    const getExperimentsResult = () => {
        if(experimentsResults===null || experimentsResults.status === 'QUEUED' || experimentsResults.status === 'CALCULATING')
            getSortingExperiments(props.experimentId, setExperimentsResults)
    }

    return (
        <>
            {
                experimentsResults === null ? <> Loading...</> : <> {experimentsResults.status !== "DONE" ? <SortingNotDoneView
                    queuePosition={experimentsResults.queuePosition}
                    errorCause={experimentsResults.errorCause}
                    status={experimentsResults.status}
                    results={experimentsResults.results}
                /> : <SortingDoneView
                    queuePosition={experimentsResults.queuePosition}
                    errorCause={experimentsResults.errorCause}
                    status={experimentsResults.status}
                    results={experimentsResults.results}
                />} </>
            }
        </>
    )
}