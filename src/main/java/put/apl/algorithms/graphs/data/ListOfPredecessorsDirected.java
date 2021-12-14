package put.apl.algorithms.graphs.data;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
    Directed version
 */
@Component("List Of Predecessors Directed")
public class ListOfPredecessorsDirected implements GraphRepresentation {
    private final int[][] edges;
    private int vertexNum;
    private int edgeNum;
    private int observations;

    public ListOfPredecessorsDirected () {
        edges = new int[0][];
    }

    // Format: line number = vertex id, successors separated by comma
    public ListOfPredecessorsDirected(String input) {
        vertexNum = input.split(System.getProperty("line.separator")).length;
        edgeNum = 0;
        List<ArrayList<Integer>> edgesList = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < vertexNum; i++) {
            edgesList.add(new ArrayList<Integer>());
        }
        edges  = new int[vertexNum][];
        Scanner scanner = new Scanner(input);
        int lineNumber = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.equals("")) {
                lineNumber++;
                continue;
            }
            String[] split = line.split(",");
            for (String s : split) {
                edgesList.get(Integer.parseInt(s)).add(lineNumber);
                edgeNum+=1;
            }
            lineNumber++;
        }
        for (int i = 0; i < edgesList.size(); i++) {
            edges[i] = new int[edgesList.get(i).size()];
            for (int j = 0; j < edgesList.get(i).size(); j++) {
                edges[i][j] = edgesList.get(i).get(j);
            }
        }
        scanner.close();
    }

    public ListOfPredecessorsDirected(int[][] edges) {
        this.edges = edges;
    }

    public int[] getSuccessors(Integer id) {
        List<Integer> successors = new ArrayList<Integer>();
        for (int i = 0; i < edges.length; i++) {
            for (int j = 0; j < edges[i].length; j++) {
                if (edges[i][j] == id) {
                    successors.add(i);
                }
            }
        }
        return successors.stream().mapToInt(i->i).toArray();
    };

    public int getFirstSuccessor(Integer id) {
        for (int i = 0; i < edges.length; i++) {
            for (int j = 0; j < edges[i].length; j++) {
                if (edges[i][j] == id) {
                    return i;
                }
            }
        }
        return -1;
    };

    public int[] getPredecessors(Integer id) {
        return edges[id];
    };

    public int getFirstPredecessor(Integer id) {
        if (edges[id].length > 0) {
            return edges[id][0];
        }
        return -1;
    };

    public int[] getNonIncident(Integer id) {
        boolean[] nonIncident = new boolean[edges.length];
        List<Integer> nonIncidentIds = new ArrayList<Integer>();
        for(int i = 0; i < edges.length; i++) {
            nonIncident[i] = true;
        }
        for(int i = 0; i < edges.length; i++) {
            if (i == id) {
                for (int j = 0; j < edges[i].length; j++) {
                    nonIncident[i] = false;
                }
            }
            else {
                for (int j = 0; j < edges[i].length; j++) {
                    if (edges[i][j] == i) {
                        nonIncident[i] = false;
                        break;
                    }
                }
            }
        }
        for (int i = 0; i < edges.length; i++) {
            if (nonIncident[i]) {
                nonIncidentIds.add(i);
            }
        }
        return nonIncidentIds.stream().mapToInt(i->i).toArray();
    };

    public int[][] getRepresentation() {
        return edges;
    };

    public int getMemoryOccupancy() {
        return Integer.BYTES * edges.length * edges[0].length;
    };

    public int getEdge(Integer id1, Integer id2) {
        int[] predecessors = getPredecessors(id1);
        int[] successors = getSuccessors(id1);
        for (int predecessor : predecessors) {
            if (predecessor == id2) {
                return -1;
            }
        }
        for (int successor : successors) {
            if (successor == id2) {
                return 1;
            }
        }
        return 0;
    }
    private int getEdgeInner(int index1, int index2)
    {
        observations+=1;
        return edges[index1][index2];
    }
    @Override
    public int getVerticesNumber() {
        return 0;
    }

    @Override
    public int getEdgesNumber() {
        return 0;
    }

    @Override
    public int getOperations() {
        return 0;
    }

    @Override
    public void setOperations(int operations) {

    }

    @Override
    public GraphRepresentation clone() {
        return new ListOfPredecessorsDirected(this.edges.clone());
    };
}
