package put.apl.algorithms.graphs.data.generator;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;


@Component("Directed Graph Generator")
public class DirectedGraphDataGenerator extends GraphDataGenerator {

    @Override
    public GeneratorResult generate(GraphGeneratorConfig config) throws InterruptedException {
        Random random = new Random();
        List<List<Integer>> res = generateFull(config, true);
        int toRemove = vertexToDeleteCount(config, true);
        List<Integer> removeSet = removeList(config, true);
        int removed = 0;
        while (removed < toRemove){
            escape();
            int i = random.nextInt(removeSet.size());
            int l = removeSet.get(i);
            int j = random.nextInt(res.get(l).size());
            res.get(l).remove(j);
            if(res.get(l).isEmpty())
                removeSet.remove(i);
            removed++;
        }
        return GeneratorResult.builder().representation(res).build();
    };
}
