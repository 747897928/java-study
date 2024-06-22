package com.aquarius.wizard.jdkapi.oom;

/**
 * @author zhaoyijie
 * @since 2024/6/22 08:49
 */
public class OOMDemo {

    public static void main(String[] args) {
        //java -Xms10M -Xmx10M -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/Users/zhaoyijie/IdeaProjects/java-study/Jdk-api-demo/logs/ -cp Jdk-api-demo-1.0.jar com.aquarius.wizard.jdkapi.oom.OOMDemo
        OOMService oomService = new OOMService();
        oomService.getUserList();
    }
}
