package nl.vodafoneziggo.assignment.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class UserDto {
  private int id;
  private final String email;
  @JsonProperty(value = "first_name")
  private final String firstName;
  @JsonProperty(value = "last_name")
  private final String lastName;

}
