package org.batukhtin.t1starter;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.batukhtin.t1starter.aspect.LoggingAspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(LoggingProperties.class)
@ConditionalOnProperty(prefix = "logging-starter", value = "enable", havingValue = "true", matchIfMissing = true)
public class LoggingAutoConfiguration {

    private final LoggingProperties properties;

    public LoggingAutoConfiguration(LoggingProperties properties) {
        this.properties = properties;
    }

    @Bean
    public LoggingAspect loggingAspect(LoggingProperties loggingProperties) {
        if (loggingProperties.getRoot() == null || loggingProperties.getRoot().isEmpty()) {
            loggingProperties.setRoot("INFO");
        }
        return new LoggingAspect(loggingProperties.getRoot());
    }

}