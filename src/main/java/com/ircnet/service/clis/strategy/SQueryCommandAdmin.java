package com.ircnet.service.clis.strategy;

import com.ircnet.library.common.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Handler for:
 *  - /SQUERY Clis ADMIN
 *  - /SQUERY Clis HELP ADMIN
 */
@Component
public class SQueryCommandAdmin extends SQueryCommand {
    @Value("${service.name}")
    private String serviceName;

    @Value("${service.squery.admin}")
    private String serviceAdmin;

    /**
     * Handler for: /SQUERY Clis ADMIN
     *
     * @param from User who sent the SQUERY
     * @param message "ADMIN"
     */
    @Override
    public void processCommand(User from, String message) {
        notice(from.getNick(), "Administrative info about %s:", serviceName);
        notice(from.getNick(), serviceAdmin);
    }

    /**
     * Handler for: /SQUERY Clis HELP ADMIN
     *
     * @param from User who sent the SQUERY
     * @param message "HELP ADMIN"
     */
    @Override
    public void processHelp(User from, String message) {
        notice(from.getNick(), "Shows administrative information");
    }
}
