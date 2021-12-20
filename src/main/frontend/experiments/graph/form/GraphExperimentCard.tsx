import classNames from 'classnames'
import React, {useState, useEffect} from 'react'

import styles from './GraphExperimentCard.module.scss'
import { getParamInfos, reducePossibleGenerators, reducePossibleRepresentations } from '../GraphServices'
import { CheckResult, GraphExperiment, GraphExperimentCardProps } from '../Graph.interface'

export const GraphExperimentCard = (props:GraphExperimentCardProps) =>{
    //============================= onChangeHandlers =============================
    let experiment: GraphExperiment = {...props.experiment}
    const updateAlgorithm = (event: any) =>{
        experiment.algorithmName = event.target.value
        experiment.possibleGenerators = props.dataOptions
        reducePossibleGenerators(experiment)
        experiment.possibleRepresentations = props.representationOptions
        reducePossibleRepresentations(experiment)
        props.updateExperiment(experiment)
    }
    const updateData = (event: any) =>{
        experiment.dataGenerator = event.target.value
        experiment.possibleRepresentations = props.representationOptions
        reducePossibleRepresentations(experiment)
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
    //============================= Check =============================
    const getCheckBasedStyles = (check: CheckResult) => check.status === "ERROR" ? styles.Error : check.status === "WARNING" ? styles.Warning : ""
    const checkMsgs = [props.experimentCheckInfo.numberOfVertices, props.experimentCheckInfo.density].filter(e => e.msg !== undefined)
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
                    <select value={experiment.algorithmName} onChange={updateAlgorithm}>
                        {
                            props.algorithmOptions.map(name => <option key={name} value={name}>{name}</option>)
                        }
                    </select>
                </div>
                <div className={styles.FormControl}>
                    <label>Data Generator</label>
                    <select value={experiment.dataGenerator} onChange={updateData}>
                        {
                            experiment.possibleGenerators.map(name => <option key={name} value={name}>{name}</option>)
                        }
                    </select>
                </div>
                <div className={styles.FormControl}>
                    <label>Representation</label>
                    <select value={experiment.representation} onChange={updateRepresentation}>
                        {
                            experiment.possibleRepresentations.map(name => <option key={name} value={name}>{name}</option>)
                        }
                    </select>
                </div>
                <div className={styles.FormControl}>
                    <label>Number of Vertices</label>
                    <div>
                        <input className={getCheckBasedStyles(props.experimentCheckInfo.numberOfVertices)} type="number" value={experiment.numberOfVertices===0 ? "" : experiment.numberOfVertices} onChange={updateNumberOfVertices}/>
                    </div>
                </div>
                <div className={styles.FormControl}>
                    <label>Density</label>
                    <div>
                        <input className={getCheckBasedStyles(props.experimentCheckInfo.density)} type="number" value={experiment.density===0 ? "" : experiment.density} onChange={updateDensity}/>
                        <span className={styles.Percent}>%</span>
                    </div>
                </div>
                {
                    paramInfos.map(param =>{
                        const onChange = (event: any) => {
                            experiment.algorithmParams.set(param.name, event.target.value)
                            props.updateExperiment(experiment)
                        }
                        let val = experiment.algorithmParams.get(param.name)
                        return <div className={styles.FormControl}>
                            <label>{param.name}</label>
                            {
                                param.isSelect === true ?
                                    <select key={param.name} value={val} onChange={onChange}> 
                                        { param.options.map(name => <option key={name} value={name}>{name}</option>) } 
                                    </select> :
                                    <input key={param.name} type="number" value={val} onChange={onChange}/>
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