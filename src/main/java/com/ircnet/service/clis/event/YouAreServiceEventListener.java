package com.ircnet.service.clis.event;

import com.ircnet.library.common.SettingConstants;
import com.ircnet.library.common.SettingService;
import com.ircnet.library.common.event.AbstractEventListener;
import com.ircnet.library.service.event.YouAreServiceEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Event for YOURESERVICE (383) message:
 *  :irc.ircnet.com 383 Clis@irc.ircnet.com :You are service Clis@irc.ircnet.com
 *
 * This message will be sent when the service successfully registered on an irc server.
 *
 * This event listener will log the successful registration.
 */
@Component
public class YouAreServiceEventListener extends AbstractEventListener<YouAreServiceEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(YouAreServiceEventListener.class);

    @Autowired
    private SettingService settingService;

    protected void onEvent(YouAreServiceEvent event) {
        LOGGER.info("Service connected as {}", event.getServiceName());

        if(settingService.findInteger(SettingConstants.LAGCHECK_INTERVAL, SettingConstants.LAGCHECK_INTERVAL_DEFAULT) != 0)
            event.getIRCConnection().setLagCheckNext(new Date(System.currentTimeMillis() + 120 * 1000));
    }
}
