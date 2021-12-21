package put.apl.algorithms.graphs.data;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

@Component("Adjacency Matrix Undirected")
public class AdjacencyMatrixUndirected extends AdjacencyMatrix {

    public AdjacencyMatrixUndirected(List<List<Integer>> input) {
        super(input);
    }

    public AdjacencyMatrixUndirected(int[][] matrix) {
        super(matrix);
    }

    public AdjacencyMatrixUndirected() {
        super();
    }

    @Override
    protected int getAllEdgesInner(int edgeNumber, int i, int j, int[][] result) {
        var edge = new int[2];
        int edgesAdded=0;
        if (checkIfSTART(getEdge(i,j)))
        {
            edge[0] = i;
            edge[1] = j;
            result[edgeNumber++] = edge;
            edgesAdded+=1;
        }
        return edgesAdded;
    }

    private final static int START=1;
    private final static int END=1;

    @Override
    public void fillEdge(int start, int end) {
        matrix[start][end] = START;
        matrix[end][start] = END;
    }

    @Override
    public boolean checkIfSTART(int number) {
        return number==START;
    }

    @Override
    public boolean checkIfEND(int number) {
        return number==END;
    }


    @Override
    public GraphRepresentation clone() {
        return new AdjacencyMatrixUndirected(matrix.clone());
    }
}
