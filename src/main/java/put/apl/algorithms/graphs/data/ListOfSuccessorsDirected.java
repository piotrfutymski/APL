package put.apl.algorithms.graphs.data;

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
    public ListOfSuccessorsDirected(String input) {
        super(input);
    }

    public ListOfSuccessorsDirected(int[][] edges) {
        super(edges);
    }

    public int[] getSuccessors(Integer id) {
        return getDirect(id);
    };

    public int getFirstSuccessor(Integer id) {
        return getFirstDirect(id);
    };

    public int[] getPredecessors(Integer id) {
        return getIndirect(id);
    }

    public int getFirstPredecessor(Integer id) {
        return getFirstIndirect(id);
    }

    @Override
    public int getEdge(Integer id1, Integer id2) {
        for (int successor : getDirect(id1)) {
            if (successor == id2) {
                return 1;
            }
        }
        for (int predecessor : getIndirect(id1)) {
            if (predecessor == id2) {
                return -1;
            }
        }
        return 0;
    }

    @Override
    public GraphRepresentation clone() {
        return new ListOfSuccessorsDirected(this.edges.clone());
    };
}
