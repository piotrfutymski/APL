import axios, { AxiosError, AxiosResponse } from "axios";
import { SortingExperiment, SortingExperimentsResult } from "../Sorting/Sorting.interface";

export const startSortingExperiments = (experiments: SortingExperiment[], finite: boolean, onResponse: (arg0: string)=>void) => {
    axios.post(`/apl-api/experiment/sort?finite=${finite}`, experiments)
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

export const fetchGraphAlgorithms = (onResponse:(alg:string[])=>void) => {
    axios.get('/apl-api/experiment/possibleGraphAlgorithms')
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

export const getSortingExperiments = (id:string, onResponse:(args: SortingExperimentsResult)=>void) => {
    axios.get(`/apl-api/experiment/${id}`)
        .then((response: AxiosResponse)=>{
            onResponse(response.data)
        })
        .catch((error: AxiosError) =>{
        })
}