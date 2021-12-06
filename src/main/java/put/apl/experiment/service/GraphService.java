package put.apl.experiment.service;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import put.apl.algorithms.graphs.data.GraphDataGenerator;
import put.apl.algorithms.graphs.data.GraphGeneratorConfig;
import put.apl.algorithms.graphs.data.GraphRepresentation;
import put.apl.algorithms.graphs.data.ListOfSuccessorsDirected;
import put.apl.algorithms.graphs.implementation.GraphAlgorithm;
import put.apl.experiment.dto.GraphExperiment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GraphService {

    private static final int REPEAT_COUNT = 5;

    ApplicationContext context;

    public List<Object> runExperiments(List<GraphExperiment> experiments) throws InterruptedException {

        List<Object> res = new ArrayList<>();
        List<List<GraphExperiment>> results = new ArrayList<>();
        for (int i = 0; i < REPEAT_COUNT; i++) {
            results.add(runExperimentsOnce(experiments));
        }
        for (int i = 0; i < experiments.size(); i++) {
            List<GraphExperiment> resultsForIExperiment = new ArrayList<>();
            for (int j = 0; j < REPEAT_COUNT; j++) {
                resultsForIExperiment.add(results.get(j).get(i));
            }
            res.add(averageExperiments(resultsForIExperiment));
        }
        return res;
    }

    public GraphExperiment averageExperiments(List<GraphExperiment> experiments){
        Double timeInNano = experiments.stream().collect(Collectors.averagingDouble(GraphExperiment::getTimeInNano));
        return GraphExperiment
                .builder()
                .algorithmName(experiments.get(0).getAlgorithmName())
                .graphRepresentation(experiments.get(0).getGraphRepresentation())
                .type(experiments.get(0).getType())
                .noOfVertices(experiments.get(0).getNoOfVertices())
                .density(experiments.get(0).getDensity())
                .timeInNano(experiments.get(0).getTimeInNano())
                .memoryOccupancyInBytes(experiments.get(0).getMemoryOccupancyInBytes())
                .acyclicCount(experiments.get(0).getAcyclicCount())
                .hamiltonCyclesCount(experiments.get(0).getHamiltonCyclesCount())
                .minimumSpanningTree(experiments.get(0).getMinimumSpanningTree())
                .shortestPath(experiments.get(0).getShortestPath())
                .build();
    }

    private List<GraphExperiment> runExperimentsOnce(List<GraphExperiment> experiments) throws InterruptedException {
        List<GraphExperiment> res = new ArrayList<>();

        List<List<GraphExperiment>> groupedExperiments =
                new ArrayList<>(experiments.stream().collect(Collectors.groupingBy(GraphExperiment::dataGeneratorGroupingString)).values());

        for (List<GraphExperiment> groupedExperiment : groupedExperiments) {
            String representation = groupedExperiment.get(0).getGraphRepresentation();
            GraphRepresentation data;
            switch (representation) {
                case "LIST_SUCC_D":
                    data = new ListOfSuccessorsDirected(generateDataFor(groupedExperiment.get(0)));
                    break;
                // OTHER CASES
                default:
                    data = new ListOfSuccessorsDirected(generateDataFor(groupedExperiment.get(0)));
                    break;
            }
            for (GraphExperiment graphExperiment : groupedExperiment) {
                res.add(runExperiment(graphExperiment, data.clone()));
            }
        }

        return res;
    }

    private GraphExperiment runExperiment(GraphExperiment e, GraphRepresentation data) throws InterruptedException {
        GraphAlgorithm<GraphRepresentation> algorithm = (GraphAlgorithm<GraphRepresentation>) context.getBean(e.getAlgorithmName());
        /*if(e.getAlgorithmParams() != null && !e.getAlgorithmParams().containsKey("maxValue"))
            e.getAlgorithmParams().putAll(Map.of("maxValue", e.getMaxValue().toString()));
        else
            e.setAlgorithmParams(Map.of("maxValue", e.getMaxValue().toString()));
        algorithm.setParams(e.getAlgorithmParams());*/
        long start = System.nanoTime();
        algorithm.run(data);
        long end = System.nanoTime();
        GraphExperiment res = e.clone();
        res.setTimeInNano((double)(end-start)/1000000000.0);
        return res;
    }

    private String generateDataFor(GraphExperiment graphExperiment) throws InterruptedException {
        GraphGeneratorConfig config = GraphGeneratorConfig.builder()
                .density(graphExperiment.getDensity())
                .noOfVertices(graphExperiment.getNoOfVertices())
                .build();
        GraphDataGenerator generator = (GraphDataGenerator) context.getBean(graphExperiment.getType());
        return generator.generate(config);
    }


    public String[] getPossibleGraphAlgorithms() {
        return context.getBeanNamesForType(GraphAlgorithm.class);
    }

    public String[] getPossibleGraphGenerators() {
        return context.getBeanNamesForType(GraphDataGenerator.class);
    }
}
