package put.apl.Algorithms.Sorting.Data;

import java.util.Random;

public class RandomDataGenerator implements  SortingDataGenerator{
    @Override
    public int[] generate(DataGeneratorConfig config) {
        int[] res = new Random()
                .ints(0, config.getMaxValue()+1)
                .limit(config.getN())
                .toArray();
        return res;
    }
}
