package put.apl.Algorithms.Sorting;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SortingResult {

    protected Integer comparisonCount;
    protected Integer swapCount;
    protected Integer recursionSize;
}
