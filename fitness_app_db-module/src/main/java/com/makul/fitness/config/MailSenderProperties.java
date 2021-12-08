package com.makul.fitness.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "java-mail-sender")
@Validated
@Getter
@Setter
public class MailSenderProperties {
    @NotBlank
    String host;
    @Min(1)
    @Max(65535)
    Integer port;
    @NotBlank
    String username;
    @NotBlank
    String password;
    @NotNull
    Map<String, String> props;
}

