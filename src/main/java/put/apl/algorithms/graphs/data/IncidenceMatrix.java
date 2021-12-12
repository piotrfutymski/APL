package put.apl.algorithms.graphs.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class IncidenceMatrix implements GraphRepresentation {

    protected final int[][] matrix;
    protected int verticesSize;
    protected int edgesSize;

    public abstract void fillEdge(int edge, int start, int end);
    public abstract boolean checkIfSTART(int number);
    public abstract boolean checkIfEND(int number);

    public IncidenceMatrix(String input) {

        verticesSize = input.split(System.getProperty("line.separator")).length;
        matrix = new int[verticesSize][];
        Scanner scanner = new Scanner(input);
        edgesSize = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            int edges = line.split(",").length;
            edgesSize+=edges;
        }
        for (int i=0; i<verticesSize; i++)
        {
            matrix[i] = new int[edgesSize];
        }
        scanner.close();
        scanner = new Scanner(input);
        int verticeNumber=0;
        int edgeNumber=0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] edges = line.split(",");
            for (String edge : edges)
            {
                fillEdge(edgeNumber, verticeNumber, Integer.parseInt(edge));
                edgeNumber +=1;
            }
            verticeNumber+=1;
        }
        scanner.close();
    }

    public IncidenceMatrix(int[][] matrix) {
        verticesSize = matrix.length;
        edgesSize = matrix[0].length;
        this.matrix = matrix;
    }

    @Override
    public int[] getSuccessors(Integer id) {
        List<Integer> result = new ArrayList<>();
        for (int i=0; i<edgesSize;i++)
        {
            if (checkIfSTART(matrix[id][i]))
            {
                for (int j = 0; j < verticesSize; j++)
                {
                    if (j == id) continue;
                    if (checkIfEND(matrix[j][i]))
                    {
                        result.add(j);
                        break;
                    }
                }
            }
        }
        return result.stream().mapToInt(i->i).toArray();
    }

    @Override
    public int getFirstSuccessor(Integer id) {
        for (int i=0; i<edgesSize;i++)
        {
            if (checkIfSTART(matrix[id][i]))
            {
                for (int j = 0; j < verticesSize; j++)
                {
                    if (j == id) continue;
                    if (checkIfEND(matrix[j][i]))
                    {
                        return j;
                    }
                }
            }
        }
        return -1;
    }

    @Override
    public int[] getPredecessors(Integer id) {
        List<Integer> result = new ArrayList<>();
        for (int i=0; i<edgesSize;i++)
        {
            if (checkIfEND(matrix[id][i]))
            {
                for (int j = 0; j < verticesSize; j++)
                {
                    if (j == id) continue;
                    if (checkIfSTART(matrix[j][i]))
                    {
                        result.add(j);
                        break;
                    }
                }
            }
        }
        return result.stream().mapToInt(i->i).toArray();
    }

    @Override
    public int getFirstPredecessor(Integer id) {
        for (int i=0; i<edgesSize;i++)
        {
            if (checkIfEND(matrix[id][i]))
            {
                for (int j = 0; j < verticesSize; j++)
                {
                    if (j == id) continue;
                    if (checkIfSTART(matrix[j][i]))
                    {
                        return j;
                    }
                }
            }
        }
        return -1;
    }

    @Override
    public int[] getNonIncident(Integer id) {
        boolean[] vertices = new boolean[verticesSize];
        List<Integer> result = new ArrayList<>();
        for (int i=0; i<edgesSize;i++)
        {
            if (matrix[id][i] != 0)
            {
                for (int j = 0; j < verticesSize; j++)
                {
                    if (j == id) continue;
                    if (matrix[j][i] != 0)
                    {
                        vertices[j]=true;
                        break;
                    }
                }
            }
        }
        for (int i=0;i<verticesSize;i++)
            if (!vertices[i])
                result.add(i);

        return result.stream().mapToInt(i->i).toArray();
    }

    @Override
    public int[][] getRepresentation() {
        return matrix;
    }

    @Override
    public int getMemoryOccupancy() {
        return verticesSize * edgesSize * Integer.BYTES;
    }

    @Override
    public int getEdge(Integer id1, Integer id2) {
        for (int i=0; i<edgesSize;i++) {
            if (matrix[id1][i] != 0) {
                //if start is at id2, id1 is successor
                if (checkIfSTART(matrix[id2][i]))
                    return -1;
                    //if end is id2, id1 is predecessor
                else if (checkIfEND(matrix[id2][i]))
                    return 1;
            }
        }
        return 0;
    }

    @Override
    public int getVerticesNumber() {
        return verticesSize;
    }

    @Override
    public int getEdgesNumber() {
        return edgesSize;
    }

}
