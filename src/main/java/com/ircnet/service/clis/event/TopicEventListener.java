package com.ircnet.service.clis.event;

import com.ircnet.common.library.event.AbstractEventListener;
import com.ircnet.service.clis.service.ChannelService;
import com.ircnet.service.library.events.TopicEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TopicEventListener extends AbstractEventListener<TopicEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(com.ircnet.service.library.events.TopicEventListener.class);

    @Autowired
    private ChannelService channelService;

    protected void onEvent(TopicEvent event) {
        channelService.updateTopic(event.getChannelName(), event.getTopic(), event.getFrom());
    }
}
