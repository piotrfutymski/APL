package put.apl.Experiment.Dto;

import lombok.*;
import put.apl.Algorithms.Sorting.SortingResult;

import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SortingExperiment {
    //Algorithm
    private String algorithmName;
    private Map<String, String> algorithmParams; // np. "k" -> "3" dla szybkiego z medianą k liczb, albo "pivot" -> "first" dla quicksorta
    //Data
    private String dataDistribution;
    private Integer N;
    private Integer maxValue;
    //Result
    private Double timeInMillis;
    private SortingResult sortingResult;

    public String dataGeneratorGroupingString(){
        return dataDistribution+"_"+N.toString()+"_"+maxValue.toString();
    }

    public SortingExperiment clone() {
        return SortingExperiment.builder()
                .algorithmName(algorithmName)
                .algorithmParams(algorithmParams)
                .dataDistribution(dataDistribution)
                .N(N)
                .maxValue(maxValue)
                .timeInMillis(timeInMillis)
                .sortingResult(sortingResult)
                .build();
    }
}
