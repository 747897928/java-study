import com.aquarius.wizard.leetcode.shl.FrequencyDescendingStableSort;
import org.junit.Assert;
import org.junit.Test;

public class FrequencyDescendingStableSortTest {

    @Test
    public void manualComparatorVersionShouldMatchChainVersion() {
        FrequencyDescendingStableSort solver = new FrequencyDescendingStableSort();
        int[] nums = {1, 2, 2, 3, 3, 3, 4, 4, 5, 5, 5, 5, 6, 6, 6, 7, 8, 9, 10};

        int[] expected = solver.sortByFrequency(nums);
        int[] actual = solver.sortByFrequencyManualComparator(nums);

        Assert.assertArrayEquals(expected, actual);
    }
}
