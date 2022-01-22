package put.apl.algorithms.graphs.implementation;

import org.springframework.stereotype.Component;
import put.apl.algorithms.graphs.GraphResult;
import put.apl.algorithms.graphs.data.GraphRepresentationInterface;

import java.util.*;

@Component("Breadth First Search")
public class BreadthFirstSearch extends GraphAlgorithm {

    private boolean[] visited;
    private List<Integer> path;
    private Queue<Integer> queue;
    private boolean forceConnectedGraph = false;

    public GraphResult run(GraphRepresentationInterface graph) throws InterruptedException {
        graph.setOperations(0);
        visited = new boolean[graph.getVerticesNumber()];
        path = new ArrayList<Integer>();
        queue = new LinkedList<Integer>();
        // For non-connected graphs BFS procedure will run multiple times
        if (!forceConnectedGraph) {
            for (int i = 0; i < graph.getVerticesNumber(); i++) {
                escape();
                if (!visited[i]) {
                    breadthFirstSearch(graph, i);
                }
            }
        } else {
            breadthFirstSearch(graph, 0);
        }
        return GraphResult.builder().path(path).memoryOccupancyInBytes(graph.getMemoryOccupancy()).tableAccessCount(graph.getOperations()).build();
    }

    private void breadthFirstSearch(GraphRepresentationInterface graph, int id) throws InterruptedException {
       visited[id] = true;
       path.add(id);
        for (int successor : graph.getSuccessors(id)) {
            escape();
            queue.add(successor);
        }
       while (!queue.isEmpty()) {
           escape();
           int nextId = queue.poll();
           if (!visited[nextId]) {
               breadthFirstSearch(graph, nextId);
           }
       }
    }

    @Override
    public void setParams(Map<String, String> params) {
        if (params.containsKey("forceConnected"))
            forceConnectedGraph = Boolean.parseBoolean(params.get("forceConnected"));
    }
}
