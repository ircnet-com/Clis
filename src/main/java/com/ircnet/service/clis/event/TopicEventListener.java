package com.ircnet.service.clis.event;

import com.ircnet.library.common.event.AbstractEventListener;
import com.ircnet.service.clis.service.ChannelService;
import com.ircnet.library.service.event.TopicEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
//

/**
 * Event for TOPIC messages:
 *
 * 1. in burst:
 *  TOPIC #irc :Visit http://www.ircnet.com
 *
 * 2. when an user changed the topic:
 *  :nick123 TOPIC #irc :Visit http://www.ircnet.com
 *
 * The event listener will update the topic. The channel must have been introduced by "CHANNEL" message first.
 * The topic author will not be sent in burst (see above).
 */
@Component
public class TopicEventListener extends AbstractEventListener<TopicEvent> {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LoggerFactory.getLogger(TopicEventListener.class);

    @Autowired
    private ChannelService channelService;

    protected void onEvent(TopicEvent event) {
        channelService.updateTopic(event.getChannelName(), event.getTopic(), event.getFrom());
    }
}
