package put.apl.algorithms.graphs.implementation;

import org.springframework.stereotype.Component;
import put.apl.algorithms.graphs.GraphResult;
import put.apl.algorithms.graphs.data.GraphRepresentationInterface;
import put.apl.algorithms.graphs.data.GraphRepresentationWeightedInterface;

import java.util.*;

@Component("Dijkstra Algorithm")
public class DijkstraAlgorithm  extends GraphAlgorithm {

    PriorityQueue<Integer> queue;

    GraphRepresentationWeightedInterface graph_weighted;

    //HashSet<Integer> visited;

    boolean[] visited;
    int visited_number;

    Integer[] dist;

    @Override
    public GraphResult run(GraphRepresentationInterface graph) throws InterruptedException  {

        GraphRepresentationWeightedInterface graph_weighted = (GraphRepresentationWeightedInterface) graph;

        graph_weighted.setOperations(0);
        this.graph_weighted = graph_weighted;
        int verticesSize = graph_weighted.getVerticesNumber();
        dist = new Integer[verticesSize];
        visited = new boolean[verticesSize];

        queue = new PriorityQueue<>();

        queue.add(0);
        dist[0] = 0;
        visited_number=0;
        for (int i=1; i < verticesSize; i++)
        {
            escape();
            dist[i] = Integer.MAX_VALUE;
        }
        while (visited_number < verticesSize)
        {
            escape();
            int vertice = queue.remove();
            if (!visited[vertice])
            {
                visited_number+=1;
                visited[vertice]=true;
                propagateNeighbours(vertice);
            }

        }
        return GraphResult.builder().path(Arrays.asList(dist)).memoryOccupancyInBytes(graph_weighted.getMemoryOccupancy()).tableAccessCount(graph_weighted.getOperations()).build();
    }

    private void propagateNeighbours(int vertex) throws InterruptedException {
        for (var edge : graph_weighted.getSuccessorsWeighted(vertex))
        {
            int node = edge.vertex;
            int weight = edge.weight;
            escape();
            if (!visited[node])
            {
                int distance = dist[vertex] + weight;

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
