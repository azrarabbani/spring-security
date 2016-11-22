package hello.data.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotNull;

/**
 * Created by arabbani on 11/17/16.
 */
@ConfigurationProperties(prefix = "jms", ignoreUnknownFields = false)
public class MessageConfigurationProperties {
    public String getDefaultBrokerUrl() {
        return defaultBrokerUrl;
    }

    public void setDefaultBrokerUrl(String defaultBrokerUrl) {
        this.defaultBrokerUrl = defaultBrokerUrl;
    }

    @NotNull
    private String defaultBrokerUrl;

}


