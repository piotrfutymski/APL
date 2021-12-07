package put.apl.algorithms.graphs.data;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
    Undirected version
 */
@Component("List Of Successors Undirected")
public class ListOfSuccessorsUndirected implements GraphRepresentation {
    private final int[][] edges;

    public ListOfSuccessorsUndirected () {
        edges = new int[0][];
    }

    // Format: line number = vertex id, successors separated by comma
    public ListOfSuccessorsUndirected(String input) {
        int numOfLines = input.split(System.getProperty("line.separator")).length;
        edges  = new int[numOfLines][];
        Scanner scanner = new Scanner(input);
        int lineNumber = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.equals("")) {
                lineNumber++;
                continue;
            }
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
        return edges[id];
    };

    public int getFirstSuccessor(Integer id) {
        if (edges[id].length > 0) {
            return edges[id][0];
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

    public int[][] getRepresentation() {
        return edges;
    };

    public int getMemoryOccupancy() {
        return Integer.BYTES * edges.length * edges[0].length;
    };

    public int getEdge(Integer id1, Integer id2) {
        int[] successors = getSuccessors(id1);
        for (int successor : successors) {
            if (successor == id2) {
                return 1;
            }
        }
        return 0;
    }

    @Override
    public GraphRepresentation clone() {
        return new ListOfSuccessorsUndirected(this.edges.clone());
    };
}
