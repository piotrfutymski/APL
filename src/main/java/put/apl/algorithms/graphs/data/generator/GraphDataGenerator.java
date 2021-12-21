package put.apl.algorithms.graphs.data.generator;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class GraphDataGenerator {

    public abstract List<List<Integer>> generate(GraphGeneratorConfig config) throws InterruptedException;

    public void escape() throws InterruptedException{
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
    }

    public List<List<Integer>> generateFull(GraphGeneratorConfig config, boolean directed) throws InterruptedException {
        List<List<Integer>> res = new ArrayList<>(config.getNumberOfVertices());
        for (int i = 0; i < config.getNumberOfVertices(); i++) {
            escape();
            if(directed) {
                int finalI = i;
                res.add(IntStream.range(0, config.getNumberOfVertices()).filter(e->e!= finalI).boxed().collect(Collectors.toList()));
            }else{
                res.add(IntStream.range(i+1, config.getNumberOfVertices()).boxed().collect(Collectors.toList()));
            }
        }
        return res;
    }

    public List<Set<Integer>> generateEmpty(GraphGeneratorConfig config) throws InterruptedException {
        List<Set<Integer>> res = new ArrayList<>(config.getNumberOfVertices());
        for (int i = 0; i < config.getNumberOfVertices(); i++) {
            escape();
            res.add(new HashSet<>(config.getNumberOfVertices()));
        }
        return res;
    }

    public int vertexToDeleteCount(GraphGeneratorConfig config, boolean directed){
        int res = (int)((config.getNumberOfVertices() * (config.getNumberOfVertices() - 1) * (100.0 -config.getDensity())) / 100.0);
        if(!directed)
            res /= 2.0;
        return res;
    }

    public int beginSize(GraphGeneratorConfig config, boolean directed){
        int res = (config.getNumberOfVertices()) * (config.getNumberOfVertices() - 1);
        if(!directed)
            res /= 2.0;
        return res;
    }

    public int vertexCount(GraphGeneratorConfig config, boolean directed){
        int res = (int)((config.getNumberOfVertices() * (config.getNumberOfVertices() - 1) * config.getDensity()) / 100.0);
        if(!directed)
            res /= 2.0;
        return res;
    }

    public Map<Integer, Integer> path(GraphGeneratorConfig config) throws InterruptedException {
        List<Integer> list = IntStream.range(0, config.getNumberOfVertices()).boxed().collect(Collectors.toList());
        Collections.shuffle(list);
        Map<Integer, Integer> res = new HashMap<>(config.getNumberOfVertices()-1);
        for (int i = 0; i < list.size()-1; i++) {
            escape();
            res.put(list.get(i), list.get(i+1));
        }
        return res;
    }

    public List<Integer> removeList(GraphGeneratorConfig config, boolean directed) {
        if(directed)
            return IntStream.range(0, config.getNumberOfVertices()).boxed().collect(Collectors.toList());
        else
            return IntStream.range(0, config.getNumberOfVertices() - 1).boxed().collect(Collectors.toList());
    }

    public List<List<Integer>> setsToLists(List<Set<Integer>> sets){
        return sets.stream().map(ArrayList::new).collect(Collectors.toList());
    }

    int getNext(List<Integer> toUse, List<Set<Integer>> sets, Random random) {
        if(toUse.isEmpty())
            return random.nextInt(sets.size());
        int res = toUse.get(toUse.size()-1);
        toUse.remove(toUse.size()-1);
        return res;
    }
}
