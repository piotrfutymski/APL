package put.apl.Experiment.Dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExperimentsResults {

    public enum ExperimentStatus{
        QUEUED, CALCULATING, DONE, REMOVED
    }

    private ExperimentStatus status;
    private List<Object> results;
    private Integer queuePosition;
}
