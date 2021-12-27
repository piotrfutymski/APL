import React, {useState, useEffect} from 'react'
import { CheckResult, GraphConfig, GraphHeaderProps } from '../Graph.interface'

import styles from './GraphHeader.module.scss'

export const GraphHeader = (props: GraphHeaderProps) =>{
    let config:GraphConfig = {...props.config}
    const updateMeasureSeries = (event: any) =>{
        config.measureSeries = +event.target.value
        props.updateConfig(config)
    }
    const updateMeasureByDensity = (event: any) =>{
        config.measureByDensity = event.target.checked
        props.updateConfig(config)
    }
    const updateDensityOrVertices = (event: any) =>{
        config.densityOrVertices = +event.target.value
        props.updateConfig(config)
    }
    const getCheckBasedStyles = (check: CheckResult) => check.status === "ERROR" ? styles.Error : check.status === "WARNING" ? styles.Warning : ""
    const checkMsgs = [props.configCheckInfo.densityOrVertices, props.configCheckInfo.measureSeries].filter(e => e.msg !== undefined)
    return (
        <div className={styles.GraphHeader}>
            <div className={styles.HeaderForm}>
                <div className={styles.FormControl}>
                    {
                        props.config.measureByDensity === false ? 
                        <>
                            <label>Maximum Number Of Vertices</label> 
                            <div>
                                <input id="numberOfVertices" className={getCheckBasedStyles(props.configCheckInfo.densityOrVertices)} type="number" value={config.densityOrVertices===0 ? "" : config.densityOrVertices} onChange={updateDensityOrVertices}/>
                            </div>
                        </>
                        : 
                        <>
                            <label>Maximum Density</label>
                            <div>
                                <input id="density" className={getCheckBasedStyles(props.configCheckInfo.densityOrVertices)} type="number" value={config.densityOrVertices ===0 ? "" : config.densityOrVertices} onChange={updateDensityOrVertices}/>
                                <span className={styles.Percent}>%</span>
                            </div>
                        </>
                    }
                </div>
                <div className={styles.FormControl}>
                    <label>Number of measure series</label>
                    <input className={getCheckBasedStyles(props.configCheckInfo.measureSeries)} type="number" value={config.measureSeries===0 ? "" : config.measureSeries} onChange={updateMeasureSeries}/>
                </div>
                <div className={styles.FormControl}>
                    <div style={{height: "1rem"}}></div>
                    <label className="checkboxStyling">Measure By Density
                        {
                            config.measureByDensity ? 
                            <input type="checkbox" id="measureByDensity" onChange={updateMeasureByDensity} checked/> : 
                            <input type="checkbox" id="measureByDensity" onChange={updateMeasureByDensity} />
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
                    checkMsgs.map((check, index) => 
                    <p key={index} className={check.status === "ERROR" ? styles.ErrorMsg : check.status === "WARNING" ? styles.WarningMsg : ""}>
                        {check.msg}
                    </p>)
                }
                </div>
        </div>
    )
}