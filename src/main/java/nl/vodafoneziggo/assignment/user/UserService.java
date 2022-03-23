package nl.vodafoneziggo.assignment.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import nl.vodafoneziggo.assignment.shared.config.ZiggoRemoteUrl;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Log4j2
class UserService {

  private final ZiggoRemoteUrl ziggoRemoteUrl;

  Optional<UserDto> findUserByEmail(String email) {
    log.debug("Retrieving remote users from all pages");
    int page = 1;
    List<UserDto> users = new ArrayList<>();

    while (true) {
      UserPageResponseDto userPageResponseDto = executeRemoteRequestToFindUsers(page);

      extractRemoteUsersToLocalList(users, userPageResponseDto);

      if (userPageResponseDto.getTotalPages() == page) {
        log.debug("Finished requesting user from all available pages!");
        return findUserOrThrowException(email, users);
      }

      page++;
    }

  }

  private UserPageResponseDto executeRemoteRequestToFindUsers(int page) {
    log.debug("Executing request to find available users, current page '{}'", page);

    var userPageResponseDto = WebClient.create()
        .get()
        .uri(ziggoRemoteUrl.getUserUrl(), page)
        .retrieve()
        .bodyToMono(UserPageResponseDto.class)
        .block();

    assertResponseIsAvailable(userPageResponseDto);

    return userPageResponseDto;
  }

  private Optional<UserDto> findUserOrThrowException(String email, List<UserDto> users) {
    log.debug("Finding if remote API contains user by email '{}'", email);
    return users.stream()
        .filter(u -> u.getEmail().equals(email))
        .findFirst();
  }


  private void extractRemoteUsersToLocalList(List<UserDto> users, UserPageResponseDto userPageResponseDto) {
    if (!CollectionUtils.isEmpty(userPageResponseDto.getUsers())) {
      log.debug("Extracting '{}' users for current page", userPageResponseDto.getUsers().size());
      users.addAll(userPageResponseDto.getUsers());
    }
  }


  private void assertResponseIsAvailable(UserPageResponseDto userPageResponseDto) {
    log.debug("Asserting response is available");

    if (userPageResponseDto == null) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to reach User API");
    }
  }
}
