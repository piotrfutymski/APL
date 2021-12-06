package put.apl.algorithms.graphs.implementation;

import put.apl.algorithms.graphs.data.GraphRepresentation;

import java.util.Map;

public interface GraphAlgorithm<T> {

    T run(GraphRepresentation graph);

    void setParams(Map<String, String> params);
}
