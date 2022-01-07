package put.apl.algorithms.graphs.implementation;

import org.springframework.stereotype.Component;
import put.apl.algorithms.graphs.GraphResult;
import put.apl.algorithms.graphs.data.GraphRepresentationInterface;
import put.apl.algorithms.graphs.data.GraphRepresentationWeightedInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component("Prims Algorithm")
public class PrimsAlgorithm extends GraphAlgorithm  {


    List<Integer> MST;

    int[] key;

    boolean[] visited;

    int verticesSize;

    @Override
    public GraphResult run(GraphRepresentationInterface graph) throws InterruptedException  {

        GraphRepresentationWeightedInterface graph_weighted = (GraphRepresentationWeightedInterface) graph;

        graph_weighted.setOperations(0);
        verticesSize = graph_weighted.getVerticesNumber();
        key = new int[verticesSize];
        MST = new ArrayList<>();
        visited = new boolean[verticesSize];

        for (int i=0; i < verticesSize; i++)
        {
            escape();
            key[i] = Integer.MAX_VALUE;
            visited[i] = false;
            MST.add(0);
        }

        key[0] = 0;
        MST.set(0, -1);
        for (int count = 0; count < verticesSize - 1; count++) {

            int u = findKey();

            visited[u] = true;
            for (var v : graph_weighted.getSuccessorsWeighted(u))
            {
                int vertex = v.vertex;
                int weight = v.weight;
                escape();
                if (!visited[vertex] && weight < key[vertex])
                {
                    MST.set(vertex, u);
                    key[vertex] = graph_weighted.getEdge(u,vertex);
                }
            }
        }
        return GraphResult.builder().path(MST).memoryOccupancyInBytes(graph_weighted.getMemoryOccupancy()).tableAccessCount(graph_weighted.getOperations()).build();
    }
    private int findKey() throws InterruptedException {
        // Initialize min value
        int min = Integer.MAX_VALUE, min_index = -1;

        for (int v = 0; v < verticesSize; v++)
        {
            escape();
            if (!visited[v] && key[v] < min) {
                min = key[v];
                min_index = v;
            }
        }

        return min_index;
    }

    @Override
    public void setParams(Map<String, String> params) {

    }

}
