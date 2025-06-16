package com.aquarius.wizard.common.utils;

import lombok.Data;

import java.nio.channels.FileChannel;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author zhaoyijie
 * @since 2024/4/29 11:30
 */
public class Ip2RegionUtilsTest {

    public static void main(String[] args) throws Exception {
        String ip = "222.70.170.148";
        //String ip = "172.104.40.81";
        //String s = Ip2RegionUtils.searchByFile(ip);
        String s = Ip2RegionUtils.searchByVectorIndex(ip);
        //String s = Ip2RegionUtils.searchByMemory(ip);
        System.out.println(s);


    }


}