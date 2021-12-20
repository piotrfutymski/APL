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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

    //static String UNDIRECTED_CONNECTED_HAMILTONIAN_GRAPH =
    //        /* 0  */ "1,3\r\n" +
    //        /* 1  */ "0,1,2,3,4\r\n" +
    //        /* 2  */ "1,4\r\n" +
    //        /* 3  */ "0,4\r\n" +
    //        /* 4  */ "1,2,3";

    static List<List<Integer>> getConnectedDirectedGraph()
    {
        ArrayList<List<Integer>> list = new ArrayList<>();
        list.add(Arrays.asList());
        list.add(Arrays.asList());
        list.add(Arrays.asList(3));
        list.add(Arrays.asList(1));
        list.add(Arrays.asList(0,1));
        list.add(Arrays.asList(0,2));
        return list;
    }

    static List<List<Integer>> getConnectedUndirectedGraph()
    {
        ArrayList<List<Integer>> list = new ArrayList<>();
        list.add(Arrays.asList());
        list.add(Arrays.asList());
        list.add(Arrays.asList(3));
        list.add(Arrays.asList(1));
        list.add(Arrays.asList(0,1));
        list.add(Arrays.asList(0,2));
        return list;
    }



    static List<Integer> BFS_RESULT = new ArrayList<Integer>();
    static List<Integer> TOPO_SORT_RESULT = new ArrayList<Integer>();
    static List<Integer> HAMILTONIAN_RESULT = new ArrayList<Integer>();
    static List<ArrayList<Integer>> ALL_HAMILTONIAN_RESULT = new ArrayList<ArrayList<Integer>>();

    List<GraphRepresentation> undirected;

    List<GraphRepresentation> directed;

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

        HAMILTONIAN_RESULT.clear();
        HAMILTONIAN_RESULT.add(0);
        HAMILTONIAN_RESULT.add(1);
        HAMILTONIAN_RESULT.add(2);
        HAMILTONIAN_RESULT.add(4);
        HAMILTONIAN_RESULT.add(3);

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
    }
    /*
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
                assertArrayEquals(expectedSuccessors[i], representation.getSuccessors(i));
                assertArrayEquals(expectedSuccessors[i], representation.getPredecessors(i));
            }
        }
    }*/

    /*
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
                assertArrayEquals(expectedSuccessors[i], representation.getSuccessors(i));
                assertArrayEquals(expectedPredecessors[i], representation.getPredecessors(i));
            }
        }
    }*/

    /*
    @Test
    void UndirectedRepresentationEdgesTest() throws InterruptedException {
        var expectedEdges = new int[][] {{0,2}, {0,4}, {0,5}, {1,4}, {1,5}, {2,3},
                {2,4}, {2,5}, {3,4}};
        for( var representation : undirected)
        {
            representation.loadFromIncidenceList(getUndirectedGraph());
            var result = representation.getAllEdges();
            for (var r : result)
            {
                System.out.print(Arrays.toString(r));

            }
            System.out.println("heh");

        }
    }*/

    /*
    @Test
    void DirectedRepresentationEdgesTest() throws InterruptedException {
        var expectedEdges = new int[][] {{0,2}, {0,4}, {0,5}, {1,4}, {1,5}, {2,3},
                {2,4}, {2,5}, {3,4}};
        for( var representation : directed)
        {
            representation.loadFromIncidenceList(getDirectedGraph());
            var result = representation.getAllEdges();
            for (var r : result)
            {
                System.out.print(Arrays.toString(r));

            }
            System.out.println("heh");

        }
    }*/

    /*
  @Test
    void DFSTest() throws InterruptedException {
        var dirResult = new Integer[] {0,3,1,2,4,5};
        var undirResult = new Integer[] {0,2,3,4,1,5};
        var algo = new DepthFirstSearch();
        for (var representation : directed)
        {
            representation.loadFromIncidenceList(getDirectedGraph());
            assertArrayEquals(dirResult, algo.run(representation).getPath().toArray());
        }

      for (var representation : undirected)
      {
          representation.loadFromIncidenceList(getUndirectedGraph());
          assertArrayEquals(undirResult, algo.run(representation).getPath().toArray());
      }
    }*/

    /*
    @Test
    void BFSTest() throws InterruptedException {
        var dirResult = new Integer[] {0,3,1,4,2,5};
        var undirResult = new Integer[] {0,2,4,5,3,1};
        var algo = new BreadthFirstSearch();
        for (var representation : directed)
        {
            representation.loadFromIncidenceList(getDirectedGraph());
            assertArrayEquals(dirResult, algo.run(representation).getPath().toArray());
        }

        for (var representation : undirected)
        {
            representation.loadFromIncidenceList(getUndirectedGraph());
            assertArrayEquals(undirResult, algo.run(representation).getPath().toArray());
        }
    }*/

    /*
    @Test
    void HamiltonTest() throws InterruptedException {
        var dirResult = new Integer[] {0,3,1,4,2,5};
        var undirResult = new Integer[] {0,2,4,5,3,1};
        var algo = new HamiltonianCycle();
        for (var representation : directed)
        {
            representation.loadFromIncidenceList(getDirectedGraph());
            var array = Arrays.toString(algo.run(representation).getPath().toArray());
            System.out.println(array);
            //assertArrayEquals(dirResult, dfs.run(representation).getPath().toArray());
        }

        for (var representation : undirected)
        {
            representation.loadFromIncidenceList(getUndirectedGraph());
            var array = Arrays.toString(algo.run(representation).getPath().toArray());
            System.out.println(array);
            //assertArrayEquals(undirResult, dfs.run(representation).getPath().toArray());
        }
    }*/
    /*
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
    }*/
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
