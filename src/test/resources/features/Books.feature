@Books
@API
Feature: Books API

  Scenario: GET /books, No API Access
    Given G-Token is not provided
    When all books are requested
    Then 403 is returned

  Scenario: GET /books, Valid API Access
    Given G-Token is provided
    When all books are requested
    Then 200 is returned

  Scenario: GET /books/{id}, Search by ID, Valid ID
    Given G-Token is provided
    When a book is requested by ID
    Then 200 is returned
    And the ISBN of the returned book is 1593281056

  Scenario: GET /books/{id}, Search by ID, Invalid ID
    Given G-Token is provided
    When a book is requested by an invalid ID
    Then 404 is returned

  Scenario: GET /books&title={title}, Search by Title, Valid Title
    Given G-Token is provided
    When a book is requested by title
    Then 200 is returned
    And the ISBN of the returned book is 0060652934

  Scenario: GET /books&title={title}, Search by Title, Invalid Title
    Given G-Token is provided
    When a book is requested by a title that does not exist
    Then 200 is returned
    And the response contains no books

  Scenario: GET /books?author={author}, Search by Author, Invalid Author
    Given G-Token is provided
    When a book is requested by author that does not exist
    Then 200 is returned
    And the response contains no books

  Scenario: POST /books, Add Book
    Given a book
    When a book is added
    Then 201 is returned
    And the response contains the added book

  Scenario: DELETE /books/{id}, Valid Basic Auth
    Given a book has been added
    And Basic Auth credentials are provided
    When the book is deleted
    Then 204 is returned

  Scenario: DELETE /books/{id}, Invalid Basic Auth
    Given a book has been added
    And Basic Auth credentials are not provided
    When the book is deleted
    Then 401 is returned