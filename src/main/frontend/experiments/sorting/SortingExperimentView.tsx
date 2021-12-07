import React, {useState, useEffect} from 'react'
import { Route, Routes } from 'react-router'

import { SortingForm } from './form/SortingForm'
import { SortingExperimentResultView } from './result/SortingExperimentResultView'

export const SortingExperimentView = () =>{
    return (
        <Routes>
            <Route path="" element={<SortingForm />} />
            <Route path=":id" element={<SortingExperimentResultView />} />
        </Routes>
    )
}