package put.apl.Algorithms.Sorting.Implementation;

/**
 * Interface for all sorting algorithms in APL
 * If algorithm depends on params (for example pivot in quick sort) it should be configurable by constructor
 *
 * @author  Piotr Futymski
 */
public interface SortingAlgorithm {

    public void sort(int[] tab);
}
