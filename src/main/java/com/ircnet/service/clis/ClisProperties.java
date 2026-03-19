package com.ircnet.service.clis;

import com.ircnet.library.service.ServiceConfigurationModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "service")
@Data
@EqualsAndHashCode(callSuper = true)
public class ClisProperties extends ServiceConfigurationModel {
    private SQuery squery;
    private Persistence persistence;
    private Cache cache;

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class SQuery extends ServiceConfigurationModel.SQuery {
        private List list;

        @Data
        public static class List {
            private int maxResults;
        }
    }

    @Data
    public static class Persistence {
        private String file = "channels.json";
        private long interval = 3600000;
    }

    @Data
    public static class Cache {
        private long emptyChannelMaxAge = 28800000;
    }
}
