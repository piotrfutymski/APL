package put.apl.Algorithms.Sorting.Data;

import java.util.Random;

public class AShapeDataGenerator implements  SortingDataGenerator{
    @Override
    public SortingData generate(DataGeneratorConfig config) {
        int[] tab = new Random()
                .ints(0, config.getMaxValue() + 1)
                .limit(config.getN())
                .toArray();

        return new SortingData(tab);
    }
}
