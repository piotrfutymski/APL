package put.apl.algorithms.graphs.data.generator;
import org.springframework.stereotype.Component;
import put.apl.algorithms.graphs.data.ListOfEdgesDirected;
import put.apl.algorithms.graphs.implementation.BreadthFirstSearch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;


@Component("Connected Directed Graph Generator")
public class ConnectedDirectedGraphDataGenerator implements GraphDataGenerator {

    @Override
    public List<List<Integer>> generate(GraphGeneratorConfig config) throws InterruptedException {
        List<List<Integer>> edges = new ArrayList<List<Integer>>();
        config.setDensity(config.getDensity() / 100);
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
        // Randomly delete edges (check if deletion breaks connectivity of the graph)
        for (int i = 0; i < numToDiscard; i++) {
            while (true) {
                int removalId = random.nextInt(edges.size());
                BreadthFirstSearch bfs = new BreadthFirstSearch();
                List<List<Integer>> edgesCopy = new ArrayList<List<Integer>>(edges);
                edgesCopy.remove(removalId);
                Map<String,String> params = Map.of("forceConnected", "true");
                bfs.setParams(params);
                List<Integer> path = bfs.run(new ListOfEdgesDirected((int[][]) edgesCopy.toArray())).getPath();
                if (config.getNumberOfVertices() == path.size()) {
                    break;
                }
            }
        }
        return edges;
    };
}
