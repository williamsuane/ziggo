package nl.vodafoneziggo.assignment.shared.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "ziggo.url")
@ConstructorBinding
@AllArgsConstructor
@Getter
public class ZiggoRemoteUrl {

  private final String userUrl;
}

