package com.ircnet.service.clis;

import com.ircnet.library.service.connection.IRCServiceConnection;
import com.ircnet.service.clis.strategy.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.LinkedCaseInsensitiveMap;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Spring configuration.
 */
@Configuration
@EnableScheduling
public class ClisConfiguration extends WebMvcConfigurationSupport {
    @Autowired
    private ClisProperties properties;

    /**
     * Creates a new IRC service.
     *
     * @return An IRC service
     */
    @Bean
    public IRCServiceConnection ircServiceConnection() {
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

    @Bean("squeryCommandMap")
    @Autowired
    public Map<String, SQueryCommand> squeryCommandMap(SQueryCommandList squeryCommandList,
                                                       SQueryCommandAdmin squeryCommandAdmin,
                                                       SQueryCommandVersion squeryCommandVersion,
                                                       SQueryCommandInfo squeryCommandInfo) {
        Map<String, SQueryCommand> commandMap = new LinkedCaseInsensitiveMap<>();
        commandMap.put("LIST", squeryCommandList);
        commandMap.put("ADMIN", squeryCommandAdmin);
        commandMap.put("INFO", squeryCommandInfo);
        commandMap.put("VERSION", squeryCommandVersion);
        // HELP is not added here to avoid circular dependencies
        return commandMap;
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
