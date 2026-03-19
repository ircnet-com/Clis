package com.ircnet.service.clis.persistence.file;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ircnet.service.clis.ChannelData;
import com.ircnet.service.clis.ClisProperties;
import com.ircnet.service.clis.persistence.PersistenceService;
import com.ircnet.service.clis.service.ChannelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Persists channels in a JSON file.
 */
@Component
@Slf4j
public class FilePersistenceServiceImpl implements PersistenceService {
//  @Value("${persistence.file}")
//  private String fileName;

  private final Map<String, ChannelData> channelMap;
  private final ChannelService channelService;
  private final ObjectMapper objectMapper;
  private final ClisProperties properties;

  public FilePersistenceServiceImpl(@Qualifier("channelMap") Map<String, ChannelData> channelMap,
                                    ChannelService channelService,
                                    ObjectMapper objectMapper,
                                    ClisProperties properties) {
    this.channelMap = channelMap;
    this.channelService = channelService;
    this.objectMapper = objectMapper;
    this.properties = properties;
  }

  /**
   * Saves channels..
   */
  @Override
  public void saveChannels() {
    if(channelMap.isEmpty()) {
      return;
    }

    String fileName = properties.getPersistence().getFile();
    log.trace("Saving {} channels to {}", channelMap.values().size(), fileName);

    Collection<ChannelData> channels = channelMap.values()
            .stream().filter(e -> !channelService.isObsoleteChannel(e))
//        .stream().filter(e -> e.getModes().indexOf('s') == -1 && e.getModes().indexOf('p') == -1)
        .collect(Collectors.toList());

    File file = new File(fileName);

    try {
      objectMapper.addMixIn(ChannelData.class, ChannelDataMixin.class).writeValue(file, channels);
    }
    catch (IOException e) {
      log.error("Failed to save channels", e);
    }

    log.info("Saved {} channels to {}", channels.size(), fileName);
  }

  /**
   * Loads channels.
   */
  @Override
  public void loadChannels() {
    if(!channelMap.isEmpty()) {
      channelMap.clear();
    }

    String fileName = properties.getPersistence().getFile();
    log.trace("Loading channels from {}", fileName);

    List<ChannelData> channels;
    File file = new File(fileName);

    try {
      channels = objectMapper.readValue(file, new TypeReference<>() {
      });
    } catch (IOException e) {
      log.info("No channels could be loaded from {}", fileName);
      return;
    }

    channels.stream().filter(e -> !channelService.isObsoleteChannel(e)).forEach(e -> channelMap.put(e.getName(), e));
    log.info("Loaded {} channels from {}", channelMap.size(), fileName);
  }
}
