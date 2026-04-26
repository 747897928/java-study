import com.aquarius.wizard.leetcode.shl.MinimumWindowSubstring;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class MinimumWindowSubstringTest {

    @Test
    public void shouldReturnClassicAnswer() {
        MinimumWindowSubstring solver = new MinimumWindowSubstring();
        Assert.assertEquals("BANC", solver.minWindow("ADOBECODEBANC", "ABC"));
    }

    @Test
    public void shouldReturnSameCharacterWhenExactMatchExists() {
        MinimumWindowSubstring solver = new MinimumWindowSubstring();
        Assert.assertEquals("a", solver.minWindow("a", "a"));
    }

    @Test
    public void shouldReturnEmptyStringWhenNoCoverExists() {
        MinimumWindowSubstring solver = new MinimumWindowSubstring();
        Assert.assertEquals("", solver.minWindow("a", "aa"));
    }

    @Test
    public void bruteForceVersionShouldMatchExpectedAnswersWithoutDebugOutput() {
        MinimumWindowSubstring solver = new MinimumWindowSubstring();
        PrintStream originalOut = System.out;
        ByteArrayOutputStream capturedOut = new ByteArrayOutputStream();

        try {
            System.setOut(new PrintStream(capturedOut));

            Assert.assertEquals("BANC", solver.minWindow3("ADOBECODEBANC", "ABC"));
            Assert.assertEquals("a", solver.minWindow3("a", "a"));
            Assert.assertEquals("", solver.minWindow3("a", "aa"));
            Assert.assertEquals("aa", solver.minWindow3("aa", "aa"));
        } finally {
            System.setOut(originalOut);
        }

        Assert.assertEquals("", capturedOut.toString());
    }

    @Test
    public void beginnerFriendlyOptimizedVersionShouldMatchExpectedAnswers() {
        MinimumWindowSubstring solver = new MinimumWindowSubstring();

        Assert.assertEquals("BANC", solver.minWindow2Optimized("ADOBECODEBANC", "ABC"));
        Assert.assertEquals("a", solver.minWindow2Optimized("a", "a"));
        Assert.assertEquals("", solver.minWindow2Optimized("a", "aa"));
        Assert.assertEquals("aa", solver.minWindow2Optimized("aa", "aa"));
        Assert.assertEquals("AABC", solver.minWindow2Optimized("XAABCD", "AABC"));
    }

    @Test
    public void beginnerFriendlyOptimizedVersionShouldHandleMoreEdgeCases() {
        MinimumWindowSubstring solver = new MinimumWindowSubstring();

        Assert.assertEquals("ABC", solver.minWindow2Optimized("ABC", "ABC"));
        Assert.assertEquals("ABC", solver.minWindow2Optimized("ZZZABCZZZ", "ABC"));
        Assert.assertEquals("ABBC", solver.minWindow2Optimized("ZZZABBCAZZ", "ABBC"));
        Assert.assertEquals("AAYB", solver.minWindow2Optimized("XXAAYBB", "AAB"));
    }
}
