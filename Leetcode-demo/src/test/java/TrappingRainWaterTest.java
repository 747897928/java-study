import com.aquarius.wizard.leetcode.shl.TrappingRainWater;
import org.junit.Assert;
import org.junit.Test;

public class TrappingRainWaterTest {

    @Test
    public void shouldReturnClassicExampleAnswer() {
        TrappingRainWater solver = new TrappingRainWater();
        int[] height = {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};

        Assert.assertEquals(6, solver.trap(height));
    }

    @Test
    public void allFiveMethodsShouldProduceTheSameAnswer() {
        TrappingRainWater solver = new TrappingRainWater();
        int[] height = {4, 2, 0, 3, 2, 5};

        int byRows = solver.trapByRows(height);
        int byColumns = solver.trapByColumns(height);
        int byPrefixSuffix = solver.trapByPrefixSuffixMax(height);
        int byTwoPointers = solver.trap(height);
        int byStack = solver.trapByMonotonicStack(height);

        Assert.assertEquals(9, byTwoPointers);
        Assert.assertEquals(byRows, byColumns);
        Assert.assertEquals(byRows, byPrefixSuffix);
        Assert.assertEquals(byRows, byTwoPointers);
        Assert.assertEquals(byRows, byStack);
    }

    @Test
    public void shouldReturnZeroWhenNoWaterCanBeTrapped() {
        TrappingRainWater solver = new TrappingRainWater();

        Assert.assertEquals(0, solver.trap(new int[] {1}));
        Assert.assertEquals(0, solver.trap(new int[] {1, 2, 3}));
        Assert.assertEquals(0, solver.trap(new int[] {3, 2, 1}));
    }
}
