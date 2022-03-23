package nl.vodafoneziggo.assignment.order;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Collections;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import nl.vodafoneziggo.assignment.api.OrdersApi;
import nl.vodafoneziggo.assignment.model.OrderGetResponseBody;
import nl.vodafoneziggo.assignment.model.OrderPostRequestBody;
import nl.vodafoneziggo.assignment.model.OrderPostResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Orders", description = "Orders API")
@RequiredArgsConstructor
@Log4j2
public class OrderController implements OrdersApi {

  private final OrderService orderService;

  @Override
  public ResponseEntity<List<OrderGetResponseBody>> listAll() {
    log.debug("Request received to list all orders in '{}'", getClass().getSimpleName());
    return ResponseEntity.ok(OrderMapper.INSTANCE.toOrderGetResponseBody(orderService.listAll()));
  }

  @Override
  public ResponseEntity<OrderPostResponseBody> create(@RequestBody @Valid OrderPostRequestBody requestBody) {
    log.debug("Request received to create an order '{}' in '{}'", requestBody, getClass().getSimpleName());
    return new ResponseEntity<>(orderService.create(requestBody), HttpStatus.CREATED);
  }
}
