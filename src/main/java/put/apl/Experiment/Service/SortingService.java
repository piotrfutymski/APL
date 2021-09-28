package put.apl.Experiment.Service;

import org.springframework.stereotype.Service;
import put.apl.Algorithms.Sorting.SortingResult;
import put.apl.Experiment.Dto.SortingExperiment;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SortingService {

    public List<Object> runExperiments(String id, List<SortingExperiment> experiments) {
        //TODO uruchamianie odpowiedniego eksperymentu - poniżej kod czeka 5 s w celach testowych
        List<Object> res = experiments.stream()
                .map(experiment->{
                    try {
                        Thread.sleep(7000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    experiment.setTimeInNano(70000000000L);
                    return (Object)experiment;
                }).collect(Collectors.toList());
        return res;
    }



}
