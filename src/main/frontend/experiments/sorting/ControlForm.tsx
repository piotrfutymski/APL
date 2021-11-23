import React, {useState, useEffect} from 'react'

import styles from './ControlForm.module.scss'

export const ControlForm = () =>{
    return (
        <div className={styles.ControlFormContainer}>
            <label>N - Size of the largest instance</label>
            <input type="number" />
            <div>
                <input type="checkbox" id="nPercent" name="vehicle1" value="Bike" />
                <label htmlFor="nPercent">Calculate maximum value as percent of N</label>
            </div>
            <label>Maximum possible value</label>
            <input type="number" />

            <label>Number of measure series</label>
            <input type="number" />

            <button>Submit</button>
        </div>
    )
}