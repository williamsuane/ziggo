package nl.vodafoneziggo.assignment.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Log4j2
class UserService {

  UserPageResponseDto executeRemoteRequestToFindUsers(int page) {
    log.debug("Executing request to find available users, current page '{}'", page);

    var userPageResponseDto = WebClient.create()
        .get()
        .uri("https://reqres.in/api/users?page={page}", page)
        .retrieve()
        .bodyToMono(UserPageResponseDto.class)
        .block();
    assertResponseIsAvailable(userPageResponseDto);

    return userPageResponseDto;
  }

  private void assertResponseIsAvailable(UserPageResponseDto userPageResponseDto) {
    log.debug("Asserting response is available");

    if (userPageResponseDto == null) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to reach User API");
    }
  }
}
