package put.apl.algorithms.graphs.implementation;

import org.springframework.stereotype.Component;
import put.apl.algorithms.graphs.GraphResult;
import put.apl.algorithms.graphs.data.GraphRepresentationInterface;

import java.util.Map;

@Component("Get Successors")
public class GetSuccessors extends GraphAlgorithm {

    public GraphResult run(GraphRepresentationInterface graph) throws InterruptedException  {
        graph.setOperations(0);
        int noOfVertices = graph.getVerticesNumber();
        for (int i = 0; i < noOfVertices; i++) {
            graph.getSuccessors(i);
        }
        return GraphResult.builder().memoryOccupancyInBytes(graph.getMemoryOccupancy()).tableAccessCount(graph.getOperations()).build();
    }

    @Override
    public void setParams(Map<String, String> params) {
    }
}
