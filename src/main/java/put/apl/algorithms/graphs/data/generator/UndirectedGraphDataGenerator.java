package put.apl.algorithms.graphs.data.generator;
import org.springframework.stereotype.Component;

import java.util.*;


@Component("Undirected Graph Generator")
public class UndirectedGraphDataGenerator extends GraphDataGenerator {

    @Override
    public List<List<Integer>> generate(GraphGeneratorConfig config) throws InterruptedException {
        Random random = new Random();
        List<List<Integer>> res = generateFull(config, false);
        int toRemove = vertexToDeleteCount(config, false);
        List<Integer> removeSet = removeList(config, false);
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
        return res;
    };
}
