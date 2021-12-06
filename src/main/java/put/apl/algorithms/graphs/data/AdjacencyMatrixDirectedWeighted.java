package put.apl.algorithms.graphs.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class AdjacencyMatrixDirectedWeighted extends AdjacencyMatrix {


    @Override
    public void fillEdge(int start, int end) {
        Random rand = new Random();
        int random = rand.nextInt(verticesSize);
        matrix[start][end] = random;
        matrix[end][start] = -1 * random;
    }

    @Override
    public boolean checkIfSTART(int number) {
        return number > 0;
    }

    @Override
    public boolean checkIfEND(int number) {
        return number < 0;
    }

    public AdjacencyMatrixDirectedWeighted(String input) {
        super(input);
    }

    public AdjacencyMatrixDirectedWeighted(int[][] matrix) {
        super(matrix);
    }

    @Override
    public GraphRepresentation clone() {
        return new AdjacencyMatrixDirectedWeighted(matrix.clone());
    }
}
