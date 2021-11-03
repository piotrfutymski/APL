package put.apl.Algorithms.Sorting.Data;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component("Random Data")
public class RandomDataGenerator implements  SortingDataGenerator{
    @Override
    public SortingData generate(DataGeneratorConfig config) {
        int[] tab = new Random()
                .ints(0, config.getMaxValue() + 1)
                .limit(config.getN())
                .toArray();

        return new SortingData(tab);
    }

    @Override
    public String getDescription() {
        return null;
    }
}
