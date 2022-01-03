package put.apl.algorithms.graphs.implementation;

import org.springframework.stereotype.Component;
import put.apl.algorithms.graphs.GraphResult;
import put.apl.algorithms.graphs.data.GraphRepresentationInterface;

import java.util.Map;

@Component("Get Memory Occupancy")
public class GetMemoryOccupancy extends GraphAlgorithm {

    public GraphResult run(GraphRepresentationInterface graph) throws InterruptedException {
        graph.setOperations(0);
        return GraphResult.builder().memoryOccupancyInBytes(graph.getMemoryOccupancy()).tableAccessCount(graph.getOperations()).memoryOccupancyInBytes(graph.getMemoryOccupancy()).build();
    }

    @Override
    public void setParams(Map<String, String> params) {
    }
}
