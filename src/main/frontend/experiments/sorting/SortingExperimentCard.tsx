import classNames from 'classnames'
import React, {useState, useEffect} from 'react'
import { useParams } from 'react-router'
import { getParamInfos } from './AlgorithmParamsService'

import { SortingExperiment, SortingExperimentCardProps } from './SortingExperimentCard.interface'

import styles from './SortingExperimentCard.module.scss'

export const SortingExperimentCard = (props:SortingExperimentCardProps) =>{
    //============================= onChangeHandlers =============================
    let experiment: SortingExperiment = {...props.experiment}
    const updateAlgorithm = (event: any) =>{
        experiment.algorithm = event.target.value
        props.updateExperiment(experiment)
    }
    const updateData = (event: any) =>{
        experiment.dataDistribution = event.target.value
        props.updateExperiment(experiment)
    }
    const updateMaxVal = (event: any) =>{
        experiment.maxVal = +event.target.value
        props.updateExperiment(experiment)
    }
    //==========================================================
    const paramInfos = getParamInfos(experiment)
    
    return (
        <div className={
            classNames(
                styles.Card, 
                experiment.check !== false ? styles.Correct : styles.Error 
                )
            }>
            <div className={styles.AlgorithmSelectContainer}>
                <label>Algorithm</label>
                <select className={styles.AlgorithmSelect} value={experiment.algorithm} onChange={updateAlgorithm}>
                    <option value=""></option>
                    {
                        props.algorithmOptions.map(name => <option value={name}>{name}</option>)
                    }
                </select>
            </div>
            <div className={styles.DataSelectContainer}>
                <label>Data Distribution</label>
                <select className={styles.DataSelect} value={experiment.dataDistribution} onChange={updateData}>
                    <option value=""></option>
                    {
                        props.dataOptions.map(name => <option value={name}>{name}</option>)
                    }
                </select>
            </div>
            <div className={styles.MaxValContainer}>
                <label>Maximum possible value</label>
                <div>
                    <input type="number" value={experiment.maxVal===0 ? "" : experiment.maxVal} onChange={updateMaxVal}/>
                    <span className={props.maxValAsPercents ? styles.Percent : styles.Hide}>%</span>
                </div>
            </div>
            <div className={styles.ParamsContainer}>
            {
                paramInfos.map(param =>{
                    const onChange = (event: any) => {
                        experiment.algorithmParams.set(param.name, event.target.value)
                        props.updateExperiment(experiment)
                    }
                    let val = experiment.algorithmParams.get(param.name)
                    if(param.isSelect === true){
                        val = val || param.options.at(0)
                    }else{
                        val = val || ""
                    }
                    return <div className={styles.Param}>
                        <label>{param.name}</label>
                        {
                            param.isSelect === true ?
                                <select value={val} onChange={onChange}> 
                                    { param.options.map(name => <option value={name}>{name}</option>) } 
                                </select> :
                                <input type="number" value={val} onChange={onChange}/>
                        }
                    </div>
                })
            }
            </div>
            <div className={styles.DeleteBtn} onClick={props.removeExperiment}>X</div>
        </div>
    )
}
export * from './SortingExperimentCard.interface';