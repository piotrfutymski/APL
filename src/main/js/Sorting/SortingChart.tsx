import { Button } from "@material-ui/core"
import React, { useEffect, useState } from "react"
import { CartesianGrid, Legend, Line, LineChart, Tooltip, XAxis, YAxis } from "recharts"
import { ButtonStylizer } from "../Utility/Forms.styled"
import { ComplexityParameters, SortingChartProps } from "./Sorting.interface"
import { addCalculatedComplexity, calculateComplexityParameters } from "./Sorting.utils"
import { SortingForumla } from "./SortingFormula"


export const SortingChart = (props: SortingChartProps) => {

    const colors = ["#8884d8", "#82ca9d", "#ffc658", "#FF8042", '#FFBB28', '#00C49F', '#0088FE']

    const [logarithmScale, setLogarithmScale] = useState(false);

    const [complexityParams, setComplexityParams] = useState<ComplexityParameters>(null);

    const [data, setData] = useState([])

    const [ problematicAlgorithms, setProblematicAlgorithms ] = useState(false)

    useEffect(() => {
        recalculateDataTime()
    }, [props, logarithmScale])

    useEffect(() => {
        setProblematicAlgorithms( props.experiments.results.length != props.experiments.results.filter(v => v.timeInMillis > 0).length)
    }, [props])

    const recalculateDataTime = () => {
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
            let pp = true;
            props.experiments.results.filter(v => v.n === name).filter(v => v.timeInMillis > 0).forEach(v => {
                if (props.dataLabel === "timeInMillis" && (!logarithmScale || v.timeInMillis != 0))
                    el[v.algorithmName + " : " + v.dataDistribution + " : " + v.maxValue.toString()] = v.timeInMillis;
                if (props.dataLabel === "swapCount" && (!logarithmScale || v.sortingResult.swapCount != 0))
                    el[v.algorithmName + " : " + v.dataDistribution + " : " + v.maxValue.toString()] = v.sortingResult.swapCount;
                if (props.dataLabel === "recursionSize" && (!logarithmScale || v.sortingResult.recursionSize != 0))
                    el[v.algorithmName + " : " + v.dataDistribution + " : " + v.maxValue.toString()] = v.sortingResult.recursionSize;
                if (props.dataLabel === "comparisonCount" && (!logarithmScale || v.sortingResult.comparisonCount != 0))
                    el[v.algorithmName + " : " + v.dataDistribution + " : " + v.maxValue.toString()] = v.sortingResult.comparisonCount;
                pp = false
            })
            if(!pp)
                res.push(el);
        });
        if(props.series){
            let complexityInfo = calculateComplexityParameters(res, props.series)
            setComplexityParams(complexityInfo)
            setData(addCalculatedComplexity(res, props.series, complexityInfo))
        }else{
            setComplexityParams(null)
            setData(res);
        }    
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
                res.push(v.algorithmName + " : " + v.dataDistribution + " : " + v.maxValue.toString())
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

    return (
        <>
            {complexityParams && <SortingForumla {...complexityParams}/>}
            <ButtonStylizer>
                <Button
                    onClick={changeScaleType}
                    variant="contained"
                >
                    {!logarithmScale ? `Go to logarithmic scale` : `Go to standard scale`}
                </Button>
            </ButtonStylizer>
            {problematicAlgorithms && "Thera are some algorithms that was too long to calculate, showing partial results"}
            <LineChart width={600} height={400} data={data} margin={{ top: 5, right: 20, bottom: 5, left: 0 }}>
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