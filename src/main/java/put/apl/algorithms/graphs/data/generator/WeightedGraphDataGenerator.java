package put.apl.algorithms.graphs.data.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WeightedGraphDataGenerator implements GraphDataGeneratorInterface {

    WeightedGraphDataGenerator(GraphDataGeneratorInterface parent)
    {
        this.parent = parent;
    }

    GraphDataGeneratorInterface parent;

    @Override
    public GeneratorResult generate(GraphGeneratorConfig config) throws InterruptedException {
        var graph = parent.generate(config);
        graph.setWeights(generateWeights(graph.representation,config));
        return graph;
    }

    private List<List<Integer>> generateWeights(List<List<Integer>> graph, GraphGeneratorConfig config)
    {
        int max = config.getNumberOfVertices();
        List<List<Integer>> weights = new ArrayList<List<Integer>>();
        Random gen = new Random();
        for (var vertex : graph)
        {
            List<Integer> vertexWeights= new ArrayList<Integer>();
            for (int i=0; i<vertex.size(); i++)
                vertexWeights.add(gen.nextInt(max));
            weights.add(vertexWeights);
        }
        return weights;
    }
}
