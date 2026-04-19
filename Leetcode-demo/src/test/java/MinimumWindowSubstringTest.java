import com.aquarius.wizard.leetcode.shl.MinimumWindowSubstring;
import org.junit.Assert;
import org.junit.Test;

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
}
