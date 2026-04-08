package com.aquarius.wizard.leetcode.shl.automata;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

/**
 * Question
 *
 * Automata Pro Question Bank(1).docx
 * Question 86
 *
 * A pizza shop makes serves both vegan pizza and meat pizza. Customers place N orders at the
 * shop. Their order number is printed on their bill. The shop displays K out of N orders (both
 * vegan and meat) on their display screen at any given time. The pizza shop is very famous and
 * they receive many orders. To avoid confusion, vegan pizza orders are represented by positive
 * numbers and meat pizza orders are represented by negative numbers on the screen. The orders are
 * served in the order in which they are displayed on the screen. Each time an order is ready, its
 * number is removed from the display screen and a new order is added to the display at the end of
 * the list. A couple has arrived with their child Billy. Billy is a very naughty child. To keep
 * him occupied, his parents tell him to make a list of the first meat pizza that appear in each
 * set of K orders displayed on the shop’s display screen.
 *
 * Write an algorithm to help Billy make a list of the first meat based pizza order numbers
 * displayed on the screen each time an order is delivered to a customer.
 *
 * 补充说明
 *
 * docx 里只保留了题干，没有给出特别标准的输入模板。
 * 这份代码里我先约定下面这种输入格式：
 * 1. orderCount windowSize
 * 2. orderCount order numbers
 *
 * If a window has no meat pizza order, this version prints 0 for that window.
 *
 * <p>create: 2026-04-01 23:10:02</p>
 * @author zhaoyijie(AquariusGenius)
 */
public class Q86FirstMeatPizzaInEveryDisplayWindow {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int orderCount = scanner.nextInt();
        int windowSize = scanner.nextInt();
        int[] orders = new int[orderCount];
        for (int i = 0; i < orderCount; i++) {
            orders[i] = scanner.nextInt();
        }

        Q86FirstMeatPizzaInEveryDisplayWindow solver =
            new Q86FirstMeatPizzaInEveryDisplayWindow();
        System.out.println(solver.firstMeatOrders(orders, windowSize));
    }

    public String firstMeatOrders(int[] orders, int windowSize) {
        Deque<Integer> deque = new ArrayDeque<>();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < orders.length; i++) {
            while (!deque.isEmpty() && deque.peekFirst() <= i - windowSize) {
                deque.pollFirst();
            }
            if (orders[i] < 0) {
                deque.offerLast(i);
            }
            if (i + 1 >= windowSize) {
                if (builder.length() > 0) {
                    builder.append(' ');
                }
                builder.append(deque.isEmpty() ? 0 : orders[deque.peekFirst()]);
            }
        }
        return builder.toString();
    }
}
