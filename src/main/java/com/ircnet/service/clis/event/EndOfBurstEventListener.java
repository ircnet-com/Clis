package com.ircnet.service.clis.event;

import com.ircnet.common.library.event.AbstractEventListener;
import com.ircnet.service.library.events.EndOfBurstEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class EndOfBurstEventListener extends AbstractEventListener<EndOfBurstEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(com.ircnet.service.library.events.EndOfBurstEventListener.class);

    protected void onEvent(EndOfBurstEvent event) {
        // TODO: Burst finished. Persist data.
    }
}
