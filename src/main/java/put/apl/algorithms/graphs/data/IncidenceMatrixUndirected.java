package put.apl.algorithms.graphs.data;

import org.springframework.stereotype.Component;

import java.util.List;

@Component("Incidence Matrix Undirected")
public class IncidenceMatrixUndirected extends IncidenceMatrix {

    private final static int START=1;
    private final static int END=1;

    public IncidenceMatrixUndirected(List<List<Integer>> input) {
        super(input);
    }

    public IncidenceMatrixUndirected(int[][] matrix) {
        super(matrix);
    }

    public IncidenceMatrixUndirected() {
        super();
    }

    @Override
    public void fillEdge(int edge, int start, int end) {
        matrix[start][edge] = START;
        matrix[end][edge] = END;
    }

    @Override
    public boolean checkIfSTART(int index1, int index2) {
        return getEdgeInner(index1,index2) == START;
    }

    @Override
    public boolean checkIfEND(int index1, int index2) {
        return getEdgeInner(index1,index2) == END;
    }


    @Override
    public GraphRepresentationInterface clone() {
        return new IncidenceMatrixUndirected(matrix.clone());
    }
}
