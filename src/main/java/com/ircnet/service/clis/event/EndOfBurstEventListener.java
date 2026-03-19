package com.ircnet.service.clis.event;

import com.ircnet.library.common.connection.SingletonIRCConnectionService;
import com.ircnet.library.common.event.AbstractEventListener;
import com.ircnet.library.service.event.EndOfBurstEvent;
import com.ircnet.service.clis.ChannelData;
import com.ircnet.service.clis.persistence.PersistenceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Event for EOB message.
 *
 * This event listener is used to measure the time for parsing a burst.
 */
@Component
@Slf4j
@Order
public class EndOfBurstEventListener extends AbstractEventListener<EndOfBurstEvent, SingletonIRCConnectionService> {
    private final Map<String, ChannelData> channelMap;
    private final PersistenceService persistenceService;

    public EndOfBurstEventListener(SingletonIRCConnectionService ircConnectionService,
                                   @Qualifier("channelMap") Map<String, ChannelData> channelMap,
                                   PersistenceService persistenceService) {
        super(ircConnectionService);
        this.channelMap = channelMap;
        this.persistenceService = persistenceService;
    }

    @Override
    protected void onEvent(EndOfBurstEvent event) {
        if(event.getIRCConnection().getBurstStart() != null) {
            long timeElapsed = (System.currentTimeMillis() - event.getIRCConnection().getBurstStart().getTime()) / 1000;
            log.info("Parsed burst in {} seconds. Currently {} channels.", timeElapsed, channelMap.size());
        }
        else {
            log.info("Parsed burst. Currently {} channels.", channelMap.size());
        }

        persistenceService.saveChannels();
    }
}
