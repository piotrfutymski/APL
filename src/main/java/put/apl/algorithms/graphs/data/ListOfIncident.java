package put.apl.algorithms.graphs.data;

import java.util.ArrayList;
import java.util.List;

/*
    Undirected version
 */
public abstract class ListOfIncident extends GraphRepresentation {
    protected int[][] edges;

    protected int vertexNum;
    protected int edgeNum;
    protected int operations;

    abstract void addEdge(List<ArrayList<Integer>> edgesList, int start, int end);

    public ListOfIncident() {
        edges = new int[0][];
    }

    public ListOfIncident(List<List<Integer>> input, int vertexNum) throws InterruptedException {
        this.vertexNum = vertexNum;
        edgeNum = input.size();
        List<ArrayList<Integer>> edgesList = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < vertexNum; i++) {
            escape();
            edgesList.add(new ArrayList<Integer>());
        }
        edges = new int[vertexNum][];
        for (int i = 0; i < input.size(); i++) {
            escape();
            addEdge(edgesList, input.get(i).get(0), input.get(i).get(1));
        }
        for (int i = 0; i < edgesList.size(); i++) {
            escape();
            edges[i] = new int[edgesList.get(i).size()];
            // Just to be sure that order is right
            //Collections.sort(edgesList.get(i));
            for (int j = 0; j < edgesList.get(i).size(); j++) {
                escape();
                edges[i][j] = edgesList.get(i).get(j);
            }
        }
    };

    public ListOfIncident(List<List<Integer>> input) throws InterruptedException {
        loadFromIncidenceList(input);
    }

    public ListOfIncident(int[][] edges) throws InterruptedException {
        this.edges = edges;
        this.vertexNum=edges.length;
        edgeNum = 0;
        for (var vertex : edges)
            for (var edge : vertex){
                escape();
                edgeNum+=1;
            }
    }

    @Override
    public void loadFromIncidenceList(List<List<Integer>> input) throws InterruptedException {
        vertexNum = input.size();
        edgeNum = 0;
        List<ArrayList<Integer>> edgesList = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < vertexNum; i++) {
            escape();
            edgesList.add(new ArrayList<Integer>());
        }
        edges = new int[vertexNum][];
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).size(); j++) {
                escape();
                addEdge(edgesList, i, input.get(i).get(j));
                edgeNum++;
            }
        }
        for (int i = 0; i < edgesList.size(); i++) {
            edges[i] = new int[edgesList.get(i).size()];
            // Just to be sure that order is right
            //Collections.sort(edgesList.get(i));
            for (int j = 0; j < edgesList.get(i).size(); j++) {
                escape();
                edges[i][j] = edgesList.get(i).get(j);
            }
        }
    }

    public int[] getDirect(Integer id)
    {
        operations +=edges.length;
        return edges[id];
    }

    public int getFirstDirect(Integer id)
    {
        if (edges[id].length > 0) {
            return getEdgeInner(id,0);
        }
        return -1;
    }
    public int[] getIndirect(Integer id) throws InterruptedException {
        List<Integer> indirect = new ArrayList<Integer>();
        for (int i=0; i< vertexNum; i++)
        {
            if (i==id)
                continue;
            for (int j=0; j<edges[i].length; j++)
            {
                escape();
                if (getEdgeInner(i,j) == id)
                {
                    indirect.add(i);
                    break;
                }
            }
        }
        return indirect.stream().mapToInt(i->i).toArray();
    }

    public int getFirstIndirect(Integer id) throws InterruptedException {
        for (int i=0; i< vertexNum; i++)
        {
            if (i==id)
                continue;
            for (int j=0; j<edges[i].length; j++)
            {
                escape();
                if (getEdgeInner(i,j) == id)
                {
                    return i;
                }
            }
        }
        return -1;
    }

    public int[] getNonIncident(Integer id) throws InterruptedException {
        boolean[] nonIncident = new boolean[edges.length];
        List<Integer> nonIncidentIds = new ArrayList<Integer>();
        for(int i = 0; i < edges.length; i++) {
            escape();
            nonIncident[i] = true;
        }
        for (var index : getDirect(id)){
            escape();
            nonIncident[index] = false;
        }

        for (var index : getIndirect(id)){
            escape();
            nonIncident[index] = false;
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
    public int[][] getRepresentation() {
        return edges;
    }

    @Override
    public int getMemoryOccupancy() throws InterruptedException {
        int size=0;
        for (int[] edgeList : edges){
            escape();
            size+=Integer.BYTES *edgeList.length;
        }
        return size;
    }

    public int getEdgeInner(int index1, int index2)
    {
        operations+=1;
        return edges[index1][index2];
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
    public abstract GraphRepresentation clone();


}
