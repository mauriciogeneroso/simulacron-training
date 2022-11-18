package com.generoso.ft.training.simulacron.steps;

import com.datastax.oss.protocol.internal.request.Query;
import com.datastax.oss.simulacron.common.codec.ConsistencyLevel;
import com.datastax.oss.simulacron.server.BoundCluster;
import io.cucumber.java.After;
import io.cucumber.java.en.And;

import java.util.Map;

import static com.datastax.oss.simulacron.common.stubbing.PrimeDsl.readTimeout;
import static com.datastax.oss.simulacron.common.stubbing.PrimeDsl.when;
import static com.generoso.ft.training.simulacron.utils.SimulacronUtils.getSimulacronCluster;
import static org.assertj.core.api.Assertions.assertThat;

public class SimulacronStepsDefinitions {

    private static final Map<String, String> queries = Map.of(
            "HEALTH_CHECK", "SELECT now() FROM system.local"
    );

    private final BoundCluster cassandraCluster;

    public SimulacronStepsDefinitions() {
        this.cassandraCluster = getSimulacronCluster();
    }

    @After
    public void cleanUp() {
        resetSimulacronPrimes();
    }

    @And("cassandra query {word} times out")
    public void queryTimesOut(String query) {
        cassandraCluster.prime(when(queries.get(query))
                .then(readTimeout(ConsistencyLevel.LOCAL_QUORUM, 0, 0, true)));
    }

    @And("cassandra query {word} was executed {word}")
    public void cassandraQueryWasExecuted(String query, String frequency) {
        if (frequency.equals("once")) {
            cassandraQueryWasExecuted(query, 1);
        } else if (frequency.equals("twice")) {
            cassandraQueryWasExecuted(query, 2);
        } else {
            throw new IllegalArgumentException("Invalid string frequency argument for cassandra query: " + frequency);
        }
    }

    @And("cassandra query {word} was executed {int} times")
    public void cassandraQueryWasExecuted(String query, int frequency) {
        var nodeLogs = cassandraCluster.node(0).getLogs().getQueryLogs();

        var queryCount = nodeLogs.stream()
                .filter(log -> log.getFrame().message instanceof Query)
                .map(queryLog -> (Query) queryLog.getFrame().message)
                .filter(queryObject -> queryObject.query.equals(queries.get(query)))
                .count();

        assertThat(queryCount).isEqualTo(frequency);
    }

    private void resetSimulacronPrimes() {
        if (cassandraCluster != null) {
            cassandraCluster.clearPrimes(true);
            cassandraCluster.clearLogs();
        }
    }
}
