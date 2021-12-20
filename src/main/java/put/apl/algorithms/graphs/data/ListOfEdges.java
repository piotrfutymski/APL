package put.apl.algorithms.graphs.data;

import java.util.ArrayList;
import java.util.List;


public abstract class ListOfEdges implements GraphRepresentation {
    protected int[][] edges;
    // vertexNum is necessary for keeping number of vertices (used in getNonIncident)
    protected int vertexNum;
    protected int edgeNum;
    protected int operations;

    protected abstract int checkIfSTART(Integer id, int start, int end);

    public ListOfEdges() {
        edges = new int[0][];
    }

    // Format: line number = vertex id, successors separated by comma
    public ListOfEdges(List<List<Integer>> input) {
        loadFromIncidenceList(input);
    }

    public ListOfEdges(int[][] edges, int vertexNum) {
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
        this.vertexNum = vertexNum;
        edgeNum = edges.length;
    }

    @Override
    public void loadFromIncidenceList(List<List<Integer>> input) {
        vertexNum = input.size();

        for (var edges: input)
            edgeNum+=edges.size();
        int edgeNumber=0;
        edges = new int[edgeNum][];
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).size(); j++) {
                var edge = new int[2];
                edge[0] = i;
                edge[1] = input.get(i).get(j);
                edges[edgeNumber++] = edge;
            }
        }
    }

    protected int checkIfIncident(int id, int start, int end)
    {
        if (id==start)
            return end;
        if (id == end)
            return start;
        return -1;
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



    public int getFirstSuccessor(Integer id) {
        for (int i=0;i<edgeNum;i++) {
            int result = checkIfSTART(id, getEdgeInner(i, 0),getEdgeInner(i, 1));
            if (result != -1) {
                return result;
            }
        }
        return -1;
    }

    public int[] getPredecessors(Integer id) {
        List<Integer> predecessors = new ArrayList<Integer>();
        for (int i=0;i<edgeNum;i++) {
            //if id is end, then start is predecessor
            int result = checkIfSTART(id, getEdgeInner(i, 1),getEdgeInner(i, 0));
            if (result != -1) {
                predecessors.add(result);
            }
        }
        return predecessors.stream().mapToInt(i->i).toArray();
    };

    public int getFirstPredecessor(Integer id) {
        for (int i=0;i<edgeNum;i++) {
            int result = checkIfSTART(id, getEdgeInner(i, 1),getEdgeInner(i, 0));
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
    }

    @Override
    public int[] getAllVertices()
    {
        int[] vertices = new int[vertexNum];
        for (int i=0;i<vertexNum;i++)
            vertices[i]=i;
        return vertices;
    }
    @Override
    public int[][] getRepresentation() {
        return edges;
    };

    @Override
    public int getMemoryOccupancy() {
        return Integer.BYTES * edges.length * 2;
    };

    @Override
    public int getEdge(Integer id1, Integer id2) {
        for (int i=0;i<edgeNum;i++) {
            int start = getEdgeInner(i, 0);
            int end = getEdgeInner(i, 1);
            if (checkIfSTART(id1, getEdgeInner(i, 0), getEdgeInner(i, 1)) != -1 &&
                    checkIfSTART(id2, getEdgeInner(i, 1), getEdgeInner(i, 0)) != -1 )
            {
                return 1;
            }
            if (checkIfSTART(id2, getEdgeInner(i, 0), getEdgeInner(i, 1)) != -1 &&
                    checkIfSTART(id1, getEdgeInner(i, 1), getEdgeInner(i, 0)) != -1 ) {
                return -1;
            }
        }
        return 0;
    }

    @Override
    public int[][] getAllEdges() {
        return edges;
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
