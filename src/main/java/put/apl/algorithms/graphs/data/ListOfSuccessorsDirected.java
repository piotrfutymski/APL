package put.apl.algorithms.graphs.data;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import java.util.*;

/*
    Directed version
 */
@Component("List Of Successors Directed")
public class ListOfSuccessorsDirected extends ListOfIncident{

    public ListOfSuccessorsDirected () {
        super();
    }

    // Format: line number = vertex id, successors separated by comma
    public ListOfSuccessorsDirected(List<List<Integer>> input) throws InterruptedException {
        super(input);
    }

    public ListOfSuccessorsDirected(int[][] edges) throws InterruptedException {
        super(edges);
    }

    @Override
    void addEdge(List<ArrayList<Integer>> edges, int start, int end) {
        edges.get(start).add(end);
    }

    @Override
    public int[] getSuccessors(int id) {
        return getDirect(id);
    };

    @Override
    public int getFirstSuccessor(int id) {
        return getFirstDirect(id);
    };

    @Override
    public int[] getPredecessors(int id) throws InterruptedException {
        return getIndirect(id);
    }

    @Override
    public int getFirstPredecessor(int id) throws InterruptedException {
        return getFirstIndirect(id);
    }

    @Override
    public int getEdge(int id1, int id2) throws InterruptedException {
        for (int successor : getDirect(id1)) {
            escape();
            if (successor == id2) {
                return 1;
            }
        }
        for (int predecessor : getIndirect(id1)) {
            escape();
            if (predecessor == id2) {
                return -1;
            }
        }
        return 0;
    }

    @Override
    public int[][] getAllEdges() throws InterruptedException {
        int[][] result = new int[edgeNum][];
        int edgeNumber=0;
        for (int i=0;i<vertexNum;i++)
        {
            for (int vert : getSuccessors(i)){
                escape();
                result[edgeNumber++] = new int[] {i, vert};
            }
        }
        return result;
    }

    @SneakyThrows
    @Override
    public GraphRepresentationInterface clone() {
        return new ListOfSuccessorsDirected(this.edges.clone());
    };
}
