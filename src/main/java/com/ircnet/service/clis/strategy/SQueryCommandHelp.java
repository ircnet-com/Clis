package com.ircnet.service.clis.strategy;

import com.ircnet.library.common.User;
import com.ircnet.library.service.IRCService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;

/**
 * Handler for: /SQUERY Clis HELP
 */
@Component
public class SQueryCommandHelp implements SQueryCommand {
    @Autowired
    private IRCService ircService;

    @Value("${service.name}")
    private String serviceName;

    @Value("${service.squery.info}")
    private String serviceInfo;

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
            ircService.notice(from.getNick(), "%s help index", serviceName);
            ircService.notice(from.getNick(), "Use /SQUERY %s HELP <topic>", serviceName);
            ircService.notice(from.getNick(), "Available topics: %s", StringUtils.join((squeryCommandMap).keySet(), ", "));
        }
        else {
            // HELP <command>
            SQueryCommand squeryCommand = squeryCommandMap.get(parts[1]);

            if(squeryCommand != null) {
                squeryCommand.processHelp(from, message);
            }
            else {
                ircService.notice(from.getNick(), "No such help topic: \"%s\". Use /SQUERY %s HELP", parts[1], serviceName);
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
        ircService.notice(from.getNick(), "No such help topic: \"%s\". Use /SQUERY %s HELP", parts[1], serviceName);
    }
}
