package put.apl.Algorithms.Sorting.Data;

public interface SortingDataGenerator {

    SortingData generate(DataGeneratorConfig config) throws InterruptedException;

    String getDescription();
}
