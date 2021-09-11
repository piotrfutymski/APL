package put.apl.Experiment.Dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GraphExperiment {
    //Algorithm
    private String graphRepresentation;
    private String algorithmName;
    private Boolean undirected;
    //Data
    private Integer N;
    private Integer G;
    //Result
    private Integer timeInNano;
    private Integer memoryOccupancyInBytes;
    private Integer acyclicCount;
    private Integer hamiltonCyclesCount;

}
