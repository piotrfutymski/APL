import React from "react";
import { ComplexityParameters } from "../Graph.interface";


export const GraphFormula = (props: ComplexityParameters) => {

    if(props.complexityType === "N^2")
    return (
        <>
            time(V) = {props.data[0].toExponential()} * V^2
        </>
    )
    else if(props.complexityType === "N^3")
    return (
        <>
            time(V) = {props.data[0].toExponential()} * V^3
        </>
    )
    else if(props.complexityType === "N+K")
    return (
        <>
            time(V) = {props.data[0].toExponential()} * V + {props.data[1].toExponential()}
        </>
    )
}