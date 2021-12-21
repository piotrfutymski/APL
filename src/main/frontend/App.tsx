import React, {useState, useEffect} from 'react'
import { BrowserRouter, Routes, Route, Link } from 'react-router-dom';
import { CookiesProvider, useCookies } from 'react-cookie';

import { Header, Subpage } from './header/Header';
import { Home } from './home/Home';
import { ExperimentView } from './experiments/ExperimentView';

import styles from './App.module.scss';

export const App = () => {
	const subpages: Subpage[] = [
		{link: "/experiments/sorting", label:"SORTING ALGORITHMS"},
		{link: "/experiments/graph", label:"GRAPH ALGORITHMS"}
	]

	const [cookies, setCookie] = useCookies(['AllowCookies']);
	const allowCookies = (cookies.AllowCookies || 'false')
	return (
		<CookiesProvider>
			<BrowserRouter>
				{
					allowCookies === 'true' ? 
					<>
						<div className={styles.Header}>
							<Header subpages={subpages} showLogo={true} />
						</div>
						<Routes>
							<Route path="/" element={<Home />}/>
							<Route path="/experiments/*" element={<ExperimentView />} />
						</Routes>
					</> :
					<div className={styles.CookieForm}>
						<div className={styles.Content}>
							<div>
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
							<button onClick={()=>{setCookie('AllowCookies', 'true', {path: '/', sameSite: true, maxAge: 36288000})}}>
								Allow Cookies
							</button>
						</div>
					</div>
				}
			</BrowserRouter>
		</CookiesProvider>
	)

}