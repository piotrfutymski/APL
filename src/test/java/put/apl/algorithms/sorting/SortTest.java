package put.apl.algorithms.sorting;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import put.apl.algorithms.sorting.data.*;
import put.apl.algorithms.sorting.implementation.*;

import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;


public class SortTest {
    static SortingData TEST_NUMBERS_RANDOM = new SortingData(new int[]{8, 6, 9, 5, 3, 7, 1, 4, 2});
    static SortingData TEST_NUMBERS_ASHAPE = new SortingData(new int[]{1,3,5,7,9,8,6,4,2});
    static SortingData TEST_NUMBERS_VSHAPE = new SortingData(new int[]{9,7,5,3,1,2,4,6,8});
    static SortingData TEST_NUMBERS_SORTED = new SortingData(new int[]{1,2,3,4,5,6,7,8,9});
    static SortingData TEST_NUMBERS_CONSTANT = new SortingData(new int[]{5,5,5,5,5,5,5,5,5});
    static SortingData SORTED_NUMBERS = new SortingData(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9});
  
    private SortingData numbersRandom = null;
    private SortingData numbersAShape = null;
    private SortingData numbersVShape = null;
    private SortingData numbersSorted = null;
    private SortingData numbersConstant = null;



    @BeforeEach
    void initAll() {
        numbersRandom = new SortingData(TEST_NUMBERS_RANDOM.getTab().clone());
        numbersAShape = new SortingData(TEST_NUMBERS_ASHAPE.getTab().clone());
        numbersVShape = new SortingData(TEST_NUMBERS_VSHAPE.getTab().clone());
        numbersSorted = new SortingData(TEST_NUMBERS_SORTED.getTab().clone());
        numbersConstant = new SortingData(TEST_NUMBERS_CONSTANT.getTab().clone());

    }

    void fullSortingTests(SortingAlgorithm sortingAlgorithm) throws InterruptedException {

        sortingAlgorithm.sort(numbersRandom);
        assertArrayEquals(numbersRandom.getTab(), SORTED_NUMBERS.getTab());

        sortingAlgorithm.sort(numbersAShape);
        assertArrayEquals(numbersAShape.getTab(), SORTED_NUMBERS.getTab());

        sortingAlgorithm.sort(numbersVShape);
        assertArrayEquals(numbersVShape.getTab(), SORTED_NUMBERS.getTab());

        sortingAlgorithm.sort(numbersSorted);
        assertArrayEquals(numbersSorted.getTab(), SORTED_NUMBERS.getTab());

        sortingAlgorithm.sort(numbersConstant);
        assertArrayEquals(numbersConstant.getTab(), TEST_NUMBERS_CONSTANT.getTab());
    }

    @Test
    void selectionSortTest() throws InterruptedException {

        SelectionSort selectionSort = new SelectionSort();
        fullSortingTests(selectionSort);
    }

    @Test
    void insertionSortTest() throws InterruptedException {
        InsertionSort insertionSort = new InsertionSort();
        fullSortingTests(insertionSort);
    }

    @Test
    void binaryInsertionSortTest() throws InterruptedException {
        BinaryInsertionSort binaryInsertionSort = new BinaryInsertionSort();
        fullSortingTests(binaryInsertionSort);
    }

    @Test
    void shellSortTest() throws InterruptedException {
        ShellSort shellSort = new ShellSort();
        fullSortingTests(shellSort);
    }

    @Test
    void shellSortKnuthBase2Test() throws InterruptedException {
        ShellSortKnuth shellSortKnuth = new ShellSortKnuth();
        Map<String,String> params = Map.of("k", "2");
        shellSortKnuth.setParams(params);
        fullSortingTests(shellSortKnuth);
    }

    @Test
    void shellSortKnuthBase3Test() throws InterruptedException {
        ShellSortKnuth shellSortKnuth = new ShellSortKnuth();
        Map<String,String> params = Map.of("k", "3");
        shellSortKnuth.setParams(params);
        fullSortingTests(shellSortKnuth);
    }

    @Test
    void shellSortKnuthException() throws InterruptedException {
        ShellSortKnuth shellSortKnuth = new ShellSortKnuth();
        try{
            fullSortingTests(shellSortKnuth);
            fail();
        }catch (IllegalStateException e){

        }
    }

    @Test
    void mergeSortTest() throws InterruptedException {
        MergeSort mergeSort = new MergeSort();
        fullSortingTests(mergeSort);
    }

    @Test
    void heapSortTest() throws InterruptedException {
        HeapSort heapSort = new HeapSort();
        fullSortingTests(heapSort);
    }

    @Test
    void countingSortTest() throws InterruptedException {
        CountingSort countingSort = new CountingSort();
        Map<String,String> params = Map.of("maxValue", "9");
        countingSort.setParams(params);
        fullSortingTests(countingSort);
    }

    @Test
    void bubbleSortTest() throws InterruptedException {
        BubbleSort bubbleSort = new BubbleSort();
        fullSortingTests(bubbleSort);
    }
    @Test
    void bubbleSortAwareTest() throws InterruptedException {
        BubbleSortAware bubbleSortAware = new BubbleSortAware();
        fullSortingTests(bubbleSortAware);
    }
    @Test
    void bubbleSortSuperAwareTest() throws InterruptedException {
        BubbleSortVeryAware bubbleSortVeryAware = new BubbleSortVeryAware();
        fullSortingTests(bubbleSortVeryAware);
    }
    @Test
    void shakeSortTest() throws InterruptedException {
        ShakeSort shakeSort = new ShakeSort();
        fullSortingTests(shakeSort);
    }
    @Test
    void bucketSortTest() throws InterruptedException {
        BucketSort bucketSort = new BucketSort();
        fullSortingTests(bucketSort);
    }
    @Test
    void radixSortTest() throws InterruptedException {
        RadixSort radixSort = new RadixSort();
        fullSortingTests(radixSort);
    }

    @Test
    void quickSortFirstTest() throws InterruptedException {
        QuickSort quickSort = new QuickSort();
        Map<String,String> params = Map.of("pivotStrategy", "First item");
        quickSort.setParams(params);
        fullSortingTests(quickSort);
    }
    @Test
    void quickSortMidTest() throws InterruptedException {
        QuickSort quickSort = new QuickSort();
        Map<String,String> params = Map.of("pivotStrategy", "Middle item");
        quickSort.setParams(params);
        fullSortingTests(quickSort);
    }
    @Test
    void quickSortLastTest() throws InterruptedException {
        QuickSort quickSort = new QuickSort();
        Map<String,String> params = Map.of("pivotStrategy", "Last item");
        quickSort.setParams(params);
        fullSortingTests(quickSort);
    }
    @Test
    void quickSortRandTest() throws InterruptedException {
        QuickSort quickSort = new QuickSort();
        Map<String,String> params = Map.of("pivotStrategy", "Random item");
        quickSort.setParams(params);
        fullSortingTests(quickSort);
    }
    @Test
    void quickSortMedOfThreeTest() throws InterruptedException {
        QuickSort quickSort = new QuickSort();
        Map<String,String> params = Map.of("pivotStrategy", "Median of fixed three");
        quickSort.setParams(params);
        fullSortingTests(quickSort);
    }
    @Test
    void quickSortMedianTest() throws InterruptedException {
        QuickSort quickSort = new QuickSort();
        Map<String, String> params = Map.of("pivotStrategy", "Median", "medianCount", "5");
        quickSort.setParams(params);
        fullSortingTests(quickSort);
    }
}
