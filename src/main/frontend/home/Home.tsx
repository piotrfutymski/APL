import React, {useState, useEffect} from 'react'

import styles from './Home.module.scss';

export const Home = () =>{
    return(
        <div className={styles.HomeLayout}>
            <div className={styles.Content}>
                <h1>Gitara</h1>
                <p>
                    Siem Siema<br/>
                    Pozdrowienia z podziemia
                </p>
            </div>
            <div className={styles.Content}>
                <h1>Gitara</h1>
                <p>
                    Siem Siema<br/>
                    Pozdrowienia z podziemia
                </p>
            </div>
        </div>
    )
}