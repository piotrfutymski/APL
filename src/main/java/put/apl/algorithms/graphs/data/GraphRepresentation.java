package put.apl.algorithms.graphs.data;

import java.util.Set;

public interface GraphRepresentation {

    public Set<Integer> getSuccessors(Integer id);

    public Integer getFirstSuccessor(Integer id);

    public Set<Integer> getPredecessors(Integer id);

    public Integer getFirstPredecessor(Integer id);

    public Set<Integer> getNonIncident(Integer id);

    public Set<Edge> getAllEdges();

    public Integer getMemoryOccupancy();
}
