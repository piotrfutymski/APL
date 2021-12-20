import React, {useState, useEffect} from 'react'
import { Link } from 'react-router-dom';

import { HeaderProps, Subpage } from './Header.interface';

import styles from './Header.module.scss';

export const Header = (props: HeaderProps) => {
    return (
    <div className={styles.Header}>
        {
            props.showLogo === true ? <Link key="/" className={styles.HeaderLogo} to="/">APL</Link> : null
        }
        {
            props.subpages.map((spage: Subpage) => <Link key={spage.link} className={styles.HeaderLink} to={spage.link}>{spage.label}</Link>)
        }
    </div>
    )
}
export * from './Header.interface';