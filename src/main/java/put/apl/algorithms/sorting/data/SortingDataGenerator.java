package put.apl.algorithms.sorting.data;

public interface SortingDataGenerator {

    SortingData generate(DataGeneratorConfig config) throws InterruptedException;

    String getDescription();
}
