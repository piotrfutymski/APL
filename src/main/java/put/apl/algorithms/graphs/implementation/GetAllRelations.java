package put.apl.algorithms.graphs.implementation;

import org.springframework.stereotype.Component;
import put.apl.algorithms.graphs.GraphResult;
import put.apl.algorithms.graphs.data.GraphRepresentationInterface;

import java.util.Map;

@Component("Get All Relations")
public class GetAllRelations implements GraphAlgorithm {

    public GraphResult run(GraphRepresentationInterface graph) {
        graph.setOperations(0);
        int noOfVertices = graph.getVerticesNumber();
        for (int i = 0; i < noOfVertices; i++) {
            for (int j = 0; j < noOfVertices; j++) {
                graph.getEdge(i, j);
            }
            graph.getPredecessors(i);
        }
        return GraphResult.builder().memoryOccupancyInBytes(graph.getMemoryOccupancy()).tableAccessCount(graph.getOperations()).build();
    }

    @Override
    public void setParams(Map<String, String> params) {
    }
}
