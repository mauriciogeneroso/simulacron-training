Feature: Checking books get all endpoint return expected outputs

  @simulacron
  Scenario: Create a book returns 500 when database times out
    Given an endpoint ADD_BOOK is prepared
    And cassandra query ADD_BOOK times out
    When the request is sent
    Then the response status code should be 500
    And cassandra query ADD_BOOK was executed once

  @simulacron
  Scenario: Create a book returns 500 when node is unavailable
    Given an endpoint ADD_BOOK is prepared
    And cassandra query ADD_BOOK gets node unavailable
    When the request is sent
    Then the response status code should be 500
    And cassandra query ADD_BOOK was executed once