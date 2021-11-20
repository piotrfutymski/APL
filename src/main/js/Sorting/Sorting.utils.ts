import { ComplexityParameters, ComplexityType, SortingExperiment } from "./Sorting.interface";


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
        let n = parseInt(e.name)
        if(calculatedInfo.complexityType === "N^2"){
            res[series + " --> trend"] = calculatedInfo.data[0]*n*n
        } else {
            res[series + " --> trend"] = calculatedInfo.data[0]*n*Math.log2(n)
        }
        return res
    })
    return data
}



export const calculateComplexityParameters = (data: any[], series:string): ComplexityParameters => {
    
    let n = data.map(e=>parseInt(e.name))
    let t = data.map(e=>e[series])

    let n_2_data = minf(n,t,fb_n_2_a)
    let n_log_n_data = minf(n,t,fb_log_n_a)

    let n_2_res = fb_n_2(n_2_data[0], n, t)
    let n_log_n_res = fb_log_n(n_log_n_data[0], n, t)

    if(n_2_res < n_log_n_res){
        return {data: n_2_data, complexityType: "N^2"}
    }else{
        return {data: n_log_n_data, complexityType: "NlogN"}
    }

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