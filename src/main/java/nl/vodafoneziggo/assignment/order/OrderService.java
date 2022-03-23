package nl.vodafoneziggo.assignment.order;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import nl.vodafoneziggo.assignment.model.OrderPostRequestBody;
import nl.vodafoneziggo.assignment.model.OrderPostResponseBody;
import nl.vodafoneziggo.assignment.user.UserController;
import nl.vodafoneziggo.assignment.user.UserDto;
import nl.vodafoneziggo.assignment.user.UserPageResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

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
    assertProductWasNotOrderedAlready(postRequestBody.getEmail(), postRequestBody.getProductId());

    var userDto = findUserByEmail(postRequestBody.getEmail());
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

  private UserDto findUserByEmail(String email) {
    log.debug("Retrieving remote users from all pages");
    int page = 1;
    List<UserDto> users = new ArrayList<>();

    while (true) {
      UserPageResponseDto userPageResponseDto = userController.executeRemoteRequestToFindUsers(page);

      extractRemoteUsersToLocalList(users, userPageResponseDto);

      if (userPageResponseDto.getTotalPages() == page) {
        log.debug("Finished requesting user from all available pages!");
        return findUserOrThrowException(email, users);
      }

      page++;
    }

  }

  private UserDto findUserOrThrowException(String email, List<UserDto> users) {
    log.debug("Finding if remote API contains user by email '{}'", email);
    return users.stream()
        .filter(u -> u.getEmail().equals(email))
        .findFirst()
        .orElseThrow(() -> new InvalidOrderUserNotFoundException("User not found for email %s".formatted(email)));
  }


  private void extractRemoteUsersToLocalList(List<UserDto> users, UserPageResponseDto userPageResponseDto) {
    if (!CollectionUtils.isEmpty(userPageResponseDto.getUsers())) {
      log.debug("Extracting '{}' users for current page", userPageResponseDto.getUsers().size());
      users.addAll(userPageResponseDto.getUsers());
    }
  }

}
