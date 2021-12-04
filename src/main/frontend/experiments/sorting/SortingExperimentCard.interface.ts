export interface SortingExperiment {
    algorithm: string;
    algorithmParams: Map<string, string>;
    dataDistribution: string;
    maxVal: number;
    check: boolean;
}

export interface paramInfo{
    algorithm: string;
    name: string;
    isSelect: boolean;
    options: string[];
}

export interface SortingExperimentCardProps {
    algorithmOptions: string[];
    dataOptions: string[];

    experiment: SortingExperiment;
    maxValAsPercents: boolean;
    updateExperiment: (newExp :SortingExperiment)=>void;
    removeExperiment: ()=>void;
}