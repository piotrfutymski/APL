package put.apl.algorithms.graphs.data;

import java.util.ArrayList;
import java.util.List;

public abstract class AdjacencyMatrix extends GraphRepresentation {
    protected int[][] matrix;
    int verticesNumber;
    int edgesNumber;
    int operations;

    public abstract void fillEdge(int start, int end);
    public abstract boolean checkIfEdge(int start, int end);

    public AdjacencyMatrix(List<List<Integer>> input) throws InterruptedException {
        loadFromIncidenceList(input);
    }

    public AdjacencyMatrix(int[][] matrix) throws InterruptedException {
        operations = 0;
        verticesNumber = matrix.length;
        this.matrix = matrix;
        edgesNumber = 0;
        for (int i=0; i<matrix.length; i++)
        {
            for (int j=0; j<matrix[i].length; j++) {
                escape();
                if (checkIfEdge(i, j))
                    edgesNumber+=1;
            }
        }
    }

    public AdjacencyMatrix() {
        matrix = new int[0][];
    }

    @Override
    public void loadFromIncidenceList(List<List<Integer>> input) throws InterruptedException {
        edgesNumber = 0;
        verticesNumber = input.size();
        matrix = new int[verticesNumber][];
        for (int i = 0; i < verticesNumber; i++){
            escape();
            matrix[i] = new int[verticesNumber];
        }
        for (int i = 0; i < verticesNumber; i++) {
            for (int j = 0; j < input.get(i).size(); j++) {
                escape();
                fillEdge(i, input.get(i).get(j));
                edgesNumber+=1;
            }
        }
    }

    @Override
    public int[] getSuccessors(Integer id) throws InterruptedException {
        List<Integer> result = new ArrayList<>();

        for (int i = 0; i< verticesNumber; i++)
        {
            escape();
            if (checkIfEdge(id, i))
                result.add(i);
        }
        return result.stream().mapToInt(i->i).toArray();
    }

    @Override
    public int getFirstSuccessor(Integer id) throws InterruptedException {
        for (int i = 0; i< verticesNumber; i++)
        {
            escape();
            if (checkIfEdge(id, i))
                return i;
        }
        return -1;
    }

    @Override
    public int[] getPredecessors(Integer id) throws InterruptedException {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i< verticesNumber; i++)
        {
            escape();
            if (checkIfEdge(i, id))
                result.add(i);
        }
        return result.stream().mapToInt(i->i).toArray();
    }

    @Override
    public int getFirstPredecessor(Integer id) throws InterruptedException {
        for (int i = 0; i< verticesNumber; i++)
        {
            escape();
            if (checkIfEdge(i, id))
                return i;
        }
        return -1;
    }

    @Override
    public int[] getNonIncident(Integer id) throws InterruptedException {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i< verticesNumber; i++) {
            escape();
            if (getEdge(id, i) == 0)
                result.add(i);
        }
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

    @Override
    public int getEdge(Integer id1, Integer id2) {
        if (getEdgeInner(id1, id2) > 0)
            return getEdgeInner(id1,id2);
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
    public int[] getAllVertices() throws InterruptedException {
        int[] vertices = new int[verticesNumber];
        for (int i=0;i<verticesNumber;i++){
            escape();
            vertices[i]=i;
        }
        return vertices;
    }

    @Override
    public int[][] getAllEdges() throws InterruptedException {
        int[][] result = new int[edgesNumber][];
        int edgeNumber=0;
        for (int i=0; i < verticesNumber; i++)
        {
            for (int j=i+1; j < verticesNumber; j++)
            {
                escape();
                edgeNumber += getAllEdgesInner(edgeNumber, i, j, result);
            }
        }
        return result;
    }

    protected abstract int getAllEdgesInner(int edgeNumber, int i, int j, int[][] result);

    @Override
    public abstract GraphRepresentationInterface clone();
}
