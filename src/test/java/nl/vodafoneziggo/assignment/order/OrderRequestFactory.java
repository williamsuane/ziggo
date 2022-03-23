package nl.vodafoneziggo.assignment.order;


import nl.vodafoneziggo.assignment.model.OrderPostRequestBody;

public class OrderRequestFactory {

  public static OrderPostRequestBody newPostRequestBody() {
    return new OrderPostRequestBody()
        .email("test@test.com")
        .productId("123");
  }
}
