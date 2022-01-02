package put.apl.algorithms.graphs.data;

import java.util.List;

public interface GraphRepresentationInterface {
    void loadFromIncidenceList(List<List<Integer>> input);

    int[] getSuccessors(Integer id);

    int getFirstSuccessor(Integer id);

    int[] getPredecessors(Integer id);

    int getFirstPredecessor(Integer id);

    int[] getNonIncident(Integer id);

    int[][] getRepresentation();

    int getMemoryOccupancy();

    int getEdge(Integer id1, Integer id2);

    int getVerticesNumber();

    int getEdgesNumber();

    int[][] getAllEdges();

    int[] getAllVertices();

    int getOperations();

    void setOperations(int operations);

    GraphRepresentationInterface clone();

    void escape() throws InterruptedException;
}
