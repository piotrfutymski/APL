package put.apl.algorithms.graphs.data;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
    Undirected version
 */
@Component("List Of Incident Undirected Weighted")
public class ListOfIncidentUndirectedWeighted extends ListOfIncidentWeighted{


    public ListOfIncidentUndirectedWeighted() {
        super();
    }

    // Format: line number = vertex id, successors separated by comma
    public ListOfIncidentUndirectedWeighted(List<List<Integer>> input, List<List<Integer>> weights) {
        if (weights == null)
            loadFromIncidenceList(input);
        else
            loadFromIncidenceList(input, weights);
    }

    public ListOfIncidentUndirectedWeighted(Edge[][] edges)
    {
        this.representation = edges;
        this.vertexNum = edges.length;
        edgeNum = 0;
        for (var vertex : edges)
            edgeNum+=vertex.length;
    }

    @Override
    void addEdge(List<ArrayList<Edge>> edgesList, int start, int end, int weight) {
        edgesList.get(start).add(new Edge(end, weight));
        edgesList.get(end).add(new Edge(start, weight));
    }

    @Override
    public int[] getSuccessors(Integer id) {
        return Arrays.stream(getDirect(id)).mapToInt(i->i.vertex).toArray();
    }

    @Override
    public int getFirstSuccessor(Integer id) {
        return getFirstDirect(id).vertex;
    };

    @Override
    public int[] getPredecessors(Integer id) {
        return Arrays.stream(getDirect(id)).mapToInt(i->i.vertex).toArray();
    }

    @Override
    public int getFirstPredecessor(Integer id) {
        return getFirstDirect(id).vertex;
    }

    @Override
    public int getEdge(Integer id1, Integer id2) {
        for (var predecessor : getDirect(id1)) {
            if (predecessor.vertex == id2) {
                return predecessor.weight;
            }
        }
        return 0;
    }

    @Override
    public int[][] getAllEdges() {
        int[][] result = new int[edgeNum*2][];
        int edgeNumber=0;
        for (int i = 0; i < vertexNum; i++)
        {
            for (var vert : getDirect(i)){
                result[edgeNumber++] = new int[] {i, vert.vertex, vert.weight};
            }
        }
        return result;
    }

    @Override
    public GraphRepresentationInterface clone() {
        return new ListOfIncidentUndirectedWeighted(this.representation.clone());
    }
}
