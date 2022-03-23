package nl.vodafoneziggo.assignment.shared.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "VodafoneZiggo Order API",
        description = "API Example",
        version = "v1",
        contact = @Contact(
            name = "VodafoneZiggo",
            url = "https://www.vodafoneziggo.nl/",
            email = "someone@vodafoneziggo.nl"
        )
    ),
    servers = @Server(url = "http://localhost:8080")
)
public class OpenApiConfig {

}