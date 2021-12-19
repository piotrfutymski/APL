package put.apl.algorithms.graphs.data;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component("Weighted Adjacency Matrix Undirected")
public class AdjacencyMatrixUndirectedWeighted extends AdjacencyMatrix {

    public AdjacencyMatrixUndirectedWeighted(String input) {
        super(input);
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
        if (checkIfSTART(getEdge(i,j)))
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
