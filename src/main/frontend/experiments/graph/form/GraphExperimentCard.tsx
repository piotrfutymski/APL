import classNames from 'classnames'
import React, {useState, useEffect} from 'react'

import styles from './GraphExperimentCard.module.scss'
import { getParamInfos } from '../GraphServices'
import { GraphExperiment, GraphExperimentCardProps } from '../Graph.interface'

export const GraphExperimentCard = (props:GraphExperimentCardProps) =>{
    //============================= onChangeHandlers =============================
    let experiment: GraphExperiment = {...props.experiment}
    const updateAlgorithm = (event: any) =>{
        experiment.algorithmName = event.target.value
        props.updateExperiment(experiment)
    }
    const updateData = (event: any) =>{
        experiment.dataGenerator = event.target.value
        props.updateExperiment(experiment)
    }
    const updateRepresentation = (event: any) =>{
        experiment.representation = event.target.value
        props.updateExperiment(experiment)
    }
    const updateNumberOfVertices = (event: any) =>{
        experiment.numberOfVertices = +event.target.value
        props.updateExperiment(experiment)
    }
    const updateDensity = (event: any) =>{
        experiment.density = +event.target.value
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
                <select className={styles.AlgorithmSelect} value={experiment.algorithmName} onChange={updateAlgorithm}>
                    {
                        props.algorithmOptions.map(name => <option value={name}>{name}</option>)
                    }
                </select>
            </div>
            <div className={styles.DataSelectContainer}>
                <label>Data Generator</label>
                <select className={styles.DataSelect} value={experiment.dataGenerator} onChange={updateData}>
                    {
                        props.dataOptions.map(name => <option value={name}>{name}</option>)
                    }
                </select>
            </div>
            <div className={styles.RepresentationSelectContainer}>
                <label>Representation</label>
                <select className={styles.RepresentationSelect} value={experiment.representation} onChange={updateRepresentation}>
                    {
                        props.representationOptions.map(name => <option value={name}>{name}</option>)
                    }
                </select>
            </div>
            <div className={styles.VerticesContainer}>
                <label>Number of Vertices</label>
                <div>
                    <input type="number" value={experiment.numberOfVertices===0 ? "" : experiment.numberOfVertices} onChange={updateNumberOfVertices}/>
                </div>
            </div>
            <div className={styles.DensityContainer}>
                <label>Density</label>
                <div>
                    <input type="number" value={experiment.density===0 ? "" : experiment.density} onChange={updateDensity}/>
                    <span className={styles.Percent}>%</span>
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