package nl.vodafoneziggo.assignment.order;

import java.util.List;
import nl.vodafoneziggo.assignment.model.OrderGetResponseBody;
import nl.vodafoneziggo.assignment.model.OrderPostRequestBody;
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
  @DisplayName("save throws bad request exception when email is not formatted")
  @Order(99)
  void create_ThrowsBadRequest_WhenEmailIsNotFormatted() {
    var invalidRequestBody = OrderRequestFactory.newPostRequestBody().email("invalid");
    postRequest(invalidRequestBody)
        .expectStatus()
        .isBadRequest();

  }

  @Test
  @DisplayName("save throws bad request exception when email is null or empty")
  @Order(98)
  void create_ThrowsBadRequest_WhenEmailNullOrEmpty() {
    var invalidRequestBody = OrderRequestFactory.newPostRequestBody().email("");
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