package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.Assert;
import services.UserMockService;

public class UserMockSteps {

    private Response response;

    private String body;
    private String idUser;
    private final UserMockService userMockService = new UserMockService();

    @Given("I have the data to register a new user wiremock")
    public void i_have_the_data_to_register_a_new_user_wiremock() {
        body = userMockService.generateUserDataWiremock();
    }

    @When("I make a POST request to the URL {string} wiremock")
    public void i_make_a_post_request_to_the_url_wiremock(String url) {
        response = userMockService.createNewUserWiremock(url, body);
    }

    @Then("The response status code should be {int} to a new user wiremock")
    public void the_response_status_code_should_be_to_a_new_user_wiremock(int statusCode) {
        idUser = userMockService.validateRegisteredUserDataWiremock(response, statusCode);
        Assert.assertEquals("jogfODIlXsqxNFS2", response.path("_id"));
    }
}