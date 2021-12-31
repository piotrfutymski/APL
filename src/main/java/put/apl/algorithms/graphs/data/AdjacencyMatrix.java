package put.apl.algorithms.graphs.data;

import java.util.ArrayList;
import java.util.List;

public abstract class AdjacencyMatrix extends GraphRepresentation {
    protected int[][] matrix;
    int verticesNumber;
    int edgesNumber;
    int operations;

    public abstract void fillEdge(int start, int end);
    public abstract boolean checkIfSTART(int start, int end);
    public abstract boolean checkIfEND(int start, int end);

    public AdjacencyMatrix(List<List<Integer>> input) {
        loadFromIncidenceList(input);
    }

    public AdjacencyMatrix(int[][] matrix) {
        operations = 0;
        verticesNumber = matrix.length;
        this.matrix = matrix;
        edgesNumber = 0;
        for (int i=0; i<matrix.length; i++)
        {
            for (int j=0; j<matrix[i].length; j++)
                if (checkIfSTART(i, j))
                    edgesNumber+=1;
        }
    }

    public AdjacencyMatrix() {
        matrix = new int[0][];
    }

    @Override
    public void loadFromIncidenceList(List<List<Integer>> input)
    {
        edgesNumber = 0;
        verticesNumber = input.size();
        matrix = new int[verticesNumber][];
        for (int i = 0; i < verticesNumber; i++)
            matrix[i] = new int[verticesNumber];
        for (int i = 0; i < verticesNumber; i++) {
            for (int j = 0; j < input.get(i).size(); j++) {
                fillEdge(i, input.get(i).get(j));
                edgesNumber+=1;
            }
        }
    }

    @Override
    public int[] getSuccessors(Integer id) {
        List<Integer> result = new ArrayList<>();

        for (int i = 0; i< verticesNumber; i++)
        {
            if (checkIfSTART(id, i))
                result.add(i);
        }
        return result.stream().mapToInt(i->i).toArray();
    }

    @Override
    public int getFirstSuccessor(Integer id) {
        for (int i = 0; i< verticesNumber; i++)
        {
            if (checkIfSTART(id, i))
                return i;
        }
        return -1;
    }

    @Override
    public int[] getPredecessors(Integer id) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i< verticesNumber; i++)
        {
            if (checkIfSTART(i, id))
                result.add(i);
        }
        return result.stream().mapToInt(i->i).toArray();
    }

    @Override
    public int getFirstPredecessor(Integer id) {
        for (int i = 0; i< verticesNumber; i++)
        {
            if (checkIfSTART(i, id))
                return i;
        }
        return -1;
    }

    @Override
    public int[] getNonIncident(Integer id) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i< verticesNumber; i++)
            if (getEdge(id,i) == 0)
                result.add(i);

        return result.stream().mapToInt(i->i).toArray();
    }

    @Override
    public int[][] getRepresentation() {
        return matrix;
    }

    @Override
    public int getMemoryOccupancy() {
        return verticesNumber * verticesNumber * Integer.BYTES;
    }


    public int getEdge(Integer id1, Integer id2) {
        operations += 1;
        if (getEdgeInner(id1, id2) > 0) return getEdgeInner(id1,id2);
        else if (getEdgeInner(id1,id2) > 0) {
            return -1 * getEdgeInner(id1,id2);
        }
        return 0;
    }

    public int getEdgeInner(Integer id1, Integer id2) {
        operations += 1;
        return matrix[id1][id2];
    }

    @Override
    public int getVerticesNumber()
    {
        return verticesNumber;
    }

    @Override
    public int getEdgesNumber()
    {
        return edgesNumber;
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
    public int[] getAllVertices()
    {
        int[] vertices = new int[verticesNumber];
        for (int i=0;i<verticesNumber;i++)
            vertices[i]=i;
        return vertices;
    }

    @Override
    public int[][] getAllEdges()
    {
        int[][] result = new int[edgesNumber][];
        int edgeNumber=0;
        for (int i=0; i < verticesNumber; i++)
        {
            for (int j=i+1; j < verticesNumber; j++)
            {
                edgeNumber += getAllEdgesInner(edgeNumber, i, j, result);
            }
        }
        return result;
    }

    protected abstract int getAllEdgesInner(int edgeNumber, int i, int j, int[][] result);

    @Override
    public abstract GraphRepresentation clone();
}
