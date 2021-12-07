import React from "react";
import { ComplexityParameters } from "../Sorting.interface";


export const SortingFormula = (props: ComplexityParameters) => {

    if(props.complexityType === "N^2")
    return (
        <>
            time(n) = {props.data[0].toExponential()} * N^2
        </>
    )
    else if(props.complexityType === "NlogN")
    return (
        <>
            time(n) = {props.data[0].toExponential()} * N log_2(N)
        </>
    )
    else if(props.complexityType === "N+K")
    return (
        <>
            time(n) = {props.data[0].toExponential()} * N + {props.data[1].toExponential()}
        </>
    )
}