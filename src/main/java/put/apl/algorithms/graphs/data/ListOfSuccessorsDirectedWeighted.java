package put.apl.algorithms.graphs.data;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/*
    Directed version
 */
@Component("List Of Successors Directed Weighted")
public class ListOfSuccessorsDirectedWeighted extends ListOfSuccessorsDirected{

    public ListOfSuccessorsDirectedWeighted() {
        super();
    }

    // Format: line number = vertex id, successors separated by comma
    public ListOfSuccessorsDirectedWeighted(List<List<Integer>> input, List<List<Integer>> weights) {
        loadFromIncidenceList(input, weights);
    }

    public ListOfSuccessorsDirectedWeighted(int[][] edges, int[][] weights) {
        this.edges = edges;
        this.weights = weights;
        this.vertexNum = edges.length;
        edgeNum = 0;
        for (var vertex : edges)
            edgeNum+=vertex.length;
    }

    private int[][] weights;

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

                edgesList.get(input.get(i).get(j)).add(i);

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
    public int getMemoryOccupancy() {
        int size = 0;
        for (int[] edgeList : edges)
            size += 2 * Integer.BYTES * edgeList.length;
        return size;
    }

    @Override
    public int getEdge(Integer id1, Integer id2) {
        for (int successor : getDirect(id1)) {
            if (successor == id2) {
                return weights[id1][id2];
            }
        }
        for (int predecessor : getIndirect(id1)) {
            if (predecessor == id2) {
                return -1 * weights[id1][id2];
            }
        }
        return 0;
    }

    @Override
    public GraphRepresentation clone() {
        return new ListOfSuccessorsDirectedWeighted(this.edges.clone(), this.weights.clone());
    }
}
