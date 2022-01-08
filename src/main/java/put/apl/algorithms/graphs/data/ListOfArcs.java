package put.apl.algorithms.graphs.data;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.List;

/*
    Directed version
 */
@Component("List Of Arcs")
public class ListOfArcs extends ListOfEdgesAbstract {
    public ListOfArcs(){
        super(new int[0][], 0);
    }

    // Format: line number = vertex id, successors separated by comma
    public ListOfArcs(List<List<Integer>> input) throws InterruptedException {
        super(input);
    }

    public ListOfArcs(int[][] edges, int vertexNum) {
        super(edges, vertexNum);
    }

    @Override
    protected int checkIfSTART(Integer id, int start, int end) {
        if (id == start)
            return end;
        return -1;
    }


    @SneakyThrows
    @Override
    public GraphRepresentationInterface clone() {
        return new ListOfArcs(this.edges.clone(), vertexNum);
    };
}
