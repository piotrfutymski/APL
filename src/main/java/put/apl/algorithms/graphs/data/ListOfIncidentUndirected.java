package put.apl.algorithms.graphs.data;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/*
    Undirected version
 */
@Component("List Of Incident Undirected")
public class ListOfIncidentUndirected extends ListOfIncident {
    public ListOfIncidentUndirected () {
        super();
    }

    // Format: line number = vertex id, successors separated by comma
    public ListOfIncidentUndirected(List<List<Integer>> input) throws InterruptedException {
        super(input);
    }

    public ListOfIncidentUndirected(int[][] edges) throws InterruptedException {
        super(edges);
    }


    @Override
    void addEdge(List<ArrayList<Integer>> edges, int start, int end) {
        edges.get(start).add(end);
        edges.get(end).add(start);
    }


    @Override
    public int[] getSuccessors(Integer id) {
        return getDirect(id);
    };

    @Override
    public int getFirstSuccessor(Integer id) {
        return getFirstDirect(id);
    };

    @Override
    public int[] getPredecessors(Integer id) {
        return getDirect(id);
    }

    @Override
    public int getFirstPredecessor(Integer id) {
        return getFirstDirect(id);
    }

    @Override
    public int getEdge(Integer id1, Integer id2) throws InterruptedException {
        for (int predecessor : getDirect(id1)) {
            escape();
            if (predecessor == id2) {
                return 1;
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
            for (int vert : getSuccessors(i)){
                escape();
                if (vert > i)
                    result[edgeNumber++] = new int[] {i, vert};
            }
        }
        return result;
    }

    @SneakyThrows
    @Override
    public GraphRepresentationInterface clone() {
        return new ListOfIncidentUndirected(this.edges.clone());
    };
}
