package put.apl.algorithms.graphs.implementation;

import put.apl.algorithms.graphs.GraphResult;
import put.apl.algorithms.graphs.data.GraphRepresentationInterface;

import java.util.Map;

public abstract class GraphAlgorithm {

    public abstract GraphResult run(GraphRepresentationInterface graph) throws InterruptedException;

    abstract void setParams(Map<String, String> params);

    public void escape() throws InterruptedException{
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
    }
}
