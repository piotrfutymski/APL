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

    public AdjacencyMatrix(String input) {
        edgesNumber = 0;
        verticesNumber = input.split(System.getProperty("line.separator")).length;
        matrix = new int[verticesNumber][];
        Scanner scanner = new Scanner(input);

        scanner.close();
        scanner = new Scanner(input);
        int vertice=0;
        while (scanner.hasNextLine()) {
            matrix[vertice] = new int[verticesNumber];
            String line = scanner.nextLine();
            String[] edges = line.split(",");
            for (String edge : edges)
            {
                fillEdge(vertice, Integer.parseInt(edge));
                edgesNumber+=1;
            }
            vertice+=1;
        }
        scanner.close();
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
}
