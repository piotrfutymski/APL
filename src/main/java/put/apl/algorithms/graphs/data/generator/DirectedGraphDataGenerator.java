package put.apl.algorithms.graphs.data.generator;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Component("Directed Graph Generator")
public class DirectedGraphDataGenerator implements GraphDataGenerator {

    @Override
    public String generate(GraphGeneratorConfig config) throws InterruptedException {
        List<ArrayList<Integer>> edges = new ArrayList<ArrayList<Integer>>();
        Random random = new Random();
        // n(n-1) - g•n(n-1)/2
        int numToDiscard = (int) (config.getNumberOfVertices() * (config.getNumberOfVertices() - 1)
                - (config.getDensity() * config.getNumberOfVertices() * (config.getNumberOfVertices() - 1) / 2));
        for (int i = 0; i < config.getNumberOfVertices(); i++) {
            for (int j = 0; j < config.getNumberOfVertices(); j++) {
                if (i == j) continue;
                ArrayList<Integer> newEdge = new ArrayList<Integer>();
                newEdge.add(i);
                newEdge.add(j);
                edges.add(newEdge);
            }
        }
        // Randomly delete edges
        for (int i = 0; i < numToDiscard; i++) {
            edges.remove(random.nextInt(edges.size()));
        }
        // To string
        int prevVertex = 0;
        StringBuilder graph = new StringBuilder();
        boolean firstValue = true;
        for (ArrayList<Integer> edge : edges) {
            if (prevVertex != edge.get(0)) {
                graph.append("\n");
                prevVertex = edge.get(0);
            } else if (!firstValue){
                graph.append(",");
            }
            graph.append(String.valueOf(edge.get(1)));
            firstValue = false;
        }
        return graph.toString();
    };
}
