package put.apl.experiment.dto;

import lombok.*;
import put.apl.algorithms.graphs.GraphResult;
import put.apl.algorithms.graphs.data.GraphRepresentation;
import put.apl.algorithms.sorting.SortingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GraphExperiment {
    //Algorithm
    private String graphRepresentation;
    private String algorithmName;
    // CONNECTED, UNDIRECTED, DIRECTED, EULER, HAMILTIONIAN (also multiple types?)
    private String type;
    //Data
    private Map<String, String> algorithmParams;
    private Integer noOfVertices;
    private Double density;

    private Boolean forceConnected;
    private Boolean checkForCycles;

    private Double timeInMillis;
    private GraphResult graphResult;

    public String dataGeneratorGroupingString(){
        return type+"_"+noOfVertices.toString()+"_"+density.toString();
    }

    public GraphExperiment clone() {
        return GraphExperiment.builder()
                .algorithmName(algorithmName)
                .graphRepresentation(graphRepresentation)
                .algorithmParams(algorithmParams)
                .type(type)
                .noOfVertices(noOfVertices)
                .density(density)
                .checkForCycles(checkForCycles)
                .forceConnected(forceConnected)
                .graphResult(graphResult)
                .timeInMillis(timeInMillis)
                .build();
    }
}
