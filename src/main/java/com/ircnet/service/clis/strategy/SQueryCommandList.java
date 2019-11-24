package com.ircnet.service.clis.strategy;

import com.ircnet.library.common.User;
import com.ircnet.service.clis.ChannelData;
import com.ircnet.service.clis.constant.MatchType;
import com.ircnet.service.clis.service.ChannelService;
import com.ircnet.library.service.IRCService;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Handler for:
 *  - /SQUERY Clis LIST [options] <mask>
 *  - /SQUERY Clis HELP LIST
 */
@Component
public class SQueryCommandList implements SQueryCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(SQueryCommandList.class);

    @Autowired
    private IRCService ircService;

    @Autowired
    private ChannelService channelService;

    @Value("${service.name}")
    private String serviceName;

    @Value("${service.squery.list.maxresults:0}")
    private int maxResults;

    private Options options;

    /**
     * Prepares the option parser.
     */
    @PostConstruct
    protected void init() {
        this.options = new Options();

        Option min = Option.builder("min")
                .hasArg()
                .argName("number")
                .desc("minimum users on a channel")
                .build();

        options.addOption(min);

        Option max = Option.builder("max")
                .hasArg()
                .argName("number")
                .desc("maximum users on a channel")
                .build();

        options.addOption(max);

        Option topic = Option.builder("t")
                .longOpt("topic")
                .hasArg()
                .argName("string")
                .desc("topic of the channel must contain this string")
                .build();

        options.addOption(topic);

        Option show = Option.builder("s")
                .longOpt("show")
                .hasArg()
                .argName("[m][t]")
                .desc("show modes (m) and who set the topic (t)")
                .build();

        options.addOption(show);
    }

    /**
     * Handler for: /SQUERY Clis LIST
     *
     * @param from User who sent the SQUERY
     * @param message message with format: LIST [options] <mask>
     */
    @Override
    public void processCommand(User from, String message) {
        String nick = from.getNick();
        String[] parts = message.split(" ");
        int errorCount = 0;

        CommandLineParser parser = new DefaultParser();

        if (parts.length == 1) {
            ircService.notice(nick, "You did not specify a channel mask. Use /SQUERY %s HELP LIST", serviceName);
            return;
        }

        String[] requestArguments = Arrays.copyOfRange(parts, 1, parts.length);

        try {
            CommandLine commandLine = parser.parse(options, requestArguments);

            if (CollectionUtils.isEmpty(commandLine.getArgList())) {
                ircService.notice(nick, "You did not specify a channel mask. Use /SQUERY %s HELP LIST", serviceName);
                return;
            }

            Integer minUsers = null;
            Integer maxUsers = null;
            String topic = commandLine.getOptionValue("topic");
            boolean showTopicAuthor = false;
            boolean showModes = false;

            if (commandLine.hasOption("min")) {
                try {
                    minUsers = Integer.parseInt(commandLine.getOptionValue("min"));
                } catch (NumberFormatException e) {
                    ircService.notice(nick, "Argument of -min is not a number: '%s'", commandLine.getOptionValue("min"));
                    errorCount++;
                }
            }

            if (commandLine.hasOption("max")) {
                try {
                    maxUsers = Integer.parseInt(commandLine.getOptionValue("max"));
                } catch (NumberFormatException e) {
                    ircService.notice(nick, "Argument of -max is not a number: '%s'", commandLine.getOptionValue("max"));
                    errorCount++;
                }
            }

            String mask = commandLine.getArgList().get(0);

            if (commandLine.hasOption("show")) {
                String flags = commandLine.getOptionValue("show");

                if (!flags.matches("^[mt]+$")) {
                    ircService.notice(nick, "Invalid -show flags '%s'. Allowed flags: 'mt'", flags);
                    errorCount++;
                } else {
                    if (flags.indexOf('m') != -1) {
                        showModes = true;
                    }

                    if (flags.indexOf('t') != -1) {
                        showTopicAuthor = true;
                    }
                }
            }

            if (errorCount > 0) {
                ircService.notice(nick, "Your query contains %d errors. Use /SQUERY %s HELP LIST", errorCount, serviceName);
                return;
            }

            String querySummary = buildQuerySummary(nick, minUsers, maxUsers, topic, showModes, showTopicAuthor, mask);
            ircService.notice(nick, querySummary);

            ircService.notice(nick, "Returning a maximum of %d channel names.", maxResults);

            Collection<ChannelData> channels = channelService.find(null, mask, MatchType.REG_EXP, topic, minUsers, maxUsers, null, null);
            int actualResultCount = channels.size();
            channels = channels.stream().limit(maxResults).collect(Collectors.toList());

            for (ChannelData channel : channels) {
                StringBuilder response = new StringBuilder();

                response.append(String.format("%-50s", channel.getName()));

                if (showModes) {
                    response.append(String.format(" %-12s", channel.getModes()));
                }

                response.append(String.format("%c%3d%c:", 0x2, channel.getUserCount(), 0x2));

                if (channel.getTopic() != null) {
                    response.append(String.format(" %s", channel.getTopic()));
                }

                if (showTopicAuthor && channel.getTopicFrom() != null) {
                    response.append(String.format(" (%s)", channel.getTopicFrom()));
                }

                ircService.notice(nick, response.toString());
            }

            ircService.notice(nick, "Found %d visible channels.", actualResultCount);
        } catch (ParseException e) {
            LOGGER.error("Failed to parse '{}' from {}", message, from, e);
        }
    }

    private String buildQuerySummary(String nick, Integer minUsers, Integer maxUsers, String topic, boolean showModes, boolean showTopicAuthor, String mask) {
        StringBuilder querySummary = new StringBuilder("Query summary: searching for channels matching \"");
        querySummary.append(mask);
        querySummary.append("\"");

        if (minUsers != null) {
            querySummary.append(", minimum ");
            querySummary.append(minUsers);
            querySummary.append(" users");
        }

        if (maxUsers != null) {
            querySummary.append(", maximum ");
            querySummary.append(maxUsers);
            querySummary.append(" users");
        }

        if (topic != null) {
            querySummary.append(", topic containing \"");
            querySummary.append(topic);
            querySummary.append("\"");
        }

        if (showModes) {
            querySummary.append(", showing mode");
        }

        if (showTopicAuthor) {
            querySummary.append(", showing who set the topic");
        }

        return querySummary.toString();
    }

    /**
     * Handler for: /SQUERY Clis HELP LIST and /SQUERY Clis HELP LIST EXAMPLES
     *
     * @param from User who sent the SQUERY
     * @param message "HELP LIST [EXAMPLES]"
     */
    @Override
    public void processHelp(User from, String message) {
        String nick = from.getNick();

        String[] args = message.split(" ");

        if (args.length > 2 && args[2].equalsIgnoreCase("EXAMPLES")) {
            ircService.notice(nick, "LIST Examples:");
            ircService.notice(nick, "/SQUERY %s LIST -min 10 #ircnet*", serviceName);
            ircService.notice(nick, "  Lists all channels which start with #ircnet (#ircnet, #ircnet.com, ..) and have at least 10 users");

            ircService.notice(nick, "/SQUERY %s LIST -min 10 -t http *", serviceName);
            ircService.notice(nick, "  Lists all channels whose topic contains \"http\" and have at least 10 users");

            ircService.notice(nick, "/SQUERY %s LIST -show mt *", serviceName);
            ircService.notice(nick, "  Lists all channels and shows the modes and the topic author");
        } else {
            ircService.notice(nick, "Usage: /SQUERY %s [options] <mask>", serviceName);
            sendOptionSyntax(nick, options);
            ircService.notice(nick, "For LIST examples use /SQUERY %s HELP LIST EXAMPLES", serviceName);
        }
    }

    private void sendOptionSyntax(String nick, Options options) {
        for(Option option : options.getOptions()) {
            StringBuilder stringBuilder = new StringBuilder();

            if (option.getOpt() == null) {
                stringBuilder.append("   ").append("--").append(option.getLongOpt());
            } else {
                stringBuilder.append("-").append(option.getOpt());
                if (option.hasLongOpt()) {
                    stringBuilder.append(',').append("--").append(option.getLongOpt());
                }
            }

            if (option.hasArg()) {
                String argName = option.getArgName();
                if (argName != null && argName.length() == 0) {
                    stringBuilder.append(' ');
                } else {
                    stringBuilder.append(option.hasLongOpt() ? " " : " ");
                    stringBuilder.append("<").append(argName != null ? option.getArgName() : "arg").append(">");
                }
            }

            ircService.notice(nick, " %-20s %s", stringBuilder.toString(), option.getDescription() != null ? option.getDescription() : "");
        }
    }
}
