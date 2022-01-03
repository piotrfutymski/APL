package put.apl.algorithms.graphs.data;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("Weighted Adjacency Matrix Directed")
public class AdjacencyMatrixDirectedWeighted extends AdjacencyMatrix implements GraphRepresentationWeightedInterface {

    public AdjacencyMatrixDirectedWeighted(List<List<Integer>> input, List<List<Integer>> weights)  throws InterruptedException  {
        if (weights == null)
            loadFromIncidenceList(input);
        else
            loadFromIncidenceList(input, weights);
    }

    public AdjacencyMatrixDirectedWeighted(int[][] matrix) throws InterruptedException {
        super(matrix);
    }

    public AdjacencyMatrixDirectedWeighted() {
        super();
    }

    @Override
    public void loadFromIncidenceList(List<List<Integer>> input)
    {
        var weights = new ArrayList<List<Integer>>(input.size());
        for (var input_vertex : input)
        {
            var weights_vertex = new ArrayList<Integer>(input_vertex.size());
            for (var input_edge : input_vertex)
                weights_vertex.add(1);
            weights.add(weights_vertex);
        }
        loadFromIncidenceList(input, weights);
    }

    @Override
    public void loadFromIncidenceList(List<List<Integer>> input, List<List<Integer>> weights)
    {
        edgesNumber = 0;
        verticesNumber = input.size();
        matrix = new int[verticesNumber][];
        for (int i = 0; i < verticesNumber; i++)
            matrix[i] = new int[verticesNumber];
        for (int i = 0; i < verticesNumber; i++) {
            for (int j = 0; j < input.get(i).size(); j++) {
                matrix[i][input.get(i).get(j)] = weights.get(i).get(j);
                edgesNumber+=1;
            }
        }
    }

    @Override
    protected int getAllEdgesInner(int edgeNumber, int i, int j, int[][] result) {

        int edgesAdded=0;
        if (checkIfEdge(i,j))
        {
            var edge = new int[3];
            edge[0] = i;
            edge[1] = j;
            edge[2] = getEdgeInner(i,j);
            result[edgeNumber++] = edge;
            edgesAdded+=1;
        }
        if (checkIfEdge(j,i))
        {
            var revEdge = new int[3];
            revEdge[0] = j;
            revEdge[1] = i;
            revEdge[2] = getEdgeInner(j,i);
            result[edgeNumber] = revEdge;
            edgesAdded+=1;
        }
        return edgesAdded;
    }

    @Override
    public void fillEdge(int start, int end) {

    }

    @Override
    public boolean checkIfEdge(int start, int end) {
        return getEdgeInner(start, end) > 0;
    }

    @SneakyThrows
    @Override
    public GraphRepresentationInterface clone() {
        return new AdjacencyMatrixDirectedWeighted(matrix.clone());
    }
}
