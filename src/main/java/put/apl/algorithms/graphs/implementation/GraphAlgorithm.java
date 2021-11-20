package put.apl.algorithms.graphs.implementation;

import put.apl.algorithms.graphs.data.GraphRepresentation;

public interface GraphAlgorithm<T> {

    T run(GraphRepresentation graph, boolean... flags);
}
