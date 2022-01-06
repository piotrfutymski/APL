package put.apl.algorithms.graphs.data;

import lombok.AllArgsConstructor;

import java.util.List;

public interface GraphRepresentationWeightedInterface extends GraphRepresentationInterface {

    @AllArgsConstructor
    class Edge
    {
        public int vertex;
        public int weight;

    }

    void loadFromIncidenceList(List<List<Integer>> input, List<List<Integer>> weights) throws InterruptedException;

    Edge[] getSuccessorsWeighted(int vertex) throws InterruptedException;

}
