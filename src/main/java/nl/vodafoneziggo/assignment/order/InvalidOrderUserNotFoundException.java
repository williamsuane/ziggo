package nl.vodafoneziggo.assignment.order;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
class InvalidOrderUserNotFoundException extends RuntimeException{

  public InvalidOrderUserNotFoundException(String message) {
    super(message);
  }
}
