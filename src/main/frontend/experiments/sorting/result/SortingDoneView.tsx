import React, { useState, useEffect } from "react"
import { ComplexityParameters, SortingExperimentResultLabel, SortingExperimentsResult } from "../Sorting.interface"
import { calculateComplexityParameters, getNameForSortingExperiment } from "../SortingServices";
import { SortingChart } from "./SortingChart";

import styles from "./SortingDoneView.module.scss"
import { SortingFormula } from "./SortingFormula";

export const SortingDoneView = (props: SortingExperimentsResult) => {
    const colors = ['#3cb44b', '#ffe119', '#4363d8', '#f58231', '#911eb4', 
    '#42d4f4', '#f032e6', '#bfef45', '#fabed4', '#469990', '#dcbeff', '#9A6324', 
    '#fffac8', '#800000', '#aaffc3', '#808000', '#ffd8b1', '#000075', '#a9a9a9']
    const [series, setSeries] = useState<SortingExperimentResultLabel[]>([]);
    const [choosedSeries, setChoosedSeries] = useState<string>("")
    const [complexityParams, setComplexityParams] = useState<ComplexityParameters>(null);

    const [ problematicAlgorithms, setProblematicAlgorithms ] = useState(false)

    useEffect(() => {
        if(choosedSeries !== "")
        {
            const res: any[] = [];
            const headers: any[] = [];
            let names: number[] = [];
            for (let i = 0; i < props.results.length; i++) {
                if (!names.includes(props.results[i].n))
                    names.push(props.results[i].n);
            }
            names = names.sort((a, b) => a - b)
            headers.push({label: "N", key: "N"})
            let i = 0
            names.forEach(name => {
                let el: any = {}
                el.N = name.toString();
                let pp = true;
                props.results.filter(v => v.n === name).filter(v => v.timeInMillis > 0).forEach(v => {
                    const label = getNameForSortingExperiment(v)
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
            setComplexityParams(complexityInfo)
        }else{
            setComplexityParams(null)
        }
    }, [choosedSeries])

    useEffect(() => {
        setProblematicAlgorithms( props.results.length != props.results.filter(v => v.timeInMillis > 0).length)
    }, [props])

    useEffect(() => {
        let stoSet: SortingExperimentResultLabel[] = []
        if(props.results.length > 0){
            const name0 = props.results[0].n;
            props.results.filter(v => v.n === name0).forEach((v, index) => {
                    stoSet.push({name: getNameForSortingExperiment(v), active: true, colorStr: colors[index%colors.length]})
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
                        series.filter(lab => !lab.name.endsWith(" --> trend")).map((label, index) => <option key={index} value={label.name}> {label.name} </option>)
                    }
                </select>
                {complexityParams && <SortingFormula {...complexityParams}/>}
            </div>
            <div className={styles.LabelContainer}>
                {
                    series.map((lab, index) => <p key={index} className={styles.Label} style={{color: lab.active? lab.colorStr : "#808080"}} onClick={()=>toggleSeries(index)}>{lab.name}</p>)
                }
            </div>
        </div>
        {problematicAlgorithms && <p className={styles.ProblematicInfo}>Some experiments took too much time to calculate, showing partial results</p>}
        <div className={styles.ChartsContainer}>
            <SortingChart experiments={props} labels={seriesWithTrend} dataLabel="timeInMillis" series={choosedSeries}/>
            <SortingChart experiments={props} labels={series} dataLabel="swapCount" />
            <SortingChart experiments={props} labels={series} dataLabel="comparisonCount" />
        </div>
    </>
    )
}