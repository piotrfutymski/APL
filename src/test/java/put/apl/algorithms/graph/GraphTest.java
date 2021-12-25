package put.apl.algorithms.graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import put.apl.algorithms.graphs.data.*;
import put.apl.algorithms.graphs.data.generator.*;
import put.apl.algorithms.graphs.implementation.*;
import put.apl.algorithms.sorting.SortingResult;
import put.apl.experiment.dto.GraphExperiment;
import put.apl.experiment.service.GraphService;

import java.io.ObjectInputFilter;
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

    //get NOT connected Directed Graph
    //get NOT


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

    List<GraphRepresentation> undirected;

    List<GraphRepresentation> directed;

    List<GraphRepresentation> undirectedWeighted;

    List<GraphRepresentation> directedWeighted;

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
                new IncidenceMatrixUndirectedWeighted(), new AdjacencyMatrixUndirected());

        directed = Arrays.asList(new ListOfEdgesDirected(), new ListOfPredecessorsDirected(),
                new ListOfSuccessorsDirected(), new AdjacencyMatrixDirected(), new IncidenceMatrixDirected(),
                new AdjacencyMatrixDirectedWeighted(), new IncidenceMatrixDirectedWeighted());

        undirectedWeighted = Arrays.asList(new IncidenceMatrixUndirectedWeighted(), new AdjacencyMatrixUndirected());

        directedWeighted = Arrays.asList(new AdjacencyMatrixDirectedWeighted(), new IncidenceMatrixDirectedWeighted());
    }

    String getInfo(GraphRepresentation representation, Integer[] expected, Integer[] actual)
    {
        return representation.getClass().toString() + "\nExpected: " +
                Arrays.toString(expected) +
                "\nActual: " + Arrays.toString(actual);
    }

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
        for (var representation : directed)
        {
            representation.loadFromIncidenceList(getDirectedGraph());
            assertArrayEquals(dirResult, algo.run(representation).getPath().toArray(), representation.getClass().toString());
        }

      for (var representation : undirected)
      {
          representation.loadFromIncidenceList(getUndirectedGraph());
          assertArrayEquals(undirResult, algo.run(representation).getPath().toArray(), representation.getClass().toString());
      }
    }

    @Test
    void BFSTest() throws InterruptedException {
        var dirResult = new Integer[] {0,3,1,4,2,5};
        var undirResult = new Integer[] {0,2,4,5,3,1};
        var algo = new BreadthFirstSearch();
        for (var representation : directed)
        {
            representation.loadFromIncidenceList(getDirectedGraph());
            assertArrayEquals(dirResult, algo.run(representation).getPath().toArray(), representation.getClass().toString());
        }

        for (var representation : undirected)
        {
            representation.loadFromIncidenceList(getUndirectedGraph());
            assertArrayEquals(undirResult, algo.run(representation).getPath().toArray(), representation.getClass().toString());
        }
    }

    @Test
    void HamiltonTest() throws InterruptedException {
        var dirResult = new Integer[] {0,1,2,4,3};
        var undirResult = new Integer[] {0,1,2,4,3};
        var algo = new HamiltonianCycle();
        for (var representation : directed)
        {
            representation.loadFromIncidenceList(getHamiltonianDirectedGraph());
            assertArrayEquals(dirResult, algo.run(representation).getPath().toArray(), representation.getClass().toString());
        }

        for (var representation : undirected)
        {
            representation.loadFromIncidenceList(getHamiltonianUndirectedGraph());
            assertArrayEquals(undirResult, algo.run(representation).getPath().toArray(), representation.getClass().toString());
        }
    }

    //TODO fix this
    @Test
    void AllHamiltonTest() throws InterruptedException {
        var dirResult = new Integer[] {0,3,1,4,2,5};
        var undirResult = new Integer[] {0,2,4,5,3,1};
        var algo = new AllHamiltonianCycles();
        for (var representation : directed)
        {
            representation.loadFromIncidenceList(getDirectedGraph());
            var array = Arrays.toString(algo.run(representation).getMultiplePaths().toArray());
            System.out.println(array);
            //assertArrayEquals(dirResult, algo.run(representation).getPath().toArray());
        }

        for (var representation : undirected)
        {
            representation.loadFromIncidenceList(getUndirectedGraph());
            var array = Arrays.toString(algo.run(representation).getMultiplePaths().toArray());
            System.out.println(array);
            //assertArrayEquals(undirResult, algo.run(representation).getPath().toArray());
        }
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
        var dirResult = new Integer[] {0,1,2,4,3};
        var undirResult = new Integer[] {0,1,2,4,3};
        var algo = new TopologicalSort();
        for (var representation : directed)
        {
            representation.loadFromIncidenceList(getConnectedDirectedGraph());
            var result = algo.run(representation).getPath().toArray();
            assertArrayEquals(dirResult, result, getInfo(representation, dirResult, (Integer[]) result));
        }

        for (var representation : undirected)
        {
            representation.loadFromIncidenceList(getConnectedUndirectedGraph());
            var result = algo.run(representation).getPath().toArray();
            assertArrayEquals(undirResult, result, representation.getClass().toString());
        }
    }

    @Test
    void DijkstraTest() throws InterruptedException {
        var dirResult = new Integer[] {0,1,2,4,3};
        var undirResult = new Integer[] {0,1,2,4,3};
        var algo = new DijkstraAlgorithm();
        for (var representation : directed)
        {
            representation.loadFromIncidenceList(getConnectedDirectedGraph());
            var result = algo.run(representation).getPath().toArray();
            assertArrayEquals(dirResult, result, getInfo(representation, dirResult, (Integer[]) result));
        }

        for (var representation : undirected)
        {
            representation.loadFromIncidenceList(getConnectedUndirectedGraph());
            var result = algo.run(representation).getPath().toArray();
            assertArrayEquals(undirResult, result, representation.getClass().toString());
        }
    }

    @Test
    void KruskalTest() throws InterruptedException {
        var dirResult = new Integer[] {0,1,2,4,3};
        var undirResult = new Integer[] {0,1,2,4,3};
        var algo = new KruskalAlgorithm();
        for (var representation : directed)
        {
            representation.loadFromIncidenceList(getConnectedDirectedGraph());
            assertArrayEquals(dirResult, algo.run(representation).getPath().toArray(), representation.getClass().toString());
        }

        for (var representation : undirected)
        {
            representation.loadFromIncidenceList(getConnectedUndirectedGraph());
            assertArrayEquals(undirResult, algo.run(representation).getPath().toArray(), representation.getClass().toString());
        }
    }

    @Test
    void PrimeTest() throws InterruptedException {
        var dirResult = new Integer[] {0,1,2,4,3};
        var undirResult = new Integer[] {0,1,2,4,3};
        var algo = new PrimAlgorithm();
        for (var representation : directed)
        {
            representation.loadFromIncidenceList(getConnectedDirectedGraph());
            assertArrayEquals(dirResult, algo.run(representation).getPath().toArray(), representation.getClass().toString());
        }

        for (var representation : undirected)
        {
            representation.loadFromIncidenceList(getConnectedUndirectedGraph());
            assertArrayEquals(undirResult, algo.run(representation).getPath().toArray(), representation.getClass().toString());
        }
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
