import React from "react"
import { SortingExperimentsResult } from "./Sorting.interface"
import { SortingChart } from "./SortingChart"

export const SortingDoneView = (props: SortingExperimentsResult) => {

    return (
    <>
        <SortingChart experiments={props} dataLabel="timeInMillis" />
        <SortingChart experiments={props} dataLabel="swapCount" />
        <SortingChart experiments={props} dataLabel="comparisonCount" />
    </>
    )
}