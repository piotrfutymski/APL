import { Button } from "@material-ui/core"
import { scaleLog } from "d3-scale"
import React, { useState } from "react"
import { CartesianGrid, Legend, Line, LineChart, Tooltip, XAxis, YAxis } from "recharts"
import { ButtonStylizer } from "../Utility/Forms.styled"
import { SortingChartProps } from "./Sorting.interface"


export const SortingChart = (props: SortingChartProps) => {

    const colors = ["#8884d8", "#82ca9d", "#ffc658", "#FF8042", '#FFBB28', '#00C49F', '#0088FE']

    const [logarithmScale, setLogarithmScale] = useState(false);

    const getRecalculatedDataTime = () => {
        const res: any[] = [];
        let names: number[] = [];
        for (let i = 0; i < props.experiments.results.length; i++) {
            if (!names.includes(props.experiments.results[i].n))
                names.push(props.experiments.results[i].n);
        }
        names = names.sort((a, b) => a - b)
        names.forEach(name => {
            let el: any = {}
            el.name = name.toString();
            props.experiments.results.filter(v => v.n === name).forEach(v => {
                if (props.dataLabel === "timeInMillis" && (!logarithmScale || v.timeInMillis != 0))
                    el[v.algorithmName + " : " + v.dataDistribution + " : " + v.maxValue.toString()] = v.timeInMillis;
                if (props.dataLabel === "swapCount" && (!logarithmScale || v.sortingResult.swapCount != 0))
                    el[v.algorithmName + " : " + v.dataDistribution + " : " + v.maxValue.toString()] = v.sortingResult.swapCount;
                if (props.dataLabel === "recursionSize" && (!logarithmScale || v.sortingResult.recursionSize != 0))
                    el[v.algorithmName + " : " + v.dataDistribution + " : " + v.maxValue.toString()] = v.sortingResult.recursionSize;
                if (props.dataLabel === "comparisonCount" && (!logarithmScale || v.sortingResult.comparisonCount != 0))
                    el[v.algorithmName + " : " + v.dataDistribution + " : " + v.maxValue.toString()] = v.sortingResult.comparisonCount;
            })
            res.push(el);
        });
        return res;
    }

    const getDomainTab = () => {
        let tab: number[] = [];
        if (props.dataLabel === "timeInMillis")
            tab = props.experiments.results.map(e => e.timeInMillis)
        if (props.dataLabel === "swapCount")
            tab = props.experiments.results.map(e => e.sortingResult.swapCount)
        if (props.dataLabel === "recursionSize")
            tab = props.experiments.results.map(e => e.sortingResult.recursionSize)
        if (props.dataLabel === "comparisonCount")
            tab = props.experiments.results.map(e => e.sortingResult.comparisonCount)
        
        return tab.filter(e=>e != 0);
    }

    const getDataKeys = () => {
        const res: any[] = [];
        const names: number[] = [];
        for (let i = 0; i < props.experiments.results.length; i++) {
            if (!names.includes(props.experiments.results[i].n))
                names.push(props.experiments.results[i].n);
        }
        if (names.length > 0) {
            props.experiments.results.filter(v => v.n === names[0]).forEach(v => {
                res.push(v.algorithmName + " : " + v.dataDistribution + " : " + v.maxValue.toString())
            })
        }
        return res;
    }

    const getLines = () => {
        return getDataKeys().map((element, index) => {
            return (
                <Line type="monotone" dataKey={element} stroke={colors[index % colors.length]} />
            )
        })
    }

    const changeScaleType = () => {
        setLogarithmScale(!logarithmScale);
    }

    return (
        <>
            <ButtonStylizer>
                <Button
                    onClick={changeScaleType}
                    variant="contained"
                >
                    {!logarithmScale ? `Go to logarithmic scale` : `Go to standard scale`}
                </Button>
            </ButtonStylizer>
            <LineChart width={1200} height={800} data={getRecalculatedDataTime()} margin={{ top: 5, right: 20, bottom: 5, left: 0 }}>
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="name" />
                {logarithmScale ? <YAxis scale="log" domain={[Math.min(...getDomainTab())/2, Math.max(...getDomainTab())*2] }/> : <YAxis/>}
                <Tooltip />
                <Legend />
                {getLines()}
            </LineChart>
        </>
    )
}