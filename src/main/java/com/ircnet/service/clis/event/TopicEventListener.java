package com.ircnet.service.clis.event;

import com.ircnet.library.common.connection.SingletonIRCConnectionService;
import com.ircnet.library.common.event.AbstractEventListener;
import com.ircnet.library.service.event.TopicEvent;
import com.ircnet.service.clis.service.ChannelService;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

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
@Order(2)
public class TopicEventListener extends AbstractEventListener<TopicEvent, SingletonIRCConnectionService> {
    private final ChannelService channelService;

    public TopicEventListener(SingletonIRCConnectionService ircConnectionService,
                              ChannelService channelService) {
        super(ircConnectionService);
        this.channelService = channelService;
    }

    @Override
    protected void onEvent(TopicEvent event) {
        channelService.updateTopic(event.getChannelName(), event.getTopic(), event.getFrom());
    }
}
