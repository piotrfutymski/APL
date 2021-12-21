import classNames from 'classnames'
import React, {useState, useEffect} from 'react'

import styles from './SortingExperimentCard.module.scss'
import { getParamInfos } from '../SortingServices'
import { CheckResult, SortingExperiment, SortingExperimentCardProps } from '../Sorting.interface'

export const SortingExperimentCard = (props:SortingExperimentCardProps) =>{
    //============================= onChangeHandlers =============================
    let experiment: SortingExperiment = {...props.experiment}
    const updateAlgorithm = (event: any) =>{
        experiment.algorithmName = event.target.value
        props.updateExperiment(experiment)
    }
    const updateData = (event: any) =>{
        experiment.dataDistribution = event.target.value
        props.updateExperiment(experiment)
    }
    const updateMaxVal = (event: any) =>{
        experiment.maxValue = +event.target.value
        props.updateExperiment(experiment)
    }
    //==========================================================
    //============================= Check =============================
    const getCheckBasedStyles = (check: CheckResult) => check.status === "ERROR" ? styles.Error : check.status === "WARNING" ? styles.Warning : ""
    const checkMsgs = [props.experimentCheckInfo.maxValue, ...props.experimentCheckInfo.algorithmParams.values()].filter(e => e.msg !== undefined)
    //==========================================================
    const paramInfos = getParamInfos(experiment)
    return (
        <div className={
            classNames(
                styles.Card, 
                props.experimentCheckInfo.errorFlag === true ? styles.Error :
                props.experimentCheckInfo.warningFlag === true ? styles.Warning : styles.Correct
                )
            }>
            <div className={styles.CardForm}>
                <div className={styles.FormControl}>
                    <label>Algorithm</label>
                    <select id="algorithm" className={styles.AlgorithmSelect} value={experiment.algorithmName} onChange={updateAlgorithm}>
                        {
                            props.algorithmOptions.map(name => <option key={name} value={name}>{name}</option>)
                        }
                    </select>
                </div>
                <div className={styles.FormControl}>
                    <label>Data Distribution</label>
                    <select id="data_distribution" className={styles.DataSelect} value={experiment.dataDistribution} onChange={updateData}>
                        {
                            props.dataOptions.map(name => <option key={name} value={name}>{name}</option>)
                        }
                    </select>
                </div>
                <div className={styles.FormControl}>
                    <label>Maximum possible value</label>
                    <div>
                        <input id="max_value" className={getCheckBasedStyles(props.experimentCheckInfo.maxValue)}
                            type="number"
                            value={experiment.maxValue===0 ? "" : experiment.maxValue}
                            onChange={updateMaxVal}
                        />
                        <span className={props.maxValAsPercents ? styles.Percent : styles.Hide}>%</span>
                    </div>
                </div>
                {
                    paramInfos.map(param =>{
                        const onChange = (event: any) => {
                            let stringORnumber = param.isSelect === true ? event.target.value : +event.target.value
                            experiment.algorithmParams.set(param.name, stringORnumber)
                            props.updateExperiment(experiment)
                        }
                        let val = experiment.algorithmParams.get(param.name)
                        const checkStyle = getCheckBasedStyles(props.experimentCheckInfo.algorithmParams.get(param.name))
                        return <div className={styles.FormControl}>
                            <label>{param.name}</label>
                            {
                                param.isSelect === true ?
                                    <select key={param.name} className={checkStyle} value={val} onChange={onChange}>
                                        { param.options.map(name => <option key={name} value={name}>{name}</option>) }
                                    </select> :
                                    <input key={param.name} className={checkStyle} type="number" value={+val === 0 ? "" : val} onChange={onChange}/>
                            }
                        </div>
                    })
                }
            </div>
            {
                checkMsgs.length > 0 ?
                    <div className={styles.MessagesContainer}>
                        {
                            checkMsgs.map((check, index) =>
                            <p key={index} className={check.status === "ERROR" ? styles.ErrorMsg : check.status === "WARNING" ? styles.WarningMsg : ""}>
                                {check.msg}
                            </p>)
                        }
                    </div> : ""
            }
            <div className={styles.DeleteBtn} onClick={props.removeExperiment}>X</div>
        </div>
    )
}