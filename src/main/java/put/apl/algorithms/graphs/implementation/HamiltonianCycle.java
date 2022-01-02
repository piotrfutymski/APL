package put.apl.algorithms.graphs.implementation;

import org.springframework.stereotype.Component;
import put.apl.algorithms.graphs.GraphResult;
import put.apl.algorithms.graphs.data.GraphRepresentationInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// Returns empty list if cycle is not found
@Component("Hamiltonian Cycle")
public class HamiltonianCycle implements GraphAlgorithm  {

    private List<Integer> path;
    private GraphRepresentationInterface graph;

    public GraphResult run(GraphRepresentationInterface graph) {
        graph.setOperations(0);
        this.path = new ArrayList<Integer>();
        this.graph = graph;
        this.path.add(0);
        if (graph.getVerticesNumber() > 1) {
            if (!hamiltonianCycle(1)) {
                path = new ArrayList<Integer>();
            }
        }
        return GraphResult.builder().path(path).memoryOccupancyInBytes(graph.getMemoryOccupancy()).tableAccessCount(graph.getOperations()).build();
    }

    private boolean hamiltonianCycle(int pos) {
        if (pos == graph.getVerticesNumber()) {
            int[] successors = graph.getSuccessors(this.path.get(pos-1));
            for (int s : successors) {
                if (s == this.path.get(0)) {
                    return true;
                }
            }
            return false;
        }
        for (var successor : graph.getSuccessors(this.path.get(pos-1)))
        {
            if (!path.contains(successor))
            {
                path.add(successor);
                if (hamiltonianCycle(pos+1))
                {
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
