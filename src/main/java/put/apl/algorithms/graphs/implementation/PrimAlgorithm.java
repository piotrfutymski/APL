package put.apl.algorithms.graphs.implementation;

import org.springframework.stereotype.Component;
import put.apl.algorithms.graphs.GraphResult;
import put.apl.algorithms.graphs.data.GraphRepresentation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component("Prim Algorithm")
public class PrimAlgorithm implements GraphAlgorithm  {

    GraphRepresentation graph;

    List<Integer> MST;

    int[] key;

    boolean[] visited;

    int verticesSize;

    @Override
    public GraphResult run(GraphRepresentation graph) {
        graph.setOperations(0);
        this.graph = graph;
        verticesSize = graph.getVerticesNumber();
        key = new int[verticesSize];
        MST = new ArrayList<>();
        visited = new boolean[verticesSize];

        for (int i=0; i < verticesSize; i++)
        {
            key[i] = Integer.MAX_VALUE;
            visited[i] = false;
            MST.add(0);
        }

        key[0] = 0;
        MST.set(0, -1);
        for (int count = 0; count < verticesSize - 1; count++) {

            int u = findKey();

            visited[u] = true;
            for (int v : graph.getSuccessors(u))
            {
                if (!visited[v] && graph.getEdge(u,v) < key[v])
                {
                    MST.set(v, u);
                    key[v] = graph.getEdge(u,v);
                }
            }
        }
        return GraphResult.builder().path(MST).memoryOccupancyInBytes(graph.getMemoryOccupancy()).tableAccessCount(graph.getOperations()).build();
    }
    private int findKey()
    {
        // Initialize min value
        int min = Integer.MAX_VALUE, min_index = -1;

        for (int v = 0; v < verticesSize; v++)
            if (!visited[v] && key[v] < min) {
                min = key[v];
                min_index = v;
            }
        return min_index;
    }

    @Override
    public void setParams(Map<String, String> params) {

    }

}
