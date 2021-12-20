package put.apl.algorithms.graphs.implementation;

import org.springframework.stereotype.Component;
import put.apl.algorithms.graphs.GraphResult;
import put.apl.algorithms.graphs.data.GraphRepresentation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// Returns empty list if cycle is not found
@Component("All Hamiltonian Cycles")
public class AllHamiltonianCycles implements GraphAlgorithm  {

    private List<ArrayList<Integer>> paths;
    private ArrayList<Integer> currentPath;
    private GraphRepresentation graph;

    public GraphResult run(GraphRepresentation graph) {
        graph.setOperations(0);
        currentPath = new ArrayList<Integer>();
        paths = new ArrayList<ArrayList<Integer>>();
        this.graph = graph;
        currentPath.add(0);
        if (graph.getVerticesNumber() > 1) {
            hamiltonianCycle(1);
            return GraphResult.builder().multiplePaths(paths).memoryOccupancyInBytes(graph.getMemoryOccupancy()).build();
        }
        else {
            paths.add(currentPath);
            return GraphResult.builder().multiplePaths(paths).memoryOccupancyInBytes(graph.getMemoryOccupancy()).build();
        }
    }

    private boolean hamiltonianCycle(int pos) {
        if (pos == graph.getVerticesNumber()) {
            int[] successors = graph.getSuccessors(this.currentPath.get(pos-1));
            for (int s : successors) {
                if (s == 0) {
                    ArrayList<Integer> newPath = new ArrayList<Integer>(currentPath);
                    paths.add(newPath);
                    return false;
                }
            }
            return false;
        }
        for (int i = 1; i < graph.getVerticesNumber(); i++) {
            if ((graph.getEdge(i, currentPath.get(pos-1))!=0) && !currentPath.contains(i)) {
                currentPath.add(i);
                if (hamiltonianCycle(pos + 1)) {
                    return true;
                }
                currentPath.remove(currentPath.size() - 1);
            }
        }
        return false;
    }

    @Override
    public void setParams(Map<String, String> params) {
    }
}
