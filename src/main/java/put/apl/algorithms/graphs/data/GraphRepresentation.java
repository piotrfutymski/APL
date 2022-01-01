package put.apl.algorithms.graphs.data;

import java.util.List;

public abstract class GraphRepresentation {

    abstract public void loadFromIncidenceList(List<List<Integer>> input) throws InterruptedException;

    abstract public int[] getSuccessors(Integer id) throws InterruptedException;

    abstract public int getFirstSuccessor(Integer id) throws InterruptedException;

    abstract public int[] getPredecessors(Integer id) throws InterruptedException;

    abstract public int getFirstPredecessor(Integer id) throws InterruptedException;

    abstract public int[] getNonIncident(Integer id) throws InterruptedException;

    abstract public int[][] getRepresentation();

    abstract public int getMemoryOccupancy() throws InterruptedException;

    abstract public int getEdge(Integer id1, Integer id2) throws InterruptedException;

    abstract public int getVerticesNumber();

    abstract public int getEdgesNumber();

    abstract public int[][] getAllEdges() throws InterruptedException;

    abstract public int[] getAllVertices() throws InterruptedException;

    abstract public int getOperations();

    abstract public void setOperations(int operations);

    abstract public GraphRepresentation clone();

    public void escape() throws InterruptedException{
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
    }
}
