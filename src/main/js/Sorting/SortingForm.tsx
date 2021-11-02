import { Button, Checkbox, FormControl, FormControlLabel, FormLabel, InputLabel, MenuItem, OutlinedInput, Radio, RadioGroup, Select, TextField } from "@material-ui/core";
import React, { useEffect, useState } from "react";
import { LabelValuePair } from "../Utility/Form.interface";
import { ButtonStylizer, ColumnContainer, RowContainer, FormStylizer } from "../Utility/Forms.styled";
import { fetchDataDistributions, fetchSortingAlgorithms, startSortingExperiments } from "../Utility/Requests";
import { SortingExperiment, SortingFormProps } from "./Sorting.interface";
import { SortingExperimentList } from "./SortingExperimentList";


export const SortingForm = (props: SortingFormProps) => {

    const [experiments, setExperiments] = useState<SortingExperiment[]>([]);

    const [sortingAlgorithms, setSortingAlgorithms] = useState<LabelValuePair[]>([]);
    const [choosedSortingAlgorithm, setChoosedSortingAlgorithm] = useState<string>("");

    const [dataDistributions, setDataDistributions] = useState<LabelValuePair[]>([]);
    const [choosedDataDistribution, setChoosedDataDistribution] = useState<string>("");

    const [N, setN] = useState(1000);
    const [seriesSize, setSeriesSize] = useState(10);
    const [maxValueAsPercentOfN, setMaxValueAsPercentOfN] = useState(true);
    const [maxValue, setMaxValue] = useState(1.0)

    const [infinite, setInfinite] = useState(false)

    const handleChangeSortingAlhorithm = (event: any) => {
        setChoosedSortingAlgorithm(event.target.value)
    }

    const handleChangeDataDistribution = (event: any) => {
        setChoosedDataDistribution(event.target.value)
    }

    const handleChangeN = (event: any) => {
        setN(event.target.value)
    }

    const handleChangeSeriesSize = (event: any) => {
        setSeriesSize(event.target.value)
    }

    const handleChangeMaxValue = (event: any) => {
        setMaxValue(event.target.value)
    }

    const handleChangeMaxValueType = (event: React.ChangeEvent<HTMLInputElement>) => {
        setMaxValueAsPercentOfN((event.target as HTMLInputElement).value == "Max value as percent of N" ? true : false);
    };

    const handleChangeInfinite = (event: any) => {
        setInfinite(event.target.checked)
    }

    const addAlgorithm = () => {
        let toAdd: SortingExperiment;
        toAdd = {
            algorithmName: choosedSortingAlgorithm,
            dataDistribution: choosedDataDistribution,
            maxValue: maxValue,
            n: 0
        }
        setExperiments([...experiments, toAdd])
    }

    const startExperiments = () => {
        startSortingExperiments(createExperimentsFromTemplates(), !infinite, (id: string) => {
            if (props.setExperimentId) {
                props.setExperimentId(id);
            }
        })
        setExperiments([]);
    }

    const createExperimentsFromTemplates = () => {
        let res: SortingExperiment[] = []
        experiments.forEach(element => {
            for(let i = 0; i<seriesSize; i++){
                res.push({
                    algorithmName: element.algorithmName,
                    dataDistribution: element.dataDistribution,
                    maxValue: maxValueAsPercentOfN ? element.maxValue * N : element.maxValue,
                    n: (N * (i+1)) / seriesSize
                })
            }
        });
        return res;
    }

    useEffect(() => {
        fetchSortingAlgorithms((alg: string[]) => {
            setSortingAlgorithms(alg.map(e => ({ value: e, label: e })))
        });
        fetchDataDistributions((alg: string[]) => {
            setDataDistributions(alg.map(e => ({ value: e, label: e })))
        })
    }, [])

    return (
        <RowContainer>
            <ColumnContainer>
                <FormStylizer>
                    <FormControl>
                        <InputLabel>Sorting algorithm</InputLabel>
                        <Select
                            fullWidth
                            value={choosedSortingAlgorithm}
                            input={<OutlinedInput label="Sorting algorithm" />}
                            onChange={handleChangeSortingAlhorithm}>
                            {sortingAlgorithms.map((option) => (
                                <MenuItem key={option.value} value={option.value}>
                                    {option.label}
                                </MenuItem>
                            ))}
                        </Select>
                    </FormControl>
                </FormStylizer>

                <FormStylizer>
                    <FormControl>
                        <InputLabel>Data distribution</InputLabel>
                        <Select
                            fullWidth
                            value={choosedDataDistribution}
                            input={<OutlinedInput label="Data distribution" />}
                            onChange={handleChangeDataDistribution}>
                            {dataDistributions.map((option) => (
                                <MenuItem key={option.value} value={option.value}>
                                    {option.label}
                                </MenuItem>
                            ))}
                        </Select>
                    </FormControl>
                </FormStylizer>
                <FormStylizer>
                    <TextField
                        label={maxValueAsPercentOfN ? "Max value as percent of N" : "Max value"}
                        value={maxValue}
                        onChange={handleChangeMaxValue}
                        type="number"
                        InputLabelProps={{
                            shrink: true,
                        }}
                    />
                </FormStylizer>
                <ButtonStylizer>
                    <Button
                        onClick={addAlgorithm}
                        variant="contained"
                    >
                        Add to configuration
                    </Button>
                </ButtonStylizer>
                <FormStylizer>
                    <TextField
                        label="max N"
                        value={N}
                        onChange={handleChangeN}
                        type="number"
                        InputLabelProps={{
                            shrink: true,
                        }}
                    />
                </FormStylizer>
                <FormStylizer>
                    <TextField
                        label="seriesSize"
                        value={seriesSize}
                        onChange={handleChangeSeriesSize}
                        type="number"
                        InputLabelProps={{
                            shrink: true,
                        }}
                    />
                </FormStylizer>
                <FormStylizer>
                    <FormLabel component="legend">How to calculate maxValue?</FormLabel>
                    <RadioGroup
                        aria-label="gender"
                        defaultValue="Max value as part of N (0.1 means maxValue = 0.1*N)"
                        name="radio-buttons-group"
                        value={maxValueAsPercentOfN ? "Max value as percent of N" : "Max value as number"}
                        onChange={handleChangeMaxValueType}
                    >
                        <FormControlLabel value="Max value as percent of N" control={<Radio />} label="Max value as percent of N" />
                        <FormControlLabel value="Max value as number" control={<Radio />} label="Max value as number" />
                    </RadioGroup>
                </FormStylizer>
            </ColumnContainer>
            <ColumnContainer>
                <FormStylizer>
                    <FormControlLabel
                        control={<Checkbox checked={infinite} onChange={handleChangeInfinite} />}
                        label="Check if your experiments is longer then 30s - you can wait longer for result but your exepriment will not expire"
                    />
                </FormStylizer>
                <SortingExperimentList experiments={experiments} setExperiments={setExperiments} asAPercent={maxValueAsPercentOfN}/>
                <ButtonStylizer>
                    <Button
                        onClick={startExperiments}
                        variant="contained"
                    >
                        StartExperiments
                    </Button>
                </ButtonStylizer>
            </ColumnContainer>
        </RowContainer>
    )
}