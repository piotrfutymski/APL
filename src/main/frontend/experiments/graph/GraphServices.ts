import axios, { AxiosError, AxiosResponse } from "axios";
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

export const reducePossibleGenerators = (experiment: GraphExperiment, generators: string[]): string[] => {
    let res=[...generators]
    if(experiment.algorithmName === "Topological Sort"){
        res = ["Connected Directed Graph Generator", "Euler Directed Graph Generator"];
    } else if(experiment.algorithmName === "All Hamiltonian Cycles" || experiment.algorithmName === "Hamiltonian Cycle" || experiment.algorithmName === "Dijkstra Algorithm" || experiment.algorithmName === "Prim Algorithm" || experiment.algorithmName === "Kruskal Algorithm"){
        res = ["Connected Directed Graph Generator", 
        "Euler Directed Graph Generator", "Connected Undirected Graph Generator", "Euler Undirected Graph Generator"];
    } else if(experiment.algorithmName === "Euler Cycle Finding Algorithm"){
        res = ["Euler Directed Graph Generator", 
        "Euler Undirected Graph Generator"];
    }
    return res
}

export const reducePossibleRepresentations = (experiment: GraphExperiment, representations: string[]): string[] => {
    let res=[...representations]
    if(experiment.algorithmName === "Dijkstra Algorithm" && experiment.dataGenerator === "Connected Directed Graph Generator"){
        res = ["Weighted Adjacency Matrix Directed"]
    } else if(experiment.algorithmName === "Dijkstra Algorithm" && experiment.dataGenerator === "Connected Undirected Graph Generator"){
        res = ["Weighted Adjacency Matrix Undirected", "Incidence Matrix Undirected Weighted"]
    } else if(experiment.algorithmName === "Prim Algorithm" || experiment.algorithmName === "Kruskal Algorithm"){
        res = ["Weighted Adjacency Matrix Directed"]
    } else if(experiment.dataGenerator === "Connected Directed Graph Generator" || experiment.dataGenerator === "Directed Graph Generator" || experiment.dataGenerator === "Euler Directed Graph Generator"){
        res = ["Adjacency Matrix Directed", "Weighted Adjacency Matrix Directed", "Incidence Matrix Directed", "Incidence Matrix Directed Weighted", "List Of Edges Directed", "List Of Predecessors Directed", "List Of Successors Directed"];
    } else if(experiment.dataGenerator === "Connected Undirected Graph Generator" || experiment.dataGenerator === "Undirected Graph Generator" || experiment.dataGenerator === "Euler Undirected Graph Generator"){
        res = ["Adjacency Matrix Undirected", "Weighted Adjacency Matrix Undirected", "Incidence Matrix Undirected", "Incidence Matrix Undirected Weighted", "List Of Edges Undirected", "List Of Incident Undirected"];
    } 
    return res
}


export const checkConfig = (config: GraphConfig): GraphConfigCheck => {
    let result:GraphConfigCheck = {warningFlag: false, errorFlag: false,  measureSeries: {status: "CORRECT"}, densityOrVertices: {status: "CORRECT"}}
    if(config.measureSeries <= 0){
        result.measureSeries.status="ERROR"
        result.measureSeries.msg="Number of measure series must be a number greater than 0"
        result.errorFlag=true
    }
    if(config.measureByDensity === true){
        if(config.densityOrVertices <= 0)
        {
            result.densityOrVertices.status="ERROR"
            result.densityOrVertices.msg="Number of vertices must be a number greater than 0"
            result.errorFlag=true
        } 
        else if (config.densityOrVertices < config.measureSeries) 
        {
            result.densityOrVertices.status="ERROR"
            result.densityOrVertices.msg="Number of vertices is smaller than the number of series"
            result.errorFlag=true
        }
        else if (config.densityOrVertices < 5 * config.measureSeries)
        {
            result.densityOrVertices.status="WARNING"
            result.densityOrVertices.msg="Number of vertices is very small compared to number of series"
            result.warningFlag=true
        }
    } else {
        if(config.densityOrVertices <= 0)
        {
            result.densityOrVertices.status="ERROR"
            result.densityOrVertices.msg="Density must be a number greater than 0"
            result.errorFlag=true
        }
        else if(config.densityOrVertices > 100)
        {
            result.densityOrVertices.status="ERROR"
            result.densityOrVertices.msg="Density must be a number below 100"
            result.errorFlag=true
        }
    }
    return result
}

export const checkExperiment = (experiment: GraphExperiment, config: GraphConfig): GraphExperimentCheck => {
    const paramInfos = getParamInfos(experiment)
    let result:GraphExperimentCheck = { warningFlag: false, errorFlag: false, numberOfVertices: {status: "CORRECT"}, density: {status: "CORRECT"}}
    if(config.measureByDensity === true){
        if(experiment.numberOfVertices <= 0)
        {
            result.numberOfVertices.status="ERROR"
            result.numberOfVertices.msg="Number of vertices must be a number greater than 0"
            result.errorFlag=true
        } 
        else if (experiment.numberOfVertices < config.measureSeries) 
        {
            result.numberOfVertices.status="ERROR"
            result.numberOfVertices.msg="Number of vertices is smaller than the number of series"
            result.errorFlag=true
        }
        else if (experiment.numberOfVertices < 5 * config.measureSeries)
        {
            result.numberOfVertices.status="WARNING"
            result.numberOfVertices.msg="Number of vertices is very small compared to number of series"
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
    }
    if (experiment.numberOfVertices * experiment.density / (100 * config.measureSeries) < 1 && config.measureByDensity == true) 
    {
        result.density.status="WARNING"
        result.density.msg="Density is too small compared with current number of vertices and measure series"
        result.warningFlag=true
    }
    
    return result
}


export const submitExperiments = (experiments: GraphExperiment[], config: GraphConfig, finite: boolean, onResponse: (arg0: string) => void) => {
    let res: any[] = []
    experiments.forEach(element => {
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
            onResponse(response.data)
        })
        .catch((error: AxiosError) =>{
        })
}

export const getNameForGraphExperiment = (v: GraphExperiment, densityXAxis: boolean) => {
    let series = v.algorithmName + " : " + v.dataGenerator + " : " + v.representation + " : " + (densityXAxis === true ? v.numberOfVertices : v.density);
    if (v.algorithmParams) {
        for (let [key, val] of Object.entries(v.algorithmParams)) {
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
        } else if(calculatedInfo.complexityType === "NlogN") {
            res[series] = calculatedInfo.data[0]*n*Math.log2(n)
        } else {
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

    let n_2_data = minf(n,t,fb_n_2_a)
    let n_log_n_data = minf(n,t,fb_log_n_a)
    let n_k_data = linearRegresion(n, t)

    let n_2_res = fb_n_2(n_2_data[0], n, t)
    let n_log_n_res = fb_log_n(n_log_n_data[0], n, t)
    let n_k_res = fb_n_k(n_k_data[0], n_k_data[1], n, t)

    if(n_2_res < n_log_n_res){
        if(n_k_res < n_2_res){
            return {data: n_k_data, complexityType: "N+K"}
        }else{
            return {data: n_2_data, complexityType: "N^2"}
        }    
    }else{
        if(n_k_res < n_log_n_res){
            return {data: n_k_data, complexityType: "N+K"}
        }else{
            return {data: n_log_n_data, complexityType: "NlogN"}
        }
    }

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

const fb_log_n = (a:number, n:number[], t:number[]):number => {
    let res = 0
    for(let i = 0; i < n.length; i++){
        res += Math.pow(t[i] - a*n[i]*Math.log2(n[i]), 2)
    }
    return res
}

const fb_log_n_a = (a:number, n:number[], t:number[]):number => {
    let res = 0
    for(let i = 0; i < n.length; i++){
        let f = n[i]*Math.log2(n[i])
        res -= (t[i] - a*f)*f
    }
    return 2*res
}