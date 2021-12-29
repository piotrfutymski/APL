package put.apl.algorithms.graphs.data;

import java.util.ArrayList;
import java.util.List;


public abstract class ListOfIncident extends GraphRepresentation {
    protected int[][] edges;

    protected int vertexNum;
    protected int edgeNum;
    protected int operations;

    abstract void addEdge(List<ArrayList<Integer>> edgesList, int start, int end);

    public ListOfIncident() {
        edges = new int[0][];
    }

    public ListOfIncident(List<List<Integer>> input) {
        loadFromIncidenceList(input);
    }

    public ListOfIncident(int[][] edges) {
        this.edges = edges;
        this.vertexNum=edges.length;
        edgeNum = 0;
        for (var vertex : edges)
            for (var edge : vertex)
                edgeNum+=1;
    }

    @Override
    public void loadFromIncidenceList(List<List<Integer>> input) {
        vertexNum = input.size();
        edgeNum = 0;
        List<ArrayList<Integer>> edgesList = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < vertexNum; i++) {
            edgesList.add(new ArrayList<Integer>());
        }
        edges = new int[vertexNum][];
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).size(); j++) {
                addEdge(edgesList, i, input.get(i).get(j));
                edgeNum++;
            }
        }
        for (int i = 0; i < edgesList.size(); i++) {
            edges[i] = new int[edgesList.get(i).size()];
            for (int j = 0; j < edgesList.get(i).size(); j++) {
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
    public int[] getIndirect(Integer id)
    {
        List<Integer> indirect = new ArrayList<Integer>();
        for (int i=0; i< vertexNum; i++)
        {
            if (i==id)
                continue;
            for (int j=0; j<edges[i].length; j++)
            {
                if (getEdgeInner(i,j) == id)
                {
                    indirect.add(i);
                    break;
                }
            }
        }
        return indirect.stream().mapToInt(i->i).toArray();
    }

    public int getFirstIndirect(Integer id)
    {
        for (int i=0; i< vertexNum; i++)
        {
            if (i==id)
                continue;
            for (int j=0; j<edges[i].length; j++)
            {
                if (getEdgeInner(i,j) == id)
                {
                    return i;
                }
            }
        }
        return -1;
    }

    public int[] getNonIncident(Integer id) {
        boolean[] nonIncident = new boolean[edges.length];
        List<Integer> nonIncidentIds = new ArrayList<Integer>();
        for(int i = 0; i < edges.length; i++) {
            nonIncident[i] = true;
        }
        for (var index : getDirect(id))
            nonIncident[index] = false;
        for (var index : getIndirect(id))
            nonIncident[index] = false;
        for (int i = 0; i < edges.length; i++) {
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
    public int getMemoryOccupancy() {
        int size=0;
        for (int[] edgeList : edges)
            size+=Integer.BYTES *edgeList.length;
        return size;
    }

    public int getEdgeInner(int index1, int index2)
    {
        operations+=1;
        return edges[index1][index2];
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
