package put.apl.algorithms.graphs.data.generator;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Component("Euler Undirected Graph Generator")
public class EulerUndirectedGraphDataGenerator extends GraphDataGenerator {

    @Override
    public GeneratorResult generate(GraphGeneratorConfig config) throws InterruptedException {
        Random random = new Random();
        List<Set<Integer>> res = generateEmpty(config);
        int toAdd = vertexCount(config, false);
        int added = 0;

        List<Integer> toUse = IntStream.range(0, config.getNumberOfVertices()).boxed().collect(Collectors.toList());
        if(toUse.size() % 3 == 1)
            toUse.add(0);
        if(toUse.size() % 3 == 2)
            toUse.add(1);
        Collections.shuffle(toUse);

        while (added < toAdd || toUse.size() > 0){
            escape();
            int i = getNext(toUse, res, random);
            int j = getNext(toUse, res, random);
            int k = getNext(toUse, res, random);
            if(i == j || j == k || i == k)
                continue;
            if(res.get(i).contains(j) || res.get(j).contains(k) || res.get(k).contains(i) ||
                    res.get(j).contains(i) || res.get(k).contains(j) || res.get(i).contains(k))
                continue;
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
    };


}
