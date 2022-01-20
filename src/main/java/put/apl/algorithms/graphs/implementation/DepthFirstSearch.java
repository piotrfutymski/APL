package put.apl.algorithms.graphs.implementation;

import org.springframework.stereotype.Component;
import put.apl.algorithms.graphs.GraphResult;
import put.apl.algorithms.graphs.data.GraphRepresentationInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component("Depth First Search")
public class DepthFirstSearch extends GraphAlgorithm {

    private boolean[] visited;
    private List<Integer> path;
    private boolean forceConnectedGraph = false;

    public GraphResult run(GraphRepresentationInterface graph) throws InterruptedException {
        graph.setOperations(0);
        visited = new boolean[graph.getVerticesNumber()];
        path = new ArrayList<Integer>();
        // For non-connected graphs DFS procedure will run multiple times
        if (!forceConnectedGraph) {
            for (int i = 0; i < graph.getVerticesNumber(); i++) {
                escape();
                if (!visited[i]) {
                    depthFirstSearch(graph, i);
                }
            }
        } else {
            depthFirstSearch(graph, 0);
        }
        return GraphResult.builder().path(path).memoryOccupancyInBytes(graph.getMemoryOccupancy()).tableAccessCount(graph.getOperations()).build();
    }

    private void depthFirstSearch(GraphRepresentationInterface graph, int id) throws InterruptedException {
       visited[id] = true;
       path.add(id);
        for (int successor : graph.getSuccessors(id)) {
            escape();
            if (!visited[successor]) {
                depthFirstSearch(graph, successor);
            }
        }
    }

    @Override
    public void setParams(Map<String, String> params) {
        if (params.containsKey("forceConnected"))
            forceConnectedGraph = Boolean.parseBoolean(params.get("forceConnected"));
    }
}
