import React, {useState, useEffect} from 'react'
import { BrowserRouter, Routes, Route, Link } from 'react-router-dom';
import { CookiesProvider } from 'react-cookie';

import { Header, Subpage } from './header/Header';
import { Home } from './home/Home';
import { ExperimentView } from './experiments/ExperimentView';

import styles from './App.module.scss';

export const App = () => {
	const subpages: Subpage[] = [
		{link: "/experiments/sorting", label:"SORTING ALGORITHMS"},
		{link: "/experiments/graph", label:"GRAPH ALGORITHMS"}
	]

	return (
		<CookiesProvider>
			<BrowserRouter>
				<div className={styles.Header}>
					<Header subpages={subpages} showLogo={true} />
				</div>
				<Routes>
					<Route path="/" element={<Home />}/>
					<Route path="/experiments/*" element={<ExperimentView />} />
				</Routes>
			</BrowserRouter>
		</CookiesProvider>
	)

}