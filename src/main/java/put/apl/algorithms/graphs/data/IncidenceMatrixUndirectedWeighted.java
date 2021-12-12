package put.apl.algorithms.graphs.data;


import java.util.Random;

public class IncidenceMatrixUndirectedWeighted extends IncidenceMatrix {

    private final static int START=1;
    private final static int END=1;

    public IncidenceMatrixUndirectedWeighted(String input) {
        super(input);
    }

    public IncidenceMatrixUndirectedWeighted(int[][] matrix) {
        super(matrix);
    }

    @Override
    public void fillEdge(int edge, int start, int end) {
        Random rand = new Random();
        int random = rand.nextInt(this.verticesSize);

        matrix[start][edge] = random;
        matrix[end][edge] = random;
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
        return new IncidenceMatrixUndirectedWeighted(matrix.clone());
    }
}
