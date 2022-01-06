package put.apl.algorithms.graphs.data;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
    Directed version
 */
@Component("Weighted List Of Successors Directed")
public class ListOfSuccessorsDirectedWeighted extends ListOfIncidentWeighted {



    public ListOfSuccessorsDirectedWeighted() {
        super();
    }

    // Format: line number = vertex id, successors separated by comma
    public ListOfSuccessorsDirectedWeighted(List<List<Integer>> input, List<List<Integer>> weights) throws InterruptedException {
        if (weights == null)
            loadFromIncidenceList(input);
        else
            loadFromIncidenceList(input, weights);
    }

    public ListOfSuccessorsDirectedWeighted(Edge[][] edges) {
        this.representation = edges;
        this.vertexNum = edges.length;
        edgeNum = 0;
        for (var vertex : edges)
            edgeNum+=vertex.length;
    }

    @Override
    void addEdge(List<ArrayList<Edge>> edgesList, int start, int end, int weight) {
        edgesList.get(start).add(new Edge(end, weight));
    }

    @Override
    public int[] getSuccessors(int id) {
        return Arrays.stream(getDirect(id)).mapToInt(i->i.vertex).toArray();
    };

    @Override
    public int getFirstSuccessor(int id) {
        return getFirstDirect(id).vertex;
    };

    @Override
    public Edge[] getSuccessorsWeighted(int vertex) throws InterruptedException {
        return getDirect(vertex);
    }

    @Override
    public int[] getPredecessors(int id) throws InterruptedException {
        return Arrays.stream(getIndirect(id)).mapToInt(i->i.vertex).toArray();
    }

    @Override
    public int getFirstPredecessor(int id) throws InterruptedException {
        return getFirstIndirect(id).vertex;
    }

    @Override
    public int getEdge(int id1, int id2) throws InterruptedException {
        for (var successor : getDirect(id1)) {
            if (successor.vertex == id2) {
                return successor.weight;
            }
        }
        for (var predecessor : getIndirect(id1)) {
            if (predecessor.vertex == id2) {
                return -1 * predecessor.weight;
            }
        }
        return 0;
    }

    @Override
    public int[][] getAllEdges() {
        int[][] result = new int[edgeNum][];
        int edgeNumber=0;
        for (int i=0;i<vertexNum;i++)
        {
            for (var vert : getDirect(i)){
                result[edgeNumber++] = new int[] {i, vert.vertex, vert.weight};
            }
        }
        return result;
    }
    @Override
    public GraphRepresentationInterface clone() {
        return new ListOfSuccessorsDirectedWeighted(this.representation.clone());
    }
}
