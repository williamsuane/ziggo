package nl.vodafoneziggo.assignment.order;

import java.util.List;
import nl.vodafoneziggo.assignment.model.OrderGetResponseBody;
import nl.vodafoneziggo.assignment.model.OrderPostRequestBody;
import nl.vodafoneziggo.assignment.model.OrderPostResponseBody;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;
import org.springframework.web.reactive.function.BodyInserters;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderControllerTest {

  private static final String URL = "/api/v1/orders";
  @Autowired
  private WebTestClient webTestClient;

  @Test
  @DisplayName("listAll returns empty list when no orders are available")
  @Order(1)
  void listAll_ReturnsEmptyList_WhenNoOrdersAreAvailable() {
    var typeReference = new ParameterizedTypeReference<List<OrderGetResponseBody>>() {
    };
    var response = getRequest()
        .expectStatus()
        .isOk()
        .expectBody(typeReference)
        .returnResult()
        .getResponseBody();

    Assertions.assertThat(response).isEmpty();
  }


  @Test
  @DisplayName("create returns orderId when email is valid and there is no producted ordered for the user")
  @Order(2)
  void create_ReturnsOrderId_WhenEmailExistsAndProductIsNew() {
    var response = postRequest(OrderRequestFactory.newValidPostRequestBody())
        .expectStatus()
        .isCreated()
        .expectBody(OrderPostResponseBody.class)
        .returnResult()
        .getResponseBody();

    Assertions.assertThat(response).isNotNull();
    Assertions.assertThat(response.getOrderId()).isNotNull();
  }

  @Test
  @DisplayName("listAll returns list of orders when available in the database")
  @Order(3)
  void listAll_ReturnsListOfOrders_WhenAvailabeInDatabase() {
    var typeReference = new ParameterizedTypeReference<List<OrderGetResponseBody>>() {
    };
    var response = getRequest()
        .expectStatus()
        .isOk()
        .expectBody(typeReference)
        .returnResult()
        .getResponseBody();

    Assertions.assertThat(response).isNotEmpty().hasSize(1);
    Assertions.assertThat(response.get(0)).hasNoNullFieldsOrProperties();
  }

  @Test
  @DisplayName("create throws bad request when email is valid and product is already ordered")
  @Order(4)
  void create_ReturnsThrowsBadRequest_WhenProductIsAlreadyOrdered() {
    postRequest(OrderRequestFactory.newValidPostRequestBody())
        .expectStatus()
        .isBadRequest();
  }

  @Test
  @DisplayName("create throws not found when email does not exist in the remote api")
  @Order(99)
  void create_ThrowsNotFound_WhenEmailDoesNotExist() {
    postRequest(OrderRequestFactory.newValidPostRequestBody().email("test@email.com"))
        .expectStatus()
        .isNotFound();
  }


  @Test
  @DisplayName("save throws bad request exception when email is not formatted")
  @Order(99)
  void create_ThrowsBadRequest_WhenEmailIsNotFormatted() {
    var invalidRequestBody = OrderRequestFactory.newValidPostRequestBody().email("invalid");
    postRequest(invalidRequestBody)
        .expectStatus()
        .isBadRequest();

  }

  @Test
  @DisplayName("save throws bad request exception when email is null or empty")
  @Order(99)
  void create_ThrowsBadRequest_WhenEmailNullOrEmpty() {
    var invalidRequestBody = OrderRequestFactory.newValidPostRequestBody().email("");
    postRequest(invalidRequestBody)
        .expectStatus()
        .isBadRequest();

    postRequest(invalidRequestBody.email(null))
        .expectStatus()
        .isBadRequest();
  }

  private ResponseSpec getRequest() {
    return webTestClient.get()
        .uri(URL)
        .exchange();
  }

  private ResponseSpec postRequest(OrderPostRequestBody postRequestBody) {
    return webTestClient.post()
        .uri(URL)
        .body(BodyInserters.fromValue(postRequestBody))
        .exchange();
  }
}