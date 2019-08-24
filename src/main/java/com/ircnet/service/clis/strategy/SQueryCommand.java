package com.ircnet.service.clis.strategy;

import com.ircnet.common.library.User;

/**
 * Handler for:
 *  - /SQUERY Clis COMMAND
 *  - /SQUERY Clis HELP COMMAND
 */
public interface SQueryCommand {
    /**
     * Handler for: /SQUERY Clis COMMAND
     *
     * @param from User who sent the SQUERY
     * @param message A message starting with "COMMAND"
     */
    void processCommand(User from, String message);

    /**
     * Handler for: /SQUERY Clis HELP COMMAND
     *
     * @param from User who sent the SQUERY
     * @param message "HELP COMMAND"
     */
    void processHelp(User from, String message);
}
