Feature: Checking books get all endpoint return expected outputs

  @simulacron
  Scenario: Update book returns 500 when database times out
    Given an endpoint UPDATE_BOOK is prepared with path parameter book-id
    And cassandra query GET_BOOK_BY_ID times out
    When the request is sent
    Then the response status code should be 500
    And cassandra query GET_BOOK_BY_ID was executed once

  @simulacron
  Scenario: Update book returns 500 when node is unavailable on insert query
    Given an endpoint UPDATE_BOOK is prepared with path parameter book-id
    And cassandra query GET_BOOK_BY_ID returns a book with id book-id
    And cassandra query INSERT_BOOK times out
    When the request is sent
    Then the response status code should be 500
    And cassandra query GET_BOOK_BY_ID was executed once
    And cassandra query INSERT_BOOK was executed once

  @simulacron
  Scenario: Update book returns 500 when node is unavailable
    Given an endpoint UPDATE_BOOK is prepared with path parameter book-id
    And cassandra query GET_BOOK_BY_ID gets node unavailable
    When the request is sent
    Then the response status code should be 500
    And cassandra query GET_BOOK_BY_ID was executed once

  @simulacron
  Scenario: Update book returns 500 when database times out on insert query
    Given an endpoint UPDATE_BOOK is prepared with path parameter book-id
    And cassandra query GET_BOOK_BY_ID returns a book with id book-id
    And cassandra query INSERT_BOOK gets node unavailable
    When the request is sent
    Then the response status code should be 500
    And cassandra query GET_BOOK_BY_ID was executed once
    And cassandra query INSERT_BOOK was executed once

