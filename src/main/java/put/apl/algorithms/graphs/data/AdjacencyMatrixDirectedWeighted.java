package put.apl.algorithms.graphs.data;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component("Weighted Adjacency Matrix Directed")
public class AdjacencyMatrixDirectedWeighted extends AdjacencyMatrix {


    public AdjacencyMatrixDirectedWeighted(List<ArrayList<Integer>> input) {
        super(input);
    }

    public AdjacencyMatrixDirectedWeighted(int[][] matrix) {
        super(matrix);
    }

    public AdjacencyMatrixDirectedWeighted() {
        super();
    }

    @Override
    public void fillEdge(int start, int end) {
        Random rand = new Random();
        int random = rand.nextInt(verticesNumber);
        matrix[start][end] = random;
        //matrix[end][start] = -1 * random;
    }

    @Override
    public boolean checkIfSTART(int number) {
        return number > 0;
    }

    @Override
    public boolean checkIfEND(int number) {
        return number < 0;
    }



    @Override
    public GraphRepresentation clone() {
        return new AdjacencyMatrixDirectedWeighted(matrix.clone());
    }
}
