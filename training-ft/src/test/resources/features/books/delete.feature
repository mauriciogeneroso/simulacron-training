Feature: Checking books get all endpoint return expected outputs

  @simulacron
  Scenario: Delete book returns 500 when database times out on get query
    Given an endpoint DELETE_BOOK is prepared with path parameter book-id
    And cassandra query GET_BOOK_BY_ID times out
    When the request is sent
    Then the response status code should be 500
    And cassandra query GET_BOOK_BY_ID was executed once

  @simulacron
  Scenario: Delete book returns 500 when node is unavailable on delete query
    Given an endpoint DELETE_BOOK is prepared with path parameter book-id
    And cassandra query GET_BOOK_BY_ID returns a book with id book-id
    And cassandra query DELETE_BOOK gets node unavailable
    When the request is sent
    Then the response status code should be 500
    And cassandra query GET_BOOK_BY_ID was executed once
    And cassandra query DELETE_BOOK was executed once

  @simulacron
  Scenario: Delete book returns 500 when node is unavailable on get query
    Given an endpoint DELETE_BOOK is prepared with path parameter book-id
    And cassandra query GET_BOOK_BY_ID gets node unavailable
    When the request is sent
    Then the response status code should be 500
    And cassandra query GET_BOOK_BY_ID was executed once

  @simulacron
  Scenario: Delete book returns 500 when database times out on delete query
    Given an endpoint DELETE_BOOK is prepared with path parameter book-id
    And cassandra query GET_BOOK_BY_ID returns a book with id book-id
    And cassandra query DELETE_BOOK gets node unavailable
    When the request is sent
    Then the response status code should be 500
    And cassandra query GET_BOOK_BY_ID was executed once
    And cassandra query DELETE_BOOK was executed once
