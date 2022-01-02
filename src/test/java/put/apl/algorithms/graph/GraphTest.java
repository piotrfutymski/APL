package put.apl.algorithms.graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import put.apl.algorithms.graphs.data.*;
import put.apl.algorithms.graphs.data.generator.*;
import put.apl.algorithms.graphs.implementation.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


public class GraphTest {
    static List<List<Integer>> getDirectedGraph()
    {
        ArrayList<List<Integer>> list = new ArrayList<>();
        list.add(Arrays.asList(3));
        list.add(Arrays.asList(0,2));
        list.add(Arrays.asList(4));
        list.add(Arrays.asList(1,4));
        list.add(Arrays.asList(0,1,5));
        list.add(Arrays.asList(1,2));
        return list;
    }

    static GeneratorResult getDirectedWeightedGraph()
    {

        ArrayList<List<Integer>> list_edges = new ArrayList<>();
        list_edges.add(Arrays.asList(3));
        list_edges.add(Arrays.asList(0,2));
        list_edges.add(Arrays.asList(4));
        list_edges.add(Arrays.asList(1,4));
        list_edges.add(Arrays.asList(0,1,5));
        list_edges.add(Arrays.asList(1,2));

        ArrayList<List<Integer>> list_weights = new ArrayList<>();
        list_weights.add(Arrays.asList(3));
        list_weights.add(Arrays.asList(5,4));
        list_weights.add(Arrays.asList(2));
        list_weights.add(Arrays.asList(3,5));
        list_weights.add(Arrays.asList(4,2,4));
        list_weights.add(Arrays.asList(3,3));

        return GeneratorResult.builder().representation(list_edges).weights(list_weights).build();
    }

    static List<List<Integer>> getUndirectedGraph()
    {
        ArrayList<List<Integer>> list = new ArrayList<>();
        list.add(Arrays.asList(2,4,5));//0
        list.add(Arrays.asList(4,5));//1
        list.add(Arrays.asList(3,4,5));//2
        list.add(Arrays.asList(4));//3
        list.add(Arrays.asList());//4
        list.add(Arrays.asList());//5
        return list;
    }

    static GeneratorResult getUndirectedWeightedGraph()
    {

        ArrayList<List<Integer>> list_edges = new ArrayList<>();
        list_edges.add(Arrays.asList(2,4,5));//0
        list_edges.add(Arrays.asList(4,5));//1
        list_edges.add(Arrays.asList(3,4,5));//2
        list_edges.add(Arrays.asList(4));//3
        list_edges.add(Arrays.asList());//4
        list_edges.add(Arrays.asList());//5

        ArrayList<List<Integer>> list_weights = new ArrayList<>();
        list_weights.add(Arrays.asList(2,4,5));//0
        list_weights.add(Arrays.asList(4,5));//1
        list_weights.add(Arrays.asList(3,4,5));//2
        list_weights.add(Arrays.asList(4));//3
        list_weights.add(Arrays.asList());//4
        list_weights.add(Arrays.asList());//5
        return GeneratorResult.builder().representation(list_edges).weights(list_weights).build();
    }

    static List<List<Integer>> getConnectedDirectedGraph()
    {
        ArrayList<List<Integer>> list = new ArrayList<>();
        list.add(Arrays.asList(1,2));
        list.add(Arrays.asList(4));
        list.add(Arrays.asList(3));
        list.add(Arrays.asList(7,8));
        list.add(Arrays.asList(5,6));
        list.add(Arrays.asList(8));
        list.add(Arrays.asList(0,1));
        list.add(Arrays.asList(8));
        list.add(Arrays.asList(0,2));
        return list;
    }

    static GeneratorResult getConnectedDirectedWeightedGraph()
    {
        ArrayList<List<Integer>> list_edges = new ArrayList<>();
        list_edges.add(Arrays.asList(1,2));
        list_edges.add(Arrays.asList(4));
        list_edges.add(Arrays.asList(3));
        list_edges.add(Arrays.asList(7,8));
        list_edges.add(Arrays.asList(5,6));
        list_edges.add(Arrays.asList(8));
        list_edges.add(Arrays.asList(0,1));
        list_edges.add(Arrays.asList(8));
        list_edges.add(Arrays.asList(0,2));

        ArrayList<List<Integer>> list_weights = new ArrayList<>();
        list_weights.add(Arrays.asList(7,1));
        list_weights.add(Arrays.asList(5));
        list_weights.add(Arrays.asList(7));
        list_weights.add(Arrays.asList(1,2));
        list_weights.add(Arrays.asList(8,7));
        list_weights.add(Arrays.asList(3));
        list_weights.add(Arrays.asList(3,2));
        list_weights.add(Arrays.asList(5));
        list_weights.add(Arrays.asList(6,9));

        return GeneratorResult.builder().representation(list_edges).weights(list_weights).build();
    }

    static List<List<Integer>> getConnectedUndirectedGraph()
    {
        ArrayList<List<Integer>> list = new ArrayList<>();
        list.add(Arrays.asList(1,2));
        list.add(Arrays.asList(4));
        list.add(Arrays.asList(3));
        list.add(Arrays.asList(7,8));
        list.add(Arrays.asList(5,6));
        list.add(Arrays.asList(8));
        list.add(Arrays.asList());
        list.add(Arrays.asList(8));
        list.add(Arrays.asList());
        return list;
    }

    static GeneratorResult getConnectedUndirectedWeightedGraph()
    {
        ArrayList<List<Integer>> list_edges = new ArrayList<>();
        list_edges.add(Arrays.asList(1,2));
        list_edges.add(Arrays.asList(4));
        list_edges.add(Arrays.asList(3));
        list_edges.add(Arrays.asList(7,8));
        list_edges.add(Arrays.asList(5,6));
        list_edges.add(Arrays.asList(8));
        list_edges.add(Arrays.asList());
        list_edges.add(Arrays.asList(8));
        list_edges.add(Arrays.asList());

        ArrayList<List<Integer>> list_weights = new ArrayList<>();
        list_weights.add(Arrays.asList(9,5));
        list_weights.add(Arrays.asList(1));
        list_weights.add(Arrays.asList(3));
        list_weights.add(Arrays.asList(4,5));
        list_weights.add(Arrays.asList(5,6));
        list_weights.add(Arrays.asList(2));
        list_weights.add(Arrays.asList());
        list_weights.add(Arrays.asList(2));
        list_weights.add(Arrays.asList());
        return GeneratorResult.builder().representation(list_edges).weights(list_weights).build();
    }

    static List<List<Integer>> getEulerianDirectedGraph()
    {
        ArrayList<List<Integer>> list = new ArrayList<>();
        list.add(Arrays.asList(1,2));//0
        list.add(Arrays.asList(3,4));//1
        list.add(Arrays.asList(3));//2
        list.add(Arrays.asList(5,7));//3
        list.add(Arrays.asList(5,6));//4
        list.add(Arrays.asList(7,8));//5
        list.add(Arrays.asList(0));//6
        list.add(Arrays.asList(1,8));//7
        list.add(Arrays.asList(4,0));//8
        return list;
    }

    static List<List<Integer>> getEulerianUndirectedGraph()
    {
        ArrayList<List<Integer>> list = new ArrayList<>();
        list.add(Arrays.asList(1,2));//0
        list.add(Arrays.asList(4));//1
        list.add(Arrays.asList(3));//2
        list.add(Arrays.asList(7,8,5));//3
        list.add(Arrays.asList(5,6));//4
        list.add(Arrays.asList(8,6));//5
        list.add(Arrays.asList());//6
        list.add(Arrays.asList(8));//7
        list.add(Arrays.asList(4));//8
        return list;
    }

    static List<List<Integer>> getHamiltonianUndirectedGraph()
    {
        List<List<Integer>> list = new ArrayList<List<Integer>>();
        list.add(Arrays.asList(1,3));
        list.add(Arrays.asList(2,3,4));
        list.add(Arrays.asList(4));
        list.add(Arrays.asList(4));
        list.add(Arrays.asList());
        return list;
    }

    static List<List<Integer>> getHamiltonianDirectedGraph()
    {
        List<List<Integer>> list = new ArrayList<List<Integer>>();
        list.add(Arrays.asList(1));
        list.add(Arrays.asList(2,3,4));
        list.add(Arrays.asList(4));
        list.add(Arrays.asList(0));
        list.add(Arrays.asList(3));
        return list;
    }

    static List<Integer> BFS_RESULT = new ArrayList<Integer>();
    static List<Integer> TOPO_SORT_RESULT = new ArrayList<Integer>();
    static List<ArrayList<Integer>> ALL_HAMILTONIAN_RESULT = new ArrayList<ArrayList<Integer>>();

    List<GraphRepresentationInterface> undirected;

    List<GraphRepresentationInterface> directed;

    List<GraphRepresentationWeightedInterface> undirectedWeighted;

    List<GraphRepresentationWeightedInterface> directedWeighted;

    @BeforeEach
    void initAll() {

        BFS_RESULT.clear();
        BFS_RESULT.add(0);
        BFS_RESULT.add(1);
        BFS_RESULT.add(2);
        BFS_RESULT.add(4);
        BFS_RESULT.add(5);
        BFS_RESULT.add(3);

        TOPO_SORT_RESULT.clear();
        TOPO_SORT_RESULT.add(5);
        TOPO_SORT_RESULT.add(4);
        TOPO_SORT_RESULT.add(2);
        TOPO_SORT_RESULT.add(3);
        TOPO_SORT_RESULT.add(1);
        TOPO_SORT_RESULT.add(0);

        ALL_HAMILTONIAN_RESULT.clear();
        ALL_HAMILTONIAN_RESULT.add(new ArrayList<Integer>());
        ALL_HAMILTONIAN_RESULT.add(new ArrayList<Integer>());
        ALL_HAMILTONIAN_RESULT.get(0).add(0);
        ALL_HAMILTONIAN_RESULT.get(0).add(1);
        ALL_HAMILTONIAN_RESULT.get(0).add(2);
        ALL_HAMILTONIAN_RESULT.get(0).add(4);
        ALL_HAMILTONIAN_RESULT.get(0).add(3);
        ALL_HAMILTONIAN_RESULT.get(1).add(0);
        ALL_HAMILTONIAN_RESULT.get(1).add(3);
        ALL_HAMILTONIAN_RESULT.get(1).add(4);
        ALL_HAMILTONIAN_RESULT.get(1).add(2);
        ALL_HAMILTONIAN_RESULT.get(1).add(1);

        undirected = Arrays.asList(new ListOfIncidentUndirected(),
                new ListOfEdgesUndirected(), new IncidenceMatrixUndirected(), new AdjacencyMatrixUndirected(),
                new IncidenceMatrixUndirectedWeighted(), new AdjacencyMatrixUndirectedWeighted(),
                new ListOfEdgesUndirectedWeighted());

        directed = Arrays.asList(new ListOfEdgesDirected(), new ListOfPredecessorsDirected(),
                new ListOfSuccessorsDirected(), new AdjacencyMatrixDirected(), new IncidenceMatrixDirected(),
                new AdjacencyMatrixDirectedWeighted(), new IncidenceMatrixDirectedWeighted(),
                new ListOfSuccessorsDirectedWeighted(), new ListOfPredecessorsDirectedWeighted(),
                new ListOfEdgesDirectedWeighted());

        undirectedWeighted = Arrays.asList(new IncidenceMatrixUndirectedWeighted(), new AdjacencyMatrixUndirectedWeighted(),
                new ListOfEdgesUndirectedWeighted());

        directedWeighted = Arrays.asList(new AdjacencyMatrixDirectedWeighted(), new IncidenceMatrixDirectedWeighted(),
                new ListOfSuccessorsDirectedWeighted(), new ListOfPredecessorsDirectedWeighted(),
                new ListOfEdgesDirectedWeighted());
    }


    String getInfo(GraphRepresentationInterface representation, Integer[] expected, Integer[] actual)
    {
        return representation.getClass().toString() + "\nExpected: " +
                Arrays.toString(expected) +
                "\nActual: " + Arrays.toString(actual);
    }

    void doAlgorithmTest(GraphAlgorithm algorithm, List<List<Integer>> graph,
                         List<GraphRepresentationInterface> reps, Integer[] expectedResult)
    {
        for (var representation : reps)
        {
            representation.loadFromIncidenceList(graph);
            Integer[] result = algorithm.run(representation).getPath().toArray(new Integer[0]);
            assertArrayEquals(expectedResult, result,
                    getInfo(representation, expectedResult, result));
        }
    }

    void doAlgorithmWeightsTest(GraphAlgorithm algorithm, List<List<Integer>> graph, List<List<Integer>> weights,
                                List<GraphRepresentationWeightedInterface> reps, Integer[] expectedResult)
    {
        for (var representation : reps)
        {
            representation.loadFromIncidenceList(graph, weights);
            Integer[] result = algorithm.run(representation).getPath().toArray(new Integer[0]);
            assertArrayEquals(expectedResult, result,
                    getInfo(representation, expectedResult, result));
        }
    }

    //TESTS

    @Test
    void UndirectedRepresentationTest() throws InterruptedException {
        var expectedSuccessors = new int[][] {{2,4,5}, {4,5}, {0,3,4,5}, {2,4}, {0,1,2,3}, {0,1,2}};
        for( var representation : undirected)
        {
            representation.loadFromIncidenceList(getUndirectedGraph());
            for (int i=0;i<expectedSuccessors.length;i++)
            {
                var succ = representation.getSuccessors(i);
                var pre = representation.getPredecessors(i);
                assertArrayEquals(expectedSuccessors[i], representation.getSuccessors(i), representation.getClass().toString());
                assertArrayEquals(expectedSuccessors[i], representation.getPredecessors(i), representation.getClass().toString());
            }
        }
    }


    @Test
    void DirectedRepresentationTest() throws InterruptedException {
        var expectedSuccessors = new int[][] {{3}, {0,2}, {4}, {1,4}, {0,1,5}, {1,2}};
        var expectedPredecessors = new int[][] {{1,4}, {3,4,5}, {1,5}, {0}, {2,3}, {4}};
        for( var representation : directed)
        {
            representation.loadFromIncidenceList(getDirectedGraph());
            for (int i=0;i<expectedSuccessors.length;i++)
            {
                var succ = representation.getSuccessors(i);
                var pre = representation.getPredecessors(i);
                assertArrayEquals(expectedSuccessors[i], representation.getSuccessors(i), representation.getClass().toString());
                assertArrayEquals(expectedPredecessors[i], representation.getPredecessors(i), representation.getClass().toString());
            }
        }
    }

    @Test
    void UndirectedRepresentationEdgesTest() throws InterruptedException {
        var expectedEdges = new int[][] {{0,2}, {0,4}, {0,5}, {1,4}, {1,5}, {2,3},
                {2,4}, {2,5}, {3,4}};
        for( var representation : undirected)
        {
            representation.loadFromIncidenceList(getUndirectedGraph());
            var result = representation.getAllEdges();
            for (var expected : expectedEdges)
            {
                boolean found = false;
                for (var r : result)
                {
                    if (Arrays.equals(r,expected))
                    {
                        found = true;
                        break;
                    }
                }
                assertTrue(found, representation.getClass().toString());
            }
        }
    }


    @Test
    void DirectedRepresentationEdgesTest() throws InterruptedException {
        var expectedEdges = new int[][] {{0,3}, {1,0}, {1,2}, {2,4}, {3,1}, {3,4},
                {4,0}, {4,1}, {4,5}, {5,1}, {5,2}};
        for( var representation : directed)
        {
            representation.loadFromIncidenceList(getDirectedGraph());
            var result = representation.getAllEdges();
            for (var expected : expectedEdges)
            {
                boolean found = false;
                for (var r : result)
                {
                    if (Arrays.equals(r,expected))
                    {
                        found = true;
                        break;
                    }
                }
                assertTrue(found, representation.getClass().toString());
            }
        }
    }

  @Test
    void DFSTest() throws InterruptedException {
        var dirResult = new Integer[] {0,3,1,2,4,5};
        var undirResult = new Integer[] {0,2,3,4,1,5};
        var algo = new DepthFirstSearch();

        doAlgorithmTest(algo, getDirectedGraph(), directed, dirResult);

        doAlgorithmTest(algo, getUndirectedGraph(), undirected, undirResult);

        var weighted_graph = getDirectedWeightedGraph();
        doAlgorithmWeightsTest(algo, weighted_graph.getRepresentation(), weighted_graph.getWeights(), directedWeighted, dirResult);

        weighted_graph = getUndirectedWeightedGraph();
        doAlgorithmWeightsTest(algo, weighted_graph.getRepresentation(), weighted_graph.getWeights(), undirectedWeighted, undirResult);

    }

    @Test
    void BFSTest() throws InterruptedException {
        var dirResult = new Integer[] {0,3,1,4,2,5};
        var undirResult = new Integer[] {0,2,4,5,3,1};
        var algo = new BreadthFirstSearch();

        doAlgorithmTest(algo, getDirectedGraph(), directed, dirResult);

        doAlgorithmTest(algo, getUndirectedGraph(), undirected, undirResult);

        var weighted_graph = getDirectedWeightedGraph();
        doAlgorithmWeightsTest(algo, weighted_graph.getRepresentation(), weighted_graph.getWeights(), directedWeighted, dirResult);

        weighted_graph = getUndirectedWeightedGraph();
        doAlgorithmWeightsTest(algo, weighted_graph.getRepresentation(), weighted_graph.getWeights(), undirectedWeighted, undirResult);
    }

    @Test
    void HamiltonTest() throws InterruptedException {
        var dirResult = new Integer[] {0,1,2,4,3};
        var undirResult = new Integer[] {0,1,2,4,3};
        var algo = new HamiltonianCycle();
        doAlgorithmTest(algo, getHamiltonianDirectedGraph(), directed, dirResult);

        doAlgorithmTest(algo, getHamiltonianUndirectedGraph(), undirected, undirResult);

        //TODO
        //var weighted_graph = getDirectedWeightedGraph();
        //doAlgorithmWeightsTest(algo, weighted_graph.getRepresentation(), weighted_graph.getWeights(), directedWeighted, dirResult);

        //weighted_graph = getUndirectedWeightedGraph();
        //doAlgorithmWeightsTest(algo, weighted_graph.getRepresentation(), weighted_graph.getWeights(), undirectedWeighted, undirResult);

    }

    //TODO fix this
    @Test
    void AllHamiltonTest() throws InterruptedException {
        var dirResult = new Integer[] {0,1,2,4,3};
        var undirResult = new Integer[] {0,1,2,4,3};
        var algo = new AllHamiltonianCycles();
        doAlgorithmTest(algo, getHamiltonianDirectedGraph(), directed, dirResult);

        doAlgorithmTest(algo, getHamiltonianUndirectedGraph(), undirected, undirResult);

        //TODO
        //var weighted_graph = getDirectedWeightedGraph();
        //doAlgorithmWeightsTest(algo, weighted_graph.getRepresentation(), weighted_graph.getWeights(), directedWeighted, dirResult);

        //weighted_graph = getUndirectedWeightedGraph();
        //doAlgorithmWeightsTest(algo, weighted_graph.getRepresentation(), weighted_graph.getWeights(), undirectedWeighted, undirResult);

    }

    @Test
    void EulerCycleTest() throws InterruptedException {
        var dirResult = new Integer[] {0, 6, 4, 1, 7, 3, 2, 0, 8, 7, 5, 4, 8, 5, 3, 1, 0};
        var undirResult = new Integer[] {0, 2, 3, 8, 4, 6, 5, 8, 7, 3, 5, 4, 1, 0};
        Arrays.sort(dirResult);
        Arrays.sort(undirResult);
        var algo = new EulerCycleAlgorithm();
        for (var representation : directed)
        {
            representation.loadFromIncidenceList(getEulerianDirectedGraph());
            Integer[] result = algo.run(representation).getPath().toArray(new Integer[0]);
            Arrays.sort(result);
            assertArrayEquals(dirResult, result, getInfo(representation, dirResult, result));
        }

        for (var representation : undirected)
        {
            representation.loadFromIncidenceList(getEulerianUndirectedGraph());
            Integer[] result = algo.run(representation).getPath().toArray(new Integer[0]);
            Arrays.sort(result);
            assertArrayEquals(undirResult, result, getInfo(representation, undirResult, (Integer[]) result));
        }
    }

    @Test
    void TopologicalTest() throws InterruptedException {
        var dirResult = new Integer[] {0,1,4,6,5,8,2,3,7};
        var undirResult = new Integer[] {0,1,4,6,5,8,3,7,2};
        var algo = new TopologicalSort();

        doAlgorithmTest(algo, getConnectedDirectedGraph(), directed, dirResult);

        doAlgorithmTest(algo, getConnectedUndirectedGraph(), undirected, undirResult);

        var weighted_graph = getConnectedDirectedWeightedGraph();
        doAlgorithmWeightsTest(algo, weighted_graph.getRepresentation(), weighted_graph.getWeights(), directedWeighted, dirResult);

        weighted_graph = getConnectedUndirectedWeightedGraph();
        doAlgorithmWeightsTest(algo, weighted_graph.getRepresentation(), weighted_graph.getWeights(), undirectedWeighted, undirResult);
    }

    @Test
    void DijkstraTest() throws InterruptedException {
        var dirResult = new Integer[] {0,7,1,8,12,20,19,9,10};
        var undirResult = new Integer[] {0,9, 5, 8, 10, 15, 16, 12, 13};
        var algo = new DijkstraAlgorithm();

        var weighted_graph = getConnectedDirectedWeightedGraph();
        doAlgorithmWeightsTest(algo, weighted_graph.getRepresentation(), weighted_graph.getWeights(), directedWeighted, dirResult);

        weighted_graph = getConnectedUndirectedWeightedGraph();
        doAlgorithmWeightsTest(algo, weighted_graph.getRepresentation(), weighted_graph.getWeights(), undirectedWeighted, undirResult);
    }

    @Test
    void KruskalTest() throws InterruptedException {
        var dirResult = new Integer[] {0,1,2,4,3};
        var undirResult = new Integer[] {1, 4, 5, 8, 7, 8, 2, 3, 3, 7, 0, 2, 4, 5, 4, 6};
        var algo = new KruskalAlgorithm();
        var weighted_graph = getConnectedDirectedWeightedGraph();
        //doAlgorithmWeightsTest(algo, weighted_graph.getRepresentation(), weighted_graph.getWeights(), directedWeighted, dirResult);

        weighted_graph = getConnectedUndirectedWeightedGraph();
        doAlgorithmWeightsTest(algo, weighted_graph.getRepresentation(), weighted_graph.getWeights(), undirectedWeighted, undirResult);
    }

    @Test
    void PrimsTest() throws InterruptedException {
        var dirResult = new Integer[] {0,1,2,4,3};
        var undirResult = new Integer[] {-1, 4, 0, 2, 5, 8, 4, 3, 7};
        var algo = new PrimsAlgorithm();

        var weighted_graph = getConnectedDirectedWeightedGraph();
        //doAlgorithmWeightsTest(algo, weighted_graph.getRepresentation(), weighted_graph.getWeights(), directedWeighted, dirResult);

        weighted_graph = getConnectedUndirectedWeightedGraph();
        doAlgorithmWeightsTest(algo, weighted_graph.getRepresentation(), weighted_graph.getWeights(), undirectedWeighted, undirResult);
    }
/*
    @Test
    void BFSTest() throws InterruptedException {
        BreadthFirstSearch bfs = new BreadthFirstSearch();
        Map<String,String> params = Map.of("numberOfVertices", "6");
        bfs.setParams(params);
        List<Integer> path = bfs.run(TEST_LIST_SUCCESSORS_DIRECTED).getPath();
        assertArrayEquals(path.toArray(), BFS_RESULT.toArray());
    }

    @Test
    void HamiltonianTest() throws InterruptedException {
        HamiltonianCycle hc = new HamiltonianCycle();
        Map<String,String> params = Map.of("numberOfVertices", "5");
        hc.setParams(params);
        List<Integer> path = hc.run(TEST_HAMILTONIAN_GRAPH).getPath();
        assertArrayEquals(path.toArray(), HAMILTONIAN_RESULT.toArray());
    }

    @Test
    void AllHamiltonianTest() throws InterruptedException {
        AllHamiltonianCycles hc = new AllHamiltonianCycles();
        Map<String,String> params = Map.of("numberOfVertices", "5");
        hc.setParams(params);
        List<ArrayList<Integer>> path = hc.run(TEST_HAMILTONIAN_GRAPH).getMultiplePaths();
        assertArrayEquals(path.toArray(), ALL_HAMILTONIAN_RESULT.toArray());
    }

    @Test
    void TopologicalSortTest() throws InterruptedException {
        TopologicalSort ts = new TopologicalSort();
        Map<String,String> params = Map.of("numberOfVertices", "6");
        ts.setParams(params);
        List<Integer> sortResult = ts.run(TEST_TOPO_SORT).getPath();
        assertArrayEquals(sortResult.toArray(), TOPO_SORT_RESULT.toArray());
    }


    @Test
    void TestGenerator() throws InterruptedException {
        GraphGeneratorConfig config = GraphGeneratorConfig.builder()
                .numberOfVertices(1000)
                .density(0.7)
                .type("Directed Graph Generator")
                .build();
        GraphDataGenerator generator = new DirectedGraphDataGenerator();

        List<ArrayList<Integer>> generatedGraph = generator.generate(config);

        long start = System.nanoTime();
        ListOfSuccessorsDirected list = new ListOfSuccessorsDirected(generatedGraph);
        DepthFirstSearch dfs = new DepthFirstSearch();
        long end = System.nanoTime();
        double t = (double)(end-start)/1000000.0;
        List<Integer> path = dfs.run(list).getPath();
        assertTrue(path.size() == 20);
    }

    @Test
    void TestGenerator() throws InterruptedException {
        GraphGeneratorConfig config = GraphGeneratorConfig.builder()
                .numberOfVertices(5)
                .density(0.7)
                .type("Connected Undirected Graph Generator")
                .build();
        GraphDataGenerator generator = new ConnectedUndirectedGraphDataGenerator();

        List<ArrayList<Integer>> generatedGraph = generator.generate(config);

        ListOfIncidentUndirected list = new ListOfIncidentUndirected(generatedGraph);
    }
    */
}
