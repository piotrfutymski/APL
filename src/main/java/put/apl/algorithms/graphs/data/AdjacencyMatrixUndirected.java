package put.apl.algorithms.graphs.data;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("Adjacency Matrix Undirected")
public class AdjacencyMatrixUndirected extends AdjacencyMatrix {

    public AdjacencyMatrixUndirected(List<List<Integer>> input) throws InterruptedException {
        super(input);
    }

    public AdjacencyMatrixUndirected(int[][] matrix) throws InterruptedException {
        super(matrix);
    }

    public AdjacencyMatrixUndirected() {
        super();
    }

    @Override
    protected int getAllEdgesInner(int edgeNumber, int i, int j, int[][] result) {
        var edge = new int[2];
        int edgesAdded=0;
        if (checkIfSTART(i,j))
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
    public boolean checkIfSTART(int start, int end) {
        return getEdgeInner(start, end) == START;
    }

    @Override
    public boolean checkIfEND(int start, int end) {
        return getEdgeInner(start, end) == END;
    }


    @SneakyThrows
    @Override
    public GraphRepresentationInterface clone() {
        return new AdjacencyMatrixUndirected(matrix.clone());
    }
}
