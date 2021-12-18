package com.ircnet.service.clis.strategy;

import com.ircnet.library.common.User;
import com.ircnet.service.clis.Constants;
import org.springframework.stereotype.Component;

/**
 * Handler for:
 *  - /SQUERY Clis VERSION
 *  - /SQUERY Clis HELP VERSION
 */
@Component
public class SQueryCommandVersion extends SQueryCommand {
    /**
     * Handler for: /SQUERY Clis VERSION
     *
     * @param from User who sent the SQUERY
     * @param message "VERSION"
     */
    @Override
    public void processCommand(User from, String message) {
        notice(from.getNick(), "Clis v%s", Constants.VERSION);
    }

    /**
     * Handler for: /SQUERY Clis HELP VERSION
     *
     * @param from User who sent the SQUERY
     * @param message "HELP VERSION"
     */
    @Override
    public void processHelp(User from, String message) {
        notice(from.getNick(), "Shows the current version");
    }
}
