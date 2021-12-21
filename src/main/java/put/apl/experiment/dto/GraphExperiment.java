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
    private String representation;
    private String algorithmName;
    // CONNECTED, UNDIRECTED, DIRECTED, EULER, HAMILTIONIAN (also multiple types?)
    private String dataGenerator;
    //Data
    private Map<String, String> algorithmParams;
    private Integer numberOfVertices;
    private Double density;

    private Boolean forceConnected;
    private Boolean checkForCycles;

    private Double timeInMillis;
    private GraphResult graphResult;

    public String dataGeneratorGroupingString(){
        return dataGenerator+"_"+numberOfVertices.toString()+"_"+density.toString();
    }

    public GraphExperiment clone() {
        return GraphExperiment.builder()
                .algorithmName(algorithmName)
                .representation(representation)
                .algorithmParams(algorithmParams)
                .dataGenerator(dataGenerator)
                .numberOfVertices(numberOfVertices)
                .density(density)
                .checkForCycles(checkForCycles)
                .forceConnected(forceConnected)
                .graphResult(graphResult)
                .timeInMillis(timeInMillis)
                .build();
    }

    public GraphExperiment clearForResponse(){
        graphResult.setPath(null);
        graphResult.setMultiplePaths(null);
        graphResult.setMinimumSpanningTree(null);
        return this;
    }
}
