package put.apl.algorithms.graphs.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AdjacencyMatrixDirected implements GraphRepresentation {
    private final int[][] matrix;
    int verticesSize;

    private final static int START=1;
    private final static int END=-1;

    public AdjacencyMatrixDirected(String input) {

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
                matrix[verticeNumber][Integer.parseInt(edge)] = START;
                matrix[Integer.parseInt(edge)][verticeNumber] = END;
            }
            verticeNumber+=1;
        }
        scanner.close();
    }

    public AdjacencyMatrixDirected(int[][] matrix) {
        verticesSize = matrix.length;
        this.matrix = matrix;
    }

    @Override
    public int[] getSuccessors(Integer id) {
        List<Integer> result = new ArrayList<>();
        for (int i=0; i<verticesSize;i++)
        {
            if (matrix[id][i] == START)
                result.add(i);
        }
        return result.stream().mapToInt(i->i).toArray();
    }

    @Override
    public int getFirstSuccessor(Integer id) {
        for (int i=0; i<verticesSize;i++)
        {
            if (matrix[id][i] == START)
                return i;
        }
        return -1;
    }

    @Override
    public int[] getPredecessors(Integer id) {
        List<Integer> result = new ArrayList<>();
        for (int i=0; i<verticesSize;i++)
        {
            if (matrix[id][i] == END)
                result.add(i);
        }
        return result.stream().mapToInt(i->i).toArray();
    }

    @Override
    public int getFirstPredecessor(Integer id) {
        for (int i=0; i<verticesSize;i++)
        {
            if (matrix[id][i] == END)
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
        if (matrix[id1][id2] == START)
            return "predecessor";
        else if (matrix[id1][id2] == END)
            return "successor";
        return "none";
    }

    @Override
    public GraphRepresentation clone() {
        return new AdjacencyMatrixDirected(matrix.clone());
    }
}
