package put.apl.algorithms.graphs.data;


import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component("Incidence Matrix Directed Weighted")
public class IncidenceMatrixDirectedWeighted extends IncidenceMatrix {

    public IncidenceMatrixDirectedWeighted(List<List<Integer>> input, List<List<Integer>> weights) {
        loadFromIncidenceList(input, weights);
    }

    public IncidenceMatrixDirectedWeighted(int[][] matrix) {
        super(matrix);
    }

    public IncidenceMatrixDirectedWeighted() {
        super();
    }


    public void loadFromIncidenceList(List<List<Integer>> input, List<List<Integer>> weights) {
        verticesSize = input.size();
        matrix = new int[verticesSize][];
        edgesSize = 0;
        for (List<Integer> integers : input) {
            edgesSize += integers.size();
        }
        for (int i=0; i< verticesSize; i++)
        {
            matrix[i] = new int[edgesSize];
        }
        int edgeNumber=0;
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).size(); j++) {
                matrix[i][edgeNumber] = weights.get(i).get(j);
                matrix[input.get(i).get(j)][edgeNumber] = -1 * weights.get(i).get(j);
                edgeNumber++;
            }
        }
    }

    @Override
    public void fillEdge(int edge, int start, int end) {
        //not used
    }

    @Override
    public boolean checkIfSTART(int index1, int index2) {
        return getEdgeInner(index1, index2) > 0;
    }

    @Override
    public boolean checkIfEND(int index1, int index2) {
        return getEdgeInner(index1, index2) < 0;
    }


    @Override
    public GraphRepresentation clone() {
        return new IncidenceMatrixDirectedWeighted(matrix.clone());
    }
}
