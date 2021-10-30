export interface SortingResult {
    comparisonCount: number;
    swapCount: number;
    recursionSize: number;
}

export interface SortingExperiment {
    algorithmName: string;
    algorithmParams?: Map<string, string>;
    dataDistribution: string;
    n: number;
    maxValue: number;
    timeInMillis?: number;
    sortingResult?: SortingResult;
}

export interface SortingExperimentsResult {
    status: ExperimentStatus;
    results: SortingExperiment[];
    queuePosition: number;
    errorCause: string;
}

export interface SortingFormProps {
    setExperimentId?: (id:string)=>void;
}

export interface SortingExperimentListProps {
    experiments: SortingExperiment[]
    setExperiments: (sExp: SortingExperiment[])=>void;
    asAPercent: boolean;
}

export interface SortingResultViewProps {
    experimentId: string;
}


export type ExperimentStatus = "QUEUED" | "CALCULATING" | "DONE" | "REMOVED" | "EXPIRED" | "ERROR"