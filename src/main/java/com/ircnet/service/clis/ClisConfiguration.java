package com.ircnet.service.clis;

import com.ircnet.library.service.AppVersionProvider;
import com.ircnet.library.service.connection.IRCServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Spring configuration.
 */
@Configuration
@EnableScheduling
//@ComponentScan(basePackages = {"com.ircnet.library.common", "com.ircnet.library.service"})
public class ClisConfiguration extends WebMvcConfigurationSupport {
    @Bean
    public AppVersionProvider appVersionProvider(GitRevision gitRevision) {
        return () -> String.format("%s (%s)", Constants.VERSION, gitRevision.display());
    }

    @Bean
    public IRCServiceConnection ircServiceConnection(ClisProperties properties) {
        return new IRCServiceConnection(properties);
    }

    /**
     * Creates a new map containing SQUERY commands.
     *
     * @return A map containing SQUERY commands
     */
    @Bean
    public Map<String, ChannelData> channelMap() {
        return new ConcurrentHashMap<>();
    }

    /**
     * Pretty print for JSON.
     *
     */
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        for (HttpMessageConverter<?> converter : converters) {
            if (converter instanceof MappingJackson2HttpMessageConverter) {
                MappingJackson2HttpMessageConverter jacksonConverter = (MappingJackson2HttpMessageConverter) converter;
                jacksonConverter.setPrettyPrint(true);
            }
        }
    }
}
