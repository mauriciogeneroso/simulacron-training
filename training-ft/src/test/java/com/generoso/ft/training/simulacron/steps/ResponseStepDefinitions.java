package com.generoso.ft.training.simulacron.steps;

import com.generoso.ft.training.simulacron.client.model.JsonMapper;
import com.generoso.ft.training.simulacron.state.ScenarioState;
import io.cucumber.java.en.Then;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ResponseStepDefinitions {

    private final ScenarioState scenarioState;
    private final JsonMapper jsonMapper;

    @Then("the response status code should be {int}")
    public void theResponseCode(int expectedResponseCode) {
        assertEquals(expectedResponseCode, scenarioState.getActualResponse().statusCode());
    }
}
