package put.apl.algorithms.graphs.implementation;

import put.apl.algorithms.graphs.data.GraphRepresentation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class TopologicalSort implements GraphAlgorithm<List<Integer>> {

    private boolean[] visited;
    GraphRepresentation graph;
    private List<Integer> path;
    private int noOfVertices;
    private List<Integer> stack;

    public List<Integer> run(GraphRepresentation graph, boolean... flags) {
        boolean checkForCycles = flags.length > 0 && flags[0];
        stack = new ArrayList<Integer>();
        this.graph = graph;
        visited = new boolean[noOfVertices];
        for (int i = 0; i < noOfVertices; i++) {
            visited[i] = false;
        }
        for (int i = 0; i < noOfVertices; i++) {
            if (!visited[i]) {
                topologicalSort(i);
            }
        }
        Collections.reverse(stack);
        if (checkForCycles && checkCyclic()) {
            return null;
        }
        return stack;
    }

    private void topologicalSort(int id) {
        visited[id] = true;
        int[] successors = graph.getSuccessors(id);
        for (int s : successors) {
            if (!visited[s]) {
                topologicalSort(s);
            }
        }
        stack.add(id);
    }

    private boolean checkCyclic() {
        for (int i = 0; i < noOfVertices; i++) {
            int[] successors = graph.getSuccessors(i);
            for (int s : successors) {
                if (stack.indexOf(s) < stack.indexOf(i)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void setParams(Map<String, String> params) {
        if (params.containsKey("noOfVertices"))
            noOfVertices = Integer.parseInt(params.get("noOfVertices"));
    }
}
