package put.apl.experiment.dto;

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

    public static int DEFAULT_TIMEOUT_MS = 60000;
    public static int INFINITE_TIMEOUT = -1;

    Future<List<Object>> future;
    Date start;
    Date lastCallForResult;
    Boolean expired;
    Integer timeout;
    Long jobNumber;
    Boolean finite;
}
