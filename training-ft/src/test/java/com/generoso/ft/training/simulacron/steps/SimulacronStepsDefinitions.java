package com.generoso.ft.training.simulacron.steps;

import com.datastax.oss.protocol.internal.request.Query;
import com.datastax.oss.simulacron.common.codec.ConsistencyLevel;
import com.datastax.oss.simulacron.server.BoundCluster;
import io.cucumber.java.After;
import io.cucumber.java.en.And;

import java.util.Map;

import static com.datastax.oss.simulacron.common.stubbing.PrimeDsl.*;
import static com.generoso.ft.training.simulacron.utils.SimulacronUtils.getSimulacronCluster;
import static org.assertj.core.api.Assertions.assertThat;

public class SimulacronStepsDefinitions {

    private static final Map<String, String> queries = Map.of(
            "HEALTH_CHECK", "SELECT now() FROM system.local",
            "INSERT_BOOK", "INSERT INTO book (id,isbn,publisher,title) VALUES (?,?,?,?)",
            "GET_ALL_BOOKS", "SELECT * FROM book",
            "GET_BOOK_BY_ID", "SELECT * FROM book WHERE id=? LIMIT 1",
            "DELETE_BOOK", "DELETE FROM book WHERE id=?"
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

    @And("cassandra query {word} gets node unavailable")
    public void unavailableNode(String query) {
        cassandraCluster.prime(when(queries.get(query))
                .then(unavailable(ConsistencyLevel.LOCAL_QUORUM, 0, 0)));
    }

    @And("cassandra query {word} returns a book with id {word}")
    public void cassandraReturnsBookOnQuery(String query, String id) {
        cassandraCluster.prime(when(queries.get(query))
                .then(new RowBuilder()
                        .row("id", id)));
    }

    @And("cassandra query {word} was executed {word}")
    public void cassandraQueryWasExecuted(String query, String frequency) {
        if (frequency.equals("once")) {
            cassandraQueryWasExecuted(query, 1);
        } else if (frequency.equals("twice")) {
            cassandraQueryWasExecuted(query, 2);
        } else {
            try {
                cassandraQueryWasExecuted(query, Integer.parseInt(frequency));
            } catch (NumberFormatException ex) {
                throw new IllegalArgumentException("Invalid string frequency argument for cassandra query: " + frequency);
            }
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

    @And("both the nodes in the cluster return unavailable for the query {word}")
    public void bothNodesReturnsTimeOutForTheQuery(String query) {
        cassandraCluster.dc(0).getNodes().forEach(node ->
                node.prime(when(queries.get(query))
                        .then(unavailable(ConsistencyLevel.LOCAL_QUORUM, 0, 0)))
        );
    }

    @And("both the nodes receive the query {word}")
    public void bothNodesReceiveTheQuery(String query) {
        var cluster = getSimulacronCluster();
        var node1Logs = cluster.node(0).getLogs().getQueryLogs();
        var node2Logs = cluster.node(1).getLogs().getQueryLogs();

        var node1LogsCount = node1Logs.stream()
                .filter(log -> log.getFrame().message instanceof Query)
                .map(queryLog -> (Query) queryLog.getFrame().message)
                .filter(queryObject -> queryObject.query.equalsIgnoreCase(queries.get(query)))
                .count();

        var node2LogsCount = node2Logs.stream()
                .filter(log -> log.getFrame().message instanceof Query)
                .map(queryLog -> (Query) queryLog.getFrame().message)
                .filter(queryObject -> queryObject.query.equalsIgnoreCase(queries.get(query)))
                .count();

        assertThat(node1LogsCount).isEqualTo(1);
        assertThat(node2LogsCount).isEqualTo(1);
    }

    private void resetSimulacronPrimes() {
        if (cassandraCluster != null) {
            cassandraCluster.clearPrimes(true);
            cassandraCluster.clearLogs();
        }
    }
}
