package put.apl.algorithms.graphs.data;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("Adjacency Matrix Directed")
public class AdjacencyMatrixDirected extends AdjacencyMatrix {

    private final static int START = 1;

    public AdjacencyMatrixDirected(List<List<Integer>> input) throws InterruptedException {
        super(input);
    }

    public AdjacencyMatrixDirected(int[][] matrix) throws InterruptedException {
        super(matrix);
    }

    public AdjacencyMatrixDirected() {
        super();
    }

    @Override
    protected int getAllEdgesInner(int edgeNumber, int i, int j, int[][] result) {
        var edge = new int[2];
        var revEdge = new int[2];
        int edgesAdded=0;
        if (checkIfEdge(i,j))
        {
            edge[0] = i;
            edge[1] = j;
            result[edgeNumber++] = edge;
            edgesAdded+=1;
        }
        if (checkIfEdge(j,i))
        {
            revEdge[0] = j;
            revEdge[1] = i;
            result[edgeNumber] = revEdge;
            edgesAdded+=1;
        }
        return edgesAdded;
    }

    @Override
    public void fillEdge(int start, int end) {
        matrix[start][end] = START;
    }

    @Override
    public boolean checkIfEdge(int start, int end) {
        return getEdgeInner(start,end) == START;
    }

    @SneakyThrows
    @Override
    public GraphRepresentationInterface clone() {
        return new AdjacencyMatrixDirected(matrix.clone());
    }
}
