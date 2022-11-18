Feature: Checking actuator endpoints return expected outputs

  Scenario: When application is healthy, returns 200 response status code and "UP" response body on health endpoint
    Given a private endpoint PRIVATE_HEALTH_CHECK is prepared
    When the request is sent
    Then the response status code should be 200
    And the health response body of the message should have the status "UP"
    And health components should contain the status UP:
      | diskSpace |
      | ping      |
      | cassandra |
    And query HEALTH_CHECK has run 1

  @simulacron
  Scenario: When application is healthy, but cassandra is down, should return unhealthy application
    Given a private endpoint PRIVATE_HEALTH_CHECK is prepared
    And cassandra query HEALTH_CHECK times out
    When the request is sent
    Then the response status code should be 503
    And the health response body of the message should have the status "DOWN"
    And cassandra query HEALTH_CHECK was executed once
    And health components should contain the status:
      | diskSpace | UP   |
      | ping      | UP   |
      | cassandra | DOWN |
    And query HEALTH_CHECK has run 1

  Scenario: When application is running, display metric content
    Given a private endpoint PRIVATE_METRICS is prepared
    When the request is sent
    Then the response status code should be 200
    And the body of the message contains "jvm_buffer_count_buffers"

  Scenario: Return correct app information when calling private/info
    Given a private endpoint PRIVATE_INFO is prepared
    When the request is sent
    Then the response status code should be 200
    And it should return build information containing the following keys and values:
      | artifact | training-app            |
      | name     | simulacron-training |
      | group    | com.generoso            |
    And the response body contains:
      | git   |
      | build |
      | java  |