package put.apl.algorithms.graphs.data.generator;
import org.springframework.stereotype.Component;
import put.apl.algorithms.graphs.data.ListOfEdgesDirected;
import put.apl.algorithms.graphs.data.ListOfSuccessorsDirected;
import put.apl.algorithms.graphs.implementation.BreadthFirstSearch;

import java.util.*;


@Component("Connected Directed Graph Generator")
public class ConnectedDirectedGraphDataGenerator extends GraphDataGenerator {

    @Override
    public List<List<Integer>> generate(GraphGeneratorConfig config) throws InterruptedException {
        Random random = new Random();
        List<List<Integer>> res = generateFull(config, true);
        Map<Integer, Integer> path = path(config);
        int toRemove = Math.min(vertexToDeleteCount(config, true), beginSize(config, true) - path.size());
        List<Integer> removeSet = removeList(config, true);
        int removed = 0;
        while (removed < toRemove){
            escape();
            int i = random.nextInt(removeSet.size());
            int l = removeSet.get(i);
            int j = random.nextInt(res.get(l).size());
            int u = res.get(l).get(j);
            if(!Objects.equals(path.get(l),u)){
                res.get(l).remove(j);
                removed++;
                if(res.get(l).isEmpty())
                    removeSet.remove(i);
            }else{
                if(res.get(l).size() == 1 && res.get(l).contains(u))
                    removeSet.remove(i);
            }
        }
        return res;
    }
}