package put.apl.algorithms.graphs.data.generator;

import java.util.ArrayList;
import java.util.List;

public interface GraphDataGenerator {

    List<ArrayList<Integer>> generate(GraphGeneratorConfig config) throws InterruptedException;
}
