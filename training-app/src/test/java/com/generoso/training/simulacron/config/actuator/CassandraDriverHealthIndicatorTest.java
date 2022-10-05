package com.generoso.training.simulacron.config.actuator;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.metadata.Metadata;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CassandraDriverHealthIndicatorTest {

    @Mock
    private Metadata metadata;

    @Mock
    private CqlSession cqlSession;

    @InjectMocks
    private CassandraDriverHealthIndicator healthIndicator;

    @BeforeEach
    public void setup() {
        when(cqlSession.getMetadata()).thenReturn(metadata);
        when(metadata.getNodes()).thenReturn(Collections.emptyMap());
    }

    @Test
    void shouldReturnHealthyWhenCassandraIsUp() {
        // Arrange
        var builder = new Health.Builder();
        when(cqlSession.execute(anyString())).thenReturn(any(ResultSet.class));

        // Act
        healthIndicator.doHealthCheck(builder);
        var health = builder.build();

        // Assert
        verify(cqlSession).execute(anyString());
        assertThat(health.getStatus()).isEqualTo(Status.UP);
    }

    @Test
    void shouldReturnUnhealthyWhenCassandraIsDown() {
        // Arrange
        var builder = new Health.Builder();
        when(cqlSession.execute(anyString())).thenThrow(new RuntimeException());

        // Act
        healthIndicator.doHealthCheck(builder);
        var health = builder.build();

        // Assert
        verify(cqlSession).execute(anyString());
        assertThat(health.getStatus()).isEqualTo(Status.DOWN);
    }
}
