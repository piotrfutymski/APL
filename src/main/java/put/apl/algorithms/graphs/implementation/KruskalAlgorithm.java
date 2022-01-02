package put.apl.algorithms.graphs.implementation;

import org.springframework.stereotype.Component;
import put.apl.algorithms.graphs.GraphResult;
import put.apl.algorithms.graphs.data.GraphRepresentationInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component("Kruskal Algorithm")
public class KruskalAlgorithm implements GraphAlgorithm  {

    static class Edge implements Comparable<Edge> {
        public Edge(int src, int dest, int weight)
        {
            this.src = src;
            this.dest = dest;
            this.weight = weight;
        }
        int src, dest, weight;

        public int compareTo(Edge compareEdge) {
            return this.weight - compareEdge.weight;
        }
    };

    static class subset {
        int parent, rank;
    };

    int find(subset[] subsets, int i) {
        if (subsets[i].parent != i)
            subsets[i].parent = find(subsets, subsets[i].parent);
        return subsets[i].parent;
    }

    void Union(subset[] subsets, int x, int y) {
        int xroot = find(subsets, x);
        int yroot = find(subsets, y);

        if (subsets[xroot].rank < subsets[yroot].rank)
            subsets[xroot].parent = yroot;
        else if (subsets[xroot].rank > subsets[yroot].rank)
            subsets[yroot].parent = xroot;
        else {
            subsets[yroot].parent = xroot;
            subsets[xroot].rank++;
        }
    }

    @Override
    public GraphResult run(GraphRepresentationInterface graph) {
        graph.setOperations(0);
        int verticesSize = graph.getVerticesNumber();
        Edge[] edges = new Edge[graph.getEdgesNumber()];
        int edges_size = 0;
        for (var edgeWeight : graph.getAllEdges())
            edges[edges_size++] = new Edge(edgeWeight[0], edgeWeight[1], edgeWeight[2]);

        Edge[] result = new Edge[verticesSize];
        Arrays.sort(edges);
        subset[] subsets = new subset[verticesSize];
        for (int i = 0; i < verticesSize; i++)
            subsets[i] = new subset();

        for (int v = 0; v < verticesSize; ++v) {
            subsets[v].parent = v;
            subsets[v].rank = 0;
        }
        int i = 0, edge_number = 0;
        while (edge_number < verticesSize - 1) {
            Edge next_edge = edges[i++];

            int first_subset = find(subsets, next_edge.src);
            int second_subset = find(subsets, next_edge.dest);

            if (first_subset != second_subset) {
                result[edge_number++] = next_edge;
                Union(subsets, first_subset, second_subset);
            }
        }
        List<Integer> final_result = new ArrayList<Integer>();
        for (Edge edge_result : result)
        {
            if (edge_result != null)
            {
                final_result.add(edge_result.src);
                final_result.add(edge_result.dest);
            }
        }
        return GraphResult.builder().path(final_result).memoryOccupancyInBytes(graph.getMemoryOccupancy()).tableAccessCount(graph.getOperations()).build();
    }

    @Override
    public void setParams(Map<String, String> params) {

    }
}
