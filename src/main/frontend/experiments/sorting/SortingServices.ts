import axios, { AxiosError, AxiosResponse } from "axios";
import { CheckResult, ComplexityParameters, paramInfo, SortingConfig, SortingConfigCheck, SortingExperiment, SortingExperimentCheck, SortingExperimentsResult } from "./Sorting.interface";

export const getParamInfos = (experiment: SortingExperiment): paramInfo[]=>{
    let res: paramInfo[] = []
    if(experiment.algorithmName === "QuickSort"){
        res.push({
            algorithm: "QuickSort",
            name: "pivotStrategy",
            isSelect: true,
            options: ["Median", "First item", "Middle item", "Last item", "Random item", "Median of three"]
        })
        if(experiment.algorithmParams.get("pivotStrategy") === "Median"){
            res.push({
                algorithm: "QuickSort",
                name: "medianCount",
                isSelect: false,
                options: []
            })
        }
    }else if(experiment.algorithmName === "Shell Sort Knuth"){
        res.push({
            algorithm: "Shell Sort Knuth",
            name: "k",
            isSelect: false,
            options: []
        })
    }
    return res
}

export const checkConfig = (config: SortingConfig): SortingConfigCheck => {
    let result:SortingConfigCheck = {warningFlag: false, errorFlag: false, n: {status: "CORRECT"}, measureSeries: {status: "CORRECT"}}
    if(config.n<=0){
        result.n.status="ERROR"
        result.n.msg="Size of the largest instance (N) must be a number greater than 0"
        result.errorFlag=true
    }
    if(config.n<100){
        result.n.status="WARNING"
        result.n.msg="Consider use size of the largest instance (N) between 100 to 100000"
        result.warningFlag=true
    }
    if(config.n>100000){
        result.n.status="WARNING"
        result.n.msg="Consider use size of the largest instance (N) between 100 to 100000"
        result.warningFlag=true
    }
    if(config.n>1000000){
        result.n.status="ERROR"
        result.n.msg="Size of the largest instance (N) must be a number less than 1000000"
        result.errorFlag=true
    }
    if(config.measureSeries <= 0){
        result.measureSeries.status="ERROR"
        result.measureSeries.msg="Number of measure series must be a number greater than 0"
        result.errorFlag=true
    }
    if(config.measureSeries >= 200){
        result.measureSeries.status="ERROR"
        result.measureSeries.msg="Number of measure series must be a number less than 200"
        result.errorFlag=true
    }
    return result
}

export const checkExperiment = (experiment: SortingExperiment, config: SortingConfig): SortingExperimentCheck => {
    const paramInfos = getParamInfos(experiment)
    let result:SortingExperimentCheck = { warningFlag: false, errorFlag: false, maxValue: {status: "CORRECT"}, algorithmParams: new Map<string, CheckResult>() }
    if(experiment.maxValue <= 0)
    {
        result.maxValue.status="ERROR"
        result.maxValue.msg="Maximum possible value must be a number greater than 0"
        result.errorFlag=true
    }else{
        let maxVal = experiment.maxValue
        if(config.maxValAsPercent){
            maxVal = experiment.maxValue*config.n/100
        }
        if(maxVal<100){
            result.maxValue.status="WARNING"
            result.maxValue.msg="Small value range, consider changing maximum possible value"
            result.warningFlag=true
        }
        if(maxVal>1000000000){
            result.maxValue.status="ERROR"
            result.maxValue.msg="To big value range, consider changing maximum possible value"
            result.errorFlag=true
        }
    }
    switch (experiment.algorithmName) {
        case "Shell Sort Knuth":
            let kCheck: CheckResult = {status: "CORRECT"}
            let k = +experiment.algorithmParams.get("k")
            if(k <= 1){
                kCheck.status="ERROR"
                kCheck.msg="Shell Sort Knuth's k parameter must be a number greater than 1"
                result.errorFlag=true
            }
            result.algorithmParams.set("k", kCheck)
            break;
        case "QuickSort":
            result.algorithmParams.set("pivotStrategy", {status: "CORRECT"})
            if(experiment.algorithmParams.get("pivotStrategy")==="Median"){
                let medianCountCheck: CheckResult = {status: "CORRECT"}
                let medianCount = +experiment.algorithmParams.get("medianCount")
                if(medianCount <= 0)
                {
                    medianCountCheck.status="ERROR"
                    medianCountCheck.msg="Quick Sort's median count parameter must be a number greater than 0"
                    result.errorFlag=true
                }else if(medianCount > config.n){
                    medianCountCheck.status="WARNING"
                    medianCountCheck.msg="Quick Sort's median count parameter will be limited to the largest instance size value (N)"
                    result.warningFlag=true
                }
                result.algorithmParams.set("medianCount", medianCountCheck)
            }
            break;
        default:
            break;
    }

    return result
}

export const submitExperiments = (experiments: SortingExperiment[], config: SortingConfig, finite: boolean, onResponse: (arg0: string) => void) => {
    let res: any[] = []
    experiments.forEach(element => {
        for(let i = 0; i < config.measureSeries; i++){
            res.push({
                algorithmName: element.algorithmName,
                dataDistribution: element.dataDistribution,
                algorithmParams: Object.fromEntries(element.algorithmParams),
                maxValue: config.maxValAsPercent ? element.maxValue/100 * config.n : element.maxValue,
                n: (config.n * (i+1)) / config.measureSeries
            })
        }
    });
    axios.post(`/api/experiment/sorting?finite=${finite}`, res)
        .then((response: AxiosResponse)=>{
            onResponse(response.data)
        })
        .catch((error: AxiosError) =>{
    })
}

export const fetchAlgorithms = (onResponse:(alg:string[])=>void) => {
    axios.get('/api/experiment/sorting/algorithms')
        .then((response: AxiosResponse)=>{
            onResponse(response.data)
        })
        .catch((error: AxiosError) =>{
        })
}

export const fetchDataDistributions = (onResponse:(alg:string[])=>void) => {
    axios.get('/api/experiment/sorting/dataDistributions')
        .then((response: AxiosResponse)=>{
            onResponse(response.data)
        })
        .catch((error: AxiosError) =>{
        })
}

export const fetchSortingExperiments = (id:string, onResponse:(args: SortingExperimentsResult)=>void) => {
    axios.get(`/api/experiment/${id}`)
        .then((response: AxiosResponse)=>{
            onResponse(response.data)
        })
        .catch((error: AxiosError) =>{
        })
}

export const deleteSortingExperiment = (id:string) => {
    axios.delete(`/api/experiment/${id}`)
        .then((response: AxiosResponse)=>{
        })
        .catch((error: AxiosError) =>{
        })
} 

export const getNameForSortingExperiment = (v: SortingExperiment) => {
    let series = v.algorithmName + " : " + v.dataDistribution + " : " + v.maxValue.toString();
    for (let [key, val] of Object.entries(v.algorithmParams)) {
        if(key != "maxValue"){
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