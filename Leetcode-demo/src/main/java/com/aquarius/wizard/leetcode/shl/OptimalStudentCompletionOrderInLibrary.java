package com.aquarius.wizard.leetcode.shl;

import java.util.Scanner;
import java.util.Arrays;

/**
 * Question
 *
 * Stephen runs a small library with N number of students as its members. All members have their
 * unique studentID. The library has the certain number of books on M different subjects. Each
 * student is given an individual assignment to complete by taking help from different books as per
 * their requirement. The library has already issued some books to its members prior to this. The
 * students can still issue required number of books from the library to complete their respective
 * assignments. Each student submits the book issued to the library after completing their
 * assignment. Only when the books have been submitted to the library can another student issue that
 * book. Also, while assigning books, Stephen starts assigning books to the student with the
 * smallest studentID and proceed to the student with the higher studentID. Once he reaches to the
 * student with the largest studentID then again goes back to the smallest studentID to whom the
 * book was not assigned and follow the same process.
 *
 * Stephen wants to find the sequence of studentIDs in which the students optimally complete their
 * assignments.
 *
 * Write an algorithm to help Stephen find the sequence of studentIDs in which the students
 * optimally complete their assignments. If all students can't complete their assignment, output a
 * list of length 1 with content -1.
 *
 * Input
 *
 * The first line of the input consists of an integer booksNum, representing the number of
 * different subjects (M).
 * The second line consists of M space-separated integers avail[0], avail[1] ... avail[M-1],
 * representing the books in the library that have not been issued to any student.
 * The third line consists of two space-separated integers studentNum and reqBooks, representing the
 * number of students (N) and number of different books required by each student (it is always
 * equal to the number of different subjects M, respectively).
 * The next N lines consist of M space-separated integers representing the books required by the
 * students.
 * The next line consists of two space-separated integers studentsIssuedBooks and issuedBooks,
 * representing the number of students with books issued (it is always equal to number of students
 * N) and number of different books issued to each student (it is always equal to the number of
 * different subjects M, respectively).
 * The next N lines consist of M space-separated integers representing the books already issued to
 * the students.
 *
 * Output
 *
 * Print space-separated integers representing the sequence of studentIDs that is optimal for the
 * students to complete their assignments. If it is not possible for all students to complete their
 * assignments, output a list of length 1 with content -1.
 *
 * Constraints
 *
 * 1 <= booksNum, reqBooks, issuedBooks <= 100
 * 1 <= studentNum <= 100
 * studentNum = studentsIssuedBooks
 *
 * Example
 *
 * Input:
 * 3
 * 2 2 3
 * 3 3
 * 2 4 0
 * 0 0 1
 * 0 1 3
 * 3 3
 * 3 5 4
 * 1 3 4
 * 2 3 5
 *
 * Output:
 * 2 0 1
 *
 * Explanation:
 * The available books are [2, 2, 3].
 * Student 2 can finish first because the remaining needed books [2, 2, 2] can be satisfied by the
 * current stock. After completion, student 2 returns [0, 1, 3]. Then students 0 and 1 can finish,
 * and the smallest feasible ID is always chosen first, so the order is [2, 0, 1].
 *
 * 备注
 *
 * 难度：困难。
 *
 * 考点：资源可行性模拟，和银行家算法很像。
 * 校对：原 docx 的文字版把“required books”和“already issued books”两个矩阵顺序写反了；示例和解释都只能按“先 issued，后 required”才能自洽。
 * 提示：真正决定能否完成的是 need = required - issued，而不是 required 本身。
 */
public class OptimalStudentCompletionOrderInLibrary {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int subjectCount = scanner.nextInt();
        int[] availableBooks = new int[subjectCount];
        for (int i = 0; i < subjectCount; i++) {
            availableBooks[i] = scanner.nextInt();
        }
        int studentCount = scanner.nextInt();
        int booksPerStudent = scanner.nextInt();
        int[][] issuedBooks = new int[studentCount][booksPerStudent];
        for (int i = 0; i < studentCount; i++) {
            for (int j = 0; j < booksPerStudent; j++) {
                issuedBooks[i][j] = scanner.nextInt();
            }
        }
        int studentsIssuedBooks = scanner.nextInt();
        int issuedBookTypeCount = scanner.nextInt();
        int[][] requiredBooks = new int[studentsIssuedBooks][issuedBookTypeCount];
        for (int i = 0; i < studentsIssuedBooks; i++) {
            for (int j = 0; j < issuedBookTypeCount; j++) {
                requiredBooks[i][j] = scanner.nextInt();
            }
        }

        /*
         * 本地自测时直接打开这一段，改上面的 Scanner 就行。
         *
         * int subjectCount = 3;
         * int[] availableBooks = {2, 2, 3};
         * int studentCount = 3;
         * int booksPerStudent = 3;
         * int[][] issuedBooks = {{2, 4, 0}, {0, 0, 1}, {0, 1, 3}};
         * int studentsIssuedBooks = 3;
         * int issuedBookTypeCount = 3;
         * int[][] requiredBooks = {{3, 5, 4}, {1, 3, 4}, {2, 3, 5}};
         */

        OptimalStudentCompletionOrderInLibrary solver = new OptimalStudentCompletionOrderInLibrary();
        System.out.println(format(solver.findOptimalOrder(availableBooks, issuedBooks, requiredBooks)));
    }

    private static String format(int[] nums) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < nums.length; i++) {
            if (i > 0) {
                builder.append(' ');
            }
            builder.append(nums[i]);
        }
        return builder.toString();
    }

    public int[] findOptimalOrder(int[] availableBooks, int[][] issuedBooks, int[][] requiredBooks) {
        int studentCount = issuedBooks.length;
        int subjectCount = availableBooks.length;
        int[] available = Arrays.copyOf(availableBooks, subjectCount);
        int[] order = new int[studentCount];
        boolean[] finished = new boolean[studentCount];
        int completed = 0;

        while (completed < studentCount) {
            boolean progressed = false;
            for (int student = 0; student < studentCount; student++) {
                if (finished[student] || !canFinish(available, issuedBooks[student], requiredBooks[student])) {
                    continue;
                }
                finished[student] = true;
                order[completed++] = student;
                progressed = true;
                for (int subject = 0; subject < subjectCount; subject++) {
                    available[subject] += issuedBooks[student][subject];
                }
            }
            if (!progressed) {
                return new int[]{-1};
            }
        }
        return order;
    }

    public int[] findOptimalOrderUsingPromptBlockOrder(int[] availableBooks, int[][] promptRequiredBooks,
                                                       int[][] promptIssuedBooks) {
        return findOptimalOrder(availableBooks, promptIssuedBooks, promptRequiredBooks);
    }

    private boolean canFinish(int[] available, int[] issued, int[] required) {
        for (int subject = 0; subject < available.length; subject++) {
            int needed = required[subject] - issued[subject];
            if (needed > available[subject]) {
                return false;
            }
        }
        return true;
    }
}
