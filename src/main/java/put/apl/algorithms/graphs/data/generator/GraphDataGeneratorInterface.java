package put.apl.algorithms.graphs.data.generator;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public interface GraphDataGeneratorInterface {

    GeneratorResult generate(GraphGeneratorConfig config) throws InterruptedException;
}
