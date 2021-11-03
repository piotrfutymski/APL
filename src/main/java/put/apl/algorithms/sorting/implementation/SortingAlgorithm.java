package put.apl.algorithms.sorting.implementation;

import put.apl.algorithms.sorting.data.SortingData;
import put.apl.algorithms.sorting.SortingResult;

import java.util.Map;

/**
 * Interface for all sorting algorithms in APL
 * If algorithm depends on params (for example pivot in quick sort) it should be configurable by constructor
 *
 * @author  Piotr Futymski
 */
public interface SortingAlgorithm {

    SortingResult sort(SortingData tab) throws InterruptedException;

    void setParams(Map<String, String> params);

}
