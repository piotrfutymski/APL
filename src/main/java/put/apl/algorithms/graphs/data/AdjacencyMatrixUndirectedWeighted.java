package put.apl.algorithms.graphs.data;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("Weighted Adjacency Matrix Undirected")
public class AdjacencyMatrixUndirectedWeighted extends AdjacencyMatrix implements GraphRepresentationWeightedInterface {

    public AdjacencyMatrixUndirectedWeighted(List<List<Integer>> input, List<List<Integer>> weights)  throws InterruptedException  {
        if (weights == null)
            loadFromIncidenceList(input);
        else
            loadFromIncidenceList(input, weights);
    }

    public void loadFromIncidenceList(List<List<Integer>> input, List<List<Integer>> weights)  throws InterruptedException  {
        edgesNumber = 0;
        verticesNumber = input.size();
        matrix = new int[verticesNumber][];
        for (int i = 0; i < verticesNumber; i++) {
            escape();
            matrix[i] = new int[verticesNumber];
        }
        for (int i = 0; i < verticesNumber; i++) {
            for (int j = 0; j < input.get(i).size(); j++) {
                escape();
                matrix[i][input.get(i).get(j)] = weights.get(i).get(j);
                matrix[input.get(i).get(j)][i] = weights.get(i).get(j);
                edgesNumber+=1;
            }
        }
    }

    public AdjacencyMatrixUndirectedWeighted(int[][] matrix)  throws InterruptedException {
        super(matrix);
    }

    public AdjacencyMatrixUndirectedWeighted() {
        super();
    }

    @Override
    public void loadFromIncidenceList(List<List<Integer>> input) throws InterruptedException
    {
        var weights = new ArrayList<List<Integer>>(input.size());
        for (var input_vertex : input)
        {
            escape();
            var weights_vertex = new ArrayList<Integer>(input_vertex.size());
            for (var input_edge : input_vertex)
                weights_vertex.add(1);
            weights.add(weights_vertex);
        }
        loadFromIncidenceList(input, weights);
    }

    @Override
    public Edge[] getSuccessorsWeighted(int vertex) throws InterruptedException {
        List<Edge> result = new ArrayList<>();

        for (int i = 0; i< verticesNumber; i++)
        {
            escape();
            if (checkIfEdge(vertex, i))
                result.add(new Edge(i, getEdgeInner(vertex, i)));
        }
        return result.toArray(new Edge[0]);
    }

    @Override
    protected int getAllEdgesInner(int edgeNumber, int i, int j, int[][] result) {
        var edge = new int[3];
        int edgesAdded=0;
        if (checkIfEdge(i, j))
        {
            edge[0] = i;
            edge[1] = j;
            edge[2] = getEdgeInner(i,j);
            result[edgeNumber] = edge;
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
        return new AdjacencyMatrixUndirectedWeighted(matrix.clone());
    }
}
