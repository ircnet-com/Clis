package com.ircnet.service.clis.service.impl;

import com.ircnet.service.clis.ChannelData;
import com.ircnet.service.clis.constant.MatchType;
import com.ircnet.service.clis.constant.SortBy;
import com.ircnet.service.clis.constant.SortOrder;
import com.ircnet.service.clis.service.ChannelService;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOCase;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * {@inheritDoc}
 */
@Component
public class ChannelServiceImpl implements ChannelService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChannelServiceImpl.class);

    @Resource(name = "channelMap")
    private Map<String, ChannelData> channelMap;

    @Value("${cache.emptyChannel.maxAge:28800000}")
    private long emptyChannelMaxAge;

    @Override
    public void updateOrInsert(String name, int userCount) {
        ChannelData channelData = channelMap.get(name);

        if (channelData == null) {
            channelData = new ChannelData(name);
            channelMap.put(name, channelData);
        }

        channelData.setUserCount(userCount);
        channelData.setModificationDate(new Date());
    }

    @Override
    public void updateModes(String name, String modes) {
        ChannelData channelData = channelMap.get(name);

        if (channelData == null) {
            channelData = new ChannelData(name);
            channelMap.put(name, channelData);
        }

        channelData.setModes(modes);
        channelData.setModificationDate(new Date());
    }

    @Override
    public void updateTopic(String name, String topic, String from) {
        ChannelData channelData = channelMap.get(name);

        if (channelData == null) {
            channelData = new ChannelData(name);
            channelMap.put(name, channelData);
        }

        channelData.setTopic(topic);

        if(!(from == null && channelData.getTopic().equals(topic))) {
            channelData.setTopicFrom(from);
        }

        channelData.setModificationDate(new Date());
    }

    @Override
    public Collection<ChannelData> find(String globalFilter, String channelFilter, MatchType chanFilterMatchType, String topicFilter, Integer minUsers, Integer maxUsers, SortBy sortBy, SortOrder sortOrder) {
        LOGGER.debug("Filtering channels for query: globalFilter={} channelFilter={} topic={} minUsers={} maxUsers={} sortBy={} sortOrder={}", globalFilter, channelFilter, topicFilter, minUsers, maxUsers, sortBy);
        Instant start = Instant.now();

        Stream<Map.Entry<String, ChannelData>> stream = channelMap.entrySet().stream();

        /*
         * Remove channels which have no users.
         * These channels are stored only to keep the topic.
         */
        stream = stream.filter(e -> e.getValue().getUserCount() > 0);

        /*
         * Remove secret and private channels.
         * These channels are also stored only to keep the topic.
         */
        stream = stream.filter(e ->  e.getValue().getModes() == null || (e.getValue().getModes().indexOf('s') == -1 && e.getValue().getModes().indexOf('p') == -1));

        /*
         * Apply user count filters (optional).
         */
        if(minUsers != null) {
            stream = stream.filter(e -> e.getValue().getUserCount() >= minUsers);
        }

        if(maxUsers != null) {
            stream = stream.filter(e -> e.getValue().getUserCount() <= maxUsers);
        }

        /*
         * Apply global filter. Either channel name or topic must match.
         */
        if(!StringUtils.isBlank(globalFilter)) {
            stream = stream.filter(e -> (e.getValue().getName().contains(globalFilter)) || (e.getValue().getTopic() != null && e.getValue().getTopic().contains(globalFilter)));
        }

        /*
         * Apply channel name filter.
         */
        if(!StringUtils.isBlank(channelFilter)) {
            if(chanFilterMatchType == MatchType.REG_EXP) {
                stream = stream.filter(e -> FilenameUtils.wildcardMatch(e.getValue().getName(), channelFilter, IOCase.INSENSITIVE));
            }
            else {
                stream = stream.filter(e -> e.getValue().getName() != null && e.getValue().getName().contains(channelFilter));
            }
        }

        /*
         * Apply topic filter.
         */
        if(!StringUtils.isBlank(topicFilter)) {
            stream = stream.filter(e -> e.getValue().getTopic() != null && e.getValue().getTopic().contains(topicFilter));
        }

        Stream<ChannelData> channelDataStream = stream.map(e -> e.getValue());

        /*
         * Sorting.
         */
        Comparator<ChannelData> comparator;

        if(sortBy == null) {
          sortBy = SortBy.NAME;
        }

        switch (sortBy) {
            case USERCOUNT:
                comparator = Comparator.comparing(ChannelData::getUserCount);
                break;

            case NAME:
            default:
                comparator = Comparator.comparing(ChannelData::getName);
                break;
        }

        if(sortOrder == SortOrder.DESC) {
            comparator = comparator.reversed();
        }

        channelDataStream = channelDataStream.sorted(comparator);

        List<ChannelData> channelList = channelDataStream.collect(Collectors.toList());

        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();
        LOGGER.debug("Found {} channels in {} seconds", channelList.size(), timeElapsed / 1000.0);

        return channelList;
    }

    @Override
    public boolean isObsoleteChannel(ChannelData channelData) {
        return channelData.getUserCount() == 0 && (channelData.getModificationDate() == null
                || (System.currentTimeMillis() - channelData.getModificationDate().getTime() > emptyChannelMaxAge));
    }
}
