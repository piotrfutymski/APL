package put.apl.algorithms.graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import put.apl.algorithms.graphs.data.*;
import put.apl.algorithms.graphs.data.generator.DirectedGraphDataGenerator;
import put.apl.algorithms.graphs.data.generator.GraphDataGenerator;
import put.apl.algorithms.graphs.data.generator.GraphGeneratorConfig;
import put.apl.algorithms.graphs.implementation.*;
import put.apl.algorithms.sorting.SortingResult;
import put.apl.experiment.dto.GraphExperiment;
import put.apl.experiment.service.GraphService;

import java.io.ObjectInputFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


public class GraphTest {

    static String DIRECTED_GRAPH =
            /* 0  */ "1,2\r\n" +
            /* 1  */ "4,5\r\n" +
            /* 2  */ "3,0\r\n" +
            /* 3  */ "4,5\r\n" +
            /* 4  */ "3\r\n" +
            /* 5  */ "1";

    static String UNDIRECTED_CONNECTED_HAMILTONIAN_GRAPH =
            /* 0  */ "1,3\r\n" +
            /* 1  */ "0,1,2,3,4\r\n" +
            /* 2  */ "1,4\r\n" +
            /* 3  */ "0,4\r\n" +
            /* 4  */ "1,2,3";

    static String DIRECTED_CONNECTED_GRAPH =
            /* 0  */ "\r\n" +
            /* 1  */ "\r\n" +
            /* 2  */ "3\r\n" +
            /* 3  */ "1\r\n" +
            /* 4  */ "0,1\r\n" +
            /* 5  */ "0,2";

    static GraphRepresentation TEST_LIST_SUCCESSORS_DIRECTED = new ListOfSuccessorsDirected(DIRECTED_GRAPH);
    static GraphRepresentation TEST_HAMILTONIAN_GRAPH = new ListOfIncidentUndirected(UNDIRECTED_CONNECTED_HAMILTONIAN_GRAPH);
    static GraphRepresentation TEST_TOPO_SORT = new ListOfSuccessorsDirected(DIRECTED_CONNECTED_GRAPH);
    static List<Integer> DFS_RESULT = new ArrayList<Integer>();
    static List<Integer> BFS_RESULT = new ArrayList<Integer>();
    static List<Integer> TOPO_SORT_RESULT = new ArrayList<Integer>();
    static List<Integer> HAMILTONIAN_RESULT = new ArrayList<Integer>();
    static List<ArrayList<Integer>> ALL_HAMILTONIAN_RESULT = new ArrayList<ArrayList<Integer>>();

    @BeforeEach
    void initAll() {
        DFS_RESULT.clear();
        DFS_RESULT.add(0);
        DFS_RESULT.add(1);
        DFS_RESULT.add(4);
        DFS_RESULT.add(3);
        DFS_RESULT.add(5);
        DFS_RESULT.add(2);

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
    }

    @Test
    void DFSTest() throws InterruptedException {
        DepthFirstSearch dfs = new DepthFirstSearch();
        Map<String,String> params = Map.of("numberOfVertices", "6");
        dfs.setParams(params);
        List<Integer> path = dfs.run(TEST_LIST_SUCCESSORS_DIRECTED).getPath();
        assertArrayEquals(path.toArray(), DFS_RESULT.toArray());
    }

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

    /*
    @Test
    void TestGenerator() throws InterruptedException {
        GraphGeneratorConfig config = GraphGeneratorConfig.builder()
                .numberOfVertices(1000)
                .density(0.7)
                .type("Directed Graph Generator")
                .build();
        GraphDataGenerator generator = new DirectedGraphDataGenerator();

        String generatedGraph = generator.generate(config);

        long start = System.nanoTime();
        ListOfSuccessorsDirected list = new ListOfSuccessorsDirected(generatedGraph);
        DepthFirstSearch dfs = new DepthFirstSearch();
        long end = System.nanoTime();
        double t = (double)(end-start)/1000000.0;
        List<Integer> path = dfs.run(list).getPath();
        assertTrue(path.size() == 20);
    }*/


}
