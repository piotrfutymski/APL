package put.apl.algorithms.graphs.implementation;

import org.springframework.stereotype.Component;
import put.apl.algorithms.graphs.GraphResult;
import put.apl.algorithms.graphs.data.GraphRepresentationInterface;

import java.util.Map;

@Component("Get All Edges")
public class GetAllEdges implements GraphAlgorithm {

    public GraphResult run(GraphRepresentationInterface graph) {
        graph.setOperations(0);
        int noOfVertices = graph.getVerticesNumber();
        graph.getAllEdges();
        return GraphResult.builder().memoryOccupancyInBytes(graph.getMemoryOccupancy()).tableAccessCount(graph.getOperations()).build();
    }

    @Override
    public void setParams(Map<String, String> params) {
    }
}
