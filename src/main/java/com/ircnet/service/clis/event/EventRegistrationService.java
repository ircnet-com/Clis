package com.ircnet.service.clis.event;

import com.ircnet.library.common.event.EventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * This class registers event listeners.
 */
@Component
public class EventRegistrationService {
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
        EventBus.setCheckInheritance(false);

        /**
         * Registration of event listeners.
         */
        EventBus.registerEventListener(youAreServiceEventListener);
        EventBus.registerEventListener(channelEventListener);
        EventBus.registerEventListener(topicEventListener);
        EventBus.registerEventListener(channelModeEventListener);
        EventBus.registerEventListener(squeryEventListener);
        EventBus.registerEventListener(endOfBurstEventListener);
    }
}
