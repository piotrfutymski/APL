package put.apl.algorithms.graphs.data.generator;
import org.springframework.stereotype.Component;
import put.apl.algorithms.graphs.data.GraphRepresentation;
import put.apl.algorithms.graphs.data.ListOfEdgesDirected;
import put.apl.algorithms.graphs.implementation.BreadthFirstSearch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;


@Component("Euler Directed Graph Generator")
public class EulerDirectedGraphDataGenerator implements GraphDataGenerator {

    @Override
    public String generate(GraphGeneratorConfig config) throws InterruptedException {
        int [][] matrix = new int[config.getNoOfVertices()][];
        Random random = new Random();
        // n(n-1) - g•n(n-1)/2
        // 10 * 9
        // 90
        //70%
        // czyli teoretycznie powinnismy miec 90 * 0.7 = 63 krawedzie
        // 90 - 0.7 * 90 / 2 = 90 - 31.5 = 58?
        int numToDiscard = (int) (config.getNoOfVertices() * (config.getNoOfVertices() - 1)
                - (config.getDensity() * config.getNoOfVertices() * (config.getNoOfVertices() - 1) / 2));
        for (int i = 0; i < config.getNoOfVertices(); i++) {
            for (int j = 0; j < config.getNoOfVertices(); j++) {
                if (i == j) continue;
                ArrayList<Integer> newEdge = new ArrayList<Integer>();
                newEdge.add(i);
                newEdge.add(j);
                edges.add(newEdge);
            }
        }
        GraphRepresentation graph = new ListOfEdgesDirected();
        // Randomly delete edges (check if deletion breaks connectivity of the graph)
        for (int i = 0; i < numToDiscard; i++) {
            while (true) {
                int removalId = random.nextInt(edges.size());
                BreadthFirstSearch bfs = new BreadthFirstSearch();
                List<ArrayList<Integer>> edgesCopy = new ArrayList<ArrayList<Integer>>(edges);
                edgesCopy.remove(removalId);
                Map<String,String> params = Map.of("forceConnected", "true");
                bfs.setParams(params);
                graph = new ListOfEdgesDirected((int[][]) edgesCopy.toArray());
                List<Integer> path = bfs.run(graph).getPath();
                if (config.getNoOfVertices() == path.size()) {
                    break;
                }
            }
        }
        //we  have connected graph with good amount of edges
        List<Integer> vertices = new ArrayList<Integer>();
        int balanceSum = 0;
        for (int i=0;i<config.getNoOfVertices();i++)
        {
            int edgesBalance = graph.getSuccessors(i).length - graph.getPredecessors(i).length;
            balanceSum += edgesBalance;
            vertices.add(edgesBalance);
        }
        if (balanceSum % 2 == 0)
        {
            //teoretycznie mozemy przejsc
            for (int i=0; i<config.getNoOfVertices();i++)
            {
                while (vertices.get(i) > 0)
                {

                }
            }
        }
        else
        {

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
