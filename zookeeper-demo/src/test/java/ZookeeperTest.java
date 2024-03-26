import com.aquarius.wizard.common.utils.JSONUtils;
import com.aquarius.wizard.zookeeper.ZookeeperUtils;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author zhaoyijie
 * @since 2024/3/26 14:45
 */
public class ZookeeperTest {

    String connectString = "127.0.0.1:2181";
    String namespace = "edt_event";
    //重试策略
    RetryPolicy retryPolicy = new ExponentialBackoffRetry(3000, 10);
    int sessionTimeoutMs = 60 * 1000;
    int maxCloseWaitMs = 15 * 1000;

    @Test
    public void createPath() throws Exception {
        CuratorFramework client = ZookeeperUtils.getClient(connectString, sessionTimeoutMs, maxCloseWaitMs, namespace, retryPolicy);
        client.start();
        //如果创建的节点没有指定数据，则默认将当前客户端的ip作为数据存储
        //String path = client.create().forPath("/event");
        //[zk: localhost:2181(CONNECTED) 10] get /edt_event/event
        //127.0.0.1
        Map<String, Object> map = new HashMap<>();
        map.put("name", "张三");
        map.put("age", 18);
        byte[] bytes = JSONUtils.toJsonByteArray(map);
        //默认类型:持久化
        try {
            //String path = client.create().withMode(CreateMode.PERSISTENT).forPath("/event", bytes);
            String path = client.create()
                    .creatingParentsIfNeeded()
                    .withMode(CreateMode.PERSISTENT)
                    .forPath("/event/attribute", bytes);
            System.out.println(path);
            //[zk: localhost:2181(CONNECTED) 7] ls /edt_event/event
            //[attribute]
            //[zk: localhost:2181(CONNECTED) 9] get /edt_event/event/attribute
            //{"name":"张三","age":18}
        } catch (KeeperException.NodeExistsException e) {
            e.printStackTrace();
        }
        client.close();
    }

    /**
     * 修改数据
     * 1. 基本修改数据：setData().forPath()
     * 2. 根据版本修改: setData().withVersion().forPath()
     * * version 是通过查询出来的。目的就是为了让其他客户端或者线程不干扰我。
     *
     * @throws Exception
     */
    @Test
    public void testSet() throws Exception {
        CuratorFramework client = ZookeeperUtils.getClient(connectString, sessionTimeoutMs, maxCloseWaitMs, namespace, retryPolicy);
        client.start();
        Map<String, Object> map = new HashMap<>();
        map.put("name", "李四");
        map.put("age", 20);
        byte[] bytes = JSONUtils.toJsonByteArray(map);
        client.setData().forPath("/event/attribute", bytes);
        client.close();
    }


    @Test
    public void testSetForVersion() throws Exception {
        CuratorFramework client = ZookeeperUtils.getClient(connectString, sessionTimeoutMs, maxCloseWaitMs, namespace, retryPolicy);
        client.start();
        Stat status = new Stat();
        //3. 查询节点状态信息：ls -s
        client.getData().storingStatIn(status).forPath("/event/attribute");
        int version = status.getVersion();//查询出来的 3
        System.out.println(version);
        Map<String, Object> map = new HashMap<>();
        map.put("name", "王五");
        map.put("age", 22);
        byte[] bytes = JSONUtils.toJsonByteArray(map);
        client.setData().withVersion(version).forPath("/event/attribute", bytes);
        client.close();
    }

    @Test
    public void listPath() {
        CuratorFramework client = ZookeeperUtils.getClient(connectString, sessionTimeoutMs, maxCloseWaitMs, namespace, retryPolicy);
        client.start();
        client.getChildren();
        client.close();
    }

    //NodeCache:只是监听某一个特定的节点
    //PathChildrenCache:监控一个ZNode的子节点.
    //TreeCache:可以监控整个树上的所有节点，类似于PathChildrenCache和NodeCache的组合
    @Test
    public void nodeCacheTest() throws Exception {
        CuratorFramework client = ZookeeperUtils.getClient(connectString, sessionTimeoutMs, maxCloseWaitMs, namespace, retryPolicy);
        client.start();
        //1. 创建NodeCache对象
        //replace by CuratorCache
        final NodeCache nodeCache = new NodeCache(client, "/event/attribute");
        //2. 注册监听
        nodeCache.getListenable().addListener(new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                System.out.println("节点变化了~");
                //获取修改节点后的数据
                byte[] data = nodeCache.getCurrentData().getData();
                System.out.println(new String(data));
            }
        });

        //3. 开启监听.如果设置为true，则开启监听是，加载缓冲数据
        nodeCache.start(true);
        //睡眠30秒防止程序过早停止
        TimeUnit.SECONDS.sleep(30);
        client.close();
    }
}
