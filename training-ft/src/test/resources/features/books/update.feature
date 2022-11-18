Feature: Checking books get all endpoint return expected outputs

#  Scenario: Update a book happy-path scenario
#    Given an endpoint UPDATE_BOOK is prepared with path parameter book-id
#    When the request is sent
#    Then the response status code should be 200

#  Scenario: Update a book returns 500 when database times out
#    Given an endpoint UPDATE_BOOK is prepared with path parameter book-id
#    And cassandra query UPDATE_BOOK times out
#    When the request is sent
#    Then the response status code should be 500
#    And cassandra query UPDATE_BOOK was executed once
