package put.apl.algorithms.graphs.implementation;

import org.springframework.stereotype.Component;
import put.apl.algorithms.graphs.GraphResult;
import put.apl.algorithms.graphs.data.GraphRepresentation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// Returns empty list if cycle is not found
@Component("Hamiltonian Cycle")
public class HamiltonianCycle implements GraphAlgorithm  {

    private List<Integer> path;
    private GraphRepresentation graph;

    public GraphResult run(GraphRepresentation graph) {
        this.path = new ArrayList<Integer>();
        this.graph = graph;
        this.path.add(0);
        if (graph.getVerticesNumber() > 1) {
            if (hamiltonianCycle(1)) {
                return GraphResult.builder().path(path).build();
            }
            return GraphResult.builder().path(new ArrayList<Integer>()).build();
        }
        else {
            return GraphResult.builder().path(path).memoryOccupancyInBytes(graph.getMemoryOccupancy()).build();
        }
    }

    private boolean hamiltonianCycle(int pos) {
        if (pos == graph.getVerticesNumber()) {
            int[] successors = graph.getSuccessors(this.path.get(pos-1));
            for (int s : successors) {
                if (s == 0) {
                    return true;
                }
            }
            return false;
        }
        for (int i = 1; i < graph.getVerticesNumber(); i++) {
            if (graph.getEdge(i, this.path.get(pos-1)) != 0 && !path.contains(i)) {
                path.add(i);
                if (hamiltonianCycle(pos + 1)) {
                    return true;
                }
                path.remove(path.size() - 1);
            }
        }
        return false;
    }

    @Override
    public void setParams(Map<String, String> params) {
    }
}
