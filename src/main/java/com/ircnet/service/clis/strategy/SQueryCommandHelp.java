package com.ircnet.service.clis.strategy;

import com.ircnet.library.common.User;
import com.ircnet.service.clis.ClisProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;

/**
 * Handler for: /SQUERY Clis HELP
 */
@Component
public class SQueryCommandHelp extends SQueryCommand {
    @Autowired
    private ClisProperties properties;

    @Resource(name = "squeryCommandMap")

    private Map<String, SQueryCommand> squeryCommandMap;

    @PostConstruct
    public void init() {
        squeryCommandMap.put("HELP", this);
    }

    /**
     * Handler for: /SQUERY Clis HELP
     *
     * @param from User who sent the SQUERY
     * @param message "HELP" or "HELP <command>"
     */
    @Override
    public void processCommand(User from, String message) {
        String[] parts = message.split(" ");

        if(parts.length == 1) {
            // HELP
            notice(from.getNick(), "%s help index", properties.getName());
            notice(from.getNick(), "Use /SQUERY %s HELP <topic>", properties.getName());
            notice(from.getNick(), "Available topics: %s", StringUtils.join((squeryCommandMap).keySet(), ", "));
        }
        else {
            // HELP <command>
            SQueryCommand squeryCommand = squeryCommandMap.get(parts[1]);

            if(squeryCommand != null) {
                squeryCommand.processHelp(from, message);
            }
            else {
                notice(from.getNick(), "No such help topic: \"%s\". Use /SQUERY %s HELP", parts[1], properties.getName());
            }
        }
    }

    /**
     * Handler for: /SQUERY Clis HELP HELP ;-)
     *
     * @param from User who sent the SQUERY
     * @param message "HELP HELP"
     */
    @Override
    public void processHelp(User from, String message) {
        String[] parts = message.split(" ");
        notice(from.getNick(), "No such help topic: \"%s\". Use /SQUERY %s HELP", parts[1], properties.getName());
    }
}
