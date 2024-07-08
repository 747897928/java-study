package com.aquarius.wizard.jdkapi.dump;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * @author zhaoyijie
 * @since 2024/7/2 17:26
 */
public class ThreadDumpExample {
    //Java中怎么获取一份线程dump文件
    //https://blog.csdn.net/Blue92120/article/details/131953001
    public static void main(String[] args) {
        // 获取Java线程管理的MBean
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();

        // 获取所有线程的ID
        long[] threadIds = threadMXBean.getAllThreadIds();

        // 获取线程信息并写入文件
        try (OutputStream outputStream = new FileOutputStream("thread_dump.txt")) {
            for (long threadId : threadIds) {
                ThreadInfo threadInfo = threadMXBean.getThreadInfo(threadId, Integer.MAX_VALUE);
                String threadDump = formatThreadInfo(threadInfo);
                outputStream.write(threadDump.getBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 格式化线程信息
    private static String formatThreadInfo(ThreadInfo threadInfo) {
        StringBuilder sb = new StringBuilder();
        sb.append("Thread name: ").append(threadInfo.getThreadName()).append("\n");
        sb.append("Thread ID: ").append(threadInfo.getThreadId()).append("\n");
        sb.append("Thread state: ").append(threadInfo.getThreadState()).append("\n");
        StackTraceElement[] stackTrace = threadInfo.getStackTrace();
        for (StackTraceElement stackTraceElement : stackTrace) {
            sb.append("\t").append(stackTraceElement.toString()).append("\n");
        }
        sb.append("\n");
        return sb.toString();
    }
}