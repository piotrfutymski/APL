import React, {useState, useEffect} from 'react'

import styles from './SortingHeader.module.scss'

export const SortingHeader = () =>{
    return (
        <div className={styles.SortingHeader}>
            <div className={styles.SizeContainer}>
                <label>N - Size of the largest instance</label>
                <input type="number" />
            </div>
            <div className={styles.SeriesContainer}>
                <label>Number of measure series</label>
                <input type="number" />
            </div>
            <div className={styles.MaxValContainer}>
                <input type="checkbox" id="nPercent" name="vehicle1" value="Bike" />
                <label htmlFor="nPercent">Calculate maximum value as percent of N</label>
            </div>
            <div className={styles.SubmitContainer}>
                <button>Submit</button>
            </div>
        </div>
    )
}