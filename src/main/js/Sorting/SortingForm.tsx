import { Button, FormControl, FormControlLabel, FormLabel, InputLabel, MenuItem, OutlinedInput, Radio, RadioGroup, Select, TextField } from "@material-ui/core";
import React, { useEffect, useState } from "react";
import { LabelValuePair } from "../Utility/Form.interface";
import { FormContainer } from "../Utility/Forms.styled";
import { fetchDataDistributions, fetchSortingAlgorithms, startSortingExperiments } from "../Utility/Requests";
import { SortingExperiment, SortingFormProps } from "./Sorting.interface";


export const SortingForm = (props: SortingFormProps) => {

    const [sortingAlgorithms, setSortingAlgorithms] = useState<LabelValuePair[]>([]);
    const [choosedSortingAlgorithm, setChoosedSortingAlgorithm] = useState<string[]>([]);
    const [dataDistributions, setDataDistributions] = useState<LabelValuePair[]>([]);
    const [choosedDataDistribution, setChoosedDataDistribution] = useState<string>("");
    const [N, setN] = useState(0);
    const [maxValueAsPercentOfN, setMaxValueAsPercentOfN] = useState(false);
    const [maxValue, setMaxValue] = useState(0)
    const [ID, setID] = useState("NULL")


    const handleChangeSortingAlhorithm = (event:any) => {
        const {
            target: { value },
          } = event;
          setChoosedSortingAlgorithm(
            typeof value === 'string' ? value.split(',') : value,
          );
    }

    const handleChangeDataDistribution = (event:any) => {
        setChoosedDataDistribution(event.target.value)
    }

    const handleChangeN = (event:any) => {
        setN(event.target.value)
    }

    const handleChangeMaxValue = (event:any) => {
        setMaxValue(event.target.value)
    }

    const handleChangeMaxValueType = (event: React.ChangeEvent<HTMLInputElement>) => {
        setMaxValueAsPercentOfN((event.target as HTMLInputElement).value == "Max value as percent of N" ? true : false);
    };

    const startAlgorithm = () =>{
        let toPost: SortingExperiment[] = []
        let maxV = maxValueAsPercentOfN ? maxValue * N / 100 : maxValue
        choosedSortingAlgorithm.forEach((alg: string)=>{
            toPost.push({
                algorithmName: alg,
                dataDistribution: choosedDataDistribution,
                n: N,
                maxValue: maxV
            })
        })
        startSortingExperiments(toPost, false, (id:string)=>{
            setID(id);
        })
    }

    useEffect(() => {
        fetchSortingAlgorithms((alg:string[])=>{
            setSortingAlgorithms(alg.map(e=> ({value: e, label: e})))
        });
        fetchDataDistributions((alg:string[])=>{
            setDataDistributions(alg.map(e=> ({value: e, label: e})))
        })
    }, [])

    return(
        <FormContainer>
            <FormControl>
                <InputLabel>Sorting algorithms</InputLabel>
                <Select 
                    multiple
                    value={choosedSortingAlgorithm}
                    input={<OutlinedInput label="Sorting algorithms" />}
                    onChange={handleChangeSortingAlhorithm}>
                        {sortingAlgorithms.map((option)=>(
                            <MenuItem key={option.value} value={option.value}>
                                {option.label}
                            </MenuItem>
                        ))}
                </Select>
            </FormControl>
            <FormControl>
                <InputLabel>Data distributions</InputLabel>
                <Select 
                    value={choosedDataDistribution}
                    input={<OutlinedInput label="Data distributions" />}
                    onChange={handleChangeDataDistribution}>
                        {dataDistributions.map((option)=>(
                            <MenuItem key={option.value} value={option.value}>
                                {option.label}
                            </MenuItem>
                        ))}
                </Select>
            </FormControl>
                <TextField
                    label="N"
                    value={N}
                    onChange={handleChangeN}
                    type="number"
                    InputLabelProps={{
                        shrink: true,
                    }}
                />
                <FormLabel component="legend">How to calculate maxValue?</FormLabel>
                <RadioGroup
                        aria-label="gender"
                        defaultValue="Max value as percent of N"
                        name="radio-buttons-group"
                        value={maxValueAsPercentOfN ? "Max value as percent of N" : "Max value as number"}
                        onChange={handleChangeMaxValueType}
                >
                    <FormControlLabel value="Max value as percent of N" control={<Radio />} label="Max value as percent of N" />
                    <FormControlLabel value="Max value as number" control={<Radio />} label="Max value as number" />
                </RadioGroup>
                <TextField
                    label={maxValueAsPercentOfN ? "Max value as percent of N" :"Max value"}
                    value={maxValue}
                    onChange={handleChangeMaxValue}
                    type="number"
                    InputLabelProps={{
                        shrink: true,
                    }}
                />
                <Button 
                    onClick={startAlgorithm}
                    variant="contained"
                >
                    Start calculations
                </Button>
            {ID}     
        </FormContainer>
    )
}