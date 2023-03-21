Feature: API testing
  Scenario: Test status endpoint
    Given I make a GET request to the status endpoint
    Then I should receive a 200 status code and "OK" status

  Scenario: Get a single book by ID
    Given I make a GET request to the book with ID "1" endpoint
    Then I should receive a 200 status code and the book details

  Scenario: Get a list of all books
    Given I make a GET request to the books endpoint
    Then I should receive a 200 status code and the list of all books

  Scenario: Get a list of books filtered by type
    Given I make a GET request to the books endpoint with type "fiction" and limit "10"
    Then I should receive a 200 status code and the list of books filtered by type "fiction"

  Scenario: Submit an order
    Given I make a POST request to the orders endpoint with book ID "1" and customer name "Omer AKBEN"
    Then I should receive a 201 status code and the order confirmation

  Scenario: Get an order by ID
    Given I make a GET request to the order with the previously created ID endpoint
    Then I should receive a 200 status code and the order details

  Scenario: Delete an order by ID
    Given I make a DELETE request to the order with the previously created ID endpoint
    Then I should receive a 204 status code
    And I should not be able to retrieve the deleted order


    Scenario: Shrinking the patient details page to one row
      Given as a Nurse credentials go to the Xsolispage
      And Select any patient
      And Click to the patient details page
      And Scroll down to the page
      Then Shirnk on the top row will display
