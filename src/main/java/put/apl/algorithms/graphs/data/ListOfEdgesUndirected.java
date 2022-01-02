package put.apl.algorithms.graphs.data;

import org.springframework.stereotype.Component;

import java.util.List;

/*
    Undirected version
 */
@Component("List Of Edges Undirected")
public class ListOfEdgesUndirected extends ListOfEdges{

    public ListOfEdgesUndirected () {
        super(new int[0][], 0);
    }

    // Format: line number = vertex id, successors separated by comma
    public ListOfEdgesUndirected(List<List<Integer>> input) {
        super(input);
    }

    public ListOfEdgesUndirected(int[][] edges, int vertexNum) {
        super(edges, vertexNum);
    }

    @Override
    protected int checkIfSTART(Integer id, int start, int end) {
        if (id == start)
            return end;
        if (id == end)
            return start;
        return -1;
    }

    @Override
    public GraphRepresentationInterface clone() {
        return new ListOfEdgesUndirected(this.edges.clone(), vertexNum);
    };
}
