package put.apl.algorithms.graphs.data;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
    Directed version
 */
@Component("Weighted List Of Predecessors Directed")
public class ListOfPredecessorsDirectedWeighted extends ListOfIncidentWeighted {
    public ListOfPredecessorsDirectedWeighted() {
        super();
    }

    // Format: line number = vertex id, successors separated by comma
    public ListOfPredecessorsDirectedWeighted(List<List<Integer>> input, List<List<Integer>> weights) throws InterruptedException {
        if (weights == null)
            loadFromIncidenceList(input);
        else
            loadFromIncidenceList(input, weights);
    }

    public ListOfPredecessorsDirectedWeighted(Edge[][] edges) {
        this.representation = edges;
        this.vertexNum = edges.length;
        edgeNum = 0;
        for (var vertex : representation)
            edgeNum+=vertex.length;
    }

    @Override
    void addEdge(List<ArrayList<Edge>> edges, int start, int end, int weight) {
        edges.get(end).add(new Edge(start, weight));
    }

    @Override
    public int[] getSuccessors(int id) throws InterruptedException {
        return Arrays.stream(getIndirect(id)).mapToInt(i->i.vertex).toArray();
    }

    @Override
    public int getFirstSuccessor(int id) throws InterruptedException {
        return getFirstIndirect(id).vertex;
    }

    @Override
    public Edge[] getSuccessorsWeighted(int vertex) throws InterruptedException {
        return getIndirect(vertex);
    }

    @Override
    public int[] getPredecessors(int id) {
        return Arrays.stream(getDirect(id)).mapToInt(i->i.vertex).toArray();
    }

    @Override
    public int getFirstPredecessor(int id) {
        return getFirstDirect(id).vertex;
    }

    @Override
    public int getEdge(int id1, int id2) throws InterruptedException {
        for (var predecessor : getDirect(id1)) {
            if (predecessor.vertex == id2) {
                return -1 * predecessor.weight;
            }
        }
        for (var successor : getIndirect(id1)) {
            if (successor.vertex == id2) {
                return successor.weight;
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
                result[edgeNumber++] = new int[] {vert.vertex, i, vert.weight};
            }
        }
        return result;
    }

    @Override
    public GraphRepresentationInterface clone() {
        return new ListOfPredecessorsDirectedWeighted(this.representation);
    }
}
