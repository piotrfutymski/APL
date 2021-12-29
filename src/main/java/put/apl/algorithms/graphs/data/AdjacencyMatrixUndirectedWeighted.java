package put.apl.algorithms.graphs.data;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component("Weighted Adjacency Matrix Undirected")
public class AdjacencyMatrixUndirectedWeighted extends AdjacencyMatrix {

    public AdjacencyMatrixUndirectedWeighted(List<List<Integer>> input, List<List<Integer>> weights) {
        loadFromIncidenceList(input, weights);
    }

    private void loadFromIncidenceList(List<List<Integer>> input, List<List<Integer>> weights) {
        edgesNumber = 0;
        verticesNumber = input.size();
        matrix = new int[verticesNumber][];
        for (int i = 0; i < verticesNumber; i++)
            matrix[i] = new int[verticesNumber];
        for (int i = 0; i < verticesNumber; i++) {
            for (int j = 0; j < input.get(i).size(); j++) {
                matrix[i][input.get(i).get(j)] = weights.get(i).get(j);
                matrix[input.get(i).get(j)][i] = weights.get(i).get(j);
                edgesNumber+=1;
            }
        }
    }

    public AdjacencyMatrixUndirectedWeighted(int[][] matrix) {
        super(matrix);
    }

    public AdjacencyMatrixUndirectedWeighted() {
        super();
    }

    @Override
    protected int getAllEdgesInner(int edgeNumber, int i, int j, int[][] result) {
        var edge = new int[2];
        int edgesAdded=0;
        if (checkIfSTART(i, j))
        {
            edge[0] = i;
            edge[1] = j;
            result[edgeNumber++] = edge;
            edgesAdded+=1;
        }
        return edgesAdded;
    }

    @Override
    public void fillEdge(int start, int end) {
    }

    @Override
    public boolean checkIfSTART(int start, int end) {
        return getEdgeInner(start, end) > 0;
    }

    @Override
    public boolean checkIfEND(int start, int end) {
        return getEdgeInner(start, end) > 0;
    }

    @Override
    public GraphRepresentation clone() {
        return new AdjacencyMatrixUndirectedWeighted(matrix.clone());
    }
}
