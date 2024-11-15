package com.ircnet.service.clis.event;

import com.ircnet.library.common.SettingService;
import com.ircnet.library.common.connection.SingletonIRCConnectionService;
import com.ircnet.library.common.event.EventBus;
import com.ircnet.service.clis.ChannelData;
import com.ircnet.service.clis.ClisProperties;
import com.ircnet.service.clis.persistence.PersistenceService;
import com.ircnet.service.clis.service.ChannelService;
import com.ircnet.service.clis.strategy.SQueryCommand;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * This class registers event listeners.
 */
@Component
public class EventRegistrationService {
    @Autowired
    private EventBus eventBus;

    @Autowired
    private SingletonIRCConnectionService ircConnectionService;

    @Autowired
    private SettingService settingService;

    @Autowired
    private ChannelService channelService;

    @Autowired
    private PersistenceService persistenceService;

    @Autowired
    private ClisProperties properties;

    @Autowired
    @Qualifier("squeryCommandMap")
    private Map<String, SQueryCommand > squeryCommandMap;

    @Autowired
    @Qualifier("channelMap")
    Map<String, ChannelData > channelMap;

    @PostConstruct
    public void init() {
        // MUST be set to false to avoid heavy performance issues.
        eventBus.setCheckInheritance(false);

        // Registration of event listeners.
        eventBus.registerEventListener(new YouAreServiceEventListener(ircConnectionService, settingService));
        eventBus.registerEventListener(new ChannelEventListener(ircConnectionService, channelService));
        eventBus.registerEventListener(new TopicEventListener(ircConnectionService, channelService));
        eventBus.registerEventListener(new ChannelModeEventListener(ircConnectionService, channelService));
        eventBus.registerEventListener(new SQueryEventListener(ircConnectionService, squeryCommandMap, properties));
        eventBus.registerEventListener(new EndOfBurstEventListener(ircConnectionService, channelMap, persistenceService));
    }
}
