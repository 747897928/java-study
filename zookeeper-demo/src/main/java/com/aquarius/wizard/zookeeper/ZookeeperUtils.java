package com.aquarius.wizard.zookeeper;

import com.aquarius.wizard.common.utils.StringUtils;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;

/**
 * @author zhaoyijie
 * @since 2024/3/26 14:45
 */
public class ZookeeperUtils {

    public static CuratorFramework getClient(String connectString, int sessionTimeoutMs, int maxCloseWaitMs, String namespace, RetryPolicy retryPolicy) {
        CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder().connectString(connectString)
                .sessionTimeoutMs(sessionTimeoutMs)
                .connectionTimeoutMs(maxCloseWaitMs)
                .retryPolicy(retryPolicy);
        if (StringUtils.isNotBlank(namespace)) {
            builder.namespace(namespace);
        }
        return builder.build();
    }

}
