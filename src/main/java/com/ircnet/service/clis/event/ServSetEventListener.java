package com.ircnet.service.clis.event;

import com.ircnet.common.library.event.AbstractEventListener;
import com.ircnet.service.library.events.ServSetEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ServSetEventListener extends AbstractEventListener<ServSetEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(com.ircnet.service.library.events.ServSetEventListener.class);

    protected void onEvent(ServSetEvent event) {
        if(event.getIRCService().getServiceConfigurationModel().getBurstFlags() != 0) {
            // TODO: Burst starts now. Store data in memory first.
        }
    }
}
