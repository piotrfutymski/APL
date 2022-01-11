package put.apl.algorithms.graphs.data.generator;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class GraphDataGenerator implements GraphDataGeneratorInterface {

    public abstract GeneratorResult generate(GraphGeneratorConfig config) throws InterruptedException;

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
        res.put(list.get(list.size()-1), list.get(0));
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

    List<Integer> getNextEuler(List<Integer> toUse, List<Set<Integer>> sets, Random random, boolean directed) throws InterruptedException {
        List<Integer> toReturn = new ArrayList<>();
        int howManyToDelete = 0;
        int ile = 0;
        while (toReturn.size() < 3){
            escape();
            if(toUse.size()-toReturn.size()>0){
                toReturn.add(toUse.get(toUse.size()-1- toReturn.size()));
                if(toReturn.size() < 3)
                    howManyToDelete++;
            }else{
                Integer randed = random.nextInt(sets.size());
                if(!toReturn.contains(randed)){
                    toReturn.add(randed);
                }
            }
            if(toReturn.size() == 3){
                int i = toReturn.get(0);
                int j = toReturn.get(1);
                int k = toReturn.get(2);
                if( (directed && (sets.get(i).contains(j) || sets.get(j).contains(k) || sets.get(k).contains(i))) ||
                        (!directed &&(sets.get(i).contains(j) || sets.get(j).contains(k) || sets.get(k).contains(i) ||
                                sets.get(j).contains(i) || sets.get(k).contains(j) || sets.get(i).contains(k)))){
                    howManyToDelete = 0;
                    toReturn.clear();
                }
            }
            ile ++;
            if(ile == 50* sets.size()){
                return new ArrayList<>();
            }
        }
        for (int i = 0; i < howManyToDelete; i++) {
            toUse.remove(toUse.size()-1);
        }
        return toReturn;
    }

}
