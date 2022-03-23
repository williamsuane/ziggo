package nl.vodafoneziggo.assignment.user;

import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerTest {

  @Autowired
  private UserController userController;

  @Test
  @DisplayName("findUserByEmail returns empty optional when user is not available")
  @Order(1)
  void findUserByEmail_ReturnsEmptyOptional_WhenNoUserIsFound() {
    Optional<UserDto> user = userController.findUserByEmail("empty@email.com");
    Assertions.assertThat(user).isEmpty();
  }

  @Test
  @DisplayName("findUserByEmail returns optional with user when email is available")
  @Order(2)
  void findUserByEmail_ReturnsOptionalWithUser_WhenUserIsFound() {
    Optional<UserDto> user = userController.findUserByEmail("george.edwards@reqres.in");
    Assertions.assertThat(user).isNotEmpty();
    Assertions.assertThat(user.get()).hasNoNullFieldsOrProperties();
  }
}