package put.apl.algorithms.graphs.data;


import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("Weighted Incidence Matrix Directed")
public class IncidenceMatrixDirectedWeighted extends IncidenceMatrix implements GraphRepresentationWeightedInterface {

    public IncidenceMatrixDirectedWeighted(List<List<Integer>> input, List<List<Integer>> weights) throws InterruptedException  {
        if (weights == null)
            loadFromIncidenceList(input);
        else
            loadFromIncidenceList(input, weights);
    }

    public IncidenceMatrixDirectedWeighted(int[][] matrix) {
        super(matrix);
    }

    public IncidenceMatrixDirectedWeighted() {
        super();
    }

    @Override
    public void loadFromIncidenceList(List<List<Integer>> input) throws InterruptedException {
        var weights = new ArrayList<List<Integer>>(input.size());
        for (var input_vertex : input)
        {
            escape();
            var weights_vertex = new ArrayList<Integer>(input_vertex.size());
            for (var input_edge : input_vertex)
                weights_vertex.add(1);
            weights.add(weights_vertex);
        }
        loadFromIncidenceList(input, weights);
    }

    public void loadFromIncidenceList(List<List<Integer>> input, List<List<Integer>> weights) throws InterruptedException {
        verticesSize = input.size();
        matrix = new int[verticesSize][];
        edgesSize = 0;
        for (List<Integer> integers : input) {
            escape();
            edgesSize += integers.size();
        }
        for (int i=0; i< verticesSize; i++)
        {
            escape();
            matrix[i] = new int[edgesSize];
        }
        int edgeNumber=0;
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).size(); j++) {
                escape();
                matrix[i][edgeNumber] = weights.get(i).get(j);
                matrix[input.get(i).get(j)][edgeNumber] = -1 * weights.get(i).get(j);
                edgeNumber++;
            }
        }
    }

    @Override
    public Edge[] getSuccessorsWeighted(int vertex) throws InterruptedException {
        List<Edge> result = new ArrayList<>();
        for (int i=0; i<edgesSize;i++)
        {
            if (checkIfSTART(vertex,i))
            {
                for (int j = 0; j < verticesSize; j++)
                {
                    escape();
                    if (j == vertex) continue;
                    if (checkIfEND(j,i))
                    {
                        result.add(new Edge(j, getEdgeInner(vertex, i)));
                        break;
                    }
                }
            }
        }
        return result.toArray(new Edge[0]);
    }

    @Override
    public int[][] getAllEdges() {
        int[][] result = new int[edgesSize][];
        for (int i=0; i<edgesSize; i++)
        {
            int start = -1;
            int end = -1;
            int weight = -1;
            for (int j=0;j<verticesSize;j++)
            {
                if (checkIfSTART(j,i))
                {
                    start = j;
                    weight = getEdgeInner(j,i);
                    for (int k=j+1; k<verticesSize;k++)
                    {
                        if (getEdgeInner(k,i) != 0)
                        {
                            end = k;
                            break;
                        }
                    }
                    break;
                }
                else if (checkIfEND(j, i))
                {
                    end = j;
                    for (int k=j+1; k<verticesSize;k++)
                    {
                        if (getEdgeInner(k,i) != 0)
                        {
                            weight = getEdgeInner(k,i);
                            start = k;
                            break;
                        }
                    }
                    break;
                }
            }
            result[i] = new int[] {start, end, weight};
        }
        return result;
    }

    @Override
    public void fillEdge(int edge, int start, int end) {
        //not used
    }

    @Override
    public boolean checkIfSTART(int index1, int index2) {
        return getEdgeInner(index1, index2) > 0;
    }

    @Override
    public boolean checkIfEND(int index1, int index2) {
        return getEdgeInner(index1, index2) < 0;
    }


    @Override
    public GraphRepresentationInterface clone() {
        return new IncidenceMatrixDirectedWeighted(matrix.clone());
    }
}
