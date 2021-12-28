package put.apl.algorithms.graphs.data.generator;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeneratorResult {
    //Result
    protected List<List<Integer>> representation;
    protected List<List<Integer>> weights;
}