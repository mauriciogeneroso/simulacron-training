package com.generoso.training.simulacron.config.actuator;

import com.datastax.oss.driver.api.core.CqlSession;
import org.springframework.boot.actuate.autoconfigure.health.CompositeHealthContributorConfiguration;
import org.springframework.boot.actuate.health.HealthContributor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration(proxyBeanMethods = false)
public class CassandraDriverConfiguration
        extends CompositeHealthContributorConfiguration<CassandraDriverHealthIndicator, CqlSession> {

    @Bean
    @ConditionalOnMissingBean(name = {"cassandraHealthIndicator", "cassandraHealthContributor"})
    public HealthContributor cassandraHealthContributor(Map<String, CqlSession> sessions) {
        return createContributor(sessions);
    }
}
