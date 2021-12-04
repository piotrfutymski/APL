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