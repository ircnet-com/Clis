package com.ircnet.service.clis;

import com.ircnet.library.service.ServiceConfigurationModel;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "service")
@Data
public class ClisProperties extends ServiceConfigurationModel {
    private SQuery squery;

    @Data
    public static class SQuery {
        private String admin;
        private String info;
        private List list;

        @Data
        public static class List {
            private int maxResults;
        }
    }
}
