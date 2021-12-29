package put.apl.experiment.service;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import put.apl.algorithms.graphs.GraphResult;
import put.apl.algorithms.graphs.data.*;
import put.apl.algorithms.graphs.data.generator.GraphDataGenerator;
import put.apl.algorithms.graphs.data.generator.GraphGeneratorConfig;
import put.apl.experiment.dto.AlgorithmFuture;
import put.apl.algorithms.graphs.implementation.*;
import put.apl.experiment.dto.GraphExperiment;
import put.apl.experiment.dto.SortingExperiment;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GraphService {

    private static final int REPEAT_COUNT = 5;

    ApplicationContext context;
    GarbageCollectorFighter garbageCollectorFighter;

    public List<Object> runExperiments(List<GraphExperiment> experiments) throws InterruptedException {

        List<Object> res = new ArrayList<>();
        List<List<GraphExperiment>> results = new ArrayList<>();
        List<GraphExperiment> bannedExperiments = new ArrayList<>();
        for (int i = 0; i < REPEAT_COUNT; i++) {
            results.add(runExperimentsOnce(experiments, bannedExperiments));
        }
        for (int i = 0; i < experiments.size(); i++) {
            boolean t_is_minus = false;
            List<GraphExperiment> resultsForIExperiment = new ArrayList<>();
            for (int j = 0; j < REPEAT_COUNT; j++) {
                if(results.get(j).get(i).getTimeInMillis() < 0)
                    t_is_minus = true;
                resultsForIExperiment.add(results.get(j).get(i));
            }
            if(!t_is_minus)
                res.add(averageExperiments(resultsForIExperiment));
            else
                res.add(results.get(0).get(i));
        }
        return res;
    }

    public GraphExperiment averageExperiments(List<GraphExperiment> experiments){
        Double timeInMillis = garbageCollectorFighter.getTime(experiments.stream().map(GraphExperiment::getTimeInMillis).collect(Collectors.toList()));
        Integer hamiltonCyclesCount = null;
        Integer memoryOccupancy = null;
        Integer tableAccessCount = null;
        if(experiments.get(0).getGraphResult().getHamiltonCyclesCount() != null)
            hamiltonCyclesCount = experiments.stream().collect(Collectors.averagingInt(e->e.getGraphResult().getHamiltonCyclesCount())).intValue();
        if(experiments.get(0).getGraphResult().getMemoryOccupancyInBytes() != null)
            memoryOccupancy = experiments.stream().collect(Collectors.averagingInt(e->e.getGraphResult().getMemoryOccupancyInBytes())).intValue();
        if(experiments.get(0).getGraphResult().getTableAccessCount() != null)
            tableAccessCount = experiments.stream().collect(Collectors.averagingInt(e->e.getGraphResult().getTableAccessCount())).intValue();
        return GraphExperiment
                .builder()
                .algorithmName(experiments.get(0).getAlgorithmName())
                .representation(experiments.get(0).getRepresentation())
                .dataGenerator(experiments.get(0).getDataGenerator())
                .numberOfVertices(experiments.get(0).getNumberOfVertices())
                .density(experiments.get(0).getDensity())
                .timeInMillis(timeInMillis)
                .graphResult(GraphResult.builder()
                        .memoryOccupancyInBytes(memoryOccupancy)
                        .hamiltonCyclesCount(hamiltonCyclesCount)
                        .tableAccessCount(tableAccessCount)
                        .build()
                )
                .measureByDensity(experiments.get(0).getMeasureByDensity())
                .build()
                .clearForResponse();
    }

    private List<GraphExperiment> runExperimentsOnce(List<GraphExperiment> experiments, List<GraphExperiment> bannedExperiments) throws InterruptedException {
        List<GraphExperiment> res = new ArrayList<>();
        float experimentTimeout = (AlgorithmFuture.DEFAULT_TIMEOUT_MS * 2) / ((float)REPEAT_COUNT * experiments.size());

        List<List<GraphExperiment>> groupedExperiments =
                new ArrayList<>(experiments.stream().collect(Collectors.groupingBy(GraphExperiment::dataGeneratorGroupingString)).values());

        groupedExperiments = groupedExperiments.stream().sorted(Comparator.comparingInt(e -> e.get(0).getNumberOfVertices() + e.get(0).getDensity().intValue() )).collect(Collectors.toList());
        for (List<GraphExperiment> groupedExperiment : groupedExperiments) {
            GraphRepresentation data;
            var gowno = generateDataFor(groupedExperiment.get(0));
            for (GraphExperiment graphExperiment : groupedExperiment) {
                switch (graphExperiment.getRepresentation()) {
                    case "List Of Edges Directed":
                        data = new ListOfEdgesDirected(gowno);
                        break;
                    case "List Of Edges Undirected":
                        data = new ListOfEdgesUndirected(gowno);
                        break;
                    case "List Of Incident Undirected":
                        data = new ListOfIncidentUndirected(gowno);
                        break;
                    case "List Of Predecessors Directed":
                        data = new ListOfPredecessorsDirected(gowno);
                        break;
                    case "List Of Successors Directed":
                        data = new ListOfSuccessorsDirected(gowno);
                        break;
                    case "Adjacency Matrix Directed":
                        data = new AdjacencyMatrixDirected(gowno);
                        break;
                    case "Adjacency Matrix Undirected":
                        data = new AdjacencyMatrixUndirected(gowno);
                        break;
                    case "Weighted Adjacency Matrix Directed":
                        data = new AdjacencyMatrixDirectedWeighted(gowno);
                        break;
                    case "Weighted Adjacency Matrix Undirected":
                        data = new AdjacencyMatrixUndirectedWeighted(gowno);
                        break;
                    case "Incidence Matrix Directed":
                        data = new IncidenceMatrixDirected(gowno);
                        break;
                    case "Incidence Matrix Undirected":
                        data = new  IncidenceMatrixUndirected(gowno);
                        break;
                    case "Incidence Matrix Directed Weighted":
                        data = new IncidenceMatrixDirectedWeighted(gowno);
                        break;
                    case "Incidence Matrix Undirected Weighted":
                        data = new IncidenceMatrixUndirectedWeighted(gowno);
                        break;
                    default:
                        data = new ListOfSuccessorsDirected(gowno);
                        break;
                }
                res.add(runExperiment(graphExperiment, data.clone(), bannedExperiments, experimentTimeout));
            }
        }

        return res;
    }

    private GraphExperiment runExperiment(GraphExperiment e, GraphRepresentation data, List<GraphExperiment> bannedExperiments, float experimentTimeout) throws InterruptedException {
        if(bannedExperiments.stream().anyMatch(
                b->
                        b.getAlgorithmName().equals(e.getAlgorithmName()) &&
                                b.getNumberOfVertices().equals(e.getNumberOfVertices()) &&
                                b.getDensity().equals(e.getDensity()) &&
                                b.getDataGenerator().equals(e.getDataGenerator())

        )){
            GraphExperiment res = e.clone();
            res.setTimeInMillis(-1.0);
            return res;
        }
        GraphAlgorithm algorithm = (GraphAlgorithm) context.getBean(e.getAlgorithmName());
        /*if(e.getAlgorithmParams() != null && e.getForceConnected() != null && !e.getAlgorithmParams().containsKey("forceConnected"))
            e.getAlgorithmParams().putAll(Map.of("forceConnected", e.getForceConnected().toString()));
        else
            e.setAlgorithmParams(Map.of("forceConnected", e.getForceConnected().toString()));
        algorithm.setParams(e.getAlgorithmParams());*/
        long start = System.nanoTime();
        e.setGraphResult(algorithm.run(data));
        long end = System.nanoTime();
        double t = (double)(end-start)/1000000.0;
        GraphExperiment res = e.clone();
        res.setTimeInMillis(t);
        // So the browser doesn't receive megabytes/gigabytes of data
        res.getGraphResult().setMultiplePaths(null);
        if(t > experimentTimeout){
            bannedExperiments.add(e);
            res.setTimeInMillis(-1.0);
            return res;
        }
        return res;
    }

    private List<List<Integer>> generateDataFor(GraphExperiment graphExperiment) throws InterruptedException {
        GraphGeneratorConfig config = GraphGeneratorConfig.builder()
                .density(graphExperiment.getDensity())
                .numberOfVertices(graphExperiment.getNumberOfVertices())
                .type(graphExperiment.getDataGenerator())
                .build();
        GraphDataGenerator generator = (GraphDataGenerator) context.getBean(graphExperiment.getDataGenerator());
        return generator.generate(config);
    }


    public String[] getPossibleGraphAlgorithms() {
        return context.getBeanNamesForType(GraphAlgorithm.class);
    }

    public String[] getPossibleGraphGenerators() {
        return context.getBeanNamesForType(GraphDataGenerator.class);
    }

    public String[] getPossibleGraphRepresentations() {
        return context.getBeanNamesForType(GraphRepresentation.class);
    }
}
