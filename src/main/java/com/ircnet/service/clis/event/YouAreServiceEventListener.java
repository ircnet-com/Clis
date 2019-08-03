package com.ircnet.service.clis.event;

import com.ircnet.common.library.event.AbstractEventListener;
import com.ircnet.service.library.events.YouAreServiceEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class YouAreServiceEventListener extends AbstractEventListener<YouAreServiceEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(YouAreServiceEventListener.class);

    protected void onEvent(YouAreServiceEvent event) {
        LOGGER.info("Service connected as {}", event.getServiceName());
    }
}
