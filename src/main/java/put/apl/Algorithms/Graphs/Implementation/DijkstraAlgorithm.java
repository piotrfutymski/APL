package put.apl.algorithms.graphs.implementation;

import put.apl.algorithms.graphs.data.GraphRepresentation;

import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

public class DijkstraAlgorithm  implements GraphAlgorithm<List<Integer>>  {

    static int DISTANCE_TBR = 1;

    PriorityQueue<Integer> queue;

    GraphRepresentation graph;

    HashSet<Integer> visited;

    int[] dist;

    @Override
    public List<Integer> run(GraphRepresentation graph) {
        this.graph = graph;
        int verticesSize = graph.getAllEdges().length;
        dist = new int[verticesSize];
        visited = new HashSet<Integer>();
        queue = new PriorityQueue<>();

        queue.add(0);
        dist[0] = 0;
        for (int i=1; i < verticesSize; i++)
        {
            dist[i] = Integer.MAX_VALUE;
        }
        while (visited.size() != verticesSize)
        {
            int vertice = queue.remove();

            visited.add(vertice);
            propagateNeighbours(vertice);
        }
        return null;
    }

    private void propagateNeighbours(int vertice)
    {
        for (int node : graph.getSuccessors(vertice))
        {
            if (!visited.contains(node))
            {
                int distance = dist[vertice] + graph.getAllEdges()[vertice][node];

                if (distance < dist[node])
                    dist[node] = distance;

                queue.add(node);

            }
        }
    }
}
