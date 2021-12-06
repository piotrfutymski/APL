package put.apl.algorithms.graphs.data;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GraphGeneratorConfig {
    private Integer noOfVertices;
    private Double density;
    private String type;
}
