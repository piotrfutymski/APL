package put.apl.algorithms.graphs.data;

import org.springframework.stereotype.Component;

import java.util.List;

@Component("Adjacency Matrix Directed")
public class AdjacencyMatrixDirected extends AdjacencyMatrix {

    private final static int START = 1;
    private final static int END = -1;

    public AdjacencyMatrixDirected(List<List<Integer>> input) {

        super(input);
    }

    public AdjacencyMatrixDirected(int[][] matrix) {
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
        if (checkIfSTART(i,j))
        {
            edge[0] = i;
            edge[1] = j;
            result[edgeNumber++] = edge;
            edgesAdded+=1;
        }
        if (checkIfSTART(j,i))
        {
            revEdge[0] = j;
            revEdge[1] = i;
            result[edgeNumber++] = revEdge;
            edgesAdded+=1;
        }
        return edgesAdded;
    }

    @Override
    public void fillEdge(int start, int end) {
        matrix[start][end] = START;
        //matrix[end][start] = END;
    }

    @Override
    public boolean checkIfSTART(int start, int end) {
        return getEdgeInner(start,end) == START;
    }

    @Override
    public boolean checkIfEND(int start, int end) {
        return getEdgeInner(start,end) == END;
    }


    @Override
    public GraphRepresentation clone() {
        return new AdjacencyMatrixDirected(matrix.clone());
    }
}
