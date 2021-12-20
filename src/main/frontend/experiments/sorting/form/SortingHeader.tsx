import React, {useState, useEffect} from 'react'
import { CheckResult, SortingConfig, SortingHeaderProps } from '../Sorting.interface'

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
    const getCheckBasedStyles = (check: CheckResult) => check.status === "ERROR" ? styles.Error : check.status === "WARNING" ? styles.Warning : ""
    const checkMsgs = [props.configCheckInfo.measureSeries, props.configCheckInfo.n].filter(e => e.msg !== undefined)
    return (
        <div className={styles.SortingHeader}>
            <div className={styles.HeaderForm}>
                <div className={styles.FormControl}>
                    <label>N - Size of the largest instance</label>
                    <input className={getCheckBasedStyles(props.configCheckInfo.n)} type="number" value={config.n===0 ? "" : config.n} onChange={updateN}/>
                </div>
                <div className={styles.FormControl}>
                    <label>Number of measure series</label>
                    <input className={getCheckBasedStyles(props.configCheckInfo.measureSeries)} type="number" value={config.measureSeries===0 ? "" : config.measureSeries} onChange={updateMeasureSeries}/>
                </div>
                <div className={styles.FormControl}>
                    <div style={{height: "1rem"}}></div>
                    <label className="checkboxStyling">Calculate maximum value as percent of N
                        {
                            config.maxValAsPercent ? 
                            <input type="checkbox" id="maxValAsPercent" onChange={updateMaxValAsPercent} checked/> : 
                            <input type="checkbox" id="maxValAsPercent" onChange={updateMaxValAsPercent} />
                        }
                        <span></span>
                    </label>
                </div>
            </div>
            <div className={styles.SubmitContainer}>
                {
                    !props.configCheckInfo.errorFlag && props.allowSubmit ?
                    <button onClick={props.submit} >Submit</button> : 
                    <button onClick={props.submit} disabled>Submit</button>
                }
            </div>
            <div className={styles.MessageContainer}>
            {
                checkMsgs.map(check => 
                <p className={check.status === "ERROR" ? styles.ErrorMsg : check.status === "WARNING" ? styles.WarningMsg : ""}>
                    {check.msg}
                </p>)
            }
            </div>
        </div>
    )
}