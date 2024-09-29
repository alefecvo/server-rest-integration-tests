
package services;

import com.github.javafaker.Faker;
import dto.UserDto;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class UserService {

    private Response response;
    private String requestBody;
    private String userId;
    private UserDto userDto;
    private Faker faker;
    private String newUserId;

    public String generateUserData(){
        faker = new Faker();

        userDto = new UserDto();
        userDto.setName(faker.name().name());
        userDto.setEmail(faker.internet().emailAddress());
        userDto.setPassword("123456");
        userDto.setAdmin("true");

        requestBody = "{\n" +
                "  \"nome\": \""+userDto.getName()+"\",\n" +
                "  \"email\": \""+userDto.getEmail()+"\",\n" +
                "  \"password\": \""+ userDto.getPassword()+"\",\n" +
                "  \"administrador\": \""+ userDto.getAdmin()+"\"\n" +
                "}";

        return requestBody;
    }

    public Response createNewUser(String url, String requestBody){
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

    public String validateRegisteredUserData(Response response, int statusCode){
        JsonPath jsonPathEvaluator = response.jsonPath();

        assertEquals(statusCode, response.getStatusCode());

        userId = jsonPathEvaluator.get("_id");

        return userId;
    }

    public String checkExistingUser(String userId){
        String newBody;
        Response newResponse;
        if (userId == null){
            newBody = generateUserData();
            newResponse = createNewUser("https://serverest.dev/usuarios",newBody);
            newUserId = validateRegisteredUserData(newResponse, 201);
        }
        else{
            newUserId = userId;
        }
        return newUserId;
    }

    public Response queryUser(String url, String newUserId){
        response = given()
                .header("Content-type", "application/json")
                .and()
                .log().all()
                .when()
                .get(url + "/" + newUserId)
                .then()
                .log().all()
                .extract().response();

        return response;
    }

    public void validateQueriedUserData(Response response, String newId, int statusCode){
        JsonPath jsonPathEvaluator = response.jsonPath();

        if (newUserId.equals(newId)) {
            assertEquals(statusCode, response.getStatusCode());
            assertEquals(newUserId, jsonPathEvaluator.get("_id"));
        }
        else {
            assertEquals(userDto.getName(), jsonPathEvaluator.get("name"));
            assertEquals(userDto.getEmail(), jsonPathEvaluator.get("email"));
            assertEquals(userDto.getPassword(), jsonPathEvaluator.get("password"));
            assertEquals(userDto.getAdmin(), jsonPathEvaluator.get("admin"));
            assertEquals(statusCode, response.getStatusCode());
            assertEquals(userId, jsonPathEvaluator.get("_id"));
        }
    }

    public void validateNonRegisteredUserData(Response response, int statusCode){
        assertEquals(statusCode, response.getStatusCode());
    }
}
