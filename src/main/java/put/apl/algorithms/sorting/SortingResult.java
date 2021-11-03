package put.apl.algorithms.sorting;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SortingResult {

    protected Long comparisonCount;
    protected Long swapCount;
    protected Integer recursionSize;
}
