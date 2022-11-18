Feature: Checking books get all endpoint return expected outputs

#  Scenario: Get all books happy-path scenario
#    Given an endpoint GET_ALL_BOOKS is prepared
#    When the request is sent
#    Then the response status code should be 200

  @simulacron
  Scenario: Get all books returns 500 when database times out
    Given an endpoint GET_ALL_BOOKS is prepared
    And cassandra query GET_ALL_BOOKS times out
    When the request is sent
    Then the response status code should be 500
    And cassandra query GET_ALL_BOOKS was executed once

  @simulacron
  Scenario: Get all books returns 500 when node is unavailable
    Given an endpoint GET_ALL_BOOKS is prepared
    And cassandra query GET_ALL_BOOKS gets node unavailable
    When the request is sent
    Then the response status code should be 500
    And cassandra query GET_ALL_BOOKS was executed once
