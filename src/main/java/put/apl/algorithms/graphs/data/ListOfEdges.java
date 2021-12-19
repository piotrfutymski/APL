package put.apl.algorithms.graphs.data;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public abstract class ListOfEdges implements GraphRepresentation {
    protected int[][] edges;
    // vertexNum is necessary for keeping number of vertices (used in getNonIncident)
    protected int vertexNum;
    protected int edgeNum;
    protected int operations;

    public ListOfEdges() {
        edges = new int[0][];
    }

    // Format: line number = vertex id, successors separated by comma
    public ListOfEdges(String input) {
        vertexNum = input.split("\n").length;
        List<ArrayList<Integer>> edgesList = new ArrayList<ArrayList<Integer>>();
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
        edgeNum = edgesList.size();
        edges = new int[edgeNum][];
        for (int i = 0; i < edgeNum; i++) {
            edges[i] = new int[2];
            for (int j = 0; j < edgesList.get(i).size(); j++) {
                edges[i][0] = edgesList.get(i).get(0);
                edges[i][1] = edgesList.get(i).get(1);
            }
        }
        scanner.close();
    }

    public ListOfEdges(int[][] edges) {
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
        edgeNum = edges.length;

    }

    public int[] getSuccessors(Integer id) {
        List<Integer> successors = new ArrayList<Integer>();
        for (int i=0;i<edgeNum;i++) {
            int result = checkIfSTART(id, getEdgeInner(i, 0),getEdgeInner(i, 1));
            if (result != -1) {
                successors.add(result);
            }
        }
        return successors.stream().mapToInt(i->i).toArray();
    }

    protected abstract int checkIfSTART(Integer id, int start, int end);

    protected int checkIfIncident(int id, int start, int end)
    {
        if (id==start)
            return end;
        if (id == end)
            return start;
        return -1;
    }

    public int getFirstSuccessor(Integer id) {
        for (int i=0;i<edgeNum;i++) {
            int result = checkIfSTART(id, getEdgeInner(i, 0),getEdgeInner(i, 1));
            if (result != -1) {
                return result;
            }
        }
        return -1;
    };

    public int[] getPredecessors(Integer id) {
        List<Integer> predecessors = new ArrayList<Integer>();
        for (int i=0;i<edgeNum;i++) {
            int result = checkIfSTART(id, getEdgeInner(i, 0),getEdgeInner(i, 1));
            if (result != -1) {
                predecessors.add(result);
            }
        }
        return predecessors.stream().mapToInt(i->i).toArray();
    };

    public int getFirstPredecessor(Integer id) {
        for (int i=0;i<edgeNum;i++) {
            int result = checkIfSTART(id, getEdgeInner(i, 0),getEdgeInner(i, 1));
            if (result != -1) {
                return result;
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
        for (int i=0;i<edgeNum;i++) {
            int result = checkIfIncident(id, getEdgeInner(i, 0),getEdgeInner(i, 1));
            if (result != -1)
                nonIncident[result]=false;
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
        for (int i=0;i<edgeNum;i++) {
            if (getEdgeInner(i, 0) == id1 && getEdgeInner(i, 1) == id2) {
                return 1;
            }
            if (getEdgeInner(i, 1) == id1 && getEdgeInner(i, 0) == id2) {
                return -1;
            }
        }
        return 0;
    }


    public int getEdgeInner(Integer index1, Integer index2) {
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
        this.operations=operations;
    }

    @Override
    public abstract GraphRepresentation clone();


}
