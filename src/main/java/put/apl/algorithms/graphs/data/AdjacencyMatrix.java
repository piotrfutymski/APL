package put.apl.algorithms.graphs.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class AdjacencyMatrix implements GraphRepresentation {
    protected final int[][] matrix;
    int verticesNumber;
    int edgesNumber;
    int operations;

    public abstract void fillEdge(int start, int end);
    public abstract boolean checkIfSTART(int number);
    public abstract boolean checkIfEND(int number);

    public AdjacencyMatrix(List<ArrayList<Integer>> input) {
        edgesNumber = 0;
        verticesNumber = input.size();
        matrix = new int[verticesNumber][];
        for (int i = 0; i < input.size(); i++) {
            matrix[i] = new int[verticesNumber];

            for (int j = 0; j < input.get(i).size(); j++) {
                fillEdge(i, input.get(i).get(j));
                edgesNumber+=1;
            }
        }
    }

    public AdjacencyMatrix(int[][] matrix) {
        operations = 0;
        verticesNumber = matrix.length;
        this.matrix = matrix;
        edgesNumber = 0;
        for (var row : this.matrix)
        {
            for (var edge : row)
                if (checkIfSTART(edge))
                    edgesNumber+=1;
        }
    }

    public AdjacencyMatrix() {
        matrix = new int[0][];
    }

    @Override
    public int[] getSuccessors(Integer id) {
        List<Integer> result = new ArrayList<>();

        for (int i = 0; i< verticesNumber; i++)
        {
            if (checkIfSTART(getEdge(id,i)))
                result.add(i);
        }
        return result.stream().mapToInt(i->i).toArray();
    }

    @Override
    public int getFirstSuccessor(Integer id) {
        for (int i = 0; i< verticesNumber; i++)
        {
            if (checkIfSTART(getEdge(id,i)))
                return i;
        }
        return -1;
    }

    @Override
    public int[] getPredecessors(Integer id) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i< verticesNumber; i++)
        {
            if (checkIfSTART(getEdge(i,id)))
                result.add(i);
        }
        return result.stream().mapToInt(i->i).toArray();
    }

    @Override
    public int getFirstPredecessor(Integer id) {
        for (int i = 0; i< verticesNumber; i++)
        {
            if (checkIfSTART(getEdge(i,id)))
                return i;
        }
        return -1;
    }

    @Override
    public int[] getNonIncident(Integer id) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i< verticesNumber; i++)
            if (getEdge(id,i) != 0)
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

    @Override
    public int getEdge(Integer id1, Integer id2) {
        operations+=1;
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
    public abstract GraphRepresentation clone();
}
