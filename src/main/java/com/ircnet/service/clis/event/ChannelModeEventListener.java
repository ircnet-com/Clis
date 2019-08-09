package com.ircnet.service.clis.event;

import com.ircnet.common.library.event.AbstractEventListener;
import com.ircnet.service.clis.service.ChannelService;
import com.ircnet.service.library.events.ChannelModeEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChannelModeEventListener extends AbstractEventListener<ChannelModeEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(com.ircnet.service.library.events.ChannelModeEventListener.class);

    @Autowired
    private ChannelService channelService;

    protected void onEvent(ChannelModeEvent event) {
        channelService.updateModes(event.getChannelName(), event.getModes());
    }
}
