package com.aquarius.wizard.leetcode;

/**
 * <p>description:  </p>
 * <p>create:  2020/11/15 10:51</p>
 * @author: zhaoyijie(AquariusGenius)
 */
public class EffectiveBrackets {
    public static void main(String[] args) {

    }
    /**
     * 20.有效的括号
     * 示例1
     * 输入:"( )"输出: true
     * 给定一个只包括《'，)"，"T'，7，['，丁的字符串，判断字符串
     * 是否有效。
     * 示例2:
     * 输入:"(]"
     * 有效字符串需满足:
     * 输出:false
     * 1.左括号必须用相同类型的右括号闭合。
     * 2.左括号必须以正确的顺序闭合。
     * *注意空字符串可被认为是有效字符串。
     *
     * 思路：可以用栈来实现，将遇到的左括号压栈，遇到一个右括号就将栈顶的左括号做对比，
     * 如果匹配就将栈顶的左括号弹出，最后栈没有元素就说明是有效的括号
     */
}
