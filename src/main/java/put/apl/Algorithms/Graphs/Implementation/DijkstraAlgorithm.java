package put.apl.algorithms.graphs.implementation;

import put.apl.algorithms.graphs.data.GraphRepresentation;

import java.util.List;
import java.util.PriorityQueue;

public class DijkstraAlgorithm  implements GraphAlgorithm<List<Integer>>  {

    private static class Node implements Comparable<Node>
    {
        Node(int id, int distance)
        {
            this.id=id;
            this.distance=distance;
        }
        int id;
        int distance;

        @Override
        public int compareTo(Node o) {
            return Integer.compare(distance, o.distance);
        }
    }
    PriorityQueue<Node> queue;
    @Override
    public List<Integer> run(GraphRepresentation graph) {
        int verticesSize = graph.getAllEdges().length;
        queue = new PriorityQueue<>();
        queue.add(new Node(0, 0));
        for (int i=1; i < verticesSize; i++)
        {
            queue.add(new Node(i, Integer.MAX_VALUE));
        }

        return null;
    }
}
