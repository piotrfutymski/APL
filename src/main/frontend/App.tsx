import React, {useState, useEffect} from 'react'
import { BrowserRouter, Routes, Route, Link } from 'react-router-dom';

import { Header, Subpage } from './header/Header';
import { Home } from './home/Home';
import { Experiment } from './experiments/Experiment';

import styles from './App.module.scss';

export const App = () => {
	const subpages: Subpage[] = [
		{link: "/experiments/sorting", label:"SORTING ALGORITHMS"},
		{link: "/experiments/graph", label:"GRAPH ALGORITHMS"}
	]

	return (
	<BrowserRouter>
		<div className={styles.Header}>
			<Header subpages={subpages} showLogo={true} />
		</div>
		<div className={styles.Content}>
			<Routes>
				<Route path="/" element={<Home />}/>
				<Route path="/experiments/*" element={<Experiment />} />
			</Routes>
		</div>
	</BrowserRouter>	
	)

}