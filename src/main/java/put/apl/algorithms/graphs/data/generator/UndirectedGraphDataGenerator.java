package put.apl.algorithms.graphs.data.generator;
import org.springframework.stereotype.Component;
import put.apl.algorithms.graphs.data.generator.GraphDataGenerator;
import put.apl.algorithms.graphs.data.generator.GraphGeneratorConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Component("Undirected Graph Generator")
public class UndirectedGraphDataGenerator implements GraphDataGenerator {

    @Override
    public String generate(GraphGeneratorConfig config) throws InterruptedException {
        List<ArrayList<Integer>> edges = new ArrayList<ArrayList<Integer>>();
        Random random = new Random();
        // n(n-1) - g•n(n-1)/2
        int numToDiscard = (int) (config.getNoOfVertices() * (config.getNoOfVertices() - 1) / 2
                - (config.getDensity() * config.getNoOfVertices() * (config.getNoOfVertices() - 1) / 2));
        for (int i = 0; i < config.getNoOfVertices(); i++) {
            for (int j = i + 1; j < config.getNoOfVertices(); j++) {
                ArrayList<Integer> newEdge = new ArrayList<Integer>();
                ArrayList<Integer> newEdgeMirrored = new ArrayList<Integer>();
                newEdge.add(i);
                newEdge.add(j);
                newEdgeMirrored.add(j);
                newEdgeMirrored.add(i);
                edges.add(newEdge);
                edges.add(newEdgeMirrored);
            }
        }
        // Randomly delete edges
        for (int i = 0; i < numToDiscard / 2; i++) {
            int removeId = random.nextInt(edges.size() / 2);
            // We remove twice at the same ID so both edges x->y and y->x are removed
            edges.remove(random.nextInt(removeId * 2));
            edges.remove(random.nextInt(removeId * 2));
        }
        // To string
        int prevVertex = 0;
        String graph = "";
        for (ArrayList<Integer> edge : edges) {
            if (prevVertex != edge.get(0)) {
                graph = graph.concat("\n");
                prevVertex = edge.get(0);
            } else if (!graph.equals("")){
                graph = graph.concat(",");
            }
            graph = graph.concat(String.valueOf(edge.get(1)));
        }
        return graph;
    };
}
