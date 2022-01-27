import React, { useCallback, useEffect, useState } from "react"
import { CartesianGrid, Label, Legend, Line, LineChart, ResponsiveContainer, Tooltip, XAxis, YAxis } from "recharts"
import { ComplexityParameters, SortingChartProps, SortingExperimentResultLabel } from "../Sorting.interface"
import { SortingFormula } from "./SortingFormula"
import { CSVLink } from "react-csv";
import { addCalculatedComplexity, calculateComplexityParameters, getNameForSortingExperiment } from "../SortingServices"
import styles from "./SortingChart.module.scss"
import { useCurrentPng } from "recharts-to-png";
import FileSaver from "file-saver";


export const SortingChart = (props: SortingChartProps) => {

    const [logarithmScale, setLogarithmScale] = useState(false);

    const [data, setData] = useState([])

    const [headers, setHeaders] = useState([])

    const [getPng, { ref, isLoading }] = useCurrentPng();

    useEffect(() => {
        recalculateDataTime()
    }, [props, logarithmScale])

    const activeLabels : SortingExperimentResultLabel[] = []

    props.labels.filter(label=> label.active).forEach(label => {
        let tmp : SortingExperimentResultLabel = {...label};
        if (props.dataLabel === "recursionSize") {
            if (!(tmp.name.startsWith("Merge") || tmp.name.startsWith("Quick"))) {
                return;
            }
        }
        let found : boolean = false;
        activeLabels.forEach(activeLabel => {
            if (tmp.name === activeLabel.name) {
                found = true;
            }
        });
        if (!found) {
            activeLabels.push(tmp);
        }
    });

    const recalculateDataTime = () => {
        const res: any[] = [];
        const headers: any[] = [];
        let names: number[] = [];
        for (let i = 0; i < props.experiments.results.length; i++) {
            if (!names.includes(props.experiments.results[i].n))
                names.push(props.experiments.results[i].n);
        }
        names = names.sort((a, b) => a - b)
        headers.push({label: "N", key: "N"})
        let i = 0
        names.forEach(name => {
            let el: any = {}
            el["N"] = name.toString();
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
            const infoLabel = props.series + " --> trend"
            setData(addCalculatedComplexity(res, infoLabel, complexityInfo))
            headers.push({label: infoLabel, key: infoLabel})
        }else{
            setData(res);
        }         
        setHeaders(headers);
    }

    const dataLabelToLabel = () =>{
        if (props.dataLabel === "timeInMillis")
            return "Time [ms]"
        if (props.dataLabel === "swapCount")
            return "Swap Count"
        if (props.dataLabel === "recursionSize")
            return "Recursion Level"
        if (props.dataLabel === "comparisonCount")
            return "Comparison Count"
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
    const getXAxisLabel = () =>{
        return "Instance Size"
    }
    const getYAxisLabel = () =>{
        if (props.dataLabel === "timeInMillis")
            return "Time [ms]"
        if (props.dataLabel === "swapCount")
            return "Swap Count"
        if (props.dataLabel === "recursionSize")
            return "Recursion Level"
        if (props.dataLabel === "comparisonCount")
            return "Comparison Count"
        return ""
    }

    const getDataKeys = () => {
        let res = activeLabels.map(lab => lab.name)
        return res;
    }

    const getLines = () => {
        return getDataKeys().map((element, index) => {
            return (
                <Line key={index} type="monotone" dot={false} dataKey={element} stroke={activeLabels[index].colorStr} />
            )
        })
    }

    const changeScaleType = () => {
        setLogarithmScale(!logarithmScale);
    }

    const prepareDataToCSV = () => {
        return data.map(line => {
            let toRes: any = {}
            for (const key in line){
                if(typeof line[key] === "number")
                    toRes[key] = line[key].toString().replace(".", ",")
                else
                    toRes[key] = line[key]
            }
            return toRes
        })
    }
    const handleDownload = useCallback(async () => {
        const png = await getPng();
        if (png) {
          FileSaver.saveAs(png, props.dataLabel + '.png');
        }
      }, [getPng]);
    return (
        <div className={styles.Container} style={activeLabels.length > 0 ? {} : {display: "none"}}>
            <div className={styles.Label}>{dataLabelToLabel()}</div>             
            <div className={styles.Chart}>
            <ResponsiveContainer width={"100%"} height={"100%"}>
                <LineChart data={data} ref={ref} margin={{top: 5, right:5, bottom: 20, left: 20}}>
                    <CartesianGrid strokeDasharray="3 3" />
                    <XAxis dataKey="N">
                        <Label style={{fill: "gray", fontWeight: "bold", textAnchor: 'middle'}} position="bottom">
                            {getXAxisLabel()}
                        </Label>
                    </XAxis>
                    <YAxis {...(logarithmScale === true ? {scale:"log", domain: [Math.min(...getDomainTab())/2, Math.max(...getDomainTab())*2]} : {})} >
                        <Label style={{fill: "gray", fontWeight: "bold", textAnchor: 'middle'}} angle={-90} position="left">
                            {getYAxisLabel()}
                        </Label>
                    </YAxis>
                    <Tooltip labelFormatter={(n) => 'Instance size: ' + n} wrapperStyle={{zIndex: 1}} 
                    contentStyle={{background: '#202020', border: 0, borderRadius: "8px", whiteSpace: "normal", maxWidth: "400px"}} allowEscapeViewBox={{x: false, y: true }} />
                    {getLines()}
                </LineChart>
            </ResponsiveContainer>
            </div>
            <div className={styles.ButtonsContainer}>
                <CSVLink data={prepareDataToCSV()} headers={headers} separator={";"} filename={`${props.dataLabel}.csv`}>Download CSV</CSVLink>
                <button onClick={handleDownload}>{isLoading ? 'Downloading...' : 'Download Chart'}</button>
                <button onClick={changeScaleType}>{!logarithmScale ? `Go to logarithmic scale` : `Go to standard scale`}</button>

            </div>
        </div>
    )
}