package com.ircnet.service.clis.event;

import com.ircnet.library.common.event.EventBus;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This class registers event listeners.
 */
@Component
public class EventRegistrationService {
    @Autowired
    private EventBus eventBus;

    @Autowired
    private YouAreServiceEventListener youAreServiceEventListener;

    @Autowired
    private ChannelEventListener channelEventListener;

    @Autowired
    private TopicEventListener topicEventListener;

    @Autowired
    private ChannelModeEventListener channelModeEventListener;

    @Autowired
    private SQueryEventListener squeryEventListener;

    @Autowired
    private EndOfBurstEventListener endOfBurstEventListener;

    @PostConstruct
    public void init() {
        /**
         * MUST be set to false to avoid heavy performance issues.
         */
        eventBus.setCheckInheritance(false);

        /**
         * Registration of event listeners.
         */
        eventBus.registerEventListener(youAreServiceEventListener);
        eventBus.registerEventListener(channelEventListener);
        eventBus.registerEventListener(topicEventListener);
        eventBus.registerEventListener(channelModeEventListener);
        eventBus.registerEventListener(squeryEventListener);
        eventBus.registerEventListener(endOfBurstEventListener);
    }
}
