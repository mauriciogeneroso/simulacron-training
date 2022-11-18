package com.generoso.ft.training.simulacron.utils;

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
public class SimulacronUtils {

    private static final int CASSANDRA_PORT = findUnusedLocalPort();

    private static BoundCluster simulacronCluster;
    private static boolean isSimulacronRunning;

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
            SimulacronUtils.simulacronCluster = boundCluster;
            SimulacronUtils.isSimulacronRunning = true;
        } catch (Exception ex) {
            log.error("Could not start cassandra with simulacron ", ex);
            throw ex;
        }
    }

    public static void startMultiNodeSimulacron() {
        try {
            var server = Server.builder().build();
            var clusterSpec = ClusterSpec.builder().build();
            var node1Address = new Inet4Resolver(defaultStartingIp, CASSANDRA_PORT);
            var node2Address = new Inet4Resolver(new byte[]{127, 0, 0, 2}, CASSANDRA_PORT);

            var dataCenter = clusterSpec.addDataCenter().withName("datacenter1")
                    .withCassandraVersion("3.8").build();

            dataCenter.addNode().withAddress(node1Address.get()).withName("Default").build();
            dataCenter.addNode().withAddress(node2Address.get()).withName("Node1").build();
            var boundCluster = server.register(clusterSpec);

            log.info("Starting Multinode Simulacron node");
            SimulacronUtils.simulacronCluster = boundCluster;
            SimulacronUtils.isSimulacronRunning = true;
        } catch (Exception ex) {
            log.error("Could not start cassandra with simulacron ", ex);
            throw ex;
        }
    }

    public static void stopSimulacron() {
        if (isSimulacronRunning) {
            SimulacronUtils.simulacronCluster.stop();
            isSimulacronRunning = false;
        }
    }

    public static BoundCluster getSimulacronCluster() {
        return SimulacronUtils.simulacronCluster;
    }

    public static String getSimulacronPort() {
        return String.valueOf(CASSANDRA_PORT);
    }
}