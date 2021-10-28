import axios, { AxiosError, AxiosResponse } from "axios";
import { SortingExperiment } from "../Sorting/Sorting.interface";

export const startSortingExperiments = (experiments: SortingExperiment[], finite: boolean, onResponse: (arg0: string)=>void) => {
    axios.post('/apl-api/experiment/sort', experiments)
        .then((response: AxiosResponse)=>{
            onResponse(response.data)
        })
        .catch((error: AxiosError) =>{
        })
}

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