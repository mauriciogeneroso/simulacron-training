package com.generoso.ft.training.simulacron.config;

import com.datastax.oss.simulacron.common.cluster.ClusterSpec;
import com.datastax.oss.simulacron.server.BoundCluster;
import com.datastax.oss.simulacron.server.Inet4Resolver;
import com.datastax.oss.simulacron.server.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static com.datastax.oss.simulacron.server.AddressResolver.defaultStartingIp;
import static com.datastax.oss.simulacron.server.AddressResolver.defaultStartingPort;

@Slf4j
@Profile("local")
@Configuration
public class SimulacronConfig {

    @Bean
    public BoundCluster simulacron() {
        try {
            var server = Server.builder().build();

            var clusterSpec = ClusterSpec.builder().build();
            var addressResolver = new Inet4Resolver(defaultStartingIp, defaultStartingPort);
            var dataCenter = clusterSpec.addDataCenter()
                    .withName("datacenter1").withCassandraVersion("3.8").build();

            dataCenter.addNode().withAddress(addressResolver.get()).withName("Default").build();
            var boundCluster = server.register(clusterSpec);

            log.info("Starting Simulacron node");
            boundCluster.start();
            log.info("Simulacron started successfully");
            return boundCluster;
        } catch (Exception ex) {
            log.error("Could not start cassandra with simulacron ", ex);
            throw ex;
        }
    }
}