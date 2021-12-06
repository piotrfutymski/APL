package put.apl.algorithms.graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import put.apl.algorithms.graphs.data.GraphRepresentation;
import put.apl.algorithms.graphs.data.ListOfSuccessorsDirected;
import put.apl.algorithms.graphs.implementation.BreadthFirstSearch;
import put.apl.algorithms.graphs.implementation.DepthFirstSearch;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.fail;


public class GraphTest {

    static String DIRECTED_GRAPH =
            /* 0  */ "1,2\r\n" +
            /* 1  */ "4,5\r\n" +
            /* 2  */ "3,0\r\n" +
            /* 3  */ "4,5\r\n" +
            /* 4  */ "3\r\n" +
            /* 5  */ "1";

    static GraphRepresentation TEST_LIST_SUCCESSORS_DIRECTED = new ListOfSuccessorsDirected(DIRECTED_GRAPH);
    static List<Integer> DFS_RESULT = new ArrayList<Integer>();
    static List<Integer> BFS_RESULT = new ArrayList<Integer>();

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

    }

    @Test
    void DFSTest() throws InterruptedException {
        DepthFirstSearch dfs = new DepthFirstSearch();
        List<Integer> path = dfs.run(TEST_LIST_SUCCESSORS_DIRECTED);
        assertArrayEquals(path.toArray(), DFS_RESULT.toArray());
    }

    @Test
    void BFSTest() throws InterruptedException {
        BreadthFirstSearch bfs = new BreadthFirstSearch();
        List<Integer> path = bfs.run(TEST_LIST_SUCCESSORS_DIRECTED);
        assertArrayEquals(path.toArray(), BFS_RESULT.toArray());
    }
}
