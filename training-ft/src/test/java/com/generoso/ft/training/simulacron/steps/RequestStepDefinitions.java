package com.generoso.ft.training.simulacron.steps;

import com.generoso.ft.training.simulacron.client.Client;
import com.generoso.ft.training.simulacron.client.model.Endpoint;
import com.generoso.ft.training.simulacron.client.template.RequestTemplate;
import com.generoso.ft.training.simulacron.state.ScenarioState;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RequestStepDefinitions {

    private final Map<Endpoint, RequestTemplate> requestTemplates;
    private final Client client;
    private final ScenarioState scenarioState;

    @Given("an endpoint {} is prepared")
    public void thePrivateEndpointIsPrepared(Endpoint endpoint) {
        var requestTemplate = getRequestTemplate(endpoint);
        scenarioState.setRequestTemplate(requestTemplate);
    }

    @Given("an endpoint {} is prepared with path parameter {word}")
    public void anEndpointIsPreparedWithPathParameter(Endpoint endpoint, String pathParameter) {
        var requestTemplate = getRequestTemplate(endpoint);
        requestTemplate.pathParameter(pathParameter);
        scenarioState.setRequestTemplate(requestTemplate);
    }

    @Given("an endpoint {} is prepared with id {}")
    public void anEndpointIsPreparedWithId(Endpoint endpoint, String id) {
        var requestTemplate = getRequestTemplate(endpoint);
        requestTemplate.pathParameter(id);
        scenarioState.setRequestTemplate(requestTemplate);
    }

    @When("the request is sent")
    public void theEndpointReceivesARequest() {
        var response = client.execute(scenarioState.getRequestTemplate());
        scenarioState.setActualResponse(response);
    }

    private RequestTemplate getRequestTemplate(Endpoint endpoint) {
        if (requestTemplates.containsKey(endpoint)) {
            return requestTemplates.get(endpoint);
        }

        throw new RuntimeException("Invalid request template provided: " + endpoint);
    }
}
