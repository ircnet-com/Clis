package com.ircnet.service.clis.event;

import com.ircnet.library.common.event.AbstractEventListener;
import com.ircnet.service.clis.service.ChannelService;
import com.ircnet.library.service.event.ChannelModeEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Event for MODE message:
 *  MODE #irc +nt
 *
 * This message will be sent in burst and whenever the modes of a channel changes. If no modes are set or all modes have
 * been removed, only '+' will be sent,.
 *
 * The event listener will update the modes. The channel must have been introduced by "CHANNEL" message first.
 * When parsing a list request, it must be checked if the channel is secret or private. Secret and private channels will
 * not be removed immediately. This avoids that the topic gets lost, when an user decided to make the channel public
 * again.
 */
@Component
public class ChannelModeEventListener extends AbstractEventListener<ChannelModeEvent> {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LoggerFactory.getLogger(ChannelModeEventListener.class);

    @Autowired
    private ChannelService channelService;

    protected void onEvent(ChannelModeEvent event) {
        channelService.updateModes(event.getChannelName(), event.getModes());
    }
}
