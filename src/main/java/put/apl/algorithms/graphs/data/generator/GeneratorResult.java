package put.apl.algorithms.graphs.data.generator;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeneratorResult {
    //Result
    protected List<List<Integer>> representation;
    protected List<List<Integer>> weights;

    public void generateWeights(GraphGeneratorConfig config)
    {
        int max = config.getNumberOfVertices();
        List<List<Integer>> weights = new ArrayList<>();
        Random gen = new Random();
        for (var vertex : representation)
        {
            List<Integer> vertexWeights= new ArrayList<>();
            for (int i=0; i<vertex.size(); i++)
                vertexWeights.add(gen.nextInt(max)+1);
            weights.add(vertexWeights);
        }
        this.weights = weights;
    }
}