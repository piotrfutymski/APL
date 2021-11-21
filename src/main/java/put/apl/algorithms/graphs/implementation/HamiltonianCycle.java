package put.apl.algorithms.graphs.implementation;

import put.apl.algorithms.graphs.data.GraphRepresentation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// Returns empty list if cycle is not found
public class HamiltonianCycle implements GraphAlgorithm<List<Integer>>  {

    private List<Integer> path;
    private int noOfVertices;
    private GraphRepresentation graph;

    public List<Integer> run(GraphRepresentation graph, boolean... flags) {
        this.path = new ArrayList<Integer>();
        this.graph = graph;
        this.path.add(0);
        if (noOfVertices > 1) {
            if (hamiltonianCycle(1)) {
                return this.path;
            }
            return new ArrayList<Integer>();
        }
        else {
            return this.path;
        }
    }

    private boolean hamiltonianCycle(int next) {
        if (next == noOfVertices) {
            int[] successors = graph.getSuccessors(next);
            for (int s : successors) {
                if (s == 0) {
                    return true;
                }
            }
            return false;
        }
        for (int i = 1; i < noOfVertices; i++) {
            if ((graph.getRelationBetween(i, next).equals("successor") ||
                graph.getRelationBetween(i, next).equals("incident")) && !path.contains(i)) {
                path.add(i);
                if (hamiltonianCycle(next + 1)) {
                    return true;
                }
                path.remove(path.size() - 1);
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
