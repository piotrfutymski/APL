import axios, { AxiosError, AxiosResponse } from "axios";
import { paramInfo, SortingExperiment } from "./SortingExperimentCard.interface";

export const startSortingExperiments = (experiments: SortingExperiment[], finite: boolean, onResponse: (arg0: string)=>void) => {
    axios.post(`/api/experiment/sort?finite=${finite}`, experiments)
        .then((response: AxiosResponse)=>{
            onResponse(response.data)
        })
        .catch((error: AxiosError) =>{
        })
}

export const fetchSortingAlgorithms = (onResponse:(alg:string[])=>void) => {
    axios.get('/api/experiment/possibleSortingAlgorithms')
        .then((response: AxiosResponse)=>{
            onResponse(response.data)
        })
        .catch((error: AxiosError) =>{
        })
}

export const fetchSortingDataDistributions = (onResponse:(alg:string[])=>void) => {
    axios.get('/api/experiment/possibleSortingDataDistributions')
        .then((response: AxiosResponse)=>{
            onResponse(response.data)
        })
        .catch((error: AxiosError) =>{
        })
}