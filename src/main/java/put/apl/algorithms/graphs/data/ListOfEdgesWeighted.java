package put.apl.algorithms.graphs.data;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("Weighted List Of Edges")
public class ListOfEdgesWeighted extends ListOfEdges implements GraphRepresentationWeightedInterface {
    public ListOfEdgesWeighted() throws InterruptedException  {
        super(new int[0][], 0);
    }

    // Format: line number = vertex id, successors separated by comma
    public ListOfEdgesWeighted(List<List<Integer>> input, List<List<Integer>> weights) throws InterruptedException  {
        if (weights == null)
            loadFromIncidenceList(input);
        else
            loadFromIncidenceList(input, weights);
    }

    public ListOfEdgesWeighted(int[][] edges, int[] weights, int vertexNum){
        this.edges = edges;
        this.weights = weights;
        this.vertexNum = vertexNum;
        edgeNum = edges.length;
    }

    private int[] weights;

    @Override
    public void loadFromIncidenceList(List<List<Integer>> input) throws InterruptedException {
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

    public void loadFromIncidenceList(List<List<Integer>> input, List<List<Integer>> weights_input) throws InterruptedException {
        vertexNum = input.size();

        for (var edges: input) {
            escape();
            edgeNum+=edges.size();
        }

        int edgeNumber=0;
        edges = new int[edgeNum][];
        weights = new int[edgeNum];
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).size(); j++) {
                escape();
                var edge = new int[2];
                edge[0] = i;
                edge[1] = input.get(i).get(j);
                weights[edgeNumber] = weights_input.get(i).get(j);
                edges[edgeNumber++] = edge;
            }
        }
    }

    @Override
    public Edge[] getSuccessorsWeighted(int vertex) throws InterruptedException {
        List<Edge> successors = new ArrayList<>();
        for (int i=0;i < edgeNum;i++) {
            escape();
            int result = checkIfSTART(vertex, getEdgeInner(i, 0),getEdgeInner(i, 1));
            if (result != -1) {
                successors.add(new Edge(result, getWeight(i)));
            }
        }
        return successors.toArray(new Edge[0]);
    }

    @Override
    public int[][] getAllEdges() {
        int[][] result = new int[edges.length][];
        for (int i=0; i<edges.length;i++)
        {
            result[i] = new int[] {getEdgeInner(i,0), getEdgeInner(i,1), weights[i]};
        }
        return result;
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
    public GraphRepresentationInterface clone(){
        return new ListOfEdgesWeighted(this.edges.clone(), this.weights.clone(), vertexNum);
    }
}
