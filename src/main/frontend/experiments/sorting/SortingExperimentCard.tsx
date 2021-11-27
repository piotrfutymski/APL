import classNames from 'classnames'
import React, {useState, useEffect} from 'react'

import { SortingExperiment, SortingExperimentCardProps } from './SortingExperimentCard.interface'

import styles from './SortingExperimentCard.module.scss'

export const SortingExperimentCard = (props:SortingExperimentCardProps) =>{
    let experiment: SortingExperiment = {...props.experiment}
    const updateAlgorithm = (event: any) =>{
        experiment.algorithm = event.target.value
        props.updateExperiment(experiment)
    }
    const updateData = (event: any) =>{
        experiment.dataDistribution = event.target.value
        props.updateExperiment(experiment)
    }

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
                <input type="number" />
            </div>
            <div className={styles.DeleteBtn} onClick={props.removeExperiment}>X</div>
        </div>
    )
}
export * from './SortingExperimentCard.interface';