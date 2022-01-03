package put.apl.algorithms.graphs.data;

import java.util.List;

public interface GraphRepresentationWeightedInterface extends GraphRepresentationInterface {

    void loadFromIncidenceList(List<List<Integer>> input, List<List<Integer>> weights) throws InterruptedException;

}
