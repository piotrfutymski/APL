import React from "react"
import { CartesianGrid, Legend, Line, LineChart, Tooltip, XAxis, YAxis } from "recharts"
import { SortingExperimentsResult } from "./Sorting.interface"

export const SortingDoneView = (props: SortingExperimentsResult) => {

    const colors = ["#8884d8", "#82ca9d", "#ffc658", "#FF8042", '#FFBB28', '#00C49F', '#0088FE']

    const getRecalculatedDataTime = (dataLabel: string) => {
        const res: any[] = [];
        let names: number[] = [];
        for (let i = 0; i < props.results.length; i++) {
            if (!names.includes(props.results[i].n))
                names.push(props.results[i].n);
        }
        names = names.sort((a, b) => a - b)
        names.forEach(name => {
            let el: any = {}
            el.name = name.toString();
            props.results.filter(v => v.n === name).forEach(v => {
                if (dataLabel === "timeInMillis")
                    el[v.algorithmName + " : " + v.dataDistribution + " : " + v.maxValue.toString()] = v.timeInMillis;
                if (dataLabel === "swapCount")
                    el[v.algorithmName + " : " + v.dataDistribution + " : " + v.maxValue.toString()] = v.sortingResult.swapCount;
                if (dataLabel === "recursionSize")
                    el[v.algorithmName + " : " + v.dataDistribution + " : " + v.maxValue.toString()] = v.sortingResult.recursionSize;
                if (dataLabel === "comparisonCount")
                    el[v.algorithmName + " : " + v.dataDistribution + " : " + v.maxValue.toString()] = v.sortingResult.comparisonCount;
            })
            res.push(el);
        });
        return res;
    }

    const getDataKeys = () => {
        const res: any[] = [];
        const names: number[] = [];
        for (let i = 0; i < props.results.length; i++) {
            if (!names.includes(props.results[i].n))
                names.push(props.results[i].n);
        }
        if (names.length > 0) {
            props.results.filter(v => v.n === names[0]).forEach(v => {
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


    return (<>
        <LineChart width={1200} height={800} data={getRecalculatedDataTime("timeInMillis")} margin={{ top: 5, right: 20, bottom: 5, left: 0 }}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="name" />
            <YAxis />
            <Tooltip />
            <Legend />
            {getLines()}
        </LineChart>
        <LineChart width={1200} height={800} data={getRecalculatedDataTime("swapCount")} margin={{ top: 5, right: 20, bottom: 5, left: 0 }}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="name" />
            <YAxis />
            <Tooltip />
            <Legend />
            {getLines()}
        </LineChart>
        <LineChart width={1200} height={800} data={getRecalculatedDataTime("comparisonCount")} margin={{ top: 5, right: 20, bottom: 5, left: 0 }}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="name" />
            <YAxis />
            <Tooltip />
            <Legend />
            {getLines()}
        </LineChart>
    </>
    )
}