package put.apl.algorithms.graphs.data;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
    public int[] getSuccessors(int id) throws InterruptedException {
        List<Integer> successors= new ArrayList<>();
        for (int i=0; i<edgeNum; i++)
        {
            escape();
            if (getEdgeInner(i, 0) == id)
                successors.add(getEdgeInner(i,1));
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
        }
        return 0;
    }

    @Override
    public int[] getPredecessors(int id) throws InterruptedException {
        List<Integer> predecessors = new ArrayList<>();
        for (int i=0; i<edgeNum; i++) {
            escape();
            if (getEdgeInner(i, 1) == id)
                predecessors.add(getEdgeInner(i,0));
        }
        return predecessors.stream().mapToInt(i->i).toArray();
    }

    @Override
    public int getFirstPredecessor(int id) throws InterruptedException {
        for (int i=0; i<edgeNum; i++)
        {
            escape();
            if (getEdgeInner(i, 1) == id)
                return getEdgeInner(i,0);
        }
        return 0;
    }

    @Override
    public int getEdge(int id1, int id2) throws InterruptedException {
        for (int i=0; i<edgeNum; i++)
        {
            escape();
            if (getEdgeInner(i, 0) == id1 && getEdgeInner(i,1) == id2)
                return getWeight(i);
            else if (getEdgeInner(i, 1) == id1 && getEdgeInner(i,0) == id2)
                return -1 * getWeight(i);
        }
        return 0;
    }


    @SneakyThrows
    @Override
    public GraphRepresentationInterface clone() {
        return new ListOfArcs(this.edges.clone(), vertexNum);
    };
}
