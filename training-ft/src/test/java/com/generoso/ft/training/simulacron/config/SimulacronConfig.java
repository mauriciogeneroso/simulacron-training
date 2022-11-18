package com.generoso.ft.training.simulacron.config;

import com.datastax.oss.simulacron.common.cluster.ClusterSpec;
import com.datastax.oss.simulacron.server.BoundCluster;
import com.datastax.oss.simulacron.server.Inet4Resolver;
import com.datastax.oss.simulacron.server.Server;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.datastax.oss.simulacron.server.AddressResolver.defaultStartingIp;
import static com.generoso.ft.training.simulacron.utils.NetworkUtils.findUnusedLocalPort;

@Slf4j
@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SimulacronConfig {

    private static final int CASSANDRA_PORT = findUnusedLocalPort();

    private static BoundCluster simulacronCluster;

    public static void startSimulacron() {
        try {
            var server = Server.builder().build();

            var clusterSpec = ClusterSpec.builder().build();
            var addressResolver = new Inet4Resolver(defaultStartingIp, CASSANDRA_PORT);
            var dataCenter = clusterSpec.addDataCenter()
                    .withName("datacenter1").withCassandraVersion("3.8").build();

            dataCenter.addNode().withAddress(addressResolver.get()).withName("Default").build();
            var boundCluster = server.register(clusterSpec);

            log.info("Starting Simulacron node");
            boundCluster.start();
            log.info("Simulacron started successfully");
            SimulacronConfig.simulacronCluster = boundCluster;
        } catch (Exception ex) {
            log.error("Could not start cassandra with simulacron ", ex);
            throw ex;
        }
    }

    public static void stopSimulacron() {
        SimulacronConfig.simulacronCluster.stop();
    }

    public static BoundCluster getSimulacronCluster() {
        return SimulacronConfig.simulacronCluster;
    }

    public static String getSimulacronPort() {
        return String.valueOf(CASSANDRA_PORT);
    }
}