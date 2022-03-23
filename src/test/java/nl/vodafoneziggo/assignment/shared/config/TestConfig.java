package nl.vodafoneziggo.assignment.shared.config;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.test.web.reactive.server.WebTestClient;

@Component
public class TestConfig {

  @Bean
  public WebTestClient webTestClient(@Value("${local.server.port}") int port) {
    return WebTestClient
        .bindToServer()
        .responseTimeout(Duration.ofSeconds(60))
        .baseUrl("http://localhost:" + port)
        .build();
  }

}
