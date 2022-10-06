package com.generoso.training.simulacron.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cassandra.CassandraProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.CqlSessionFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.DropKeyspaceSpecification;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import java.util.List;

@Configuration
@EnableCassandraRepositories(basePackages = "com.generoso.training.simulacron.datarest")
public class CassandraConfig extends AbstractCassandraConfiguration {

    @Autowired
    private CassandraProperties properties;

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

    @Override
    protected String getContactPoints() {
        var contactPoint = properties.getContactPoints().stream().findFirst();
        return contactPoint.orElseGet(super::getContactPoints);
    }

    @Override
    protected int getPort() {
        return properties.getPort();
    }

    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.CREATE_IF_NOT_EXISTS;
    }

    @Override
    public String[] getEntityBasePackages() {
        return new String[]{"com.generoso.training.simulacron.datarest"};
    }
}
