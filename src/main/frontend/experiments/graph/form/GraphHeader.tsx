import React, {useState, useEffect} from 'react'
import { GraphConfig, GraphHeaderProps } from '../Graph.interface'

import styles from './GraphHeader.module.scss'

export const GraphHeader = (props: GraphHeaderProps) =>{
    let config:GraphConfig = {...props.config}
    const updateMeasureSeries = (event: any) =>{
        config.measureSeries = +event.target.value
        props.updateConfig(config)
    }
    return (
        <div className={styles.GraphHeader}>
            <div className={styles.SeriesContainer}>
                <label>Number of measure series</label>
                <input type="number" value={config.measureSeries===0 ? "" : config.measureSeries} onChange={updateMeasureSeries}/>
            </div>
            <div className={styles.SubmitContainer}>
                <button onClick={props.submit}>Submit</button>
            </div>
        </div>
    )
}