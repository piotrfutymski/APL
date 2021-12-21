package put.apl.algorithms.graphs.data.generator;
import org.springframework.stereotype.Component;
import put.apl.algorithms.graphs.data.ListOfEdgesUndirected;
import put.apl.algorithms.graphs.data.ListOfIncident;
import put.apl.algorithms.graphs.data.ListOfIncidentUndirected;
import put.apl.algorithms.graphs.implementation.BreadthFirstSearch;

import java.util.*;


@Component("Connected Undirected Graph Generator")
public class ConnectedUndirectedGraphDataGenerator extends GraphDataGenerator {

    @Override
    public List<List<Integer>> generate(GraphGeneratorConfig config) throws InterruptedException {
        Random random = new Random();
        List<List<Integer>> res = generateFull(config, false);
        Map<Integer, Integer> path = path(config);
        int toRemove = Math.min(vertexToDeleteCount(config, false), beginSize(config, false) - path.size());
        List<Integer> removeSet = removeList(config, false);
        int removed = 0;
        while (removed < toRemove){
            escape();
            int i = random.nextInt(removeSet.size());
            int l = removeSet.get(i);
            int j = random.nextInt(res.get(l).size());
            int u = res.get(l).get(j);
            if(!Objects.equals(path.get(u), l) && !Objects.equals(path.get(l), u)){
                res.get(l).remove(j);
                removed++;
                if(res.get(l).isEmpty())
                    removeSet.remove(i);
            }
        }
        return res;
    }
}
