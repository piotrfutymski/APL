package put.apl.algorithms.sorting.data;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component("Random Gaussian Data")
public class RandomGaussianDataGenerator implements SortingDataGenerator {
    @Override
    public SortingData generate(DataGeneratorConfig config) {
        int[] tab = new int[config.getN()];
        // Mean of Gaussian distribution is set to half of max value
        int mean = config.getMaxValue() / 2;
        /* Variance of Gaussian distribution is set to 1/8 of max value
           so that at least 99.9% of generated values will not need to be clamped
        */
        int variance = (int) ((double) (config.getMaxValue()) * (0.125));
        Random random = new Random();
        for(int i = 0; i < config.getN(); i++) {
            tab[i] = (int) (random.nextGaussian() * variance + mean);
            while (tab[i] < 0 || tab[i] > config.getMaxValue()) {
                tab[i] = (int) (random.nextGaussian() * variance + mean);
            }
        }
        return new SortingData(tab);
    }

    @Override
    public String getDescription() {
        return null;
    }
}
