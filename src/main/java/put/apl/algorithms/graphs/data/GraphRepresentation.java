package put.apl.algorithms.graphs.data;

public interface GraphRepresentation {

    public int[] getSuccessors(Integer id);

    public int getFirstSuccessor(Integer id);

    public int[] getPredecessors(Integer id);

    public int getFirstPredecessor(Integer id);

    public int[] getNonIncident(Integer id);

    public int[][] getAllEdges();

    public int getMemoryOccupancy();

    public int getNumberOfVertices();

    public String getRelationBetween(Integer id1, Integer id2);

    public GraphRepresentation clone();
}
