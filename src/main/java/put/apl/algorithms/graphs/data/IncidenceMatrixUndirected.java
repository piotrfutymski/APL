package put.apl.algorithms.graphs.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class IncidenceMatrixUndirected implements GraphRepresentation {

    private final int[][] matrix;
    int verticesSize;
    int edgesSize;

    private final static int START=1;
    private final static int END=1;

    public IncidenceMatrixUndirected(String input) {

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
                matrix[verticeNumber][edgeNumber] = START;
                matrix[Integer.parseInt(edge)][edgeNumber] = END;
                edgeNumber +=1;
            }
            verticeNumber+=1;
        }
        scanner.close();
    }

    public IncidenceMatrixUndirected(int[][] matrix) {
        verticesSize = matrix.length;
        edgesSize = matrix[0].length;
        this.matrix = matrix;
    }

    @Override
    public int[] getSuccessors(Integer id) {
        List<Integer> result = new ArrayList<>();
        for (int i=0; i<edgesSize;i++)
        {
            if (matrix[id][i] == START)
            {
                for (int j = 0; j < verticesSize; j++)
                {
                    if (j == id) continue;
                    if (matrix[j][i] == END)
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
            if (matrix[id][i] == START)
            {
                for (int j = 0; j < verticesSize; j++)
                {
                    if (j == id) continue;
                    if (matrix[j][i] == END)
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
            if (matrix[id][i] == END)
            {
                for (int j = 0; j < verticesSize; j++)
                {
                    if (j == id) continue;
                    if (matrix[j][i] == START)
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
            if (matrix[id][i] == END)
            {
                for (int j = 0; j < verticesSize; j++)
                {
                    if (j == id) continue;
                    if (matrix[j][i] == START)
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
    public int[][] getAllEdges() {
        return matrix;
    }

    @Override
    public int getMemoryOccupancy() {
        return verticesSize * edgesSize * Integer.BYTES;
    }

    @Override
    public String getRelationBetween(Integer id1, Integer id2) {
        for (int i=0; i<edgesSize;i++) {
            if (matrix[id1][i] != 0) {
                if (matrix[id2][i] == START)
                    return "incident";
            }
        }
        return "none";
    }

    @Override
    public GraphRepresentation clone() {
        return new IncidenceMatrixUndirected(matrix.clone());
    }
}
