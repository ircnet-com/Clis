package com.ircnet.service.clis.event;

import com.ircnet.library.common.event.AbstractEventListener;
import com.ircnet.library.service.event.EndOfBurstEvent;
import com.ircnet.service.clis.ChannelData;
import com.ircnet.service.clis.persistence.PersistenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Event for EOB message.
 *
 * This event listener is used to measure the time for parsing a burst.
 */
@Component
public class EndOfBurstEventListener extends AbstractEventListener<EndOfBurstEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(EndOfBurstEventListener.class);

    @Resource(name = "channelMap")
    private Map<String, ChannelData> channelMap;

    @Autowired
    private PersistenceService persistenceService;

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
