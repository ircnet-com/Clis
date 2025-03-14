package com.ircnet.service.clis.event;

import com.ircnet.library.common.connection.IRCConnectionService;
import com.ircnet.library.common.event.AbstractEventListener;
import com.ircnet.library.service.event.ChannelEvent;
import com.ircnet.service.clis.service.ChannelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Event for CHANNEL message:
 *  CHANNEL #irc 42
 *
 * This message will be sent in burst and whenever the user count of a channel changes because of JOIN, PART or QUIT.
 * The count is 0 when the last user has left the channel. This may also happen during netsplit.
 *
 * The event listener will insert the channel with the user count or, if the channel exists, update the user count.
 * Empty channels will not be removed immediately. This avoids that the topic gets lost if the channel gets empty during
 * netsplit.
 */
public class ChannelEventListener extends AbstractEventListener<ChannelEvent, IRCConnectionService> {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LoggerFactory.getLogger(ChannelEventListener.class);

    private ChannelService channelService;

    public ChannelEventListener(IRCConnectionService ircConnectionService, ChannelService channelService) {
        super(ircConnectionService);
        this.channelService = channelService;
    }

    protected void onEvent(ChannelEvent event) {
        channelService.updateOrInsert(event.getChannelName(), event.getUserCount());
    }
}
