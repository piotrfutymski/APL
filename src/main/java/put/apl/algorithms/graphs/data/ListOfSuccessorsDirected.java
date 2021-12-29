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


    @Override
    void addEdge(List<ArrayList<Integer>> edges, int start, int end) {
        edges.get(start).add(end);
    }

    // Format: line number = vertex id, successors separated by comma
    public ListOfSuccessorsDirected(List<List<Integer>> input) throws InterruptedException {
        super(input);
    }

    public ListOfSuccessorsDirected(int[][] edges) throws InterruptedException {
        super(edges);
    }

    public ListOfSuccessorsDirected(List<List<Integer>> input, int vertexNum) throws InterruptedException {
      super(input, vertexNum);
    };

    public int[] getSuccessors(Integer id) {
        return getDirect(id);
    };

    public int getFirstSuccessor(Integer id) {
        return getFirstDirect(id);
    };

    public int[] getPredecessors(Integer id) throws InterruptedException {
        return getIndirect(id);
    }

    public int getFirstPredecessor(Integer id) throws InterruptedException {
        return getFirstIndirect(id);
    }

    @Override
    public int getEdge(Integer id1, Integer id2) throws InterruptedException {
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
    public GraphRepresentation clone() {
        return new ListOfSuccessorsDirected(this.edges.clone());
    };
}
