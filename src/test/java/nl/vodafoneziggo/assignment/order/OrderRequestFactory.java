package nl.vodafoneziggo.assignment.order;


import nl.vodafoneziggo.assignment.model.OrderPostRequestBody;

class OrderRequestFactory {

  public static OrderPostRequestBody newValidPostRequestBody() {
    return new OrderPostRequestBody()
        .email("george.edwards@reqres.in")
        .productId("123");
  }
}
