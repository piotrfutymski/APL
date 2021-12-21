package put.apl.algorithms.graphs.data.generator;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Component("Directed Graph Generator")
public class DirectedGraphDataGenerator implements GraphDataGenerator {

    @Override
    public List<List<Integer>> generate(GraphGeneratorConfig config) throws InterruptedException {
        List<List<Integer>> edges = new ArrayList<List<Integer>>();
        config.setDensity(config.getDensity() / 100);
        Random random = new Random();
        // n(n-1) - g•n(n-1)/2
        int numToDiscard = (int) (config.getNumberOfVertices() * (config.getNumberOfVertices() - 1)
                - (config.getDensity() * config.getNumberOfVertices() * (config.getNumberOfVertices() - 1)));
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
        List<List<Integer>> graph = new ArrayList<List<Integer>>();
        for (int i = 0; i < config.getNumberOfVertices(); i++) {
            graph.add(new ArrayList<Integer>());
        }
        for (List<Integer> edge : edges) {
            graph.get(edge.get(0)).add(edge.get(1));
        }
        return graph;
    };
}
