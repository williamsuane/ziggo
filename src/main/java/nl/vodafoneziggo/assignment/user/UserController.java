package nl.vodafoneziggo.assignment.user;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Log4j2
public class UserController {

  private final UserService userService;

  public Optional<UserDto> findUserByEmail(String email) {
    log.debug("Executing request to find user by email '{}'", email);

    return userService.findUserByEmail(email);
  }
}
