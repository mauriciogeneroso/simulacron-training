package com.generoso.ft.training.simulacron.config;

import com.generoso.training.simulacron.SimulacronTrainingApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Profile("local")
@DependsOn("cassandraCluster")
@Configuration
public class LocalApplicationServer {

    private ConfigurableApplicationContext sdApplicationContext;

    @PostConstruct
    public void startUp() {
        sdApplicationContext = SpringApplication.run(SimulacronTrainingApplication.class);
    }

    @PreDestroy
    public void shutDown() {
        sdApplicationContext.close();
    }
}