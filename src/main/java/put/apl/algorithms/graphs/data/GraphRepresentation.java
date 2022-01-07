package put.apl.algorithms.graphs.data;

import java.util.List;

public abstract class GraphRepresentation implements GraphRepresentationInterface {

    @Override
    public void escape() throws InterruptedException{
        if (Thread.interrupted()) {
            //throw new InterruptedException();
        }
    }

    abstract public GraphRepresentationInterface clone();
}
