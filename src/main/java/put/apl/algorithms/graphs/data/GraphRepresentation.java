package put.apl.algorithms.graphs.data;

import java.util.List;

public interface GraphRepresentation {

    void loadFromIncidenceList(List<List<Integer>> input);

    public int[] getSuccessors(Integer id);

    public int getFirstSuccessor(Integer id);

    public int[] getPredecessors(Integer id);

    public int getFirstPredecessor(Integer id);

    public int[] getNonIncident(Integer id);

    public int[][] getRepresentation();

    public int getMemoryOccupancy();

    public int getEdge(Integer id1, Integer id2);

    public int getVerticesNumber();

    public int getEdgesNumber();

    public int[][] getAllEdges();

    public int[] getAllVertices();

    public int getOperations();

    public void setOperations(int operations);

    public GraphRepresentation clone();
}
