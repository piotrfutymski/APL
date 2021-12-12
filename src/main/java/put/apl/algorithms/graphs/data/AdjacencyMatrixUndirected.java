package put.apl.algorithms.graphs.data;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

@Component("Adjacency Matrix Undirected")
public class AdjacencyMatrixUndirected extends AdjacencyMatrix {

    public AdjacencyMatrixUndirected(String input) {
        super(input);
    }

    public AdjacencyMatrixUndirected(int[][] matrix) {
        super(matrix);
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
