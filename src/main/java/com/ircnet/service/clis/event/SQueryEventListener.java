package com.ircnet.service.clis.event;

import com.ircnet.library.common.connection.SingletonIRCConnectionService;
import com.ircnet.library.common.event.AbstractEventListener;
import com.ircnet.library.service.event.SQueryEvent;
import com.ircnet.service.clis.ClisProperties;
import com.ircnet.service.clis.strategy.SQueryCommand;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Event for SQUERY message:
 * :nick!user@localhost SQUERY Clis@irc.ircnet.com :list #irc
 *
 * SQUERY is used by users to talk to services. Services reply by NOTICE.
 *
 * This event listener parses all supported commands of Clis.
 */
public class SQueryEventListener extends AbstractEventListener<SQueryEvent, SingletonIRCConnectionService> {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LoggerFactory.getLogger(SQueryEventListener.class);

    private ClisProperties properties;
    private Map<String, SQueryCommand> squeryCommandMap;

    public SQueryEventListener(SingletonIRCConnectionService ircConnectionService,
                               Map<String, SQueryCommand> squeryCommandMap,
                               ClisProperties properties) {
        super(ircConnectionService);
        this.squeryCommandMap = squeryCommandMap;
        this.properties = properties;
    }

    protected void onEvent(SQueryEvent event) {
        String nick = event.getFrom().getNick();

        if (StringUtils.isEmpty(event.getMessage())) {
            return;
        }

        String[] parts = event.getMessage().split(" ");

        SQueryCommand squeryCommand = squeryCommandMap.get(parts[0]);

        if (squeryCommand != null) {
            squeryCommand.processCommand(event.getFrom(), event.getMessage());
        }
        else {
            ircConnectionService.notice(nick,
                    "Unrecognized command: \"%s\". Use /SQUERY %s HELP\n", parts[0], properties.getName());
        }
    }
}
