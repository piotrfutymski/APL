package put.apl.algorithms.graphs.data;

import java.util.List;

public interface GraphRepresentationInterface {
    void loadFromIncidenceList(List<List<Integer>> input) throws InterruptedException;

    int[] getSuccessors(Integer id) throws InterruptedException;

    int getFirstSuccessor(Integer id) throws InterruptedException;

    int[] getPredecessors(Integer id) throws InterruptedException;

    int getFirstPredecessor(Integer id) throws InterruptedException;

    int[] getNonIncident(Integer id) throws InterruptedException;

    int[][] getRepresentation();

    int getMemoryOccupancy() throws InterruptedException;

    int getEdge(Integer id1, Integer id2) throws InterruptedException;

    int getVerticesNumber();

    int getEdgesNumber();

    int[][] getAllEdges() throws InterruptedException;

    int[] getAllVertices() throws InterruptedException;

    int getOperations();

    void setOperations(int operations);

    GraphRepresentationInterface clone() throws InterruptedException;

    void escape() throws InterruptedException;
}
