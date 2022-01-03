package put.apl.algorithms.graphs.implementation;

import org.springframework.stereotype.Component;
import put.apl.algorithms.graphs.GraphResult;
import put.apl.algorithms.graphs.data.GraphRepresentationInterface;

import java.util.*;

@Component("Dijkstra Algorithm")
public class DijkstraAlgorithm  extends GraphAlgorithm {

    PriorityQueue<Integer> queue;

    GraphRepresentationInterface graph;

    HashSet<Integer> visited;

    Integer[] dist;

    @Override
    public GraphResult run(GraphRepresentationInterface graph) throws InterruptedException {
        graph.setOperations(0);
        this.graph = graph;
        int verticesSize = graph.getVerticesNumber();
        dist = new Integer[verticesSize];
        visited = new HashSet<Integer>();
        queue = new PriorityQueue<>();

        queue.add(0);
        dist[0] = 0;
        for (int i=1; i < verticesSize; i++)
        {
            escape();
            dist[i] = Integer.MAX_VALUE;
        }
        while (visited.size() != verticesSize)
        {
            escape();
            int vertice = queue.remove();
            visited.add(vertice);
            propagateNeighbours(vertice);
        }
        return GraphResult.builder().path(Arrays.asList(dist)).memoryOccupancyInBytes(graph.getMemoryOccupancy()).tableAccessCount(graph.getOperations()).build();
    }

    private void propagateNeighbours(int vertice) throws InterruptedException {
        for (int node : graph.getSuccessors(vertice))
        {
            escape();
            if (!visited.contains(node))
            {
                int distance = dist[vertice] + graph.getEdge(vertice, node);

                if (distance < dist[node])
                    dist[node] = distance;
                queue.add(node);
            }
        }
    }
    @Override
    public void setParams(Map<String, String> params) {
    }
}
