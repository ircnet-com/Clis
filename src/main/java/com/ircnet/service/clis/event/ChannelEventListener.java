package com.ircnet.service.clis.event;

import com.ircnet.common.library.event.AbstractEventListener;
import com.ircnet.service.clis.service.ChannelService;
import com.ircnet.service.library.events.ChannelEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChannelEventListener extends AbstractEventListener<ChannelEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(com.ircnet.service.library.events.ChannelEventListener.class);

    @Autowired
    private ChannelService channelService;

    protected void onEvent(ChannelEvent event) {
        channelService.updateOrInsert(event.getChannelName(), event.getUserCount());
    }
}
