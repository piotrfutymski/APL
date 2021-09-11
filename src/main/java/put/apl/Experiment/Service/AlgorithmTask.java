package put.apl.Experiment.Service;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlgorithmTask implements Runnable{

    String id;
    Runnable f;

    @Override
    public void run() {
        f.run();
    }
}
