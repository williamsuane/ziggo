package nl.vodafoneziggo.assignment.order;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import nl.vodafoneziggo.assignment.model.OrderPostRequestBody;
import nl.vodafoneziggo.assignment.model.OrderPostResponseBody;
import nl.vodafoneziggo.assignment.user.UserController;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
class OrderService {

  private final OrderRepository repository;
  private final UserController userController;

  List<Order> listAll() {
    return repository.findAll();
  }

  OrderPostResponseBody create(OrderPostRequestBody postRequestBody) {
    var email = postRequestBody.getEmail();
    var productId = postRequestBody.getProductId();

    assertProductWasNotOrderedAlready(email, productId);

    var userDto = userController.findUserByEmail(postRequestBody.getEmail())
        .orElseThrow(() -> new InvalidOrderUserNotFoundException("No user found %s".formatted(email)));

    var order = OrderMapper.INSTANCE.toOrder(userDto, postRequestBody);

    repository.save(order);

    return new OrderPostResponseBody().orderId(order.getOrderId());
  }

  private void assertProductWasNotOrderedAlready(String email, String productId) {
    log.debug("Checking if product was already ordered for user '{}' and product '{}'", email, productId);

    repository.findByEmailAndProductId(email, productId)
        .ifPresent(o -> {
          throw new InvalidOrderAlreadyOrderedException("Product already ordered");
        });

  }

}
