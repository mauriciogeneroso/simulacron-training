package com.generoso.training.simulacron.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.CqlSessionFactoryBean;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.DropKeyspaceSpecification;

import java.util.List;

@Configuration
public class CassandraConfig extends AbstractCassandraConfiguration {

    @Override
    protected String getKeyspaceName() {
        return "training";
    }

    @Bean
    @Override
    public CqlSessionFactoryBean cassandraSession() {
        var sessionFactory = super.cassandraSession();
        sessionFactory.setLocalDatacenter("datacenter1");
        return sessionFactory;
    }

    @Override
    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
        var specification = CreateKeyspaceSpecification
                .createKeyspace(getKeyspaceName()).ifNotExists();
        return List.of(specification);
    }

    @Override
    protected List<DropKeyspaceSpecification> getKeyspaceDrops() {
        return List.of(DropKeyspaceSpecification.dropKeyspace(getKeyspaceName()));
    }
}
