package put.apl.algorithms.graphs.data;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
    Directed version
 */
@Component("List Of Edges Directed")
public class ListOfEdgesDirected extends ListOfEdges {
    public ListOfEdgesDirected () {
        super(new int[0][], 0);
    }

    // Format: line number = vertex id, successors separated by comma
    public ListOfEdgesDirected(List<List<Integer>> input) {
        super(input);
    }

    public ListOfEdgesDirected(int[][] edges, int vertexNum) {
        super(edges, vertexNum);
    }

    @Override
    protected int checkIfSTART(Integer id, int start, int end) {
        if (id == start)
            return end;
        return -1;
    }


    @Override
    public GraphRepresentation clone() {
        return new ListOfEdgesDirected(this.edges.clone(), vertexNum);
    };
}
