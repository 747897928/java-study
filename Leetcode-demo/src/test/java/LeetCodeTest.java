import com.aquarius.wizard.leetcode.sort.SelectionSort;
import com.aquarius.wizard.leetcode.sort.SortBase;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author zhaoyijie
 * @since 2024/5/25 13:48
 */
public class LeetCodeTest {

    @Test
    public void test1() {
        int[] arr = new int[]{9, 6, 3, 4, 5, 2, 7, 8, 1};
        SortBase sort = new SelectionSort();
        int[] ints = sort.sort(arr);
        System.out.println(Arrays.toString(ints));
    }
}
