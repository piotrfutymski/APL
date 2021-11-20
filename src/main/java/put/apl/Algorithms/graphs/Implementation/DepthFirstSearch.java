package put.apl.algorithms.graphs.implementation;

import put.apl.algorithms.graphs.data.GraphRepresentation;

import java.util.ArrayList;
import java.util.List;

public class DepthFirstSearch implements GraphAlgorithm<List<Integer>> {

    private boolean[] visited;
    private List<Integer> path;

    public List<Integer> run(GraphRepresentation graph, boolean... flags) {
        boolean forceConnectedGraph = flags.length > 0 && flags[0];
        visited = new boolean[graph.getAllEdges().length];
        path =  new ArrayList<Integer>();
        for (int i = 0; i < graph.getAllEdges().length; i++) {
            visited[i] = false;
        }
        // For non-connected graphs DFS procedure will run multiple times
        if (!forceConnectedGraph) {
            for (int i = 0; i < graph.getAllEdges().length; i++) {
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
}
