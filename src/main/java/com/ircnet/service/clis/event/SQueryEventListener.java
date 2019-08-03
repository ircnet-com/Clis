package com.ircnet.service.clis.event;

import com.ircnet.common.library.connection.IRCConnection;
import com.ircnet.common.library.event.AbstractEventListener;
import com.ircnet.service.clis.Constants;
import com.ircnet.service.library.IRCService;
import com.ircnet.service.library.events.SQueryEvent;
import org.apache.commons.cli.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Formatter;

@Component
public class SQueryEventListener extends AbstractEventListener<SQueryEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(com.ircnet.service.library.events.SQueryEventListener.class);

    @Autowired
    private IRCService ircService;

    @Value("${service.name}")
    private String serviceName;

    @Value("${service.squery.admin}")
    private String serviceAdmin;

    @Value("${service.squery.info}")
    private String serviceInfo;

    private Options options;

    @PostConstruct
    protected void init() {
        this.options = new Options();
        options.addOption("min", true, "minimum users on a channel");
        options.addOption("max", true, "maximum users on a channel");
        options.addOption("t", "topic", true, "topic of the channel shall contain string");
        options.addOption("s", "show", true, "show modes (m) and who set the topic (t)");
    }

    protected void onEvent(SQueryEvent event) {
        String nick = event.getFrom().getNick();

        if (StringUtils.isEmpty(event.getMessage())) {
            return;
        }

        String[] parts = event.getMessage().split(" ");

        if (parts[0].equalsIgnoreCase("ADMIN")) {
            notice(nick, "Administrative info about %s:", serviceName);
            notice(nick, serviceAdmin);
        }

        else if(parts[0].equalsIgnoreCase("INFO")) {
            notice(nick, "Channel List Service (Clis)");
            notice(nick, serviceInfo);
        }

        else if(parts[0].equalsIgnoreCase("VERSION")) {
            notice(nick, "Clis v%s", Constants.VERSION);
        }

        else if(parts[0].equalsIgnoreCase("LIST")) {
            CommandLineParser parser = new DefaultParser();

            if(parts.length > 1) {
                String[] requestArguments = Arrays.copyOfRange(parts, 1, parts.length);

                try {
                    CommandLine commandLine = parser.parse(options, requestArguments);

                    if(CollectionUtils.isEmpty(commandLine.getArgList())) {
                        notice(nick, "You did not specify a channel mask.  Use /SQUERY %s HELP LIST", serviceName);
                        return;
                    }

                    Integer min = null;
                    Integer max = null;
                    String topic = commandLine.getOptionValue("topic");
                    boolean showTopicAuthor = false;
                    boolean showModes = false;

                    if(commandLine.hasOption("min")) {
                        try {
                            min = Integer.parseInt(commandLine.getOptionValue("min"));
                        }
                        catch (NumberFormatException e) {
                            // ..
                            return;
                        }
                    }

                    if(commandLine.hasOption("max")) {
                        try {
                            max = Integer.parseInt(commandLine.getOptionValue("min"));
                        }
                        catch (NumberFormatException e) {
                            // ..
                            return;
                        }
                    }

                    StringBuilder querySummary = new StringBuilder("Query summary: searching for channels matching \"");
                    querySummary.append(commandLine.getArgList().get(0));
                    querySummary.append("\"");

                    if(min != null) {
                        querySummary.append(", min ");
                        querySummary.append(min);
                        querySummary.append(" users");
                    }

                    if(max != null) {
                        querySummary.append(", max ");
                        querySummary.append(max);
                        querySummary.append(" users");
                    }

                    if(topic != null) {
                        querySummary.append(", topic containing \"");
                        querySummary.append(topic);
                        querySummary.append("\"");
                    }

                    if(commandLine.hasOption("show")) {
                        String flags = commandLine.getOptionValue("show");

                        if(!flags.matches("^[mt]+$")) {
                            // ..
                            return;
                        }

                        if(flags.indexOf('m') != -1) {
                            showModes = true;
                        }

                        if(flags.indexOf('t') != -1) {
                            showTopicAuthor = true;
                        }
                    }

                    if(showModes) {
                        querySummary.append(", showing mode");
                    }

                    if(showTopicAuthor) {
                        querySummary.append(", showing who set topic");
                    }

                    notice(nick, querySummary.toString());

                    // TODO: execute search
                } catch (ParseException e) {
                    LOGGER.error("Failed to parse '{}' from {}", event.getMessage(), event.getFrom(), e);
                }
            }
        }
    }

    private void notice(String nick, String format, Object... args) {
        String content = new Formatter().format(format, args).toString();
        ircService.getIRCConnection().send("NOTICE %s :%s", nick, content);
    }
}
