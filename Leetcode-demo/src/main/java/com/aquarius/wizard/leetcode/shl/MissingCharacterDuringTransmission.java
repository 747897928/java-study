package com.aquarius.wizard.leetcode.shl;

import java.util.Scanner;


/**
 * Question
 *
 * A company provides network encryption for secure data transfer. The data string is encrypted
 * prior to transmission and gets decrypted at the receiving end. But due to some technical error,
 * the encrypted data is lost and the received string is different from the original string by 1
 * character. Arnold, a network administrator, is tasked with finding the character that got lost
 * in the network so that the bug does not harm other data that is being transferred through the
 * network.
 *
 * Write an algorithm to help Arnold find the character that was missing at the receiving end but
 * present at the sending end.
 *
 * Input
 *
 * The first line of the input consists of a string - stringSent, representing the string that was
 * sent through the network.
 * The next line consists of a string - stringRec, representing the string that was received at the
 * receiving end of the network.
 *
 * Output
 *
 * Print a character representing the character that was lost in the network during transmission.
 *
 * Note
 *
 * The input strings stringSent and stringRec consist of lowercase and uppercase English alphabets
 * (i.e. a-z, A-Z).
 *
 * Example
 *
 * Input:
 * abcdfigerj
 * abcdfiger
 *
 * Output:
 * j
 *
 * Explanation:
 * The character j at the end of the sent string was lost in the network during transmission.
 *
 * 备注
 *
 * 难度：简单。
 *
 * 考点：异或、字符计数。
 * 校对：样例已做代码校验。
 * 做法：把两个字符串所有字符异或起来，剩下的就是丢失字符。
 */
public class MissingCharacterDuringTransmission {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String sentString = scanner.nextLine();
        String receivedString = scanner.nextLine();

        /*
         * 本地自测时直接打开这一段，改上面的 Scanner 就行。
         *
         * String sentString = "abcdfigerj";
         * String receivedString = "abcdfiger";
         */

        MissingCharacterDuringTransmission solver = new MissingCharacterDuringTransmission();
        System.out.println(solver.findMissingCharacter(sentString, receivedString));
    }

    public char findMissingCharacter(String sent, String received) {
        int xor = 0;
        for (int i = 0; i < sent.length(); i++) {
            xor ^= sent.charAt(i);
        }
        for (int i = 0; i < received.length(); i++) {
            xor ^= received.charAt(i);
        }
        return (char) xor;
    }
}
