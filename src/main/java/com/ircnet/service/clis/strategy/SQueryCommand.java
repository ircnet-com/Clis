package com.ircnet.service.clis.strategy;

import com.ircnet.library.common.User;
import com.ircnet.library.common.connection.IRCConnectionService;
import com.ircnet.library.service.IRCServiceTask;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Handler for:
 *  - /SQUERY Clis COMMAND
 *  - /SQUERY Clis HELP COMMAND
 */
public abstract class SQueryCommand {
    @Autowired
    protected IRCConnectionService ircConnectionService;

    @Autowired
    protected IRCServiceTask ircServiceTask;

    /**
     * Sends a notice.
     *
     * @param target Target nick
     * @param format format string
     * @param args Arguments
     */
    protected void notice(String target, String format, Object... args) {
        ircConnectionService.notice(ircServiceTask.getIRCConnection(), target, format, args);
    }

    /**
     * Handler for: /SQUERY Clis COMMAND
     *
     * @param from User who sent the SQUERY
     * @param message A message starting with "COMMAND"
     */
    abstract public void processCommand(User from, String message);

    /**
     * Handler for: /SQUERY Clis HELP COMMAND
     *
     * @param from User who sent the SQUERY
     * @param message "HELP COMMAND"
     */
    abstract public void processHelp(User from, String message);
}
