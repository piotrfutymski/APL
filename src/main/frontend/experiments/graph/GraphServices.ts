import axios, { AxiosError, AxiosResponse } from "axios";
import { ComplexityType } from "../graph/Graph.interface";
import { ComplexityParameters, paramInfo, GraphConfig, GraphConfigCheck, GraphExperimentCheck, GraphExperiment, GraphExperimentsResult } from "./Graph.interface";

export const getParamInfos = (experiment: GraphExperiment): paramInfo[]=>{
    let res: paramInfo[] = []
    if(experiment.algorithmName === "Topological Sort"){
        res.push({
            algorithm: "Topological Sort",
            name: "checkForCycles",
            isSelect: true,
            options: ["True", "False"]
        })
    }
    return res
}

export const reducePossibleGenerators = (algorithmName: string, generators: string[]): string[] => {
    let res=[...generators]
    if(algorithmName === "Topological Sort"){
        res = ["Connected Directed Graph", "Euler Directed Graph"];
    } else if(algorithmName === "All Hamiltonian Cycles" || algorithmName === "Hamiltonian Cycle" || algorithmName === "Dijkstra Algorithm"){
        res = ["Connected Directed Graph", 
        "Euler Directed Graph", "Connected Undirected Graph", "Euler Undirected Graph"];
    } else if(algorithmName === "Prims Algorithm" || algorithmName === "Kruskal Algorithm") {
        res = ["Connected Undirected Graph", "Euler Undirected Graph"];
    } else if(algorithmName === "Euler Cycle Finding Algorithm"){
        res = ["Euler Directed Graph", 
        "Euler Undirected Graph"];
    }
    return res
}

export const reducePossibleRepresentations = (algorithmName: string, dataGenerator: string, representations: string[]): string[] => {
    let res=[...representations]
    if(algorithmName === "Dijkstra Algorithm" && (dataGenerator === "Connected Directed Graph" || dataGenerator === "Euler Directed Graph")){
        res = ["Weighted Adjacency Matrix Directed", "Weighted Incidence Matrix Directed", "Weighted List Of Arcs", "Weighted List Of Predecessors Directed", "Weighted List Of Successors Directed"]
    } else if(algorithmName === "Dijkstra Algorithm" && (dataGenerator === "Connected Undirected Graph" || dataGenerator === "Euler Undirected Graph")){
        res = ["Weighted Adjacency Matrix Undirected", "Weighted Incidence Matrix Undirected", "Weighted List Of Edges", "Weighted List Of Incident Undirected"]
    } else if(algorithmName === "Prims Algorithm" || algorithmName === "Kruskal Algorithm"){
        res = ["Weighted Adjacency Matrix Undirected", "Weighted Incidence Matrix Undirected", "Weighted List Of Edges", "Weighted List Of Incident Undirected"]
    } else if (algorithmName === "Get All Edges" 
              || algorithmName === "Get All Relations"
              || algorithmName === "Get First Predecessor"
              || algorithmName === "Get First Successor"
              || algorithmName === "Get Memory Occupancy"
              || algorithmName === "Get Non Incident"
              || algorithmName === "Get Successors"
              || algorithmName === "Get Predecessors") {
        if (dataGenerator === "Connected Directed Graph" || dataGenerator === "Directed Graph" || dataGenerator === "Euler Directed Graph") {
            res = ["Adjacency Matrix Directed", "Incidence Matrix Directed", "List Of Arcs", "List Of Predecessors Directed", 
                   "List Of Successors Directed", "Weighted Adjacency Matrix Directed", "Weighted Incidence Matrix Directed", 
                   "Weighted List Of Arcs", "Weighted List Of Predecessors Directed", "Weighted List Of Successors Directed"];
        }
        else if (dataGenerator === "Connected Undirected Graph" || dataGenerator === "Undirected Graph" || dataGenerator === "Euler Undirected Graph") {
            res = ["Adjacency Matrix Undirected", "Incidence Matrix Undirected", "List Of Edges", "List Of Incident Undirected", 
                   "Weighted Adjacency Matrix Undirected", "Weighted Incidence Matrix Undirected", "Weighted List Of Edges", 
                   "Weighted List Of Incident Undirected"];
        }
        return res;
    } else if(dataGenerator === "Connected Directed Graph" || dataGenerator === "Directed Graph" || dataGenerator === "Euler Directed Graph"){
        res = ["Adjacency Matrix Directed", "Incidence Matrix Directed", "List Of Arcs", "List Of Predecessors Directed", "List Of Successors Directed"];
    } else if(dataGenerator === "Connected Undirected Graph" || dataGenerator === "Undirected Graph" || dataGenerator === "Euler Undirected Graph"){
        res = ["Adjacency Matrix Undirected", "Incidence Matrix Undirected", "List Of Edges", "List Of Incident Undirected"];
    } 
    return res
}


export const checkConfig = (config: GraphConfig, experiments: GraphExperiment[]): GraphConfigCheck => {
    let result:GraphConfigCheck = {warningFlag: false, errorFlag: false,  measureSeries: {status: "CORRECT"}, densityOrVertices: {status: "CORRECT"}}
    let foundEuler=false
    let foundHamiltonian=false
        experiments.forEach(e=>{
            if(e.dataGenerator.includes("Euler"))
                foundEuler=true
            if(e.algorithmName.includes("Hamiltonian"))
                foundHamiltonian=true
        })
    if(config.measureSeries <= 0){
        result.measureSeries.status="ERROR"
        result.measureSeries.msg="Number of measure series must be a number greater than 0"
        result.errorFlag=true
    }
    else if(config.measureSeries > 200){
        result.measureSeries.status="ERROR"
        result.measureSeries.msg="Number of measure series can not be greater than 200"
        result.errorFlag=true
    }
    else if (config.densityOrVertices < config.measureSeries) 
    {
        if(config.measureByDensity === true){
            result.measureSeries.status="ERROR"
            result.measureSeries.msg="Number of measure series cannot be greater than maximum density"
            result.errorFlag=true
        }else{
            result.measureSeries.status="ERROR"
            result.measureSeries.msg="Number of measure series cannot be greater than maximum number of vertices"
            result.errorFlag=true
        }
    }

    if(config.measureByDensity === true){
        if(config.densityOrVertices <= 0)
        {
            result.densityOrVertices.status="ERROR"
            result.densityOrVertices.msg="Maximum density must be a number greater than 0"
            result.errorFlag=true
        }
        else if(config.densityOrVertices > 100)
        {
            result.densityOrVertices.status="ERROR"
            result.densityOrVertices.msg="Maximum density must be a number below 100"
            result.errorFlag=true
        }
        else if(foundEuler && config.densityOrVertices > 80){
            result.densityOrVertices.status="ERROR"
            result.densityOrVertices.msg="Maximum density must be less than 80 for Euler generated graphs"
            result.errorFlag=true
        }
    } else {
        if(config.densityOrVertices <= 0)
        {
            result.densityOrVertices.status="ERROR"
            result.densityOrVertices.msg="Number of vertices must be a number greater than 0"
            result.errorFlag=true
        } 
        else if (foundHamiltonian && config.densityOrVertices > 15) {
            result.densityOrVertices.status="ERROR"
            result.densityOrVertices.msg="For Hamiltonian cycles algorithm number of vertices can not be greater than 15"
            result.errorFlag=true
        }
        else if (config.densityOrVertices > 1000)
        {
            result.densityOrVertices.status="ERROR"
            result.densityOrVertices.msg="Number of vertices can not be greater than 1000"
            result.errorFlag=true
        }
        else if (config.densityOrVertices / config.measureSeries < 6 && foundEuler)
        {
            result.densityOrVertices.status="ERROR"
            result.densityOrVertices.msg="Too low vertices for generating Euler graph"
            result.errorFlag=true
        }
        else if (config.densityOrVertices > 500)
        {
            result.densityOrVertices.status="WARNING"
            result.densityOrVertices.msg="Number of vertices should not be greater than 500"
            result.warningFlag=true
        }
    }
    return result
}

export const checkExperiment = (experiment: GraphExperiment, config: GraphConfig): GraphExperimentCheck => {
    let result:GraphExperimentCheck = { warningFlag: false, errorFlag: false, numberOfVertices: {status: "CORRECT"}, density: {status: "CORRECT"}}
    if(config.measureByDensity === true){
        if(experiment.numberOfVertices <= 0)
        {
            result.numberOfVertices.status="ERROR"
            result.numberOfVertices.msg="Number of vertices must be a number greater than 0"
            result.errorFlag=true
        } 
        else if (experiment.algorithmName.includes("Hamiltonian") && experiment.numberOfVertices > 15) {
            result.numberOfVertices.status="ERROR"
            result.numberOfVertices.msg="Number of vertices can not be greater than 15 for algorithm of N * N! complexity"
            result.errorFlag=true
        }
        else if (experiment.numberOfVertices > 1000)
        {
            result.numberOfVertices.status="ERROR"
            result.numberOfVertices.msg="Number of vertices can not be greater than 1000"
            result.errorFlag=true
        }
        else if (experiment.numberOfVertices < 6 && experiment.dataGenerator.includes("Euler"))
        {
            result.numberOfVertices.status="ERROR"
            result.numberOfVertices.msg="Too low vertices for generating Euler graph"
            result.errorFlag=true
        }
        else if (experiment.numberOfVertices > 500)
        {
            result.numberOfVertices.status="WARNING"
            result.numberOfVertices.msg="Number of vertices should not be greater than 500"
            result.warningFlag=true
        }
    } else {
        if(experiment.density <= 0)
        {
            result.density.status="ERROR"
            result.density.msg="Density must be a number greater than 0"
            result.errorFlag=true
        }
        else if(experiment.density > 100)
        {
            result.density.status="ERROR"
            result.density.msg="Density must be a number below 100"
            result.errorFlag=true
        }
        else if(experiment.dataGenerator.includes("Euler") && experiment.density > 80)
        {
            result.density.status="ERROR"
            result.density.msg="Density must be less than 80 for Euler generated graphs"
            result.errorFlag=true
        }
    }
    return result
}


export const submitExperiments = (experiments: GraphExperiment[], config: GraphConfig, finite: boolean, onResponse: (arg0: string) => void) => {
    let res: any[] = []
    let labels: string[] = []
    experiments.forEach(element => {
        let label = getNameForGraphExperiment(element, config.measureByDensity)
        if(!labels.includes(label))
        {
            labels.push(label)
            for(let i = 0; i < config.measureSeries; i++){
                res.push({
                    algorithmName: element.algorithmName,
                    dataGenerator: element.dataGenerator,
                    representation: element.representation,
                    numberOfVertices: (config.measureByDensity) ? element.numberOfVertices : ((+element.numberOfVertices * (i+1)) / config.measureSeries),
                    density: (config.measureByDensity) ? ((+element.density * (i+1)) / config.measureSeries) : +element.density,
                    algorithmParams: Object.fromEntries(element.algorithmParams),
                    measureByDensity: config.measureByDensity
                })
            }
        }
    });
    axios.post(`/api/experiment/graph?finite=${finite}`, res)
        .then((response: AxiosResponse)=>{
            onResponse(response.data)
        })
        .catch((error: AxiosError) =>{
    })
}

export const fetchAlgorithms = (onResponse:(alg:string[])=>void) => {
    axios.get('/api/experiment/graph/algorithms')
        .then((response: AxiosResponse)=>{
            onResponse(response.data)
        })
        .catch((error: AxiosError) =>{
        })
}

export const fetchDataGenerators = (onResponse:(alg:string[])=>void) => {
    axios.get('/api/experiment/graph/dataGenerators')
        .then((response: AxiosResponse)=>{
            onResponse(response.data)
        })
        .catch((error: AxiosError) =>{
        })
}

export const fetchRepresentations = (onResponse:(alg:string[])=>void) => {
    axios.get('/api/experiment/graph/representations')
        .then((response: AxiosResponse)=>{
            onResponse(response.data)
        })
        .catch((error: AxiosError) =>{
        })
}

export const fetchGraphExperiments = (id:string, onResponse:(args: GraphExperimentsResult)=>void) => {
    axios.get(`/api/experiment/${id}`)
        .then((response: AxiosResponse)=>{
            let result: GraphExperimentsResult = response.data
            if(result.results !== null)
            {
                result.results.forEach(e=> {
                    if(e.algorithmParams !== null)
                        e.algorithmParams = new Map<string,string>(Object.entries(e.algorithmParams))
                    else
                        e.algorithmParams = new Map<string,string>()
                })
            }
            onResponse(result)
        })
        .catch((error: AxiosError) =>{
        })
}

export const deleteGraphExperiment = (id:string) => {
    axios.delete(`/api/experiment/${id}`)
        .then((response: AxiosResponse)=>{
        })
        .catch((error: AxiosError) =>{
        })
} 

export const getNameForGraphExperiment = (v: GraphExperiment, densityXAxis: boolean) => {
    let series = v.algorithmName + " : " + v.dataGenerator + " : " + v.representation + " : " + (densityXAxis === true ? v.numberOfVertices : v.density);
    if (v.algorithmParams) {
        for (let [key, val] of v.algorithmParams.entries()) {
            series += " : [ " +key + " - " + val + " ]"
        }
    }
    return series
}


export const addCalculatedComplexity = (data: any[], series:string, calculatedInfo:ComplexityParameters) => {
    data = data.map(e=> {
        let res = e;
        let n = parseInt(e.N)
        if(calculatedInfo.complexityType === "N^2"){
            res[series] = calculatedInfo.data[0]*n*n
        } else if(calculatedInfo.complexityType === "N^3") {
            res[series] = calculatedInfo.data[0]*n*n*n
        } else if(calculatedInfo.complexityType === "N^4") {
            res[series] = calculatedInfo.data[0]*n*n*n*n
        }else if(calculatedInfo.complexityType === "N!") {
            res[series] = calculatedInfo.data[0]*factorialize(n)
        }else {
            res[series] = calculatedInfo.data[0]*n + calculatedInfo.data[1]
        }
        return res
    })
    return data
}



export const calculateComplexityParameters = (data: any[], series:string): ComplexityParameters => {
    
    let n = []
    let t = []
    let i = 0
    while(i < data.length && data[i][series] != undefined && data[i][series] > 0) {
        n.push(parseInt(data[i].N))
        t.push(data[i][series])
        i++
    }

    const dataMap = new Map();
    dataMap.set("N+K", linearRegresion(n, t));
    dataMap.set("N^2", minf(n,t,fb_n_2_a));
    dataMap.set("N^3", minf(n,t,fb_n_3_a));
    dataMap.set("N^4", minf(n,t,fb_n_4_a));
    if(n[n.length - 1] < 20){
        dataMap.set("N!", minf(n,t,fb_n_n_a));
    }
    
    let complexityType: ComplexityType = "N+K"
    let minRes = fb_n_k(dataMap.get("N+K")[0], dataMap.get("N+K")[1], n, t)
    let tmp = fb_n_2(dataMap.get("N^2")[0], n, t)
    if(tmp < minRes){
        minRes = tmp;
        complexityType = "N^2"
    }
    tmp = fb_n_3(dataMap.get("N^3")[0], n, t)
    if(tmp < minRes){
        minRes = tmp;
        complexityType = "N^3"
    }
    tmp = fb_n_4(dataMap.get("N^4")[0], n, t)
    if(tmp < minRes){
        minRes = tmp;
        complexityType = "N^4"
    }
    if(n[n.length - 1] < 20){
        tmp = fb_n_n(dataMap.get("N!")[0], n, t)
        if(tmp < minRes){
            minRes = tmp;
            complexityType = "N!"
        }
    }
    return {data: dataMap.get(complexityType), complexityType: complexityType}

}

const linearRegresion = (n:number[], t:number[]) => {
    let data = []
    let l = 0
    let m = 0
    let s_n = 0 
    n.forEach(e => s_n += e)
    s_n /= n.length
    let s_t = 0
    t.forEach(e => s_t += e)
    s_t /= t.length
    for (let i = 0; i < n.length; i++) {
        l += (n[i] - s_n) * (t[i] - s_t)
        m += (n[i] - s_n) * (n[i] - s_n)
    }
    let a = l/m
    let b = s_t - a * s_n
    data.push(a)
    data.push(b)
    return data
}

const minf = (n:number[], t:number[],  
    f_aprim:(a:number, n:number[], t:number[])=>number
) => {

    let data = []
    let a = 1.1;
    let delta_a = 0.00001;
    const epsilon = 0.01;
    let ap = epsilon * 2
    let doa = true
    while(Math.abs(ap) > epsilon && doa){
        ap = f_aprim(a,n,t)
        let aptoMin = ap * delta_a
        if(Math.abs(aptoMin/a) > 1.0){
            delta_a /= 10
            aptoMin = Math.abs(a)*0.5 * aptoMin/Math.abs(aptoMin) 
        }else if(Math.abs(aptoMin/a) < 0.000001){
            doa = false
        }
        if(a - aptoMin < 0)
            delta_a /= 10
        else
            a -= aptoMin;
    }   
    data.push(a)    
    return data;
}

const fb_n_k = (a:number, b:number, n:number[], t:number[]):number => {
    let res = 0
    for(let i = 0; i < n.length; i++){
        res += Math.pow(t[i] - a*n[i] - b, 2)
    }
    return res
}

const fb_n_2 = (a:number, n:number[], t:number[]):number => {
    let res = 0
    for(let i = 0; i < n.length; i++){
        res += Math.pow(t[i] - a*n[i]*n[i], 2)
    }
    return res
}

const fb_n_2_a = (a:number, n:number[], t:number[]):number => {
    let res = 0
    for(let i = 0; i < n.length; i++){
        let f = n[i]*n[i]
        res -= (t[i] - a*f)*f
    }
    return 2*res
}

const fb_n_3 = (a:number, n:number[], t:number[]):number => {
    let res = 0
    for(let i = 0; i < n.length; i++){
        res += Math.pow(t[i] - a*n[i]*n[i]*n[i], 2)
    }
    return res
}

const fb_n_3_a = (a:number, n:number[], t:number[]):number => {
    let res = 0
    for(let i = 0; i < n.length; i++){
        let f = n[i]*n[i]*n[i]
        res -= (t[i] - a*f)*f
    }
    return 2*res
}

const fb_n_4 = (a:number, n:number[], t:number[]):number => {
    let res = 0
    for(let i = 0; i < n.length; i++){
        res += Math.pow(t[i] - a*n[i]*n[i]*n[i]*n[i], 2)
    }
    return res
}

const fb_n_4_a = (a:number, n:number[], t:number[]):number => {
    let res = 0
    for(let i = 0; i < n.length; i++){
        let f = n[i]*n[i]*n[i]*n[i]
        res -= (t[i] - a*f)*f
    }
    return 2*res
}

const factorialize = (num: number): number => {
    if (num < 0) 
          return -1;
    else if (num == 0) 
        return 1;
    else {
        return (num * factorialize(num - 1));
    }
}

const fb_n_n = (a:number, n:number[], t:number[]):number => {
    let res = 0
    for(let i = 0; i < n.length; i++){
        res += Math.pow(t[i] - a*factorialize(n[i]), 2)
    }
    return res
}

const fb_n_n_a = (a:number, n:number[], t:number[]):number => {
    let res = 0
    for(let i = 0; i < n.length; i++){
        let f = factorialize(n[i])
        res -= (t[i] - a*f)*f
    }
    return 2*res
}