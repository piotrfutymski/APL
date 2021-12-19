import React, {useState, useEffect} from 'react'
import { Route, Routes } from 'react-router';
import { Link } from 'react-router-dom';

import { SortingExperimentView } from './sorting/SortingExperimentView';
import { GraphExperimentView } from './graph/GraphExperimentView';

import styles from './ExperimentView.module.scss';

export const ExperimentView = () => {
    return(
        <>
            <Routes>
                <Route path="sorting/*" element={<SortingExperimentView />} />
                <Route path="graph/*" element={<GraphExperimentView />} />
                <Route path="" element={
                    <div className={styles.ExperimentContents}>
                        <h1>Choose your experiment target</h1>
                        <div className={styles.ExperimentSwitch}>
                            <Link className={styles.ExperimentCard} to="sorting">Sorting Algorithms</Link>
                            < Link className={styles.ExperimentCard} to="graph">Graph Algorithms</Link>
                        </div>
                    </div>
                } />
            </Routes>
        </>
    )
}