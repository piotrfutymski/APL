package put.apl.algorithms.graphs.data;
import org.springframework.stereotype.Component;
import put.apl.algorithms.graphs.implementation.BreadthFirstSearch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;


@Component("Connected Undirected Graph Generator")
public class ConnectedUndirectedGraphDataGenerator implements GraphDataGenerator {

    @Override
    public String generate(GraphGeneratorConfig config) throws InterruptedException {
        List<ArrayList<Integer>> edges = new ArrayList<ArrayList<Integer>>();
        Random random = new Random();
        // n(n-1) - g•n(n-1)/2
        int numToDiscard = (int) (config.getNoOfVertices() * (config.getNoOfVertices() - 1)
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
        // Randomly delete edges (check if deletion breaks connectivity of the graph)
        for (int i = 0; i < numToDiscard / 2; i++) {
            while (true) {
                int removalId = random.nextInt(edges.size() / 2);
                BreadthFirstSearch bfs = new BreadthFirstSearch();
                List<ArrayList<Integer>> edgesCopy = new ArrayList<ArrayList<Integer>>(edges);
                edgesCopy.remove(removalId * 2);
                edgesCopy.remove(removalId * 2);
                Map<String,String> params = Map.of("forceConnected", "true");
                bfs.setParams(params);
                List<Integer> path = bfs.run(new ListOfEdgesUndirected((int[][]) edgesCopy.toArray()));
                if (config.getNoOfVertices() == path.size()) {
                    break;
                }
            }
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
