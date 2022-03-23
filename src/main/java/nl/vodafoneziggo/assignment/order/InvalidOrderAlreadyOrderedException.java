package nl.vodafoneziggo.assignment.order;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
class InvalidOrderAlreadyOrderedException extends RuntimeException {

  public InvalidOrderAlreadyOrderedException(String message) {
    super(message);
  }
}
