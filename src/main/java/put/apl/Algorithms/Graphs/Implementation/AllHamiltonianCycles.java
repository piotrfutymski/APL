package put.apl.algorithms.graphs.implementation;

import put.apl.algorithms.graphs.data.GraphRepresentation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// Returns empty list if cycle is not found
public class AllHamiltonianCycles implements GraphAlgorithm<List<ArrayList<Integer>>>  {

    private List<ArrayList<Integer>> paths;
    private ArrayList<Integer> currentPath;
    private int noOfVertices;
    private GraphRepresentation graph;

    public List<ArrayList<Integer>> run(GraphRepresentation graph, boolean... flags) {
        currentPath = new ArrayList<Integer>();
        this.graph = graph;
        currentPath.add(0);
        if (noOfVertices > 1) {
            hamiltonianCycle(1);
            return paths;
        }
        else {
            paths.add(currentPath);
            return paths;
        }
    }

    private boolean hamiltonianCycle(int next) {
        if (next == noOfVertices) {
            int[] successors = graph.getSuccessors(next);
            for (int s : successors) {
                if (s == 0) {
                    ArrayList<Integer> newPath = new ArrayList<Integer>(currentPath);
                    paths.add(newPath);
                    return false;
                }
            }
            return false;
        }
        for (int i = 1; i < noOfVertices; i++) {
            if ((graph.getRelationBetween(i, next).equals("successor") ||
                graph.getRelationBetween(i, next).equals("incident")) && !currentPath.contains(i)) {
                currentPath.add(i);
                if (hamiltonianCycle(next + 1)) {
                    return true;
                }
                currentPath.remove(currentPath.size() - 1);
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
