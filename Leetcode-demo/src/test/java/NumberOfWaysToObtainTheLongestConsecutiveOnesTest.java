import com.aquarius.wizard.leetcode.shl.NumberOfWaysToObtainTheLongestConsecutiveOnes;
import org.junit.Assert;
import org.junit.Test;

public class NumberOfWaysToObtainTheLongestConsecutiveOnesTest {

    @Test
    public void bruteForceMaxLengthShouldMatchSlidingWindowVersion() {
        NumberOfWaysToObtainTheLongestConsecutiveOnes solver =
            new NumberOfWaysToObtainTheLongestConsecutiveOnes();

        Assert.assertEquals(3, solver.findMaximumLengthBruteForce("1010101", 1));
        Assert.assertEquals(
            solver.findMaximumLengthBruteForce("110001011", 2),
            solver.findMaximumLengthForLearning("110001011", 2)
        );
    }

    @Test
    public void bruteForceCountWaysShouldMatchOptimizedVersion() {
        NumberOfWaysToObtainTheLongestConsecutiveOnes solver =
            new NumberOfWaysToObtainTheLongestConsecutiveOnes();

        Assert.assertEquals(3, solver.countWaysBruteForce("1010101", 1));
        Assert.assertEquals(
            solver.countWaysBruteForce("110001011", 2),
            solver.countWays("110001011", 2)
        );
    }
}
