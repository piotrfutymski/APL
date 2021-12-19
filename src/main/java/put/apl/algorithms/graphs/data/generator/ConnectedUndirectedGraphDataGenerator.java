package put.apl.algorithms.graphs.data.generator;
import org.springframework.stereotype.Component;
import put.apl.algorithms.graphs.data.ListOfEdgesUndirected;
import put.apl.algorithms.graphs.implementation.BreadthFirstSearch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;


@Component("Connected Undirected Graph Generator")
public class ConnectedUndirectedGraphDataGenerator implements GraphDataGenerator {

    @Override
    public List<ArrayList<Integer>> generate(GraphGeneratorConfig config) throws InterruptedException {
        List<ArrayList<Integer>> edges = new ArrayList<ArrayList<Integer>>();
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
                List<ArrayList<Integer>> edgesCopy = new ArrayList<ArrayList<Integer>>(edges);
                edgesCopy.remove(removalId);
                //edgesCopy.remove(removalId * 2);
                Map<String,String> params = Map.of("forceConnected", "true");
                bfs.setParams(params);
                Object[] arrayEdges = edgesCopy.toArray();
                int[][] intArray = edgesCopy.stream().map(  u  ->  u.stream().mapToInt(n->n).toArray()  ).toArray(int[][]::new);
                List<Integer> path = bfs.run(new ListOfEdgesUndirected(intArray)).getPath();
                if (config.getNumberOfVertices() == path.size()) {
                    break;
                }
            }
        }
        return edges;
    };
}
