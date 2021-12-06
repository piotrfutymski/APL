package put.apl.algorithms.graphs.implementation;

import org.springframework.stereotype.Component;
import put.apl.algorithms.graphs.data.GraphRepresentation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component("Depth First Search")
public class DepthFirstSearch implements GraphAlgorithm<List<Integer>> {

    private boolean[] visited;
    private int noOfVertices;
    private List<Integer> path;
    private boolean forceConnectedGraph = false;

    public List<Integer> run(GraphRepresentation graph) {
        visited = new boolean[noOfVertices];
        path =  new ArrayList<Integer>();
        for (int i = 0; i < noOfVertices; i++) {
            visited[i] = false;
        }
        // For non-connected graphs DFS procedure will run multiple times
        if (!forceConnectedGraph) {
            for (int i = 0; i < noOfVertices; i++) {
                if (!visited[i]) {
                    depthFirstSearch(graph, i);
                }
            }
        } else {
            depthFirstSearch(graph, 0);
        }
        return path;
    }

    private void depthFirstSearch(GraphRepresentation graph, int id) {
       int[] successors = graph.getSuccessors(id);
       visited[id] = true;
       path.add(id);
       for (int i = 0; i < successors.length; i++) {
           if (!visited[successors[i]]) {
               depthFirstSearch(graph, successors[i]);
           }
       }
    }

    @Override
    public void setParams(Map<String, String> params) {
        if (params.containsKey("noOfVertices"))
            noOfVertices = Integer.parseInt(params.get("noOfVertices"));
        if (params.containsKey("forceConnected"))
            forceConnectedGraph = Boolean.parseBoolean(params.get("forceConnected"));
    }
}
