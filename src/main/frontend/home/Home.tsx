import React, {useState, useEffect} from 'react'

import styles from './Home.module.scss';

export const Home = () =>{
    return(
        <div className={styles.HomeLayout}>
            <div className={styles.Content}>
                <h1>Theory Meets Reality</h1>
                <p>
                    APL allows you to confront your theoretical knowledge of algorithmics with reality.<br/>
                    Compare different algorithms, choose various data distributions and see if the result is like you have expected.<br/>
                    Choose either sorting experiment or graph experiment tab to create your own experiment!
                </p>
            </div>
            <div className={styles.Content}>
                <h1>About Us</h1>
                <p>
                    This is an open project hosted on GitHub and created by students of Poznań University of Technology.<br/>
                    You can see the source code <a href="https://github.com/piotrfutymski/APL">here</a>.
                </p>
            </div>
            <div className={styles.Content}>
                <h1>Cookies</h1>
                <p>
                    This website use cookies to improve user experience.<br /> 
                </p>
                    <b>Information collected:</b>
                    <ul>
                        <li>Recent experiment configurations</li>
                    </ul>
                    <b>Information NOT collected:</b>
                    <ul>
                        <li>Personal information</li>
                    </ul>
            </div>
        </div>
    )
}