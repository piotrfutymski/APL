package put.apl.Algorithms.Sorting.Implementation;

import put.apl.Algorithms.Sorting.Data.SortingData;
import put.apl.Algorithms.Sorting.SortingResult;

import java.util.Map;

/**
 * Interface for all sorting algorithms in APL
 * If algorithm depends on params (for example pivot in quick sort) it should be configurable by constructor
 *
 * @author  Piotr Futymski
 */
public interface SortingAlgorithm {

    SortingResult sort(SortingData tab);

    void setParams(Map<String, String> params);

}
