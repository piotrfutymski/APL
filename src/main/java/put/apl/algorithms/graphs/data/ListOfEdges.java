package put.apl.algorithms.graphs.data;

import java.util.ArrayList;
import java.util.List;


public abstract class ListOfEdges extends GraphRepresentation {
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
    public ListOfEdges(List<List<Integer>> input) throws InterruptedException {
        loadFromIncidenceList(input);
    }

    public ListOfEdges(int[][] edges, int vertexNum){
        this.edges = edges;
        this.vertexNum = vertexNum;
        edgeNum = edges.length;
    }

    @Override
    public void loadFromIncidenceList(List<List<Integer>> input) throws InterruptedException {
        vertexNum = input.size();

        for (var edges: input){
            escape();
            edgeNum+=edges.size();
        }

        int edgeNumber=0;
        edges = new int[edgeNum][];
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).size(); j++) {
                escape();
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

    public int[] getSuccessors(Integer id) throws InterruptedException {
        List<Integer> successors = new ArrayList<Integer>();
        for (int i=0;i<edgeNum;i++) {
            escape();
            int result = checkIfSTART(id, getEdgeInner(i, 0),getEdgeInner(i, 1));
            if (result != -1) {
                successors.add(result);
            }
        }
        return successors.stream().mapToInt(i->i).toArray();
    }



    public int getFirstSuccessor(Integer id) throws InterruptedException {
        for (int i=0;i<edgeNum;i++) {
            escape();
            int result = checkIfSTART(id, getEdgeInner(i, 0),getEdgeInner(i, 1));
            if (result != -1) {
                return result;
            }
        }
        return -1;
    }

    public int[] getPredecessors(Integer id) throws InterruptedException {
        List<Integer> predecessors = new ArrayList<Integer>();
        for (int i=0;i<edgeNum;i++) {
            escape();
            //if id is end, then start is predecessor
            int result = checkIfSTART(id, getEdgeInner(i, 1),getEdgeInner(i, 0));
            if (result != -1) {
                predecessors.add(result);
            }
        }
        return predecessors.stream().mapToInt(i->i).toArray();
    };

    public int getFirstPredecessor(Integer id) throws InterruptedException {
        for (int i=0;i<edgeNum;i++) {
            escape();
            int result = checkIfSTART(id, getEdgeInner(i, 1),getEdgeInner(i, 0));
            if (result != -1) {
                return result;
            }
        }
        return -1;
    };

    public int[] getNonIncident(Integer id) throws InterruptedException {
        boolean[] nonIncident = new boolean[vertexNum];
        List<Integer> nonIncidentIds = new ArrayList<Integer>();
        for(int i = 0; i < edges.length; i++) {
            escape();
            nonIncident[i] = true;
        }
        for (int i=0;i<edgeNum;i++) {
            escape();
            int result = checkIfIncident(id, getEdgeInner(i, 0),getEdgeInner(i, 1));
            if (result != -1)
                nonIncident[result]=false;
        }
        for (int i = 0; i < edges.length; i++) {
            escape();
            if (nonIncident[i]) {
                nonIncidentIds.add(i);
            }
        }
        return nonIncidentIds.stream().mapToInt(i->i).toArray();
    }

    @Override
    public int[] getAllVertices() throws InterruptedException {
        int[] vertices = new int[vertexNum];
        for (int i=0;i<vertexNum;i++){
            escape();
            vertices[i]=i;
        }
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
    public int getEdge(Integer id1, Integer id2) throws InterruptedException {
        for (int i=0;i<edgeNum;i++) {
            escape();
            int start = getEdgeInner(i, 0);
            int end = getEdgeInner(i, 1);
            if (checkIfSTART(id1, start, end) != -1 &&
                    checkIfSTART(id2, end, start) != -1 )
            {
                return getWeight(i);
            }
            if (checkIfSTART(id2, start, end) != -1 &&
                    checkIfSTART(id1, end, start) != -1 ) {
                return -1 * getWeight(i);
            }
        }
        return 0;
    }

    @Override
    public int[][] getAllEdges() {
        return edges.clone();
    }



    protected int getEdgeInner(Integer index1, Integer index2) {
        operations+=1;
        return edges[index1][index2];
    }

    protected int getWeight(int edgeNumber)
    {
        return 1;
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
    public abstract GraphRepresentationInterface clone();


}
