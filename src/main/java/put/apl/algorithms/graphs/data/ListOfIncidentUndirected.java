package put.apl.algorithms.graphs.data;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/*
    Undirected version
 */
@Component("List Of Incident Undirected")
public class ListOfIncidentUndirected extends ListOfIncident {
    public ListOfIncidentUndirected () {
        super();
    }

    @Override
    void addEdge(List<ArrayList<Integer>> edges, int start, int end) {
        edges.get(start).add(end);
        edges.get(end).add(start);
    }

    // Format: line number = vertex id, successors separated by comma
    public ListOfIncidentUndirected(List<ArrayList<Integer>> input) {
        super(input);
    }

    public ListOfIncidentUndirected(int[][] edges) {
        super(edges);
    }

    public int[] getSuccessors(Integer id) {
        return getDirect(id);
    };

    public int getFirstSuccessor(Integer id) {
        return getFirstDirect(id);
    };

    public int[] getPredecessors(Integer id) {
        return getDirect(id);
    }

    public int getFirstPredecessor(Integer id) {
        return getFirstDirect(id);
    }

    @Override
    public int getEdge(Integer id1, Integer id2) {
        for (int predecessor : getDirect(id1)) {
            if (predecessor == id2) {
                return 1;
            }
        }
        return 0;
    }

    @Override
    public GraphRepresentation clone() {
        return new ListOfIncidentUndirected(this.edges.clone());
    };
}
