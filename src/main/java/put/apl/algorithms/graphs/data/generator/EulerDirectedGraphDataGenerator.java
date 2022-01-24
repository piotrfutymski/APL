package put.apl.algorithms.graphs.data.generator;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Component("Eulerian Directed Graph")

public class EulerDirectedGraphDataGenerator extends GraphDataGenerator {

    @Override
    public GeneratorResult generate(GraphGeneratorConfig config) throws InterruptedException {
        Random random = new Random();
        List<Set<Integer>> res = generateEmpty(config);
        int toAdd = vertexCount(config, true);
        int added = 0;

        List<Integer> toUse = IntStream.range(0, config.getNumberOfVertices()).boxed().collect(Collectors.toList());
        Collections.shuffle(toUse);

        while (added < toAdd || !toUse.isEmpty()){
            escape();
            List<Integer> ijk = getNextEuler(toUse, res, random, true);
            if(ijk.isEmpty())
                break;
            int i = ijk.get(0);
            int j = ijk.get(1);
            int k = ijk.get(2);
            res.get(i).add(j);
            res.get(j).add(k);
            res.get(k).add(i);
            added+=3;
        }
        return GeneratorResult.builder().representation(setsToLists(res)).build();
    }
}
