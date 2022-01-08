package put.apl.algorithms.graphs.data;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.List;

/*
    Undirected version
 */
@Component("List Of Edges")
public class ListOfEdges extends ListOfEdgesAbstract {

    public ListOfEdges() {
        super(new int[0][], 0);
    }

    // Format: line number = vertex id, successors separated by comma
    public ListOfEdges(List<List<Integer>> input) throws InterruptedException {
        super(input);
    }

    public ListOfEdges(int[][] edges, int vertexNum) {
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

    @SneakyThrows
    @Override
    public GraphRepresentationInterface clone(){
        return new ListOfEdges(this.edges.clone(), vertexNum);
    };
}
