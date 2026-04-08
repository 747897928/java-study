package com.aquarius.wizard.leetcode.shl.automata;

import java.util.Scanner;

/**
 * Question
 *
 * Automata Pro Question Bank(1).docx
 * Question 32
 *
 * An agent sends a secret message to headquarters containing the details of his project. He sends
 * one soft copy to the agency's computer (P) and sends one hard copy by fax to Roger, the
 * technical head of the agency (Q). But during the transmission, noise in the network causes some
 * bits of the data message P to get distorted. However, we know that Roger always matches the
 * binary values of both messages and checks whether he can convert the message P to message Q by
 * flipping the minimum number of bits.
 *
 * Write an algorithm to help Roger find the minimum number of bits that must be flipped to convert
 * message P to message Q.
 *
 * 补充说明
 *
 * docx 里只保留了题干，没有给出特别标准的输入模板。
 * 这份代码里我先约定下面这种输入格式：
 * 1. binary message P
 * 2. binary message Q
 *
 * <p>create: 2026-04-01 23:10:02</p>
 * @author zhaoyijie(AquariusGenius)
 */
public class Q32MinimumBitFlipsBetweenMessages {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String messageP = scanner.next();
        String messageQ = scanner.next();

        /*
         * 本地自测输入：
         *
         * String messageP = "101001";
         * String messageQ = "100111";
         */

        Q32MinimumBitFlipsBetweenMessages solver = new Q32MinimumBitFlipsBetweenMessages();
        System.out.println(solver.minimumBitFlips(messageP, messageQ));
    }

    public int minimumBitFlips(String messageP, String messageQ) {
        if (messageP.length() != messageQ.length()) {
            throw new IllegalArgumentException("P and Q must have the same length.");
        }
        int flips = 0;
        for (int i = 0; i < messageP.length(); i++) {
            if (messageP.charAt(i) != messageQ.charAt(i)) {
                flips++;
            }
        }
        return flips;
    }
}
