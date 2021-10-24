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

export interface SortingFormProps {

}

