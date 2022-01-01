package put.apl.algorithms.graphs.implementation;

import org.springframework.stereotype.Component;
import put.apl.algorithms.graphs.GraphResult;
import put.apl.algorithms.graphs.data.GraphRepresentation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component("Euler Cycle Finding Algorithm")
public class EulerCycleAlgorithm extends GraphAlgorithm  {

    GraphRepresentation graph;
    boolean[][] visitedEdges;
    List<Integer> stack;

    @Override
    public GraphResult run(GraphRepresentation graph) throws InterruptedException {
        graph.setOperations(0);
        this.graph = graph;
        int verticesSize = graph.getVerticesNumber();
        stack = new ArrayList<Integer>();
        visitedEdges = new boolean[verticesSize][];
        for (int i=0; i<verticesSize; i++)
        {
            visitedEdges[i] = new boolean[verticesSize];
            for (int j=0; j<verticesSize; j++) {
                escape();
                visitedEdges[i][j] = false;
            }
        }
        DFSEuler(0);
        return GraphResult.builder().path(stack).memoryOccupancyInBytes(graph.getMemoryOccupancy()).tableAccessCount(graph.getOperations()).build();
    }

    private void DFSEuler(int vertex) throws InterruptedException {
        for (int i : graph.getSuccessors(vertex))
        {
            escape();
            if (!visitedEdges[vertex][i])
            {
                visitedEdges[vertex][i]=true;
                DFSEuler(i);
            }
        }
        stack.add(vertex);
    }

    @Override
    public void setParams(Map<String, String> params) {

    }
}
