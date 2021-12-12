import React, { useState, useEffect } from "react"
import { GraphExperimentsResult } from "../Graph.interface"
import { getNameForGraphExperiment } from "../GraphServices";
import { GraphChart } from "./GraphChart";

import styles from "./GraphDoneView.module.scss"

export const GraphDoneView = (props: GraphExperimentsResult) => {

    const [series, setSeries] = useState<string[]>([]);
    const [choosedSeries, setChoosedSeries] = useState<string>("")

    useEffect(() => {
        let stoSet: string[] = [""]
        if(props.results.length > 0){
            const name0 = props.results[0].numberOfVertices;
            props.results.filter(v => v.numberOfVertices === name0).forEach(v => {
                    stoSet.push(getNameForGraphExperiment(v))
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

        <GraphChart experiments={props} dataLabel="timeInMillis" series={choosedSeries}/>
        <GraphChart experiments={props} dataLabel="tableAccessCounter"/>
    </div>
    )
}