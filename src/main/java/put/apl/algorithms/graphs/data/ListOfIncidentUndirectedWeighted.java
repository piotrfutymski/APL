package put.apl.algorithms.graphs.data;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
    Undirected version
 */
@Component("Weighted List Of Incident Undirected")
public class ListOfIncidentUndirectedWeighted extends ListOfIncidentWeighted{


    public ListOfIncidentUndirectedWeighted() {
        super();
    }

    // Format: line number = vertex id, successors separated by comma
    public ListOfIncidentUndirectedWeighted(List<List<Integer>> input, List<List<Integer>> weights) throws InterruptedException {
        if (weights == null)
            loadFromIncidenceList(input);
        else
            loadFromIncidenceList(input, weights);
    }

    public ListOfIncidentUndirectedWeighted(Edge[][] edges, int edgeNumber)
    {
        super(edges,edgeNumber);
    }

    @Override
    void addEdge(List<ArrayList<Edge>> edgesList, int start, int end, int weight) {
        edgesList.get(start).add(new Edge(end, weight));
        edgesList.get(end).add(new Edge(start, weight));
    }

    @Override
    public int[] getNonIncident(int id) throws InterruptedException {
        boolean[] nonIncident = new boolean[representation.length];
        List<Integer> nonIncidentIds = new ArrayList<Integer>();
        for(int i = 0; i < representation.length; i++) {
            escape();
            nonIncident[i] = true;
        }
        for (var index : getDirect(id)){
            escape();
            nonIncident[index.vertex] = false;
        }

        for (int i = 0; i < representation.length; i++) {
            escape();
            if (nonIncident[i]) {
                nonIncidentIds.add(i);
            }
        }
        return nonIncidentIds.stream().mapToInt(i->i).toArray();
    }

    @Override
    public int[] getSuccessors(int id) {
        return Arrays.stream(getDirect(id)).mapToInt(i->i.vertex).toArray();
    }

    @Override
    public int getFirstSuccessor(int id) {
        return getFirstDirect(id).vertex;
    }

    @Override
    public Edge[] getSuccessorsWeighted(int vertex) throws InterruptedException {
        return getDirect(vertex);
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
            escape();
            if (predecessor.vertex == id2) {
                return predecessor.weight;
            }
        }
        return 0;
    }

    @Override
    public int[][] getAllEdges() throws InterruptedException {
        int[][] result = new int[edgeNum][];
        int edgeNumber=0;
        for (int i = 0; i < vertexNum; i++)
        {
            escape();
            for (var vert : getDirect(i)){
                if (vert.vertex > i)
                    result[edgeNumber++] = new int[] {i, vert.vertex, vert.weight};
            }
        }
        return result;
    }

    @Override
    public GraphRepresentationInterface clone() {
        return new ListOfIncidentUndirectedWeighted(this.representation.clone(), this.edgeNum);
    }

}
