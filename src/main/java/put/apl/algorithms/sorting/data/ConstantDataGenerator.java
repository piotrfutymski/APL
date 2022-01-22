package put.apl.algorithms.sorting.data;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Random;

@Component("Constant Data")
public class ConstantDataGenerator implements  SortingDataGenerator{
    @Override
    public SortingData generate(DataGeneratorConfig config) {
        int[] tab = new int[config.getN()];
        Arrays.fill(tab, new Random().nextInt(config.getMaxValue() + 1));

        return new SortingData(tab);
    }
    @Override
    public String getDescription() {
        return null;
    }
}
