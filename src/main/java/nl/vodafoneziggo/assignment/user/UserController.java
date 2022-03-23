package nl.vodafoneziggo.assignment.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.client.WebClient;

@Controller
@RequiredArgsConstructor
@Log4j2
public class UserController {
  private final UserService userService;

  public UserPageResponseDto executeRemoteRequestToFindUsers(int page) {
    userService.executeRemoteRequestToFindUsers(page);
    log.debug("Executing request to find available users, current page '{}'", page);

    return WebClient.create()
        .get()
        .uri("https://reqres.in/api/users?page={page}", page)
        .retrieve()
        .bodyToMono(UserPageResponseDto.class)
        .block();
  }
}
