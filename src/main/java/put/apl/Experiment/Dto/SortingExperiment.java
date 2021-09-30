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
    private Map<String, Object> algorithmParams; // np. "k" -> 3 dla szybkiego z medianą k liczb, albo "pivot" -> "first" dla quicksorta
    //Data
    private String dataDistribution;
    private Integer N;
    private Integer maxValue;
    //Result
    private Long timeInNano;
    private SortingResult sortingResult;
}
