package put.apl.algorithms.graphs.data;

import java.util.List;

public interface GraphRepresentationInterface {
    void loadFromIncidenceList(List<List<Integer>> input) throws InterruptedException;

    int[] getSuccessors(int id) throws InterruptedException;

    int getFirstSuccessor(int id) throws InterruptedException;

    int[] getPredecessors(int id) throws InterruptedException;

    int getFirstPredecessor(int id) throws InterruptedException;

    int[] getNonIncident(int id) throws InterruptedException;

    int[][] getRepresentation();

    int getMemoryOccupancy() throws InterruptedException;

    int getEdge(int id1, int id2) throws InterruptedException;

    int getVerticesNumber();

    int getEdgesNumber();

    int[][] getAllEdges() throws InterruptedException;

    int[] getAllVertices() throws InterruptedException;

    int getOperations();

    void setOperations(int operations);

    GraphRepresentationInterface clone() throws InterruptedException;

    void escape() throws InterruptedException;
}
