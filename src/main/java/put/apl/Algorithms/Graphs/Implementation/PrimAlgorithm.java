package put.apl.algorithms.graphs.implementation;

import put.apl.algorithms.graphs.data.GraphRepresentation;

import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

public class PrimAlgorithm implements GraphAlgorithm<List<Integer>>  {

    GraphRepresentation graph;

    int[] MST;

    int[] key;

    boolean[] visited;

    int verticesSize;

    @Override
    public List<Integer> run(GraphRepresentation graph) {
        final int CONSTANT_COST = 1;
        this.graph = graph;
        verticesSize = graph.getAllEdges().length;
        key = new int[verticesSize];
        MST = new int[verticesSize];
        visited = new boolean[verticesSize];

        for (int i=0; i < verticesSize; i++)
        {
            key[i] = Integer.MAX_VALUE;
            visited[i] = false;
        }

        key[0] = 0;
        MST[0] = -1;
        for (int count = 0; count < verticesSize - 1; count++) {

            int u = findKey();

            visited[u] = true;
            for (int v : graph.getSuccessors(u))
            {
                if (!visited[v] && graph.getAllEdges()[u][v] < key[v])
                {
                    MST[v] = u;
                    key[v] = graph.getAllEdges()[u][v];
                }

            }
        }
        return null;
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
}
