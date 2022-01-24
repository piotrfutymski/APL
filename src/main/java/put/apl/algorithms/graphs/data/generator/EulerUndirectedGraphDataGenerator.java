package put.apl.algorithms.graphs.data.generator;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;



@Component("Eulerian Undirected Graph")

public class EulerUndirectedGraphDataGenerator extends GraphDataGenerator {

    @Override
    public GeneratorResult generate(GraphGeneratorConfig config) throws InterruptedException {
        Random random = new Random();
        List<Set<Integer>> res = generateEmpty(config);
        int toAdd = vertexCount(config, false);
        int added = 0;

        List<Integer> toUse = IntStream.range(0, config.getNumberOfVertices()).boxed().collect(Collectors.toList());
        Collections.shuffle(toUse);

        while (added < toAdd || toUse.size() > 0){
            escape();
            List<Integer> ijk = getNextEuler(toUse, res, random, false);
            if(ijk.isEmpty())
                break;
            int i = ijk.get(0);
            int j = ijk.get(1);
            int k = ijk.get(2);
            if(i < j)
                res.get(i).add(j);
            else
                res.get(j).add(i);
            if(j < k )
                res.get(j).add(k);
            else
                res.get(k).add(j);
            if(k < i)
                res.get(k).add(i);
            else
                res.get(i).add(k);
            added+=3;
        }
        return GeneratorResult.builder().representation(setsToLists(res)).build();
    }


}
