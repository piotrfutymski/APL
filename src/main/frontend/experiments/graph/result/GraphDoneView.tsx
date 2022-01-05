import React, { useState, useEffect } from "react"
import { ComplexityParameters, GraphExperiment, GraphExperimentResultLabel, GraphExperimentsResult } from "../Graph.interface"
import { calculateComplexityParameters, getNameForGraphExperiment } from "../GraphServices";
import { GraphChart } from "./GraphChart";

import styles from "./GraphDoneView.module.scss"
import { GraphFormula } from "./GraphFormula";

export const GraphDoneView = (props: GraphExperimentsResult) => {
    const colors = ['#3cb44b', '#ffe119', '#4363d8', '#f58231', '#911eb4', 
    '#42d4f4', '#f032e6', '#bfef45', '#fabed4', '#469990', '#dcbeff', '#9A6324', 
    '#fffac8', '#800000', '#aaffc3', '#808000', '#ffd8b1', '#000075', '#a9a9a9']
    const [series, setSeries] = useState<GraphExperimentResultLabel[]>([]);
    const [choosedSeries, setChoosedSeries] = useState<string>("")
    const [complexityParams, setComplexityParams] = useState<ComplexityParameters>(null);

    const [ problematicAlgorithms, setProblematicAlgorithms ] = useState(false)

    useEffect(() => {
        if(choosedSeries !== "")
        {
            const res: any[] = [];
        const headers: any[] = [];
        let names: number[] = [];
        let densityAsX: boolean = props.results[0].measureByDensity;
        headers.push({label: densityAsX === true ? "Density" : "Number Of Vertices", key: "N"})
        let i = 0
        if (densityAsX) {
            for (let i = 0; i < props.results.length; i++) {
                if (!names.includes(props.results[i].density))
                    names.push(props.results[i].density);
            }
        } else {
            for (let i = 0; i < props.results.length; i++) {
                if (!names.includes(props.results[i].numberOfVertices))
                    names.push(props.results[i].numberOfVertices);
            }
        }
        names = names.sort((a, b) => a - b)
        names.forEach(name => {
            let el: any = {}
            el["N"] = name.toString();
            let pp = true;
            props.results.filter(v => densityAsX === true ? v.density === name : v.numberOfVertices === name).filter(v => v.timeInMillis > 0).forEach(v => {
                const label = getNameForGraphExperiment(v, densityAsX)
                el[label] = v.timeInMillis;
                if(i === 0){
                    headers.push({label: label, key: label})
                }
                pp = false
            })
            if(!pp)
                res.push(el);
            i++
        });
            let complexityInfo = calculateComplexityParameters(res, choosedSeries)
            setComplexityParams({...complexityInfo, density: densityAsX})
        }else{
            setComplexityParams(null)
        }
    }, [choosedSeries])

    useEffect(() => {
        setProblematicAlgorithms( props.results.length != props.results.filter(v => v.timeInMillis > 0).length)
    }, [props])

    useEffect(() => {
        let names: number[] = [];
        let densityAsX: boolean = props.results[0].measureByDensity;
        let stoSet: GraphExperimentResultLabel[] = []
        if(props.results.length > 0){
            let tmp : GraphExperiment[] = [];
            props.results.forEach((v : GraphExperiment) => {
                let found : boolean = false;
                tmp.forEach((l : GraphExperiment) => {
                    if (getNameForGraphExperiment(l, densityAsX) === getNameForGraphExperiment(v, densityAsX)) {
                        found = true;
                    }
                });
                if (!found) {
                    tmp.push(v);
                }
            });
            tmp.forEach((v, index) => {
                    stoSet.push({name: getNameForGraphExperiment(v, densityAsX), active: true, colorStr: colors[index%colors.length]})
            });
        }
        setSeries(stoSet)
    }, [props])

    const handleChangeSeries = (event: any) => {
        setChoosedSeries(event.target.value)
    }

    const toggleSeries = (index: number) =>{
        let tmp = [...series]
        tmp[index].active=!tmp[index].active
        setSeries(tmp)
    }
    let seriesWithTrend = [...series]
    choosedSeries && seriesWithTrend.push({name: choosedSeries + " --> trend", active: true, colorStr: "#ff0000"})
    return (
    <>
        <div className={styles.Header}>
            <div className={styles.TrendContainer}>
                <p>Choose data series for trend line</p>
                <select className={styles.SeriesSelect} value={choosedSeries} onChange={handleChangeSeries}>
                    <option key="emptyOpt" value=""></option>
                    {
                        series.filter(lab => !lab.name.endsWith(" --> trend")).map((label, index) => <option id={"opt_" + index.toString()} key={index} value={label.name}> {label.name} </option>)
                    }
                </select>
                {complexityParams && <GraphFormula {...complexityParams}/>}
            </div>
            <div className={styles.LabelContainer}>
                {
                    series.map((lab, index) => <p key={index} className={styles.Label} style={{color: lab.active? lab.colorStr : "#808080"}} onClick={()=>toggleSeries(index)}>{lab.name}</p>)
                }
            </div>
        </div>
        {problematicAlgorithms && <p className={styles.ProblematicInfo}>Some experiments took too much time to calculate, showing partial results</p>}
        <div className={styles.ChartsContainer}>
            <GraphChart experiments={props} labels={seriesWithTrend} dataLabel="timeInMillis" series={choosedSeries}/>
            <GraphChart experiments={props} labels={series} dataLabel="memoryOccupancyInBytes" />
            <GraphChart experiments={props} labels={series} dataLabel="tableAccessCount" />
            <GraphChart experiments={props} labels={series} dataLabel="hamiltonCyclesCount" />
        </div>
    </>
    )
}