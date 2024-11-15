package com.ircnet.service.clis.event;

import com.ircnet.library.common.connection.IRCConnectionService;
import com.ircnet.library.common.event.AbstractEventListener;
import com.ircnet.library.service.event.EndOfBurstEvent;
import com.ircnet.service.clis.ChannelData;
import com.ircnet.service.clis.persistence.PersistenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Event for EOB message.
 *
 * This event listener is used to measure the time for parsing a burst.
 */
public class EndOfBurstEventListener extends AbstractEventListener<EndOfBurstEvent, IRCConnectionService> {
    private static final Logger LOGGER = LoggerFactory.getLogger(EndOfBurstEventListener.class);

    private Map<String, ChannelData> channelMap;
    private PersistenceService persistenceService;

    public EndOfBurstEventListener(IRCConnectionService ircConnectionService, Map<String, ChannelData> channelMap,
                                   PersistenceService persistenceService) {
        super(ircConnectionService);
        this.channelMap = channelMap;
        this.persistenceService = persistenceService;
    }

    protected void onEvent(EndOfBurstEvent event) {
        if(event.getIRCConnection().getBurstStart() != null) {
            long timeElapsed = (System.currentTimeMillis() - event.getIRCConnection().getBurstStart().getTime()) / 1000;
            LOGGER.info("Parsed burst in {} seconds. Currently {} channels.", timeElapsed, channelMap.size());
        }
        else {
            LOGGER.info("Parsed burst. Currently {} channels.", channelMap.size());
        }

        persistenceService.saveChannels();
    }
}
