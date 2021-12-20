package put.apl.algorithms.graphs.data;


import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component("Incidence Matrix Directed Weighted")
public class IncidenceMatrixDirectedWeighted extends IncidenceMatrix {

    private final static int START=1;
    private final static int END=-1;

    public IncidenceMatrixDirectedWeighted(List<List<Integer>> input) {
        super(input);
    }

    public IncidenceMatrixDirectedWeighted(int[][] matrix) {
        super(matrix);
    }

    public IncidenceMatrixDirectedWeighted() {
        super();
    }

    @Override
    public void fillEdge(int edge, int start, int end) {
        Random rand = new Random();
        int random = rand.nextInt(this.verticesSize) + 1;

        matrix[start][edge] = random;
        matrix[end][edge] = random * -1;
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
        return new IncidenceMatrixDirectedWeighted(matrix.clone());
    }
}
