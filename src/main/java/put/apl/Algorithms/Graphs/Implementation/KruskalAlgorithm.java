package put.apl.algorithms.graphs.implementation;

import put.apl.algorithms.graphs.data.GraphRepresentation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KruskalAlgorithm implements GraphAlgorithm<List<Integer>>  {

    GraphRepresentation graph;

    int verticesSize;

    static class Edge implements Comparable<Edge> {
        public Edge(int src, int dest, int weight)
        {
            this.src=src;
            this.dest=dest;
            this.weight=weight;
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
    public List<Integer> run(GraphRepresentation graph) {
            Edge[] edges = new Edge[verticesSize*verticesSize];
            int edges_size=0;
            for (int i = 0; i < verticesSize; i++)
                for (int j=0; j<verticesSize; j++)
                    if (graph.getAllEdges()[i][j] > 0)

                        edges[edges_size++] = new Edge(i, j, graph.getAllEdges()[i][j]);

            Edge[] result = new Edge[edges_size];
            // Sorting the edges
            Arrays.sort(edges);
            subset[] subsets = new subset[verticesSize];
            for (int i = 0; i < verticesSize; i++)
                subsets[i] = new subset();

            for (int v = 0; v < verticesSize; ++v) {
                subsets[v].parent = v;
                subsets[v].rank = 0;
            }
            int i = 0, e = 0;
            while (e < verticesSize - 1) {
                Edge next_edge = edges[i++];
                int x = find(subsets, next_edge.src);
                int y = find(subsets, next_edge.dest);
                if (x != y) {
                    result[e++] = next_edge;
                    Union(subsets, x, y);
                }
            }
            List<Integer> final_result = new ArrayList<Integer>();
            for (Edge edge_result : result)
            {
                final_result.add(edge_result.src);
                final_result.add(edge_result.dest);
            }
            return final_result;
        }
}
