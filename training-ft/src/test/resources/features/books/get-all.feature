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

   # to run these locally run ` sudo ifconfig lo0 alias 127.0.0.2 ` on shell to open up loopback address needed for 2nd node
#  @simulacron-multinode
#  Scenario: Request fails when both cassandra nodes are down
#    Given an endpoint GET_ALL_BOOKS is prepared
#    And both the nodes in the cluster return unavailable for the query GET_ALL_BOOKS
#    When the request is sent
#    Then the response status code should be 500
#    And both the nodes receive the query GET_ALL_BOOKS