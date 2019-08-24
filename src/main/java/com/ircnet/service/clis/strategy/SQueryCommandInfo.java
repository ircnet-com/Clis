package com.ircnet.service.clis.strategy;

import com.ircnet.common.library.User;
import com.ircnet.service.library.IRCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Handler for:
 *  - /SQUERY Clis INFO
 *  - /SQUERY Clis HELP INFO
 */
@Component
public class SQueryCommandInfo implements SQueryCommand {
    @Autowired
    private IRCService ircService;

    @Value("${service.squery.info}")
    private String serviceInfo;

    /**
     * Handler for: /SQUERY Clis INFO
     *
     * @param from User who sent the SQUERY
     * @param message "INFO"
     */
    @Override
    public void processCommand(User from, String message) {
        ircService.notice(from.getNick(), "Channel List Service (Clis)");
        ircService.notice(from.getNick(), serviceInfo);
    }

    /**
     * Handler for: /SQUERY Clis HELP INFO
     *
     * @param from User who sent the SQUERY
     * @param message "HELP ADMIN"
     */
    @Override
    public void processHelp(User from, String message) {
        ircService.notice(from.getNick(), "Shows information about this software");
    }
}
