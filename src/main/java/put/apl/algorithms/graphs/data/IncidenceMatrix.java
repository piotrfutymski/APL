package put.apl.algorithms.graphs.data;

import java.util.ArrayList;
import java.util.List;

public abstract class IncidenceMatrix extends GraphRepresentation {

    protected int[][] matrix;
    protected int verticesSize;
    protected int edgesSize;
    int operations;

    public abstract void fillEdge(int edge, int start, int end);
    public abstract boolean checkIfSTART(int number);
    public abstract boolean checkIfEND(int number);

    public IncidenceMatrix(List<List<Integer>> input) throws InterruptedException {
        loadFromIncidenceList(input);
    }

    public IncidenceMatrix(int[][] matrix) {
        verticesSize = matrix.length;
        edgesSize = matrix[0].length;
        this.matrix = matrix;
    }

    public IncidenceMatrix() {
        matrix = new int[0][];
    }

    @Override
    public void loadFromIncidenceList(List<List<Integer>> input) throws InterruptedException {
        verticesSize = input.size();
        matrix = new int[verticesSize][];
        edgesSize = 0;
        for (List<Integer> integers : input) {
            escape();
            edgesSize += integers.size();
        }
        for (int i=0; i<verticesSize; i++)
        {
            escape();
            matrix[i] = new int[edgesSize];
        }
        int edgeNumber=0;
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).size(); j++) {
                escape();
                fillEdge(edgeNumber, i, input.get(i).get(j));
                edgeNumber++;
            }
        }
    }

    @Override
    public int[] getSuccessors(Integer id) throws InterruptedException {
        List<Integer> result = new ArrayList<>();
        for (int i=0; i<edgesSize;i++)
        {
            escape();
            if (checkIfSTART(getEdgeInner(id,i)))
            {
                for (int j = 0; j < verticesSize; j++)
                {
                    escape();
                    if (j == id) continue;
                    if (checkIfEND(getEdgeInner(j,i)))
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
    public int getFirstSuccessor(Integer id) throws InterruptedException {
        for (int i=0; i<edgesSize;i++)
        {
            escape();
            if (checkIfSTART(getEdgeInner(id,i)))
            {
                for (int j = 0; j < verticesSize; j++)
                {
                    escape();
                    if (j == id) continue;
                    if (checkIfEND(getEdgeInner(j,i)))
                    {
                        return j;
                    }
                }
            }
        }
        return -1;
    }

    @Override
    public int[] getPredecessors(Integer id) throws InterruptedException {
        List<Integer> result = new ArrayList<>();
        for (int i=0; i<edgesSize;i++)
        {
            escape();
            if (checkIfEND(getEdgeInner(id,i)))
            {
                for (int j = 0; j < verticesSize; j++)
                {
                    escape();
                    if (j == id) continue;
                    if (checkIfSTART(getEdgeInner(j,i)))
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
    public int getFirstPredecessor(Integer id) throws InterruptedException {
        for (int i=0; i<edgesSize;i++)
        {
            escape();
            if (checkIfEND(getEdgeInner(id,i)))
            {
                for (int j = 0; j < verticesSize; j++)
                {
                    escape();
                    if (j == id) continue;
                    if (checkIfSTART(getEdgeInner(j,i)))
                    {
                        return j;
                    }
                }
            }
        }
        return -1;
    }

    @Override
    public int[] getNonIncident(Integer id) throws InterruptedException {
        boolean[] vertices = new boolean[verticesSize];
        List<Integer> result = new ArrayList<>();
        for (int i=0; i<edgesSize;i++)
        {
            escape();
            if (getEdgeInner(id,i) != 0)
            {
                for (int j = 0; j < verticesSize; j++)
                {
                    escape();
                    if (j == id) continue;
                    if (getEdgeInner(j,i) != 0)
                    {
                        vertices[j]=true;
                        break;
                    }
                }
            }
        }
        for (int i=0;i<verticesSize;i++){
            escape();
            if (!vertices[i])
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
        return verticesSize * edgesSize * Integer.BYTES;
    }

    @Override
    public int getEdge(Integer id1, Integer id2) throws InterruptedException {
        for (int i=0; i<edgesSize;i++) {
            escape();
            if (getEdgeInner(id1, i) != 0) {
                //if start is at id2, id1 is successor
                if (checkIfSTART(getEdgeInner(id2, i)))
                    return -1;
                    //if end is id2, id1 is predecessor
                else if (checkIfEND(getEdgeInner(id2, i)))
                    return 1;
            }
        }
        return 0;
    }

    private int getEdgeInner(int index1, int index2) {
        operations+=1;
        return matrix[index1][index2];
    }

    @Override
    public int[] getAllVertices() throws InterruptedException {
        int[] vertices = new int[verticesSize];
        for (int i=0;i<verticesSize;i++){
            escape();
            vertices[i]=i;
        }
        return vertices;
    }

    @Override
    public int[][] getAllEdges() throws InterruptedException {
        int[][] result = new int[edgesSize][];
        for (int i=0; i<edgesSize; i++)
        {
            int start = -1;
            int end = -1;
            for (int j=0;j<verticesSize;j++)
            {
                escape();
                if (checkIfSTART(getEdgeInner(j,i)))
                {
                    start = j;
                    for (int k=j+1; k<verticesSize;k++)
                    {
                        escape();
                        if (getEdgeInner(k,i) != 0)
                        {
                            end = k;
                            break;
                        }
                    }
                    break;
                }
                else if (checkIfEND(getEdgeInner(j, i)))
                {
                    end = j;
                    for (int k=j+1; k<verticesSize;k++)
                    {
                        escape();
                        if (getEdgeInner(k,i) != 0)
                        {
                            start = k;
                            break;
                        }
                    }
                    break;
                }
            }
            result[i] = new int[] {start, end};
        }
        return result;
    }

    @Override
    public int getVerticesNumber() {
        return verticesSize;
    }

    @Override
    public int getEdgesNumber() {
        return edgesSize;
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
