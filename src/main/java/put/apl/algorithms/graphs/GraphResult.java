package put.apl.algorithms.graphs;
import lombok.*;
import put.apl.algorithms.graphs.data.GraphRepresentation;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GraphResult {
    //Result
    private Integer memoryOccupancyInBytes;
    private Integer acyclicCount;
    private Integer hamiltonCyclesCount;
    private Integer tableAccessCount;
    protected GraphRepresentation minimumSpanningTree;
    protected List<Integer> path;
    protected List<ArrayList<Integer>> multiplePaths;
}
