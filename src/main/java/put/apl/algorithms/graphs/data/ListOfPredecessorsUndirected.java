package put.apl.algorithms.graphs.data;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
    Undirected version
 */
@Component("List Of Predecessors Undirected")
public class ListOfPredecessorsUndirected implements GraphRepresentation {
    private final int[][] edges;

    public ListOfPredecessorsUndirected () {
        edges = new int[0][];
    }

    // Format: line number = vertex id, successors separated by comma
    public ListOfPredecessorsUndirected(String input) {
        int numOfLines = input.split(System.getProperty("line.separator")).length;
        List<ArrayList<Integer>> edgesList = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < numOfLines; i++) {
            edgesList.add(new ArrayList<Integer>());
        }
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
            for (String s : split) {
                edgesList.get(Integer.parseInt(s)).add(lineNumber);
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

    public ListOfPredecessorsUndirected(int[][] edges) {
        this.edges = edges;
    }

    public int[] getSuccessors(Integer id) {
        return getPredecessors(id);
    };

    public int getFirstSuccessor(Integer id) {
        return getFirstPredecessor(id);
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
        for (int predecessor : predecessors) {
            if (predecessor == id2) {
                return 1;
            }
        }
        return 0;
    }

    @Override
    public GraphRepresentation clone() {
        return new ListOfPredecessorsUndirected(this.edges.clone());
    };
}
