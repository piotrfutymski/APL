package put.apl.algorithms.graphs.implementation;

import put.apl.algorithms.graphs.GraphResult;
import put.apl.algorithms.graphs.data.GraphRepresentationInterface;

import java.util.Map;

public interface GraphAlgorithm {

    GraphResult run(GraphRepresentationInterface graph);

    void setParams(Map<String, String> params);
}
