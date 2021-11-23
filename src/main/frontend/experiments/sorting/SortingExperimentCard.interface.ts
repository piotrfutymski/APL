export interface SortingExperiment {
    algorithm: string;
    algorithmParams: Map<string, string>;
    dataDistribution: string;
    check: boolean;
}

export interface SortingExperimentCardProps {
    algorithmOptions: string[];
    dataOptions: string[];
    experiment: SortingExperiment;
    updateExperiment: (newExp :SortingExperiment)=>void;
    removeExperiment: ()=>void;
}