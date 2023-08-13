package com.ircnet.service.clis;

import com.ircnet.library.common.configuration.IRCServerModel;
import com.ircnet.library.common.configuration.ServerModel;
import com.ircnet.library.service.IRCServiceTask;
import com.ircnet.library.service.ServiceConfigurationModel;
import com.ircnet.service.clis.strategy.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.LinkedCaseInsensitiveMap;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Spring configuration.
 */
@Configuration
@EnableScheduling
@ComponentScan(basePackages = {"com.ircnet.library.common", "com.ircnet.library.service"})
public class ClisConfiguration extends WebMvcConfigurationSupport {
    @Value("${service.name}")
    private String serviceName;

    @Value("${service.distributionMask}")
    private String serviceDistributionMask;

    @Value("${service.info}")
    private String serviceInfo;

    @Value("${service.password}")
    private String servicePassword;

    @Value("${service.type}")
    private int serviceType;

    @Value("${service.dataFlags}")
    private int serviceDataFlags;

    @Value("${service.burstFlags}")
    private int serviceBurstFlags;

    @Value("${ircserver.host}")
    private String ircServerHost;

    @Value("${ircserver.port}")
    private int ircServerPort;

    @Value("${ircserver.protocol:#{null}}")
    private String ircServerProtocol;

    /**
     * Creates a new IRC service.
     *
     * @return An IRC service
     */
    @Bean
    public IRCServiceTask ircServiceTask() {
        IRCServerModel ircServerModel = new IRCServerModel();
        ircServerModel.setAddress(ircServerHost);
        ircServerModel.setPort(ircServerPort);

        if (!StringUtils.isEmpty(ircServerProtocol)) {
            ircServerModel.setProtocol(ServerModel.Protocol.valueOf(ircServerProtocol.toUpperCase()));
        }

        ServiceConfigurationModel serviceConfiguration = new ServiceConfigurationModel();
        serviceConfiguration.setServiceName(serviceName);
        serviceConfiguration.setDistributionMask(serviceDistributionMask);
        serviceConfiguration.setServiceType(serviceType);
        serviceConfiguration.setDataFlags(serviceDataFlags);
        serviceConfiguration.setBurstFlags(serviceBurstFlags);
        serviceConfiguration.setInfo(serviceInfo);
        serviceConfiguration.setPassword(servicePassword);
        serviceConfiguration.setIrcServers(Collections.singletonList(ircServerModel));

        return new IRCServiceTask(serviceConfiguration);
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
