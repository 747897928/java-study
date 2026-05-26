package com.aquarius.wizard.leetcode.exam01;

import java.io.BufferedInputStream;
import java.io.IOException;

public class MinimumCableLengthToTurnOnSystems {

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner();
        int n = fs.nextInt();
        int[] state = new int[n];
        long[] dist = new long[n];

        for (int i = 0; i < n; i++) {
            state[i] = fs.nextInt();
        }
        for (int i = 0; i < n; i++) {
            dist[i] = fs.nextLong();
        }

        System.out.print(minCableLength(state, dist));
    }

    public static long minCableLength(int[] state, long[] dist) {
        long ans = 0;
        int n = state.length;
        int i = 0;

        while (i < n) {
            if (state[i] == 1) {
                i++;
                continue;
            }

            int start = i;
            while (i < n && state[i] == 0) {
                i++;
            }
            int end = i - 1;

            long best = Long.MAX_VALUE;
            if (start > 0) {
                best = Math.min(best, dist[end] - dist[start - 1]);
            }
            if (i < n) {
                best = Math.min(best, dist[i] - dist[start]);
            }
            ans += best;
        }

        return ans;
    }

    static class FastScanner {
        private final BufferedInputStream in = new BufferedInputStream(System.in);
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0;
        private int len = 0;

        int nextInt() throws IOException {
            return (int) nextLong();
        }

        long nextLong() throws IOException {
            int c;
            do {
                c = read();
            } while (c <= ' ' && c != -1);

            long sign = 1;
            if (c == '-') {
                sign = -1;
                c = read();
            }

            long val = 0;
            while (c > ' ') {
                val = val * 10 + c - '0';
                c = read();
            }
            return val * sign;
        }

        private int read() throws IOException {
            if (ptr >= len) {
                len = in.read(buffer);
                ptr = 0;
                if (len <= 0) {
                    return -1;
                }
            }
            return buffer[ptr++];
        }
    }
}
