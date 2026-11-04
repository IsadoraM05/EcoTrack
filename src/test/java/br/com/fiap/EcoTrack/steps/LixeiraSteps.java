
package br.com.fiap.EcoTrack.steps;

import java.io.InputStream;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

import io.cucumber.java.pt.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class LixeiraSteps {

    private Response resposta;
    private Long localizacaoId;
    private Map<String, Object> dadosLixeira;

    private final String baseUrl =
            System.getProperty("api.url", "http://localhost:8080");

    private final String usuario =
            System.getProperty("api.user", "");

    private final String senha =
            System.getProperty("api.password", "");

    private io.restassured.specification.RequestSpecification requisicao() {
        return RestAssured.given()
                .baseUri(baseUrl)
                .auth().preemptive().basic(usuario, senha)
                .contentType("application/json");
    }

    @Dado("que existe uma localização cadastrada")
    public void existeLocalizacao() {

        Map<String, Object> localizacao = Map.of(
                "nome", "Local Teste BDD",
                "tipoLocal", "EMPRESA",
                "endereco", "Rua Teste, 100"
        );

        Response criacao = requisicao()
                .body(localizacao)
                .post("/localizacoes");

        assertEquals(200, criacao.statusCode(),
                "Não foi possível criar a localização de teste");

        localizacaoId =
                criacao.jsonPath().getLong("localizacaoId");
    }

    @Dado("que possuo dados válidos de uma lixeira")
    public void dadosValidos() {
        dadosLixeira = Map.of(
                "tipoResiduo", "PLASTICO",
                "capacidadeMaxima", 100.0,
                "nivelAtual", 20.0,
                "status", "DISPONIVEL",
                "localizacaoId", localizacaoId
        );
    }

    @Dado("que possuo uma lixeira com capacidade negativa")
    public void capacidadeNegativa() {
        dadosLixeira = Map.of(
                "tipoResiduo", "PLASTICO",
                "capacidadeMaxima", -100.0,
                "nivelAtual", 20.0,
                "status", "DISPONIVEL",
                "localizacaoId", localizacaoId
        );
    }

    @Quando("envio uma requisição para cadastrar a lixeira")
    public void cadastrarLixeira() {
        resposta = requisicao()
                .body(dadosLixeira)
                .post("/lixeiras");
    }

    @Então("a API deve retornar o status {int}")
    public void verificarStatus(int status) {
        assertEquals(status, resposta.statusCode(),
                "Resposta inesperada: " + resposta.asString());
    }

    @Então("a resposta deve conter os dados da lixeira cadastrada")
    public void validarDados() {
        resposta.then()
                .body("lixeiraId", notNullValue())
                .body("tipoResiduo", equalTo("PLASTICO"))
                .body("capacidadeMaxima", equalTo(100.0f))
                .body("nivelAtual", equalTo(20.0f))
                .body("status", equalTo("DISPONIVEL"))
                .body("localizacaoId",
                        equalTo(localizacaoId.intValue()));
    }

    @Entao("o JSON deve respeitar o contrato definido")
    public void validarContrato() {

        InputStream schema = getClass()
                .getClassLoader()
                .getResourceAsStream("schemas/lixeira-schema.json");

        assertNotNull(
                schema,
                "O arquivo lixeira-schema.json não foi encontrado no classpath"
        );

        resposta.then()
                .body(matchesJsonSchema(schema));
    }

    @Dado("que não existe uma lixeira com o identificador informado")
    public void lixeiraInexistente() {
        // Será utilizado um ID reservado para este teste.
    }

    @Quando("envio uma requisição para consultar essa lixeira")
    public void consultarLixeira() {
        resposta = requisicao()
                .get("/lixeiras/999999999");
    }
}
