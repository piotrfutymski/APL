import React, { useEffect, useState } from "react"
import { CartesianGrid, Legend, Line, LineChart, ResponsiveContainer, Tooltip, XAxis, YAxis } from "recharts"
import { ComplexityParameters, SortingChartProps } from "../Sorting.interface"
import { SortingFormula } from "./SortingFormula"
import { CSVLink } from "react-csv";
import { addCalculatedComplexity, calculateComplexityParameters, getNameForSortingExperiment } from "../SortingServices"
import styles from "./SortingChart.module.scss"


export const SortingChart = (props: SortingChartProps) => {

    const colors = ["#8884d8", "#82ca9d", "#ffc658", "#FF8042", '#FFBB28', '#00C49F', '#0088FE']

    const [logarithmScale, setLogarithmScale] = useState(false);

    const [complexityParams, setComplexityParams] = useState<ComplexityParameters>(null);

    const [data, setData] = useState([])

    const [headers, setHeaders] = useState([])

    const [ problematicAlgorithms, setProblematicAlgorithms ] = useState(false)

    useEffect(() => {
        recalculateDataTime()
    }, [props, logarithmScale])

    useEffect(() => {
        setProblematicAlgorithms( props.experiments.results.length != props.experiments.results.filter(v => v.timeInMillis > 0).length)
    }, [props])

    const recalculateDataTime = () => {
        const res: any[] = [];
        const headers: any[] = [];
        let names: number[] = [];
        for (let i = 0; i < props.experiments.results.length; i++) {
            if (!names.includes(props.experiments.results[i].n))
                names.push(props.experiments.results[i].n);
        }
        names = names.sort((a, b) => a - b)
        headers.push({label: "name", key: "N"})
        let i = 0
        names.forEach(name => {
            let el: any = {}
            el.name = name.toString();
            let pp = true;
            props.experiments.results.filter(v => v.n === name).filter(v => v.timeInMillis > 0).forEach(v => {
                const label = getNameForSortingExperiment(v)
                if (props.dataLabel === "timeInMillis" && (!logarithmScale || v.timeInMillis != 0))
                    el[label] = v.timeInMillis;
                if (props.dataLabel === "swapCount" && (!logarithmScale || v.sortingResult.swapCount != 0))
                    el[label] = v.sortingResult.swapCount;
                if (props.dataLabel === "recursionSize" && (!logarithmScale || v.sortingResult.recursionSize != 0))
                    el[label] = v.sortingResult.recursionSize;
                if (props.dataLabel === "comparisonCount" && (!logarithmScale || v.sortingResult.comparisonCount != 0))
                    el[label] = v.sortingResult.comparisonCount;
                if(i === 0){
                    headers.push({label: label, key: label})
                }
                pp = false
            })
            if(!pp)
                res.push(el);
            i++
        });
        if(props.series){
            let complexityInfo = calculateComplexityParameters(res, props.series)
            setComplexityParams(complexityInfo)
            const infoLabel = props.series + " --> trend"
            setData(addCalculatedComplexity(res, infoLabel, complexityInfo))
            headers.push({label: infoLabel, key: infoLabel})
        }else{
            setComplexityParams(null)
            setData(res);
        }
        setHeaders(headers);
    }

    const getDomainTab = () => {
        let tab: number[] = [];
        if (props.dataLabel === "timeInMillis")
            tab = props.experiments.results.filter(e=>e.timeInMillis > 0).map(e => e.timeInMillis)
        if (props.dataLabel === "swapCount")
            tab = props.experiments.results.filter(e=>e.timeInMillis > 0).map(e => e.sortingResult.swapCount)
        if (props.dataLabel === "recursionSize")
            tab = props.experiments.results.filter(e=>e.timeInMillis > 0).map(e => e.sortingResult.recursionSize)
        if (props.dataLabel === "comparisonCount")
            tab = props.experiments.results.filter(e=>e.timeInMillis > 0).map(e => e.sortingResult.comparisonCount)
        
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
                res.push(getNameForSortingExperiment(v))
            })

            if(props.series){
                res.push(props.series + " --> trend")
            }
        }
        return res;
    }

    const getLines = () => {
        return getDataKeys().map((element, index, array) => {
            if(index != array.length - 1 || !props.series){
                return (
                    <Line type="monotone" dataKey={element} stroke={colors[index % colors.length]} />
                )
            }else{
                return (
                    <Line type="monotone" dataKey={element} stroke="#ff0000" />
                )
            }
            
        })
    }

    const changeScaleType = () => {
        setLogarithmScale(!logarithmScale);
    }

    const prepareDataToCSV = () => {
        return data.map(line => {
            let toRes: any = {}
            for (const key in line){
                if(typeof line[key] === "string")
                    toRes[key] = line[key].replace(".", ",")
                if(typeof line[key] === "number")
                    toRes[key] = line[key].toString().replace(".", ",")
               
            }
            return toRes
        })
    } 

    return (
        <div className={styles.Container}>
            {problematicAlgorithms && "There were some algorithms that was too long to calculate, showing partial results"}
            <div className={styles.Chart}>
            {complexityParams && <SortingFormula {...complexityParams}/>}
            {problematicAlgorithms && "There were some algorithms that was too long to calculate, showing partial results"}
            <ResponsiveContainer width={600} height={600} debounce={1}>
                <LineChart data={data}>
                    <CartesianGrid strokeDasharray="3 3" />
                    <XAxis dataKey="name" />
                    {logarithmScale ? <YAxis scale="log" domain={[Math.min(...getDomainTab())/2, Math.max(...getDomainTab())*2] }/> : <YAxis/>}
                    <Tooltip />
                    <Legend/>
                    {getLines()}
                </LineChart>
            </ResponsiveContainer>
            </div>
            <div className={styles.ButtonsContainer}>
                <button onClick={changeScaleType}>{!logarithmScale ? `Go to logarithmic scale` : `Go to standard scale`}</button>
                <CSVLink data={prepareDataToCSV()} headers={headers} separator={";"} filename={`${props.dataLabel}.csv`}>Download CSV</CSVLink>
            </div>
        </div>
    )
}