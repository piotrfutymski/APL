import React, {useState, useEffect} from 'react'
import { Route, Routes } from 'react-router'

import { GraphForm } from './form/GraphForm'
//import { SortingExperimentResultView } from './result/SortingExperimentResultView'

export const GraphExperimentView = () =>{
    return (
        <Routes>
            <Route path="" element={<GraphForm />} />
        </Routes>
    )
}