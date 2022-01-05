import React from "react";
import { ComplexityParameters } from "../Graph.interface";


export const GraphFormula = (props: ComplexityParameters) => {

    if(props.complexityType === "N^2")
    return (
        <>
            {props.density ? <>time(D)</> : <>time(V)</>} = {props.data[0].toExponential(3)} * {props.density ? <>D</> : <>V</>}^2
        </>
    )
    else if(props.complexityType === "N^3")
    return (
        <>
            {props.density ? <>time(D)</> : <>time(V)</>} = {props.data[0].toExponential(3)} * {props.density ? <>D</> : <>V</>}^3
        </>
    )
    else if(props.complexityType === "N+K")
    return (
        <>
            {props.density ? <>time(D)</> : <>time(V)</>} = {props.data[0].toExponential(3)} * {props.density ? <>D</> : <>V</>} + {props.data[1].toExponential(3)}
        </>
    )
    else if(props.complexityType === "N^4")
    return (
        <>
            {props.density ? <>time(D)</> : <>time(V)</>} = {props.data[0].toExponential(3)} * {props.density ? <>D</> : <>V</>}^4
        </>
    )
    else if(props.complexityType === "N!")
    return (
        <>
            {props.density ? <>time(D)</> : <>time(V)</>} = {props.data[0].toExponential(3)} * {props.density ? <>D</> : <>V</>}!
        </>
    )
}