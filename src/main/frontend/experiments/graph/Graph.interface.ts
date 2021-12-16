export interface GraphResult {
    tableAccessCount: number;
    memoryUsed: number;
}
export interface GraphExperiment {
    algorithmName: string;
    algorithmParams: Map<string, string>;
    dataGenerator: string;
    representation: string;
    numberOfVertices: number;
    density: number;
    check?: boolean;

    timeInMillis?: number;
    graphResult?: GraphResult;
}

export interface paramInfo{
    algorithm: string;
    name: string;
    isSelect: boolean;
    options: string[];
}

export interface GraphExperimentCardProps {
    algorithmOptions: string[];
    dataOptions: string[];
    representationOptions: string[];
    experiment: GraphExperiment;
    updateExperiment: (newExp :GraphExperiment)=>void;
    removeExperiment: ()=>void;
}

export interface GraphConfig {
    measureSeries: number;
    measureByDensity: boolean;
}

export interface GraphHeaderProps{
    config: GraphConfig
    submit: ()=>void;
    updateConfig: (conf: GraphConfig)=>void;
}

export type ExperimentStatus = "QUEUED" | "CALCULATING" | "DONE" | "REMOVED" | "EXPIRED" | "ERROR"

export interface GraphExperimentsResult {
    status: ExperimentStatus;
    results: GraphExperiment[];
    queuePosition: number;
    errorCause: string;
}

export type ComplexityType = "N^2" | "NlogN" | "N+K"

export interface GraphChartProps {
    experiments: GraphExperimentsResult;
    dataLabel: string;
    series?: string;
}

export interface ComplexityParameters {
    complexityType: ComplexityType
    data: number[]
}