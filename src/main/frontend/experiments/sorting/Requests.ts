import axios, { AxiosError, AxiosResponse } from "axios";

export const fetchSortingAlgorithms = (onResponse:(alg:string[])=>void) => {
    axios.get('/apl-api/experiment/possibleSortingAlgorithms')
        .then((response: AxiosResponse)=>{
            onResponse(response.data)
        })
        .catch((error: AxiosError) =>{
        })
}

export const fetchDataDistributions = (onResponse:(alg:string[])=>void) => {
    axios.get('/apl-api/experiment/possibleDataDistributions')
        .then((response: AxiosResponse)=>{
            onResponse(response.data)
        })
        .catch((error: AxiosError) =>{
        })
}