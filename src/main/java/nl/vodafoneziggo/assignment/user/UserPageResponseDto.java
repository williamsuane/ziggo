package nl.vodafoneziggo.assignment.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class UserPageResponseDto {

  private int page;
  @JsonProperty(value = "per_page")
  private int perPage;
  private int total;
  @JsonProperty(value = "total_pages")
  private int totalPages;
  @JsonProperty("data")
  private List<UserDto> users;
}
