package put.apl.algorithms.graphs.data;

import java.util.ArrayList;
import java.util.List;


public abstract class ListOfIncidentWeighted extends GraphRepresentation implements GraphRepresentationWeightedInterface {


    protected Edge[][] representation;

    protected int vertexNum;
    protected int edgeNum;
    protected int operations;

    abstract void addEdge(List<ArrayList<Edge>> edgesList, int start, int end, int weight);

    public ListOfIncidentWeighted() {
        representation = new Edge[0][];
    }

    public ListOfIncidentWeighted(List<List<Integer>> input, List<List<Integer>> weights) throws InterruptedException {
        loadFromIncidenceList(input, weights);
    }

    public ListOfIncidentWeighted(Edge[][] edges) {
        this.representation = edges;
        this.vertexNum = edges.length;
        edgeNum = 0;
        for (var vertex : edges)
            for (var edge : vertex)
                edgeNum+=1;
    }

    @Override
    public void loadFromIncidenceList(List<List<Integer>> input) throws InterruptedException {
        var weights = new ArrayList<List<Integer>>(input.size());
        for (var input_vertex : input)
        {
            var weights_vertex = new ArrayList<Integer>(input_vertex.size());
            for (var input_edge : input_vertex) {
                escape();
                weights_vertex.add(1);
            }
            weights.add(weights_vertex);
        }
        loadFromIncidenceList(input, weights);
    }

    public void loadFromIncidenceList(List<List<Integer>> input, List<List<Integer>> weights) throws InterruptedException {
        vertexNum = input.size();
        edgeNum = 0;
        List<ArrayList<Edge>> edgesList = new ArrayList<>();
        for (int i = 0; i < vertexNum; i++) {
            escape();
            edgesList.add(new ArrayList<Edge>());
        }
        representation = new Edge[vertexNum][];
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).size(); j++) {
                escape();
                addEdge(edgesList, i, input.get(i).get(j), weights.get(i).get(j));
                edgeNum++;
            }
        }
        for (int i = 0; i < edgesList.size(); i++) {
            representation[i] = new Edge[edgesList.get(i).size()];
            for (int j = 0; j < edgesList.get(i).size(); j++) {
                escape();
                representation[i][j] = edgesList.get(i).get(j);
            }
        }
    }

    public Edge[] getDirect(Integer id)
    {
        operations+=representation[id].length;
        return representation[id];
    }
    public Edge getFirstDirect(Integer id)
    {
        if (representation[id].length > 0) {
            return getEdgeInner(id,0);
        }
        return new Edge(-1,-1);
    }

    public Edge[] getIndirect(Integer id) throws InterruptedException {
        List<Edge> indirect = new ArrayList<Edge>();
        for (int i=0; i< vertexNum; i++)
        {
            if (i==id)
                continue;
            for (int j = 0; j< representation[i].length; j++)
            {
                escape();
                if (getEdgeInner(i,j).vertex == id)
                {
                    indirect.add(new Edge(i, getEdgeInner(i,j).weight));
                    break;
                }
            }
        }
        return indirect.toArray(new Edge[0]);
    }

    public Edge getFirstIndirect(Integer id) throws InterruptedException {
        for (int i=0; i< vertexNum; i++)
        {
            if (i==id)
                continue;
            for (int j = 0; j< representation[i].length; j++)
            {
                escape();
                if (getEdgeInner(i,j).vertex == id)
                {
                    return new Edge(i, getEdgeInner(i,j).weight);
                }
            }
        }
        return new Edge(-1,-1);
    }

    public int[] getNonIncident(int id) throws InterruptedException {
        boolean[] nonIncident = new boolean[representation.length];
        List<Integer> nonIncidentIds = new ArrayList<Integer>();
        for(int i = 0; i < representation.length; i++) {
            escape();
            nonIncident[i] = true;
        }
        for (var index : getDirect(id)) {
            escape();
            nonIncident[index.vertex] = false;
        }
        for (var index : getIndirect(id)) {
            escape();
            nonIncident[index.vertex] = false;
        }
        for (int i = 0; i < representation.length; i++) {
            escape();
            if (nonIncident[i]) {
                nonIncidentIds.add(i);
            }
        }
        return nonIncidentIds.stream().mapToInt(i->i).toArray();
    }

    @Override
    public int[][] getRepresentation() {
        int[][] result = new int[vertexNum][];
        for (int i=0;i<vertexNum;i++)
        {
            result[i] = new int[representation[i].length];
            for (int j=0; j<representation[i].length;j++) {
                result[i][j] = representation[i][j].vertex;
            }

        }
        return result;
    }

    @Override
    public int getMemoryOccupancy() {
        int size=0;
        for (Edge[] edgeList : representation)
            size+= 2 * Integer.BYTES * edgeList.length;
        return size;
    }

    public Edge getEdgeInner(int index1, int index2)
    {
        operations+=1;
        return representation[index1][index2];
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
    public abstract GraphRepresentationInterface clone();


}
