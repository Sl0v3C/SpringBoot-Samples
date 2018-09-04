package com.sl0v3c.samples.config.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
@EnableConfigurationProperties()
@ConfigurationProperties(prefix = "redis.cluster")
public class ClusterProperties {
    /*
     * spring.redis.cluster.nodes[0] = 127.0.0.1:7379
     * spring.redis.cluster.nodes[1] = 127.0.0.1:7380
     */
    private List<String> nodes = new ArrayList<>();

    /*
     * Get initial collection of known cluster nodes in format {@code host:port}.
     * @return
     */
    public List<String> getNodes() {
        return nodes;
    }
}
