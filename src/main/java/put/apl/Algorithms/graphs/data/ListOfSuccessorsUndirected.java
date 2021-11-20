package put.apl.algorithms.graphs.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
    Undirected version
 */
public class ListOfSuccessorsUndirected implements GraphRepresentation {
    private final int[][] edges;

    // Format: line number = vertex id, successors separated by comma
    public ListOfSuccessorsUndirected(String input) {
        int numOfLines = input.split(System.getProperty("line.separator")).length;
        edges  = new int[numOfLines][];
        Scanner scanner = new Scanner(input);
        int lineNumber = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] split = line.split(",");
            edges[lineNumber] = new int[split.length];
            for (int i = 0; i < split.length; i++) {
                edges[lineNumber][i] = Integer.parseInt(split[i]);
            }
            lineNumber++;
        }
        scanner.close();
    }

    public ListOfSuccessorsUndirected(int[][] edges) {
        this.edges = edges;
    }

    public int[] getSuccessors(Integer id) {
        for (int i = 0; i < edges.length; i++) {
            if (i == id) {
                return edges[i];
            }
        }
        return null;
    };

    public int getFirstSuccessor(Integer id) {
        for (int i = 0; i < edges.length; i++) {
            if (i == id) {
                return edges[i][0];
            }
        }
        return -1;
    };

    public int[] getPredecessors(Integer id) {
        return getSuccessors(id);
    };

    public int getFirstPredecessor(Integer id) {
        return getFirstSuccessor(id);
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
        }
        for (int i = 0; i < edges.length; i++) {
            if (nonIncident[i]) {
                nonIncidentIds.add(i);
            }
        }
        return nonIncidentIds.stream().mapToInt(i->i).toArray();
    };

    public int[][] getAllEdges() {
        return edges;
    };

    public int getMemoryOccupancy() {
        return Integer.BYTES * edges.length * edges[0].length;
    };

    public String getRelationBetween(Integer id1, Integer id2) {
        int[] predecessors = getPredecessors(id1);
        int[] successors = getSuccessors(id1);
        for (int predecessor : predecessors) {
            if (predecessor == id2) {
                return "successor";
            }
        }
        for (int successor : successors) {
            if (successor == id2) {
                return "predecessor";
            }
        }
        return "none";
    }

    @Override
    public GraphRepresentation clone() {
        return new ListOfSuccessorsUndirected(this.edges.clone());
    };
}
