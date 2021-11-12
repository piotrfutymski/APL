package put.apl.experiment.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GarbageCollectorFighter {

    public Double getTime(List<Double> times) {
        int len = (times.size()*7)/10;
        for (int i = 0; i < len; i++) {
            Double mean = mean(times);
            int index = 0;
            double value = 0;
            for (int j = 0; j < times.size(); j++) {
                double tmp = (mean - times.get(j)) * (mean - times.get(j));
                if(tmp > value){
                    value = tmp;
                    index = j;
                }
            }
            times.remove(index);
        }
        return mean(times);
    }

    private Double mean(List<Double> data) {
        return data.stream().collect(Collectors.averagingDouble(e->e));
    }


}
