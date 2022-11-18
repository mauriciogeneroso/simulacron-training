package com.generoso.ft.training.simulacron.steps;

import com.generoso.training.simulacron.SimulacronTrainingApplication;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import static com.generoso.ft.training.simulacron.utils.SimulacronUtils.*;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HooksDefinitions {

//    private static final String SIMULACRON_TAG = "@simulacron";
    private static final String MULTINODE_TAG = "@simulacron-multinode";

    private ConfigurableApplicationContext sdApplicationContext;

    @Before
    public void before(Scenario scenario) {
        startDatabase(scenario);
        startApplication();
    }

    @After
    public void after(Scenario scenario) {
        stopDatabase(scenario);
        stopApplication();
    }

    private void startDatabase(Scenario scenario) {
        var sourceTagNames = scenario.getSourceTagNames();
        if (sourceTagNames.contains(MULTINODE_TAG)) {
            startMultiNodeSimulacron();
        } else {
            startSimulacron();
        }

        System.setProperty("spring.data.cassandra.port", getSimulacronPort());
    }

    private void stopDatabase(Scenario scenario) {
        stopSimulacron();
    }

    private void startApplication() {
        sdApplicationContext = SpringApplication.run(SimulacronTrainingApplication.class);
    }

    private void stopApplication() {
        sdApplicationContext.stop();
    }
}
