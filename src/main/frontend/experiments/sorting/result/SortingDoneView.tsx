import React, { useState, useEffect } from "react"
import { ComplexityParameters, SortingExperimentResultLabel, SortingExperimentsResult } from "../Sorting.interface"
import { calculateComplexityParameters, getNameForSortingExperiment } from "../SortingServices";
import { SortingChart } from "./SortingChart";

import styles from "./SortingDoneView.module.scss"
import { SortingFormula } from "./SortingFormula";

export const SortingDoneView = (props: SortingExperimentsResult) => {
    const colors = ["#8884d8", "#82ca9d", "#ffc658", "#FF8042", '#FFBB28', '#00C49F', '#0088FE']
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
    <div className={styles.Container}>
        <div className={styles.Header}>
            {problematicAlgorithms && <p>"Some experiments took too much time to calculate, showing partial results"</p>}
            <p>Choose data series for trend line</p>
            <select className={styles.SeriesSelect} value={choosedSeries} onChange={handleChangeSeries}>
                <option key="emptyOpt" value=""></option>
                {
                    series.filter(lab => !lab.name.endsWith(" --> trend")).map((label) => <option key={label.name} value={label.name}> {label.name} </option>)
                }
            </select>
            {complexityParams && <SortingFormula {...complexityParams}/>}
            {
                series.map((lab, index) => <p className={styles.Label} style={{color: lab.active? lab.colorStr : "#808080"}} onClick={()=>toggleSeries(index)}>{lab.name}</p>)
            }
        </div>
        <div className={styles.ChartsContainer}>
            <SortingChart experiments={props} labels={seriesWithTrend} dataLabel="timeInMillis" series={choosedSeries}/>
            <SortingChart experiments={props} labels={series} dataLabel="swapCount" />
            <SortingChart experiments={props} labels={series} dataLabel="comparisonCount" />
        </div>
    </div>
    )
}