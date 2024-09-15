package com.ircnet.service.clis.persistence.file;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ircnet.service.clis.ChannelData;
import com.ircnet.service.clis.persistence.PersistenceService;
import com.ircnet.service.clis.service.ChannelService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
public class FilePersistenceServiceImpl implements PersistenceService {
  private static final Logger LOGGER = LoggerFactory.getLogger(FilePersistenceServiceImpl.class);

  @Value("${persistence.file}")
  private String fileName;

  @Resource(name = "channelMap")
  private Map<String, ChannelData> channelMap;

  @Autowired
  private ChannelService channelService;

  @Autowired
  private ObjectMapper objectMapper;

  /**
   * Saves channels..
   */
  @Override
  public void saveChannels() {
    if(channelMap.isEmpty()) {
      return;
    }

    LOGGER.trace("Saving {} channels to {}", channelMap.values().size(), fileName);

    Collection<ChannelData> channels = channelMap.values()
            .stream().filter(e -> !channelService.isObsoleteChannel(e))
//        .stream().filter(e -> e.getModes().indexOf('s') == -1 && e.getModes().indexOf('p') == -1)
        .collect(Collectors.toList());

    File file = new File(fileName);

    try {
      objectMapper.addMixIn(ChannelData.class, ChannelDataMixin.class).writeValue(file, channels);
    }
    catch (IOException e) {
      LOGGER.error("Failed to save channels", e);
    }

    LOGGER.info("Saved {} channels to {}", channels.size(), fileName);
  }

  /**
   * Loads channels.
   */
  @Override
  public void loadChannels() {
    if(!channelMap.isEmpty()) {
      channelMap.clear();
    }

    LOGGER.trace("Loading channels from {}", fileName);

    List<ChannelData> channels;
    File file = new File(fileName);

    try {
      channels = objectMapper.readValue(file, new TypeReference<List<ChannelData>>(){});
    } catch (IOException e) {
      LOGGER.info("No channels could be loaded from {}", fileName);
      return;
    }

    channels.stream().filter(e -> !channelService.isObsoleteChannel(e)).forEach(e -> channelMap.put(e.getName(), e));

    LOGGER.info("Loaded {} channels from {}", channelMap.size(), fileName);
  }
}
