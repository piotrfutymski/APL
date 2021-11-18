import { FormControl, InputLabel, MenuItem, OutlinedInput, Select } from "@material-ui/core"
import React, { useState, useEffect } from "react"
import { FormStylizer } from "../Utility/Forms.styled"
import { SortingExperimentsResult } from "./Sorting.interface"
import {  getNameForSortingExperiment } from "./Sorting.utils"
import { SortingChart } from "./SortingChart"

export const SortingDoneView = (props: SortingExperimentsResult) => {

    const [series, setSeries] = useState<string[]>([]);
    const [choosedSeries, setChoosedSeries] = useState<string>("")

    useEffect(() => {
        let stoSet: string[] = [""]
        if(props.results.length > 0){
            const name0 = props.results[0].n;
            props.results.filter(v => v.n === name0).forEach(v => {
                    stoSet.push(getNameForSortingExperiment(v))
            });
        }
        setSeries(stoSet)
    }, [props])

    const handleChangeSeries = (event: any) => {
        setChoosedSeries(event.target.value)
    }

    return (
    <>
         <FormStylizer>
                Choose algorithm data series for more details
                    <FormControl>
                        <InputLabel>Choose data series</InputLabel>
                        <Select
                            fullWidth
                            value={choosedSeries}
                            input={<OutlinedInput label="Choose data series" />}
                            onChange={handleChangeSeries}>
                            {series.map((option) => (
                                <MenuItem key={option} value={option}>
                                    {option}
                                </MenuItem>
                            ))}
                        </Select>
                    </FormControl>
                </FormStylizer>

        <SortingChart experiments={props} dataLabel="timeInMillis" series={choosedSeries}/>
        <SortingChart experiments={props} dataLabel="swapCount"/>
        <SortingChart experiments={props} dataLabel="comparisonCount"/>
    </>
    )
}