package put.apl.Algorithms.Graphs.Data;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Edge {
    Integer predecessor;
    Integer successor;
}
