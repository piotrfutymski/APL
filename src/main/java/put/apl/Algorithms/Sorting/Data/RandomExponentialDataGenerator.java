package put.apl.Algorithms.Sorting.Data;

import java.util.List;
import java.util.Random;

public class RandomExponentialDataGenerator implements SortingDataGenerator {
    @Override
    public SortingData generate(DataGeneratorConfig config) {
        int[] tab = new int[config.getN()];
        /* This lambda value means that the E(X) is equal to 1/10 of max value and variance is 1/(lambda^2)
           so that >99% of values do not need to be clamped
        */
        double lambda = 1 / ((double) config.getMaxValue() / 10.0);
        double[] domain = new Random()
                .doubles(0.0, 1.0)
                .limit(config.getN())
                .toArray();
        for (int i = 0; i < config.getN(); i++) {
            tab[i] = (int) (Math.log(1 - domain[i]) / (-lambda));
            while (tab[i] > config.getMaxValue()) {
                tab[i] = (int) (Math.log(1 - new Random().nextDouble()) / (-lambda));
            }
        }
        return new SortingData(tab);
    }
}
