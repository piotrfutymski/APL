package put.apl.algorithms.graphs.implementation;

import put.apl.algorithms.graphs.data.GraphRepresentation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

public class EulerCycleAlgorithm implements GraphAlgorithm<List<Integer>>  {



    GraphRepresentation graph;
    boolean[][] visitedEdges;
    List<Integer> stack;

    @Override
    public List<Integer> run(GraphRepresentation graph) {
        this.graph = graph;
        int verticesSize = graph.getAllEdges().length;
        stack = new ArrayList<Integer>();
        visitedEdges = new boolean[verticesSize][];
        for (int i=0; i<verticesSize; i++)
        {
            visitedEdges[i] = new boolean[verticesSize];
            for (int j=0; j<verticesSize; j++)
                visitedEdges[i][j] = false;
        }
        DFSEuler(0);
        return stack;
    }
    private void DFSEuler(int vertex)
    {
        for (int i : graph.getSuccessors(vertex))
        {
            if (!visitedEdges[vertex][i])
            {
                visitedEdges[vertex][i]=true;
                DFSEuler(i);
            }
        }
        stack.add(vertex);
    }
}
