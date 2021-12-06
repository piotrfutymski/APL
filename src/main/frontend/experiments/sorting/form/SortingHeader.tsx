import React, {useState, useEffect} from 'react'
import { SortingConfig, SortingHeaderProps } from '../Sorting.interface'

import styles from './SortingHeader.module.scss'

export const SortingHeader = (props: SortingHeaderProps) =>{
    let config: SortingConfig = {...props.config}
    const updateN = (event: any) =>{
        config.n = +event.target.value
        props.updateConfig(config)
    }
    const updateMaxValAsPercent = (event: any) =>{
        config.maxValAsPercent = event.target.checked
        props.updateConfig(config)
    }
    const updateMeasureSeries = (event: any) =>{
        config.measureSeries = +event.target.value
        props.updateConfig(config)
    }
    return (
        <div className={styles.SortingHeader}>
            <div className={styles.SizeContainer}>
                <label>N - Size of the largest instance</label>
                <input type="number" value={config.n===0 ? "" : config.n} onChange={updateN}/>
            </div>
            <div className={styles.SeriesContainer}>
                <label>Number of measure series</label>
                <input type="number" value={config.measureSeries===0 ? "" : config.measureSeries} onChange={updateMeasureSeries}/>
            </div>
            <div className={styles.MaxValContainer}>
                {
                    config.maxValAsPercent ? 
                    <input type="checkbox" id="maxValAsPercent" onChange={updateMaxValAsPercent} checked/> : 
                    <input type="checkbox" id="maxValAsPercent" onChange={updateMaxValAsPercent} />
                }
                <label htmlFor="maxValAsPercent">Calculate maximum value as percent of N</label>
            </div>
            <div className={styles.SubmitContainer}>
                <button onClick={props.submit}>Submit</button>
            </div>
        </div>
    )
}