import React, { useCallback, useEffect, useState } from "react"
import { CartesianGrid, Label, Legend, Line, LineChart, ResponsiveContainer, Tooltip, XAxis, YAxis } from "recharts"
import { ComplexityParameters, GraphChartProps, GraphExperimentResultLabel } from "../Graph.interface"
import { GraphFormula } from "./GraphFormula"
import { CSVLink } from "react-csv";
import { addCalculatedComplexity, calculateComplexityParameters, getNameForGraphExperiment } from "../GraphServices"
import styles from "./GraphChart.module.scss"
import { useCurrentPng } from "recharts-to-png";
import FileSaver from "file-saver";


export const GraphChart = (props: GraphChartProps) => {

    const [logarithmScale, setLogarithmScale] = useState(false);

    const [data, setData] = useState([])

    const [headers, setHeaders] = useState([])

    const [getPng, { ref, isLoading }] = useCurrentPng();

    useEffect(() => {
        recalculateDataTime()
    }, [props, logarithmScale])

    const cropLabel = (label : string) : string => {
        return label.substring(label.indexOf(" : ", label.indexOf(" : ")+1)+2);
    };

    const activeLabels : GraphExperimentResultLabel[] = []
    
    props.labels.filter(label=> label.active).forEach(label => {
        let tmp : GraphExperimentResultLabel = {...label};
        if (props.dataLabel === "memoryOccupancyInBytes") {
            tmp.name = cropLabel(tmp.name);
        };
        if (props.dataLabel === "hamiltonCyclesCount") {
            if (!tmp.name.startsWith("All Hamiltonian Cycles")) {
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
        let densityAsX: boolean = props.experiments.results[0].measureByDensity
        headers.push({label: densityAsX === true ? "Density" : "Number Of Vertices", key: "N"})
        let i = 0
        if (densityAsX) {
            for (let i = 0; i < props.experiments.results.length; i++) {
                if (!names.includes(props.experiments.results[i].density))
                    names.push(props.experiments.results[i].density);
            }
        } else {
            for (let i = 0; i < props.experiments.results.length; i++) {
                if (!names.includes(props.experiments.results[i].numberOfVertices))
                    names.push(props.experiments.results[i].numberOfVertices);
            }
        }
        names = names.sort((a, b) => a - b)
        names.forEach(name => {
            let el: any = {}
            el["N"] = name.toString();
            let pp = true;
            props.experiments.results.filter(v => densityAsX === true ? v.density === name : v.numberOfVertices === name).filter(v => v.timeInMillis > 0).forEach(v => {
                const label = getNameForGraphExperiment(v, densityAsX)
                if (props.dataLabel === "timeInMillis" && (!logarithmScale || v.timeInMillis != 0))
                    el[label] = v.timeInMillis;
                if (props.dataLabel === "memoryOccupancyInBytes" && (!logarithmScale || v.graphResult.memoryOccupancyInBytes != 0))
                    el[cropLabel(label)] = v.graphResult.memoryOccupancyInBytes;
                if (props.dataLabel === "tableAccessCount" && (!logarithmScale || v.graphResult.tableAccessCount != 0))
                    el[label] = v.graphResult.tableAccessCount;
                if (props.dataLabel === "hamiltonCyclesCount" && (!logarithmScale || v.graphResult.hamiltonCyclesCount != 0))
                    el[label] = v.graphResult.hamiltonCyclesCount;
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
        if (props.dataLabel === "tableAccessCount")
            return "Table Access Count"
        if (props.dataLabel === "memoryOccupancyInBytes")
            return "Memory Used [B]"
        if (props.dataLabel === "hamiltonCyclesCount")
            return "Cycles Count"
    }

    const getDomainTab = () => {
        let tab: number[] = [];
        if (props.dataLabel === "timeInMillis")
            tab = props.experiments.results.filter(e=>e.timeInMillis > 0).map(e => e.timeInMillis)
        if (props.dataLabel === "tableAccessCount")
            tab = props.experiments.results.filter(e=>e.timeInMillis > 0).map(e => e.graphResult.tableAccessCount)
        if (props.dataLabel === "memoryOccupancyInBytes")
            tab = props.experiments.results.filter(e=>e.timeInMillis > 0).map(e => e.graphResult.memoryOccupancyInBytes)
        
        return tab.filter(e=>e != 0);
    }
    const getXAxisLabel = () =>{
        return props.experiments.results[0].measureByDensity === true ? "Density [%]" : "Number Of Vertices"
    }
    const getYAxisLabel = () =>{
        if (props.dataLabel === "timeInMillis")
            return "Time [ms]"
        if (props.dataLabel === "tableAccessCount")
            return "Table Access Count"
        if (props.dataLabel === "memoryOccupancyInBytes")
            return "Memory Used [B]"
        if (props.dataLabel === "hamiltonCyclesCount")
            return "Cycles Count"
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
                    <Tooltip labelFormatter={(n) => props.experiments.results[0].measureByDensity === true ? "Density: " + n+"%" : "Number Of Vertices: "+n} wrapperStyle={{zIndex: 1}} 
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