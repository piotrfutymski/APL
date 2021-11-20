package put.apl.algorithms.graphs.data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Component("connectedUndirectedData")
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
                if (hasNeighborsAfterRemove(edges, removalId)) {
                    edges.remove(removalId);
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

    private boolean hasNeighborsAfterRemove(List<ArrayList<Integer>> edges, int id) {
        int firstVertex = edges.get(id * 2).get(0);
        int secondVertex = edges.get(id * 2).get(1);
        boolean firstVertexHasNeighbour = false;
        boolean secondVertexHasNeighbour = false;
        for (int i = 0; i < edges.size(); i++) {
            if (i == id * 2 || i == id * 2 + 1) {
                continue;
            }
            if (edges.get(i).get(0) == firstVertex || edges.get(i).get(1) == firstVertex) {
                firstVertexHasNeighbour = true;
                if (secondVertexHasNeighbour) {
                    return true;
                }
            }
            if (edges.get(i).get(0) == secondVertex || edges.get(i).get(1) == secondVertex) {
                secondVertexHasNeighbour = true;
                if (firstVertexHasNeighbour) {
                    return true;
                }
            }
        }
        return false;
    }
}
