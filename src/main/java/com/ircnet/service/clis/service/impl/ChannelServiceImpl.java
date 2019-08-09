package com.ircnet.service.clis.service.impl;

import com.ircnet.service.clis.ChannelData;
import com.ircnet.service.clis.service.ChannelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * TODO.
 */
@Component
public class ChannelServiceImpl implements ChannelService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChannelServiceImpl.class);

    @Resource(name = "channelMap")
    private Map<String, ChannelData> channelMap;

    @Override
    public void updateOrInsert(String name, int userCount) {
        Assert.isTrue(userCount > 0, "User count must be greater than zero");

        ChannelData channelData = channelMap.get(name);

        if (channelData == null) {
            channelData = new ChannelData(name);
            channelMap.put(name, channelData);
        }

        channelData.setUserCount(userCount);
    }

    @Override
    public void updateModes(String name, String modes) {
        ChannelData channelData = channelMap.get(name);

        if (channelData == null) {
            channelData = new ChannelData(name);
            channelMap.put(name, channelData);
        }

        channelData.setModes(modes);
    }

    @Override
    public void updateTopic(String name, String topic, String from) {
        ChannelData channelData = channelMap.get(name);

        if (channelData == null) {
            channelData = new ChannelData(name);
            channelMap.put(name, channelData);
        }

        channelData.setTopic(topic);
        channelData.setTopicFrom(from);
    }

    @Override
    public Collection<ChannelData> findAll(String topic, Integer minUsers, Integer maxUsers, String sortBy, String sortOrder) {
        LOGGER.debug("Filtering channels for query: topic={} minUsers={} maxUsers={} sortBy={} sortOrder={}", topic, minUsers, maxUsers, sortBy);
        Instant start = Instant.now();

        Stream<Map.Entry<String, ChannelData>> stream = channelMap.entrySet().stream();

        stream = stream.filter(e -> e.getValue().getModes().indexOf('s') == -1 && e.getValue().getModes().indexOf('p') == -1);

        if(minUsers != null) {
            stream = stream.filter(e -> e.getValue().getUserCount() >= minUsers);
        }

        if(maxUsers != null) {
            stream = stream.filter(e -> e.getValue().getUserCount() <= maxUsers);
        }

        if(topic != null) {
            stream = stream.filter(e -> e.getValue().getTopic() != null && e.getValue().getTopic().contains(topic));
        }

        Stream<ChannelData> channelDataStream = stream.map(e -> e.getValue());

        // Sort
        Comparator<ChannelData> comparator;

        if("usercount".equalsIgnoreCase(sortBy)) {
            comparator = Comparator.comparingInt(ChannelData::getUserCount);

        }
        else {
            // Sort by name
            comparator = Comparator.comparing(ChannelData::getName);
        }

        if("desc".equalsIgnoreCase(sortOrder)) {
            comparator = comparator.reversed();
        }

        channelDataStream = channelDataStream.sorted(comparator);

        List<ChannelData> channelList = channelDataStream.collect(Collectors.toList());

        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();
        LOGGER.debug("Found {} channels in {} seconds", channelList.size(), timeElapsed / 1000.0);

        return channelList;
    }
}
