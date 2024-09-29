package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.Assert;
import services.UserService;

public class UserSteps {

    private Response response;

    private String body;
    private String idUser;
    private final UserService userService = new UserService();

    @Given("I have the data to register a new user")
    public void i_have_the_data_to_register_a_new_user() {
        body = userService.generateUserData();
    }

    @When("I make a POST request to the URL {string}")
    public void i_make_a_post_request_to_the_url(String url) {
        response = userService.createNewUser(url, body);
    }

    @Then("The response status code should be {int} to a new user")
    public void the_response_status_code_should_be_to_a_new_user(int statusCode) {
        idUser = userService.validateRegisteredUserData(response, statusCode);
    }

    @Given("I do not have a registered user but wish to register")
    public void i_do_not_have_a_registered_user_but_wish_to_register() {
        idUser = userService.checkExistingUser(null);
    }

    @When("I make a GET request to the URL {string}")
    public void i_make_a_get_request_to_the_url(String url) {
        response = userService.queryUser(url, idUser);
    }

    @Then("The response status code should be {int} to registered user")
    public void the_response_status_code_should_be_to_registered_user(int statusCode) {
        userService.validateQueriedUserData(response, idUser, statusCode);
    }

    @Given("I do not have a registered user {string}")
    public void i_do_not_have_a_registered_user(String newUserId) {
        idUser = newUserId;
    }

    @When("I make an attempt to GET request to the URL {string}")
    public void i_make_an_attempt_to_get_request_to_the_url(String url) {
        response = userService.queryUser(url, idUser);
    }

    @Then("The response status code should be {int} to non-registered user")
    public void the_response_status_code_should_be_to_non_registered_user(int statusCode) {
        userService.validateNonRegisteredUserData(response, statusCode);
    }
}