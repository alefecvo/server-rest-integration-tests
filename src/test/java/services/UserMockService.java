
package services;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class UserMockService {

    private Response response;
    private String requestBody;
    private String userId;

    public String generateUserDataWiremock(){
        requestBody = "{\n" +
                "  \"nome\": \"Wiremock\",\n" +
                "  \"email\": \"wiremock@qa.com.br\",\n" +
                "  \"password\": \"123456\",\n" +
                "  \"administrador\": \"true\"\n" +
                "}";

        return requestBody;
    }

    public Response createNewUserWiremock(String url, String requestBody){
        response = given()
                .header("Content-type", "application/json")
                .and()
                .log().all()
                .body(requestBody)
                .when()
                .post(url)
                .then()
                .log().all()
                .extract().response();
        return response;
    }

    public String validateRegisteredUserDataWiremock(Response response, int statusCode){
        JsonPath jsonPathEvaluator = response.jsonPath();

        assertEquals(statusCode, response.getStatusCode());

        userId = jsonPathEvaluator.get("_id");

        return userId;
    }


}
