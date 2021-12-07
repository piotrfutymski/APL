package put.apl.algorithms.graphs.data;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
    Directed version
 */
@Component("List Of Edges Directed")
public class ListOfEdgesDirected implements GraphRepresentation {
    private final int[][] edges;
    // vertexNum is necessary for keeping number of vertices (used in getNonIncident)
    private int vertexNum;

    public ListOfEdgesDirected () {
        edges = new int[0][];
    }

    // Format: line number = vertex id, successors separated by comma
    public ListOfEdgesDirected(String input) {
        int numOfLines = input.split(System.getProperty("line.separator")).length;
        vertexNum = numOfLines;
        List<ArrayList<Integer>> edgesList = new ArrayList<ArrayList<Integer>>();
        //edges  = new int[numOfLines][];
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
                ArrayList<Integer> edge = new ArrayList<Integer>();
                edge.add(lineNumber, Integer.parseInt(s));
                edgesList.add(edge);
            }
            lineNumber++;
        }
        edges = new int[edgesList.size()][];
        for (int i = 0; i < edgesList.size(); i++) {
            edges[i] = new int[2];
            for (int j = 0; j < edgesList.get(i).size(); j++) {
                edges[i][0] = edgesList.get(i).get(0);
                edges[i][1] = edgesList.get(i).get(1);
            }
        }
        scanner.close();
    }

    public ListOfEdgesDirected(int[][] edges) {
        this.edges = edges;
        int highestEdgeId = 0;
        for (int[] edge : edges) {
            if (edge[0] >= edge[1] && highestEdgeId < edge[0]) {
                highestEdgeId = edge[0];
            }
            else if (edge[1] > edge[0] && highestEdgeId < edge[1]) {
                highestEdgeId = edge[1];
            }
        }
        vertexNum = highestEdgeId;
    }

    public int[] getSuccessors(Integer id) {
        List<Integer> successors = new ArrayList<Integer>();
        for (int[] edge : edges) {
            if (edge[0] == id) {
                successors.add(edge[1]);
            }
        }
        return successors.stream().mapToInt(i->i).toArray();
    };

    public int getFirstSuccessor(Integer id) {
        for (int[] edge : edges) {
            if (edge[0] == id) {
                return edge[1];
            }
        }
        return -1;
    };

    public int[] getPredecessors(Integer id) {
        List<Integer> predecessors = new ArrayList<Integer>();
        for (int[] edge : edges) {
            if (edge[1] == id) {
                predecessors.add(edge[0]);
            }
        }
        return predecessors.stream().mapToInt(i->i).toArray();
    };

    public int getFirstPredecessor(Integer id) {
        for (int[] edge : edges) {
            if (edge[1] == id) {
                return edge[0];
            }
        }
        return -1;
    };

    public int[] getNonIncident(Integer id) {
        boolean[] nonIncident = new boolean[vertexNum];
        List<Integer> nonIncidentIds = new ArrayList<Integer>();
        for(int i = 0; i < edges.length; i++) {
            nonIncident[i] = true;
        }
        for (int[] edge : edges) {
            if (edge[0] == id) {
                nonIncident[edge[1]] = false;
            } else if (edge[1] == id) {
                nonIncident[edge[0]] = false;
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
        return Integer.BYTES * edges.length * 2;
    };

    public int getEdge(Integer id1, Integer id2) {
        for (int[] edge : edges) {
            if (edge[0] == id1 && edge[1] == id2) {
                return "predecessor";
            }
            if (edge[1] == id1 && edge[0] == id2) {
                return "successor";
            }
        }
        return "none";
    }

    @Override
    public GraphRepresentation clone() {
        return new ListOfEdgesDirected(this.edges.clone());
    };
}
