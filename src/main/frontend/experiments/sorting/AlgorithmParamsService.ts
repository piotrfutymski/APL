import { paramInfo, SortingExperiment } from "./SortingExperimentCard.interface";

export const getParamInfos = (experiment: SortingExperiment): paramInfo[]=>{
    let res: paramInfo[] = []
    if(experiment.algorithm === "QuickSort"){
        res.push({
            algorithm: "QuickSort",
            name: "pivotStrategy",
            isSelect: true,
            options: ["Median", "First item", "Middle item", "Last item", "Random item", "Median of three"]
        })
        if(experiment.algorithmParams.has("pivotStrategy") && experiment.algorithmParams.get("pivotStrategy") === "Median"){
            res.push({
                algorithm: "QuickSort",
                name: "medianCount",
                isSelect: false,
                options: []
            })
        }
    }else if(experiment.algorithm === "Shell Sort Knuth"){
        res.push({
            algorithm: "Shell Sort Knuth",
            name: "k",
            isSelect: false,
            options: []
        })
    }
    return res
}