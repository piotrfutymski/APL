export interface SortingResult {
    comparisonCount: number;
    swapCount: number;
    recursionSize: number;
}
export interface SortingExperiment {
    algorithmName: string;
    algorithmParams: Map<string, string>;
    dataDistribution: string;
    maxValue: number;

    n?: number;
    timeInMillis?: number;
    sortingResult?: SortingResult;
}
export type CheckStatus = "CORRECT" | "WARNING" | "ERROR" 
export interface CheckResult {
    msg?: string
    status: CheckStatus
}
export interface SortingExperimentCheck{
    maxValue: CheckResult
    algorithmParams: Map<string, CheckResult>;
    warningFlag: boolean;
    errorFlag: boolean;
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
    experimentCheckInfo: SortingExperimentCheck;
    updateExperiment: (newExp :SortingExperiment)=>void;
    removeExperiment: ()=>void;
}

export interface SortingConfig {
    n: number;
    measureSeries: number;
    maxValAsPercent: boolean;
}

export interface SortingHeaderProps{
    config: SortingConfig
    submit: ()=>void;
    updateConfig: (conf: SortingConfig)=>void;
}

export type ExperimentStatus = "QUEUED" | "CALCULATING" | "DONE" | "REMOVED" | "EXPIRED" | "ERROR"

export interface SortingExperimentsResult {
    status: ExperimentStatus;
    results: SortingExperiment[];
    queuePosition: number;
    errorCause: string;
}

export type ComplexityType = "N^2" | "NlogN" | "N+K"

export interface SortingChartProps {
    experiments: SortingExperimentsResult;
    dataLabel: string;
    series?: string;
}

export interface ComplexityParameters {
    complexityType: ComplexityType
    data: number[]
}