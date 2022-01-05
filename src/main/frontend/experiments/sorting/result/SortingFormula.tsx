import React from "react";
import { ComplexityParameters } from "../Sorting.interface";


export const SortingFormula = (props: ComplexityParameters) => {

    if(props.complexityType === "N^2")
    return (
        <>
            time(N) = {props.data[0].toExponential(3)} * N^2
        </>
    )
    else if(props.complexityType === "NlogN")
    return (
        <>
            time(N) = {props.data[0].toExponential(3)} * N log_2(N)
        </>
    )
    else if(props.complexityType === "N+K")
    return (
        <>
            time(N) = {props.data[0].toExponential(3)} * N + {props.data[1].toExponential(3)}
        </>
    )
}