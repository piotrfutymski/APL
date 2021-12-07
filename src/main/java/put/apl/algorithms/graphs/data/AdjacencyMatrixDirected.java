package put.apl.algorithms.graphs.data;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

@Component("Adjacency Matrix Directed")
public class AdjacencyMatrixDirected extends AdjacencyMatrix {

    private final static int START=1;
    private final static int END=-1;

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

    public AdjacencyMatrixDirected(String input) {

        super(input);
    }

    public AdjacencyMatrixDirected(int[][] matrix) {
        super(matrix);
    }


    @Override
    public GraphRepresentation clone() {
        return new AdjacencyMatrixDirected(matrix.clone());
    }
}
