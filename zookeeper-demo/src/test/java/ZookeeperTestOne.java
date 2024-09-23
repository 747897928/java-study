import com.aquarius.wizard.common.utils.JSONUtils;
import com.aquarius.wizard.zookeeper.ZookeeperUtils;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author zhaoyijie
 * @since 2024/3/26 14:45
 */
public class ZookeeperTestOne {

    String connectString = "127.0.0.1:2181";
    String namespace = "study_test";
    //重试策略
    RetryPolicy retryPolicy = new ExponentialBackoffRetry(3000, 10);
    int sessionTimeoutMs = 60 * 1000;
    int maxCloseWaitMs = 15 * 1000;

    @Test
    public void createEphemeralSequentialPath() throws Exception {
        CuratorFramework client = ZookeeperUtils.getClient(connectString, sessionTimeoutMs, maxCloseWaitMs, namespace, retryPolicy);
        client.start();
        String serviceName = "Zookeeper-Study-Service";
        long id = Thread.currentThread().getId();
        String content = serviceName + "-" + id;
        byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
        try {
            String path = client.create()
                    .creatingParentsIfNeeded()
                    .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                    .forPath("/zookeeper/lock", bytes);
            //顺序节点创建成功后，会返回一个带有序号的路径CreateMode.EPHEMERAL_SEQUENTIAL，lock0000000002后的0000000002
            //就是zk给顺序节点追加的序号路径
            //[zk: localhost:2181(CONNECTED) 7] ls /study_test/zookeeper
            //[lock0000000002]
            //[zk: localhost:2181(CONNECTED) 11] get /study_test/zookeeper/lock0000000002
            //Zookeeper-Study-Service-1
            System.out.println(path);
        } catch (KeeperException.NodeExistsException e) {
            e.printStackTrace();
        }

        Thread.sleep(10000);
        client.close();
    }

    @Test
    public void testGet() throws Exception {
        CuratorFramework client = ZookeeperUtils.getClient(connectString, sessionTimeoutMs, maxCloseWaitMs, namespace, retryPolicy);
        client.start();
        byte[] bytes = client.getData().forPath("/zookeeper/lock");
        System.out.println(new String(bytes));
        client.close();
    }
}
