import React, {useState, useEffect} from 'react'
import { Route, Routes } from 'react-router'

import { GraphForm } from './form/GraphForm'
import { GraphExperimentResultView } from './result/GraphExperimentResultView'

export const GraphExperimentView = () =>{
    return (
        <Routes>
            <Route path="" element={<GraphForm />} />
            <Route path=":id" element={<GraphExperimentResultView />} />
        </Routes>
    )
}