package put.apl.algorithms.graphs.data.generator;
import org.springframework.stereotype.Component;
import put.apl.algorithms.graphs.data.ListOfEdgesUndirected;
import put.apl.algorithms.graphs.implementation.BreadthFirstSearch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;


@Component("Euler Undirected Graph Generator")
public class EulerUndirectedGraphDataGenerator implements GraphDataGenerator {

    @Override
    public List<List<Integer>> generate(GraphGeneratorConfig config) throws InterruptedException {
        List<List<Integer>> edges = new ArrayList<List<Integer>>();
        config.setDensity(config.getDensity() / 100);
        Random random = new Random();
        // n(n-1) - g•n(n-1)/2
        int numToDiscard = (int) (config.getNumberOfVertices() * (config.getNumberOfVertices() - 1)
                - (config.getDensity() * config.getNumberOfVertices() * (config.getNumberOfVertices() - 1) / 2));
        for (int i = 0; i < config.getNumberOfVertices(); i++) {
            for (int j = i + 1; j < config.getNumberOfVertices(); j++) {
                ArrayList<Integer> newEdge = new ArrayList<Integer>();
                //ArrayList<Integer> newEdgeMirrored = new ArrayList<Integer>();
                newEdge.add(i);
                newEdge.add(j);
                //newEdgeMirrored.add(j);
                //newEdgeMirrored.add(i);
                edges.add(newEdge);
                //edges.add(newEdgeMirrored);
            }
        }
        // Randomly delete edges (check if deletion breaks connectivity of the graph)
        for (int i = 0; i < numToDiscard / 2; i++) {
            while (true) {
                int removalId = random.nextInt(edges.size());
                BreadthFirstSearch bfs = new BreadthFirstSearch();
                List<List<Integer>> edgesCopy = new ArrayList<List<Integer>>(edges);
                edgesCopy.remove(removalId * 2);
                //edgesCopy.remove(removalId * 2);
                Map<String,String> params = Map.of("forceConnected", "true");
                bfs.setParams(params);
                List<Integer> path = bfs.run(new ListOfEdgesUndirected((int[][]) edgesCopy.toArray(), config.getNumberOfVertices())).getPath();
                if (config.getNumberOfVertices() == path.size()) {
                    break;
                }
            }
        }
        return edges;
    };
}
