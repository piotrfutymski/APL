import React from "react";
import { ComplexityParameters } from "./Sorting.interface";


export const SortingForumla = (props: ComplexityParameters) => {

    if(props.complexityType == "N^2")
    return (
        <>
            time(n) = {props.data[0]} * N^2
        </>
    )
    else if(props.complexityType == "NlogN")
    return (
        <>
            time(n) = {props.data[0]} * N log_2(N)
        </>
    )
}