package com.aquarius.wizard.common.utils;

import org.lionsoul.ip2region.xdb.Searcher;

/**
 * @author zhaoyijie
 * @since 2024/1/8 10:50
 */
public class Ip2RegionUtils {

    private final static String dbPath = "data/ip2region.xdb";

    private static byte[] vIndex;

    private static Searcher bufferSearcher;

    private Ip2RegionUtils() {
        throw new UnsupportedOperationException("Construct Ip2RegionUtils");
    }

    /**
     * 国家|区域|省份|城市|运营商
     * 完全基于文件的查询,ip 属地在国内的话，只会展示省份，而国外的话，只会展示国家
     *
     * @param ip ip地址
     * @return example 中国|0|上海|上海市|电信 或者 新加坡|0|0|0|0
     * @throws Exception
     */
    public static String searchByFile(String ip) throws Exception {
        // 1、创建 searcher 对象
        Searcher searcher = Searcher.newWithFileOnly(dbPath);
        // 2、查询
        String region = searcher.search(ip);
        // 3、关闭资源
        searcher.close();
        // 备注：并发使用，每个线程需要创建一个独立的 searcher 对象单独使用。
        return region;
    }

    /**
     * 国家|区域|省份|城市|运营商
     * 缓存 VectorIndex 索引
     * 我们可以提前从 xdb 文件中加载出来 VectorIndex 数据，然后全局缓存，
     * 每次创建 Searcher 对象的时候使用全局的 VectorIndex 缓存可以减少一次固定的 IO 操作，
     * 从而加速查询，减少 IO 压力。
     * ip 属地在国内的话，只会展示省份，而国外的话，只会展示国家
     *
     * @param ip ip地址
     * @return example 中国|0|上海|上海市|电信 或者 新加坡|0|0|0|0
     * @throws Exception
     */
    public static String searchByVectorIndex(String ip) throws Exception {
        // 1、从 dbPath 中预先加载 VectorIndex 缓存，并且把这个得到的数据作为全局变量，后续反复使用。
        if (vIndex == null) {
            vIndex = Searcher.loadVectorIndexFromFile(dbPath);
        }
        // 2、使用全局的 vIndex 创建带 VectorIndex 缓存的查询对象。
        Searcher searcher = Searcher.newWithVectorIndex(dbPath, vIndex);
        // 3、查询
        String region = searcher.search(ip);
        // 4、关闭资源
        searcher.close();
        return region;
    }

    /**
     * 国家|区域|省份|城市|运营商
     * 缓存整个 xdb 数据
     * 我们也可以预先加载整个 ip2region.xdb 的数据到内存，
     * 然后基于这个数据创建查询对象来实现完全基于文件的查询，类似之前的 memory search。
     * ip 属地在国内的话，只会展示省份，而国外的话，只会展示国家
     *
     * @param ip ip地址
     * @return example 中国|0|上海|上海市|电信 或者 新加坡|0|0|0|0
     * @throws Exception
     */
    public static String searchByMemory(String ip) throws Exception {
        // 1、从 dbPath 加载整个 xdb 到内存。
        if (bufferSearcher == null) {
            byte[] cBuff = Searcher.loadContentFromFile(dbPath);
            //2、使用上述的 cBuff 创建一个完全基于内存的查询对象。
            bufferSearcher = Searcher.newWithBuffer(cBuff);
        }
        // 3、查询
        String region = bufferSearcher.search(ip);
        // 4、关闭资源 - 该 searcher 对象可以安全用于并发，等整个服务关闭的时候再关闭 searcher
        // bufferSearcher.close();
        // 备注：并发使用，用整个 xdb 数据缓存创建的查询对象可以安全的用于并发，也就是你可以把这个 searcher 对象做成全局对象去跨线程访问。
        return region;
    }

    public static void cleanCBuff() {
        //关闭资源 - 该 searcher 对象可以安全用于并发，等整个服务关闭的时候再关闭 searcher
        try {
            if (bufferSearcher != null) {
                bufferSearcher.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void cleanVIndex() {
        vIndex = null;
    }
}
