package put.apl.algorithms.graphs.implementation;

import org.springframework.stereotype.Component;
import put.apl.algorithms.graphs.GraphResult;
import put.apl.algorithms.graphs.data.GraphRepresentationInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component("Topological Sort")
public class TopologicalSort extends GraphAlgorithm {

    private boolean[] visited;
    GraphRepresentationInterface graph;
    private List<Integer> path;
    private List<Integer> stack;
    private boolean checkForCycles = false;

    public GraphResult run(GraphRepresentationInterface graph)throws InterruptedException  {
        graph.setOperations(0);
        stack = new ArrayList<Integer>();
        this.graph = graph;
        visited = new boolean[graph.getVerticesNumber()];
        for (int i = 0; i < graph.getVerticesNumber(); i++) {
            escape();
            visited[i] = false;
        }
        for (int i = 0; i < graph.getVerticesNumber(); i++) {
            escape();
            if (!visited[i]) {
                topologicalSort(i);
            }
        }
        Collections.reverse(stack);
        if (checkForCycles && checkCyclic()) {
            return null;
        }
        return GraphResult.builder().path(stack).memoryOccupancyInBytes(graph.getMemoryOccupancy()).tableAccessCount(graph.getOperations()).build();
    }

    private void topologicalSort(int id) throws InterruptedException {
        visited[id] = true;
        int[] successors = graph.getSuccessors(id);
        if (successors != null) {
            for (int s : successors) {
                escape();
                if (!visited[s]) {
                    topologicalSort(s);
                }
            }
        }
        stack.add(id);
    }

    private boolean checkCyclic() throws InterruptedException {
        for (int i = 0; i < graph.getVerticesNumber(); i++) {
            int[] successors = graph.getSuccessors(i);
            for (int s : successors) {
                escape();
                if (stack.indexOf(s) < stack.indexOf(i)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void setParams(Map<String, String> params) {
        if (params.containsKey("checkForCycles"))
            checkForCycles = Boolean.parseBoolean(params.get("checkForCycles"));
    }
}
