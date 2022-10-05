package com.generoso.training.simulacron.config.actuator;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.metadata.Node;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Optional;

/**
 * Custom {@link org.springframework.boot.actuate.cassandra.CassandraDriverHealthIndicator}
 */
@Slf4j
public class CassandraDriverHealthIndicator extends AbstractHealthIndicator {

    private static final String HEALTH_CHECK_CQL = "SELECT now() FROM system.local";

    private final CqlSession session;

    /**
     * Create a new {@link CassandraDriverHealthIndicator} instance.
     *
     * @param session the {@link CqlSession}.
     */
    public CassandraDriverHealthIndicator(CqlSession session) {
        super("Cassandra health check failed");
        Assert.notNull(session, "session must not be null");
        this.session = session;
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) {
        try {
            session.execute(HEALTH_CHECK_CQL);
            builder.up();
        } catch (Exception e) {
            log.info("Exception thrown while checking Cassandra healthy", e);
            builder.down();
        } finally {
            Collection<Node> nodes = session.getMetadata().getNodes().values();
            Optional<Node> anyNode = nodes.stream().findAny();
            if (anyNode.isPresent()) {
                anyNode.map(Node::getCassandraVersion)
                        .ifPresent(version -> builder.withDetail("version", version));
            }
        }
    }

}