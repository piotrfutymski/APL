package put.apl.algorithms.graphs.data;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Edge {
    Integer predecessor;
    Integer successor;
    Integer toPredecessorWeight;
    Integer toSuccessorWeight;
}
