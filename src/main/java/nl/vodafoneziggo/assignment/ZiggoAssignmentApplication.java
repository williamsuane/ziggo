package nl.vodafoneziggo.assignment;

import nl.vodafoneziggo.assignment.shared.config.ZiggoRemoteUrl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({ZiggoRemoteUrl.class})
public class ZiggoAssignmentApplication {

  public static void main(String[] args) {
    SpringApplication.run(ZiggoAssignmentApplication.class, args);
  }

}
