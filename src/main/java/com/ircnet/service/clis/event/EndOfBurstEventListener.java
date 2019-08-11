package com.ircnet.service.clis.event;

import com.ircnet.common.library.event.AbstractEventListener;
import com.ircnet.service.clis.ChannelData;
import com.ircnet.service.library.events.EndOfBurstEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    protected void onEvent(EndOfBurstEvent event) {
        if(event.getIRCConnection().getBurstStart() != null) {
            long timeElapsed = (System.currentTimeMillis() - event.getIRCConnection().getBurstStart().getTime()) / 1000;
            LOGGER.info("Parsed burst in {} seconds. Received {} channels.", timeElapsed, channelMap.size());
        }
        else {
            LOGGER.info("Parsed burst. Received {} channels.", channelMap.size());
        }
    }
}
