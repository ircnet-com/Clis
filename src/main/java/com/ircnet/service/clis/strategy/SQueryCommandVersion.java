package com.ircnet.service.clis.strategy;

import com.ircnet.library.common.User;
import com.ircnet.service.clis.Constants;
import com.ircnet.library.service.IRCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Handler for:
 *  - /SQUERY Clis VERSION
 *  - /SQUERY Clis HELP VERSION
 */
@Component
public class SQueryCommandVersion implements SQueryCommand {
    @Autowired
    private IRCService ircService;

    /**
     * Handler for: /SQUERY Clis VERSION
     *
     * @param from User who sent the SQUERY
     * @param message "VERSION"
     */
    @Override
    public void processCommand(User from, String message) {
        ircService.notice(from.getNick(), "Clis v%s", Constants.VERSION);
    }

    /**
     * Handler for: /SQUERY Clis HELP VERSION
     *
     * @param from User who sent the SQUERY
     * @param message "HELP VERSION"
     */
    @Override
    public void processHelp(User from, String message) {
        ircService.notice(from.getNick(), "Shows the current version");
    }
}
