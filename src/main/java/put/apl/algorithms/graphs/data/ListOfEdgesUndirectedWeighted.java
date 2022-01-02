package put.apl.algorithms.graphs.data;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("List Of Edges Undirected Weighted")
public class ListOfEdgesUndirectedWeighted extends ListOfEdgesUndirected implements GraphRepresentationWeightedInterface {
    public ListOfEdgesUndirectedWeighted() {
        super(new int[0][], 0);
    }

    // Format: line number = vertex id, successors separated by comma
    public ListOfEdgesUndirectedWeighted(List<List<Integer>> input, List<List<Integer>> weights) {
        loadFromIncidenceList(input, weights);
    }

    public ListOfEdgesUndirectedWeighted(int[][] edges, int[] weights, int vertexNum) {
        this.edges = edges;
        this.weights = weights;
        this.vertexNum = vertexNum;
        edgeNum = edges.length;
    }

    private int[] weights;

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

    public void loadFromIncidenceList(List<List<Integer>> input, List<List<Integer>> weights_input) {
        vertexNum = input.size();

        for (var edges: input)
            edgeNum+=edges.size();
        int edgeNumber=0;
        edges = new int[edgeNum][];
        weights = new int[edgeNum];
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).size(); j++) {
                var edge = new int[2];
                edge[0] = i;
                edge[1] = input.get(i).get(j);
                weights[edgeNumber] = weights_input.get(i).get(j);
                edges[edgeNumber++] = edge;
            }
        }
    }

    @Override
    public int getMemoryOccupancy() {
        return Integer.BYTES * edges.length * 3;
    };

    @Override
    protected int getWeight(int edgeNumber)
    {
        return weights[edgeNumber];
    }

    @Override
    public GraphRepresentationInterface clone() {
        return new ListOfEdgesUndirectedWeighted(this.edges.clone(), this.weights.clone(), vertexNum);
    }
}
