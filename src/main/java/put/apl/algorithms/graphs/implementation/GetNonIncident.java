package put.apl.algorithms.graphs.implementation;

import org.springframework.stereotype.Component;
import put.apl.algorithms.graphs.GraphResult;
import put.apl.algorithms.graphs.data.GraphRepresentation;

import java.util.Map;

@Component("Get Non Incident")
public class GetNonIncident implements GraphAlgorithm {

    public GraphResult run(GraphRepresentation graph) {
        int noOfVertices = graph.getVerticesNumber();
        for (int i = 0; i < noOfVertices; i++) {
            graph.getNonIncident(i);
        }
        return GraphResult.builder().build();
    }

    @Override
    public void setParams(Map<String, String> params) {
    }
}
