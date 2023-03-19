package steps;

import api.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static api.Endpoints.*;
import static io.restassured.RestAssured.*;
import static org.junit.Assert.*;
import static token.AccessToken.getToken;

public class ApiSteps {

    private Response response;
    private OrderConfirmation orderConfirmation;

    @Given("I make a GET request to the status endpoint")
    public void i_make_a_get_request_to_the_status_endpoint() {
        response = when()
                .get(BASE_URL.getPath()+STATUS.getPath())
                .thenReturn();

    }

    @Then("I should receive a 200 status code and {string} status")
    public void i_should_receive_a_status_code_and_status(String expectedStatus) {
        assertEquals(200, response.getStatusCode());
        Status status = response.as(Status.class);
        assertEquals(expectedStatus, status.getStatus());
    }

    @Given("I make a GET request to the book with ID {string} endpoint")
    public void i_make_a_get_request_to_the_book_with_id_endpoint(String bookId) {
        response = when()
                .get(BASE_URL.getPath() + BOOKS.getPath() + "/" + bookId)
                .thenReturn();

    }

    @Then("I should receive a 200 status code and the book details")
    public void i_should_receive_a_status_code_and_the_book_details() {
        assertEquals(200, response.getStatusCode());
        Book book = response.as(Book.class);
        assertEquals(Integer.valueOf(1), book.getId());
        assertEquals("The Russian", book.getName());
        assertEquals("James Patterson and James O. Born", book.getAuthor());
        assertEquals("1780899475", book.getIsbn());
        assertEquals(Double.valueOf(12.98), book.getPrice());
        assertEquals(Integer.valueOf(12), book.getCurrentStock());
        assertEquals(true, book.getAvailable());
        assertEquals("fiction", book.getType());
    }

    @Given("I make a GET request to the books endpoint")
    public void i_make_a_get_request_to_the_books_endpoint() {
        response = when()
                .get(BASE_URL.getPath() + BOOKS.getPath())
                .thenReturn();
    }

    @Then("I should receive a 200 status code and the list of all books")
    public void i_should_receive_a_status_code_and_the_list_of_all_books() {
        assertEquals(200, response.getStatusCode());
        Book[] books = response.as(Book[].class);
        assertEquals(6, books.length);

    }

    @Given("I make a GET request to the books endpoint with type {string} and limit {string}")
    public void i_make_a_get_request_to_the_books_endpoint_with_type_and_limit(String type, String limit) {
        response = given()
                .queryParam("type", type)
                .queryParam("limit", limit)
                .get(BASE_URL.getPath() + BOOKS.getPath())
                .thenReturn();
    }


    @Then("I should receive a 200 status code and the list of books filtered by type {string}")
    public void iShouldReceiveAStatusCodeAndTheListOfBooksFilteredByType( String bookType) {
        assertEquals(200, response.getStatusCode());
        Book[] books = response.as(Book[].class);
        for (Book book : books) {
            assertEquals(bookType, book.getType());
        }
    }

    @Given("I make a POST request to the orders endpoint with book ID {string} and customer name {string}")
    public void i_make_a_post_request_to_the_orders_endpoint_with_book_id_and_customer_name(String bookId, String customerName) {
        Map<String, Object> bookObject = new HashMap<>();
        bookObject.put("bookId", Integer.parseInt(bookId));
        bookObject.put("customerName", customerName);

        response = given()
                .headers(
                        "Authorization",
                        "Bearer " + getToken(),
                        "Content-Type",
                        ContentType.JSON,
                        "Accept",
                        ContentType.JSON)
                .body(bookObject)
                .when()
                .post(BASE_URL.getPath() + ORDERS.getPath())
                .thenReturn();

        orderConfirmation = response.as(OrderConfirmation.class);
    }

    @Then("I should receive a 201 status code and the order confirmation")
    public void i_should_receive_a_status_code_and_the_order_confirmation() {
        assertEquals(201, response.getStatusCode());
        assertTrue(orderConfirmation.getCreated());
        assertNotNull(orderConfirmation.getOrderId());
    }

    @Given("I make a GET request to the order with the previously created ID endpoint")
    public void i_make_a_get_request_to_the_order_with_the_previously_created_id_endpoint() {
        response = given()
                .headers(
                        "Authorization", "Bearer " + getToken(),
                        "Content-Type", ContentType.JSON,
                        "Accept", ContentType.JSON)
                .when()
                .get(BASE_URL.getPath() + ORDERS.getPath() + "/" + orderConfirmation.getOrderId())
                .thenReturn();
    }

    @Then("I should receive a 200 status code and the order details")
    public void i_should_receive_a_status_code_and_the_order_details() {
        assertEquals(200, response.getStatusCode());
        OrderInformation orderInformation = response.as(OrderInformation.class);
        assertEquals(200, response.getStatusCode());
        assertEquals(true, orderConfirmation.getCreated());
        assertNotNull(orderConfirmation.getOrderId());
    }

    @Given("I make a DELETE request to the order with the previously created ID endpoint")
    public void i_make_a_delete_request_to_the_order_with_the_previously_created_id_endpoint() {
        response = given()
                .headers(
                        "Authorization", "Bearer " + getToken(),
                        "Content-Type", ContentType.JSON,
                        "Accept", ContentType.JSON)
                .when()
                .delete(Endpoints.BASE_URL.getPath()+ ORDERS.getPath() + "/" + orderConfirmation.getOrderId())
                .thenReturn();
    }

    @Then("I should receive a 204 status code")
    public void i_should_receive_a_status_code() {
        assertEquals(204, response.getStatusCode());
    }

    @Then("I should not be able to retrieve the deleted order")
    public void i_should_not_be_able_to_retrieve_the_deleted_order() {
        Response deletedResponse = given()
                .headers(
                        "Authorization", "Bearer " + getToken(),
                        "Content-Type", ContentType.JSON,
                        "Accept", ContentType.JSON)
                .when()
                .get(BASE_URL.getPath() + ORDERS.getPath() + "/" + orderConfirmation.getOrderId())
                .thenReturn();

        ErrorResponse er = deletedResponse.as(ErrorResponse.class);

        assertTrue(er.getError().equalsIgnoreCase("No order with id " + orderConfirmation.getOrderId() + "."));
    }

}
