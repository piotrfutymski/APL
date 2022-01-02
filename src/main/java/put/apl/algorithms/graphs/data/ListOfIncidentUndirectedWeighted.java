package put.apl.algorithms.graphs.data;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/*
    Undirected version
 */
@Component("List Of Incident Undirected Weighted")
public class ListOfIncidentUndirectedWeighted extends ListOfIncidentUndirected implements GraphRepresentationWeightedInterface {
    public ListOfIncidentUndirectedWeighted() {
        super();
    }

    // Format: line number = vertex id, successors separated by comma
    public ListOfIncidentUndirectedWeighted(List<List<Integer>> input, List<List<Integer>> weights) {
        loadFromIncidenceList(input, weights);
    }

    public ListOfIncidentUndirectedWeighted(int[][] edges, int[][] weights)
    {
        this.edges = edges;
        this.weights = weights;
        this.vertexNum=edges.length;
        edgeNum = 0;
        for (var vertex : edges)
            edgeNum+=vertex.length;
    }

    private int[][] weights;

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
        edgeNum = 0;
        List<ArrayList<Integer>> edgesList = new ArrayList<ArrayList<Integer>>();
        List<ArrayList<Integer>> weightsList = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < vertexNum; i++) {
            edgesList.add(new ArrayList<Integer>());
            weightsList.add(new ArrayList<Integer>());
        }

        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).size(); j++) {

                edgesList.get(i).add(input.get(i).get(j));
                edgesList.get(input.get(i).get(j)).add(i);

                weightsList.get(i).add(weights_input.get(i).get(j));
                weightsList.get(input.get(i).get(j)).add(weights_input.get(i).get(j));

                edgeNum++;
            }
        }

        edges = new int[vertexNum][];
        weights = new int[vertexNum][];

        for (int i = 0; i < edgesList.size(); i++) {
            edges[i] = new int[edgesList.get(i).size()];
            weights[i] = new int[edgesList.get(i).size()];
            for (int j = 0; j < edgesList.get(i).size(); j++) {
                edges[i][j] = edgesList.get(i).get(j);
                weights[i][j] = weightsList.get(i).get(j);
            }
        }
    }

    @Override
    public int getEdge(Integer id1, Integer id2) {
        for (int predecessor : getDirect(id1)) {
            if (predecessor == id2) {
                return weights[id1][id2];
            }
        }
        return 0;
    }

    @Override
    public int getMemoryOccupancy() {
        int size = 0;
        for (int[] edgeList : edges)
            size += 2 * Integer.BYTES * edgeList.length;
        return size;
    }

    @Override
    public GraphRepresentationInterface clone() {
        return new ListOfIncidentUndirectedWeighted(this.edges.clone(), this.weights.clone());
    }
}
