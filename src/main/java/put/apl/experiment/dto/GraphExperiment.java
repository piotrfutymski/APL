package put.apl.experiment.dto;

import lombok.*;
import put.apl.algorithms.graphs.data.GraphRepresentation;

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
    //Result
    private Double timeInNano;
    private Integer memoryOccupancyInBytes;
    private Integer acyclicCount;
    private Integer hamiltonCyclesCount;
    protected GraphRepresentation minimumSpanningTree;
    protected GraphRepresentation shortestPath;

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
                .timeInNano(timeInNano)
                .memoryOccupancyInBytes(memoryOccupancyInBytes)
                .acyclicCount(acyclicCount)
                .hamiltonCyclesCount(hamiltonCyclesCount)
                .minimumSpanningTree(minimumSpanningTree)
                .shortestPath(shortestPath)
                .build();
    }
}
