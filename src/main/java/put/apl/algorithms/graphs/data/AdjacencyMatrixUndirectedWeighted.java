package put.apl.algorithms.graphs.data;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component("Weighted Adjacency Matrix Undirected")
public class AdjacencyMatrixUndirectedWeighted extends AdjacencyMatrix {

    public AdjacencyMatrixUndirectedWeighted(List<ArrayList<Integer>> input) {
        super(input);
    }

    public AdjacencyMatrixUndirectedWeighted(int[][] matrix) {
        super(matrix);
    }

    public AdjacencyMatrixUndirectedWeighted() {
        super();
    }

    @Override
    public void fillEdge(int start, int end) {
        Random rand = new Random();
        int random = rand.nextInt(verticesNumber);
        matrix[start][end] = random;
        matrix[end][start] = random;
    }

    @Override
    public boolean checkIfSTART(int number) {
        return number > 0;
    }

    @Override
    public boolean checkIfEND(int number) {
        return number > 0;
    }


    @Override
    public GraphRepresentation clone() {
        return new AdjacencyMatrixUndirectedWeighted(matrix.clone());
    }
}
