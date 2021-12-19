package put.apl.algorithms.graphs.implementation;

import org.springframework.stereotype.Component;
import put.apl.algorithms.graphs.GraphResult;
import put.apl.algorithms.graphs.data.GraphRepresentation;

import java.util.Map;

@Component("Get Memory Occupancy")
public class GetMemoryOccupancy implements GraphAlgorithm {

    public GraphResult run(GraphRepresentation graph) {
        int noOfVertices = graph.getVerticesNumber();
        return GraphResult.builder().memoryOccupancyInBytes(graph.getMemoryOccupancy()).build();
    }

    @Override
    public void setParams(Map<String, String> params) {
    }
}
