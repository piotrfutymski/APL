package put.apl.algorithms.graphs.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class AdjacencyMatrix implements GraphRepresentation {
    protected final int[][] matrix;
    int verticesSize;

    public abstract void fillEdge(int start, int end);
    public abstract boolean checkIfSTART(int number);
    public abstract boolean checkIfEND(int number);

    public AdjacencyMatrix(String input) {

        verticesSize = input.split(System.getProperty("line.separator")).length;
        matrix = new int[verticesSize][];
        Scanner scanner = new Scanner(input);

        scanner.close();
        scanner = new Scanner(input);
        int verticeNumber=0;
        while (scanner.hasNextLine()) {
            matrix[verticeNumber] = new int[verticesSize];
            String line = scanner.nextLine();
            String[] edges = line.split(",");
            for (String edge : edges)
            {
                fillEdge(verticeNumber, Integer.parseInt(edge));
            }
            verticeNumber+=1;
        }
        scanner.close();
    }

    public AdjacencyMatrix(int[][] matrix) {
        verticesSize = matrix.length;
        this.matrix = matrix;
    }

    @Override
    public int[] getSuccessors(Integer id) {
        List<Integer> result = new ArrayList<>();
        for (int i=0; i<verticesSize;i++)
        {
            if (checkIfSTART(matrix[id][i]))
                result.add(i);
        }
        return result.stream().mapToInt(i->i).toArray();
    }

    @Override
    public int getFirstSuccessor(Integer id) {
        for (int i=0; i<verticesSize;i++)
        {
            if (checkIfSTART(matrix[id][i]))
                return i;
        }
        return -1;
    }

    @Override
    public int[] getPredecessors(Integer id) {
        List<Integer> result = new ArrayList<>();
        for (int i=0; i<verticesSize;i++)
        {
            if (checkIfEND(matrix[id][i]))
                result.add(i);
        }
        return result.stream().mapToInt(i->i).toArray();
    }

    @Override
    public int getFirstPredecessor(Integer id) {
        for (int i=0; i<verticesSize;i++)
        {
            if (checkIfEND(matrix[id][i]))
                return i;
        }
        return -1;
    }

    @Override
    public int[] getNonIncident(Integer id) {
        boolean[] vertices = new boolean[verticesSize];
        List<Integer> result = new ArrayList<>();
        for (int i=0; i<verticesSize;i++)
            if (matrix[id][i] != 0)
                vertices[i]=true;

        for (int i=0;i<verticesSize;i++)
            if (!vertices[i])
                result.add(i);

        return result.stream().mapToInt(i->i).toArray();
    }

    @Override
    public int[][] getAllEdges() {
        return matrix;
    }

    @Override
    public int getMemoryOccupancy() {
        return verticesSize * verticesSize  * Integer.BYTES;
    }

    @Override
    public String getRelationBetween(Integer id1, Integer id2) {
        if ((checkIfSTART(matrix[id1][id2])))
            return "predecessor";
        else if ((checkIfEND(matrix[id1][id2])))
            return "successor";
        return "none";
    }

}
