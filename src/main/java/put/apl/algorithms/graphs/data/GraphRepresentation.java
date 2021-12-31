package put.apl.algorithms.graphs.data;

import java.util.List;

public abstract class GraphRepresentation {

    abstract public void loadFromIncidenceList(List<List<Integer>> input);

    abstract public int[] getSuccessors(Integer id);

    abstract public int getFirstSuccessor(Integer id);

    abstract public int[] getPredecessors(Integer id);

    abstract public int getFirstPredecessor(Integer id);

    abstract public int[] getNonIncident(Integer id);

    abstract public int[][] getRepresentation();

    abstract public int getMemoryOccupancy();

    abstract public int getEdge(Integer id1, Integer id2);



    abstract public int getVerticesNumber();

    abstract public int getEdgesNumber();

    abstract public int[][] getAllEdges();

    abstract public int[] getAllVertices();

    abstract public int getOperations();

    abstract public void setOperations(int operations);

    abstract public GraphRepresentation clone();

    public void escape() throws InterruptedException{
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
    }
}
