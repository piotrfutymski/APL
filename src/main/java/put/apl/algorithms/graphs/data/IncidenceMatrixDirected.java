package put.apl.algorithms.graphs.data;


public class IncidenceMatrixDirected extends IncidenceMatrix {

    private final static int START=1;
    private final static int END=-1;

    public IncidenceMatrixDirected(String input) {
        super(input);
    }

    public IncidenceMatrixDirected(int[][] matrix) {
        super(matrix);
    }

    @Override
    public void fillEdge(int edge, int start, int end) {
        matrix[start][edge] = START;
        matrix[end][edge] = END;
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
        return new IncidenceMatrixDirected(matrix.clone());
    }
}
