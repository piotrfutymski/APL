package put.apl.algorithms.graphs.data.generator;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GraphGeneratorConfig {
    private Integer numberOfVertices;
    private Double density;
    private String type;
}
