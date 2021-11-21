package put.apl.algorithms.graphs.implementation;

import put.apl.algorithms.graphs.data.GraphRepresentation;

import java.util.*;

public class BreadthFirstSearch implements GraphAlgorithm<List<Integer>> {

    private boolean[] visited;
    private List<Integer> path;
    private int noOfVertices;
    private Queue<Integer> queue;

    public List<Integer> run(GraphRepresentation graph, boolean... flags) {
        boolean forceConnectedGraph = flags.length > 0 && flags[0];
        visited = new boolean[noOfVertices];
        path =  new ArrayList<Integer>();
        queue = new LinkedList<Integer>();
        for (int i = 0; i < noOfVertices; i++) {
            visited[i] = false;
        }
        // For non-connected graphs BFS procedure will run multiple times
        if (!forceConnectedGraph) {
            for (int i = 0; i < noOfVertices; i++) {
                if (!visited[i]) {
                    breadthFirstSearch(graph, i);
                }
            }
        } else {
            breadthFirstSearch(graph, 0);
        }
        return path;
    }

    private void breadthFirstSearch(GraphRepresentation graph, int id) {
       int[] successors = graph.getSuccessors(id);
       visited[id] = true;
       path.add(id);
       for (int i = 0; i < successors.length; i++) {
           queue.add(successors[i]);
       }
       while (!queue.isEmpty()) {
           int nextId = queue.poll();
           if (!visited[nextId]) {
               breadthFirstSearch(graph, nextId);
           }
       }
    }

    @Override
    public void setParams(Map<String, String> params) {
        if (params.containsKey("noOfVertices"))
            noOfVertices = Integer.parseInt(params.get("noOfVertices"));
    }
}
