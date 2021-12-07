import React, { useState, useEffect } from "react"
import { SortingExperimentsResult } from "../Sorting.interface"
import { getNameForSortingExperiment } from "../SortingServices";
import { SortingChart } from "./SortingChart";

import styles from "./SortingDoneView.module.scss"

export const SortingDoneView = (props: SortingExperimentsResult) => {

    const [series, setSeries] = useState<string[]>([]);
    const [choosedSeries, setChoosedSeries] = useState<string>("")

    useEffect(() => {
        let stoSet: string[] = [""]
        if(props.results.length > 0){
            const name0 = props.results[0].n;
            props.results.filter(v => v.n === name0).forEach(v => {
                    stoSet.push(getNameForSortingExperiment(v))
            });
        }
        setSeries(stoSet)
    }, [props])

    const handleChangeSeries = (event: any) => {
        setChoosedSeries(event.target.value)
    }

    return (
    <div className={styles.Container}>
        <label>Choose data series for more details</label>
        <select className={styles.SeriesSelect} value={choosedSeries} onChange={handleChangeSeries}>
            {
                series.map((name) => <option key={name} value={name}> {name} </option>)
            }
        </select>

        <SortingChart experiments={props} dataLabel="timeInMillis" series={choosedSeries}/>
        <SortingChart experiments={props} dataLabel="swapCount"/>
        <SortingChart experiments={props} dataLabel="comparisonCount"/>
    </div>
    )
}