package stepDefinitions;

import com.github.javafaker.Faker;
import dto.UsuarioDto;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class UsuarioSteps {

    private Response response;
    private String requestBody;
    private String idUsuario;
    private UsuarioDto usuarioDto;
    private Faker faker;

    @Given("Eu tenho os dados para cadastrar um novo usuario")
    public void eu_tenho_os_dados_para_cadastrar_um_novo_usuario() {
        faker = new Faker();

        usuarioDto = new UsuarioDto();
        usuarioDto.setNome(faker.name().name());
        usuarioDto.setEmail(faker.internet().emailAddress());
        usuarioDto.setSenha("123456");
        usuarioDto.setAdministrador("true");

        requestBody = "{\n" +
                "  \"nome\": \""+usuarioDto.getNome()+"\",\n" +
                "  \"email\": \""+usuarioDto.getEmail()+"\",\n" +
                "  \"password\": \""+ usuarioDto.getSenha()+"\",\n" +
                "  \"administrador\": \""+ usuarioDto.getAdministrador()+"\"\n" +
                "}";
    }

    @When("Eu faço um pedido POST para a URL {string}")
    public void eu_faco_um_pedido_post_para_a_url(String url) {
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
    }

    @Then("O status do código de resposta deve ser {int}")
    public void o_status_do_codigo_de_resposta_deve_ser(int statusCode) {
        JsonPath jsonPathEvaluator = response.jsonPath();

        assertEquals(statusCode, response.getStatusCode());
        assertEquals("Cadastro realizado com sucesso",jsonPathEvaluator.get("message"));

        idUsuario = jsonPathEvaluator.get("_id");
    }



    @Given("Eu tenho um usuario cadastrado")
    public void eu_tenho_um_usuario_cadastrado() {
        if (idUsuario == null)
            idUsuario = "fprt2XYUEiT72V7V";
    }

    @When("Eu faço um pedido GET para a URL {string}")
    public void eu_faco_um_pedido_get_para_a_url(String url) {
        response = given()
                .header("Content-type", "application/json")
                .and()
                .log().all()
                .when()
                .get(url + "/" + idUsuario)
                .then()
                .log().all()
                .extract().response();
    }

    @Then("O status do código de resposta deve ser de {int}")
    public void o_status_do_codigo_de_resposta_deve_ser_de(int statusCode) {
        JsonPath jsonPathEvaluator = response.jsonPath();

        if (idUsuario.equals("fprt2XYUEiT72V7V")) {
            assertEquals(statusCode, response.getStatusCode());
            assertEquals(idUsuario, jsonPathEvaluator.get("_id"));
        }
        else {
            assertEquals(usuarioDto.getNome(), jsonPathEvaluator.get("nome"));
            assertEquals(usuarioDto.getEmail(), jsonPathEvaluator.get("email"));
            assertEquals(usuarioDto.getSenha(), jsonPathEvaluator.get("password"));
            assertEquals(usuarioDto.getAdministrador(), jsonPathEvaluator.get("administrador"));
            assertEquals(statusCode, response.getStatusCode());
            assertEquals(idUsuario, jsonPathEvaluator.get("_id"));
        }
    }
}