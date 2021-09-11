package put.apl.Experiment.Service;

import org.springframework.stereotype.Service;
import put.apl.Experiment.Dto.SortingExperiment;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SortingService implements AlgorithmService {

    private Map<String, List<SortingExperiment>> results;

    public void runExperiments(String id, List<SortingExperiment> experiments) {

    }

    @Override
    public List<Object> getResults(String id) {
            return results.get(id).stream()
                    .map(e->(Object)e)
                    .collect(Collectors.toList());
    }
}
