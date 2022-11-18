Feature: Checking books get all endpoint return expected outputs

#  Scenario: Delete book happy-path scenario
#    Given an endpoint GET_BY_ID_BOOKS is prepared with path parameter book-id
#    When the request is sent
#    Then the response status code should be 204

  @simulacron
  Scenario: Get book by id returns 500 when database times out
    Given an endpoint GET_BOOK_BY_ID is prepared with path parameter book-id
    And cassandra query GET_BOOK_BY_ID times out
    When the request is sent
    Then the response status code should be 500
    And cassandra query GET_BOOK_BY_ID was executed once

  @simulacron
  Scenario: Get book by id returns 500 when node is unavailable
    Given an endpoint GET_BOOK_BY_ID is prepared with path parameter book-id
    And cassandra query GET_BOOK_BY_ID gets node unavailable
    When the request is sent
    Then the response status code should be 500
    And cassandra query GET_BOOK_BY_ID was executed once
