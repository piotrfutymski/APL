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
    private String algorithmName;
    private Map<String, Object> algorithmParams; // np. "k" -> 3 dla szybkiego z medianą k liczb, albo "pivot" -> "first" dla quicksorta
    private String dataDistribution;
    private Integer N;
    private Integer maxValue;
    private Integer timeInNano;
    private SortingResult sortingResult;
}
