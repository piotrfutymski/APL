package put.apl.Experiment.Dto;

import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlgorithmFuture {
    Future<List<Object>> future;
    Date start;
    Date lastCallForResult;
    Long position;
}
