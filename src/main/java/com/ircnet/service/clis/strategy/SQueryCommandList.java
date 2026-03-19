package com.ircnet.service.clis.strategy;

import com.ircnet.library.common.User;
import com.ircnet.library.common.connection.SingletonIRCConnectionService;
import com.ircnet.library.service.connection.IRCServiceConnection;
import com.ircnet.library.service.squery.SQueryCommand;
import com.ircnet.service.clis.ChannelData;
import com.ircnet.service.clis.ClisProperties;
import com.ircnet.service.clis.constant.MatchType;
import com.ircnet.service.clis.service.ChannelService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/**
 * Handler for:
 *  - /SQUERY Clis LIST [options] <mask>
 *  - /SQUERY Clis HELP LIST
 */
@Component
@Order(0)
@Slf4j
public class SQueryCommandList extends SQueryCommand<SingletonIRCConnectionService> {
    private final ChannelService channelService;
    private final ClisProperties properties;

    private Options options;

    public SQueryCommandList(SingletonIRCConnectionService ircConnectionService,
                             ChannelService channelService,
                             ClisProperties properties) {
        super(ircConnectionService);
        this.channelService = channelService;
        this.properties = properties;
    }

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

    @Override
    public String getName() {
        return "LIST";
    }

    /**
     * Handler for: /SQUERY Clis LIST
     *
     * @param from User who sent the SQUERY
     * @param message message with format: LIST [options] <mask>
     */
    @Override
    public void processCommand(IRCServiceConnection ircServiceConnection, User from,
                               String message, Map<String, String> tags) {
        String nick = from.getNick();
        String[] parts = message.split(" ");
        int errorCount = 0;

        if (parts.length == 1) {
            ircConnectionService.notice(nick, "You did not specify a channel mask. Use /SQUERY %s HELP LIST", properties.getName());
            return;
        }


        try {
            String[] requestArguments = Arrays.copyOfRange(parts, 1, parts.length);
            CommandLineParser parser = new DefaultParser();
            CommandLine commandLine = parser.parse(options, requestArguments);

            if (CollectionUtils.isEmpty(commandLine.getArgList())) {
                ircConnectionService.notice(nick, "You did not specify a channel mask. Use /SQUERY %s HELP LIST", properties.getName());
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
                    ircConnectionService.notice(nick, "Argument of -min is not a number: '%s'", commandLine.getOptionValue("min"));
                    errorCount++;
                }
            }

            if (commandLine.hasOption("max")) {
                try {
                    maxUsers = Integer.parseInt(commandLine.getOptionValue("max"));
                } catch (NumberFormatException e) {
                    ircConnectionService.notice(nick, "Argument of -max is not a number: '%s'", commandLine.getOptionValue("max"));
                    errorCount++;
                }
            }

            String mask = commandLine.getArgList().get(0);

            if (commandLine.hasOption("show")) {
                String flags = commandLine.getOptionValue("show");

                if (!flags.matches("^[mt]+$")) {
                    ircConnectionService.notice(nick, "Invalid -show flags '%s'. Allowed flags: 'mt'", flags);
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
                ircConnectionService.notice(nick, "Your query contains %d errors. Use /SQUERY %s HELP LIST", errorCount, properties.getName());
                return;
            }

            String querySummary = buildQuerySummary(minUsers, maxUsers, topic, showModes, showTopicAuthor, mask);
            ircConnectionService.notice(nick, querySummary);

            ircConnectionService. notice(nick, "Returning a maximum of %d channel names.", properties.getSquery().getList().getMaxResults());

            Collection<ChannelData> channels = channelService.find(null, mask, MatchType.REG_EXP, topic, minUsers, maxUsers, null, null);
            int actualResultCount = channels.size();
            channels = channels.stream().limit(properties.getSquery().getList().getMaxResults()).toList();

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

                ircConnectionService.notice(nick, response.toString());
            }

            ircConnectionService.notice(nick, "Found %d visible channels.", actualResultCount);
        } catch (ParseException e) {
            log.debug("Failed to parse '{}' from {}", message, from, e);
        }
    }

    private String buildQuerySummary(Integer minUsers, Integer maxUsers, String topic, boolean showModes,
                                     boolean showTopicAuthor, String mask) {
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
    public void processHelp(IRCServiceConnection ircServiceConnection, User from, String message) {
        String nick = from.getNick();

        String[] args = message.split(" ");

        if (args.length > 2 && args[2].equalsIgnoreCase("EXAMPLES")) {
            ircConnectionService.notice(nick, "LIST Examples:");
            ircConnectionService.notice(nick, "/SQUERY %s LIST -min 10 #ircnet*", properties.getName());
            ircConnectionService.notice(nick, "  Lists all channels which start with #ircnet (#ircnet, #ircnet.com, ..) and have at least 10 users");

            ircConnectionService.notice(nick, "/SQUERY %s LIST -min 10 -t http *", properties.getName());
            ircConnectionService.notice(nick, "  Lists all channels whose topic contains \"http\" and have at least 10 users");

            ircConnectionService.notice(nick, "/SQUERY %s LIST -show mt *", properties.getName());
            ircConnectionService.notice(nick, "  Lists all channels and shows the modes and the topic author");
        } else {
            ircConnectionService.notice(nick, "Usage: /SQUERY %s LIST [options] <mask>", properties.getName());
            sendOptionSyntax(nick, options);
            ircConnectionService.notice(nick, "For LIST examples use /SQUERY %s HELP LIST EXAMPLES", properties.getName());
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

            ircConnectionService.notice(nick, " %-20s %s", stringBuilder.toString(), option.getDescription() != null ? option.getDescription() : "");
        }
    }
}
