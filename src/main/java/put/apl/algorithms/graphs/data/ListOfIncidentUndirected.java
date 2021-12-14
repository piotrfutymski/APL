package put.apl.algorithms.graphs.data;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/*
    Undirected version
 */
@Component("List Of Incident Undirected")
public class ListOfIncidentUndirected implements GraphRepresentation {
    private final int[][] edges;

    int vertexNum;
    int edgeNum;
    int operations;
    public ListOfIncidentUndirected () {
        edges = new int[0][];
    }

    // Format: line number = vertex id, successors separated by comma
    public ListOfIncidentUndirected(String input) {
        vertexNum = input.split(System.getProperty("line.separator")).length;
        edgeNum=0;
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
                edgesList.get(lineNumber).add(Integer.parseInt(s));
                edgeNum+=1;
            }
            lineNumber++;
        }
        for (int i = 0; i < edgesList.size(); i++) {
            edges[i] = new int[edgesList.get(i).size()];
            // Just to be sure that order is right
            //Collections.sort(edgesList.get(i));
            for (int j = 0; j < edgesList.get(i).size(); j++) {
                edges[i][j] = edgesList.get(i).get(j);
            }
        }
        scanner.close();
    }

    public ListOfIncidentUndirected(int[][] edges) {
        this.edges = edges;
    }

    public int[] getSuccessors(Integer id) {
        return getPredecessors(id);
    };

    public int getFirstSuccessor(Integer id) {
        return getFirstPredecessor(id);
    };

    public int[] getPredecessors(Integer id) {
        operations+=edges.length;
        return edges[id];
    };

    public int getFirstPredecessor(Integer id) {
        if (edges[id].length > 0) {
            return getEdgeInner(id,0);
        }
        return -1;
    };

    public int[] getNonIncident(Integer id) {
        boolean[] nonIncident = new boolean[edges.length];
        List<Integer> nonIncidentIds = new ArrayList<Integer>();
        for(int i = 0; i < edges.length; i++) {
            nonIncident[i] = true;
        }
        for (int i = 0; i < edges[id].length; i++)
        {
            nonIncident[getEdgeInner(id, i)] = false;
        }

        for(int i = 0; i < edges.length; i++) {
            if (i == id) {
                continue;
            }
            for (int j = 0; j < edges[i].length; j++) {
                nonIncident[getEdgeInner(i, j)] = false;
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
        int size=0;
        for (int[] edgeList : edges)
            size+=Integer.BYTES *edgeList.length;
        return size;
    };

    public int getEdge(Integer id1, Integer id2) {
        for (int predecessor : getPredecessors(id1)) {
            if (predecessor == id2) {
                return 1;
            }
        }
        return 0;
    }
    public int getEdgeInner(int index1, int index2)
    {
        operations+=1;
        return edges[index1][index2];
    }

    @Override
    public int getVerticesNumber() {
        return vertexNum;
    }

    @Override
    public int getEdgesNumber() {
        return edgeNum;
    }

    @Override
    public int getOperations() {
        return operations;
    }

    @Override
    public void setOperations(int operations) {
        this.operations = operations;
    }

    @Override
    public GraphRepresentation clone() {
        return new ListOfIncidentUndirected(this.edges.clone());
    };
}
