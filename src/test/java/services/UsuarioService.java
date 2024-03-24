package services;

import com.github.javafaker.Faker;
import dto.UsuarioDto;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class UsuarioService {

    private Response response;
    private String requestBody;
    private String idUsuario;
    private UsuarioDto usuarioDto;
    private Faker faker;
    private String novoIdUsuario;

    public String gerarDadosUsuario(){
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

        return requestBody;
    }

    public Response criarNovoUsuario(String url, String requestBody){
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

    public String validarDadosUsuarioCadastrado(Response response, int statusCode){
        JsonPath jsonPathEvaluator = response.jsonPath();

        assertEquals(statusCode, response.getStatusCode());
        assertEquals("Cadastro realizado com sucesso",jsonPathEvaluator.get("message"));

        idUsuario = jsonPathEvaluator.get("_id");

        return idUsuario;
    }

    public String verificarUsuarioExistente(String idUsuario){
        String novoBody;
        Response novoResponse;
        if (idUsuario == null){
            novoBody = gerarDadosUsuario();
            novoResponse = criarNovoUsuario("https://serverest.dev/usuarios",novoBody);
            novoIdUsuario = validarDadosUsuarioCadastrado( novoResponse, 201);
        }
        else{
            novoIdUsuario = idUsuario;
        }
        return novoIdUsuario;
    }

    public Response consultarUsuario(String url, String novoIdUsuario){
        response = given()
                .header("Content-type", "application/json")
                .and()
                .log().all()
                .when()
                .get(url + "/" + novoIdUsuario)
                .then()
                .log().all()
                .extract().response();

        return response;
    }

    public void validarDadosUsuarioConsultado(Response response, String novoId, int statusCode){
        JsonPath jsonPathEvaluator = response.jsonPath();


        if (novoIdUsuario.equals(novoId)) {
            assertEquals(statusCode, response.getStatusCode());
            assertEquals(novoIdUsuario, jsonPathEvaluator.get("_id"));
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

    public void validarDadosUsuarioNaoCadastrado(Response response, int statusCode){
        JsonPath jsonPathEvaluator = response.jsonPath();

        assertEquals("Usuário não encontrado", jsonPathEvaluator.get("message"));
        assertEquals(statusCode, response.getStatusCode());

    }
}
