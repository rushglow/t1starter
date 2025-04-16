package org.batukhtin.t1starter;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@ConfigurationProperties(prefix = "logging-starter")
public class LoggingProperties {
    private Boolean enable;
    private String root;

    @ConstructorBinding
    public LoggingProperties(Boolean enable, String root) {
        this.enable = enable;
        this.root = root;
    }

    public Boolean getEnable() {
        return enable;
    }

    public String getRoot() {
        return root;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public void setRoot(String root) {
        this.root = root;
    }
}
