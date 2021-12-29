export interface GraphResult {
    tableAccessCount: number;
    memoryOccupancyInBytes: number;
    hamiltonCyclesCount: number;
}
export interface GraphExperiment {
    algorithmName: string;
    algorithmParams: Map<string, string>;
    dataGenerator: string;
    representation: string;
    numberOfVertices: number;
    density: number;
    check?: boolean;
    measureByDensity?: boolean;

    timeInMillis?: number;
    graphResult?: GraphResult;
}

export interface paramInfo{
    algorithm: string;
    name: string;
    isSelect: boolean;
    options: string[];
}

export interface GraphExperimentResultLabel{
    name: string;
    active: boolean;
    colorStr: string;
}

export interface GraphExperimentCardProps {
    algorithmOptions: string[];
    dataOptions: string[];
    representationOptions: string[];
    experiment: GraphExperiment;
    config: GraphConfig;
    experimentCheckInfo: GraphExperimentCheck;
    updateExperiment: (newExp :GraphExperiment)=>void;
    removeExperiment: ()=>void;
}

export interface GraphConfig {
    measureSeries: number;
    measureByDensity: boolean;
    densityOrVertices: number;
}

export interface GraphHeaderProps{
    config: GraphConfig
    submit: ()=>void;
    updateConfig: (conf: GraphConfig)=>void;
}

export type CheckStatus = "CORRECT" | "WARNING" | "ERROR" 
export interface CheckResult {
    msg?: string
    status: CheckStatus
}
export interface GraphExperimentCheck{
    numberOfVertices: CheckResult;
    density: CheckResult;
    warningFlag: boolean;
    errorFlag: boolean;
}
export interface GraphConfigCheck{
    measureSeries: CheckResult;
    densityOrVertices: CheckResult;
    warningFlag: boolean;
    errorFlag: boolean;
}

export type ExperimentStatus = "QUEUED" | "CALCULATING" | "DONE" | "REMOVED" | "EXPIRED" | "ERROR"

export interface GraphExperimentsResult {
    status: ExperimentStatus;
    results: GraphExperiment[];
    queuePosition: number;
    errorCause: string;
    id?: string;
}

export type ComplexityType = "N^2" | "N^3" | "N+K"

export interface GraphChartProps {
    experiments: GraphExperimentsResult;
    dataLabel: string;
    series?: string;

    labels: GraphExperimentResultLabel[];
}

export interface GraphHeaderProps{
    config: GraphConfig;
    configCheckInfo: GraphConfigCheck;
    allowSubmit: boolean;
    submit: ()=>void;
    updateConfig: (conf: GraphConfig)=>void;
}

export interface ComplexityParameters {
    complexityType: ComplexityType
    data: number[]
}