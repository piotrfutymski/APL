package put.apl.algorithms.graphs.data;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/*
    Directed version
 */
@Component("List Of Predecessors Directed")
public class ListOfPredecessorsDirected extends ListOfIncident {
    public ListOfPredecessorsDirected () {
        super();
    }

    // Format: line number = vertex id, successors separated by comma
    public ListOfPredecessorsDirected(List<List<Integer>> input) {
        super(input);
    }

    public ListOfPredecessorsDirected(int[][] edges) {
        super(edges);
    }

    @Override
    void addEdge(List<ArrayList<Integer>> edges, int start, int end) {
        edges.get(end).add(start);
    }

    @Override
    public int[] getSuccessors(Integer id) {
        return getIndirect(id);
    }

    @Override
    public int getFirstSuccessor(Integer id) {
        return getFirstIndirect(id);
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
    public int getEdge(Integer id1, Integer id2) {
        for (int predecessor : getDirect(id1)) {
            if (predecessor == id2) {
                return -1;
            }
        }
        for (int successor : getIndirect(id1)) {
            if (successor == id2) {
                return 1;
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
            for (int vert : getPredecessors(i)){
                result[edgeNumber++] = new int[] {vert, i};
            }
        }
        return result;
    }

    @Override
    public GraphRepresentation clone() {
        return new ListOfPredecessorsDirected(this.edges.clone());
    };
}
