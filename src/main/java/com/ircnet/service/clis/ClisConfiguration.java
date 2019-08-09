package com.ircnet.service.clis;

import com.ircnet.common.library.configuration.IRCServerModel;
import com.ircnet.common.library.configuration.ServerModel;
import com.ircnet.service.library.IRCService;
import com.ircnet.service.library.ServiceConfigurationModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
@EnableScheduling
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
    private String ircServerHost; // FIXME

    @Value("${ircserver.port}")
    private int ircServerPort;

    @Value("${ircserver.protocol}")
    private String ircServerProtocol;

    @Bean
    public IRCService ircService() {
        IRCServerModel ircServerModel = new IRCServerModel();
        ircServerModel.setHostname(ircServerHost);
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

        return new IRCService(serviceConfiguration);
    }

    @Bean
    public Map<String, ChannelData> channelMap() {
        return new ConcurrentHashMap<>();
    }

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
