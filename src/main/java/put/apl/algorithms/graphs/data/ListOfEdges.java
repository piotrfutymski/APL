package put.apl.algorithms.graphs.data;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
    public int[] getSuccessors(int id) throws InterruptedException {
        List<Integer> successors= new ArrayList<>();
        for (int i=0; i<edgeNum; i++)
        {
            escape();
            if (getEdgeInner(i, 0) == id)
                successors.add(getEdgeInner(i,1));
            else if (getEdgeInner(i,1) == id)
                successors.add(getEdgeInner(i,0));
        }
        return successors.stream().mapToInt(i->i).toArray();
    }

    @Override
    public int getFirstSuccessor(int id) throws InterruptedException {
        for (int i=0; i<edgeNum; i++)
        {
            escape();
            if (getEdgeInner(i, 0) == id)
                return getEdgeInner(i,1);
            else if (getEdgeInner(i,1) == id)
                return getEdgeInner(i,0);
        }
        return 0;
    }

    @Override
    public int[] getPredecessors(int id) throws InterruptedException {
        return getSuccessors(id);
    }

    @Override
    public int getFirstPredecessor(int id) throws InterruptedException {
        return getFirstSuccessor(id);
    }

    @Override
    public int getEdge(int id1, int id2) throws InterruptedException {
        for (int i=0; i<edgeNum; i++)
        {
            escape();
            if (getEdgeInner(i, 0) == id1 && getEdgeInner(i,1) == id2 || getEdgeInner(i, 1) == id1 && getEdgeInner(i,0) == id2)
                return getWeight(i);
        }
        return 0;
    }


    @SneakyThrows
    @Override
    public GraphRepresentationInterface clone(){
        return new ListOfEdges(this.edges.clone(), vertexNum);
    };
}
