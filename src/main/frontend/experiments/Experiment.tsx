import React, {useState, useEffect} from 'react'
import { Route, Routes } from 'react-router';
import { Link } from 'react-router-dom';

import { GraphForm } from './graph/GraphForm';
import { SortingForm } from './sorting/SortingForm';


import styles from './Experiment.module.scss';

export const Experiment = () => {
    return(
        <>
            <Routes>
                <Route path="sorting" element={<SortingForm />} />
                <Route path="graph" element={<GraphForm />} />
                <Route path="*" element={
                    <div className={styles.ExperimentContents}>
                        <h1 className={styles.ExperimentSwitchLabel}>Choose your experiment target</h1>
                        <div className={styles.ExperimentSwitch}>
                            <Link className={styles.ExperimentCard} to="sorting">Sorting Algorithms</Link>
                            <Link className={styles.ExperimentCard} to="graph">Graph Algorithms</Link>
                        </div>
                    </div>
                } />
            </Routes>
        </>
    )
}