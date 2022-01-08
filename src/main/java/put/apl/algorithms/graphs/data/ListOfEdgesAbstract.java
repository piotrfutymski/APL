package put.apl.algorithms.graphs.data;

import java.util.ArrayList;
import java.util.List;


public abstract class ListOfEdgesAbstract extends GraphRepresentation {
    protected int[][] edges;
    // vertexNum is necessary for keeping number of vertices (used in getNonIncident)
    protected int vertexNum;
    protected int edgeNum;
    protected int operations;

    public ListOfEdgesAbstract() {
        edges = new int[0][];
    }

    // Format: line number = vertex id, successors separated by comma
    public ListOfEdgesAbstract(List<List<Integer>> input) throws InterruptedException {
        loadFromIncidenceList(input);
    }

    public ListOfEdgesAbstract(int[][] edges, int vertexNum){
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

    abstract public int[] getSuccessors(int id) throws InterruptedException;

    public abstract int getFirstSuccessor(int id) throws InterruptedException;

    abstract public int[] getPredecessors(int id) throws InterruptedException;

    abstract public int getFirstPredecessor(int id) throws InterruptedException;

    public int[] getNonIncident(int id) throws InterruptedException {
        boolean[] nonIncident = new boolean[edges.length];
        List<Integer> nonIncidentIds = new ArrayList<Integer>();
        for (int i=0;i<edgeNum;i++) {
            escape();
            int result = checkIfIncident(id, getEdgeInner(i, 0),getEdgeInner(i, 1));
            if (result != -1)
                nonIncident[result]=true;
        }
        for (int i = 0; i < edges.length; i++) {
            escape();
            if (!nonIncident[i]) {
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

    abstract public int getEdge(int id1, int id2) throws InterruptedException;

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
