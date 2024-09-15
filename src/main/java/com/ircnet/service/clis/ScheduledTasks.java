package com.ircnet.service.clis;

import com.ircnet.service.clis.persistence.PersistenceService;
import com.ircnet.service.clis.service.ChannelService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Map;

/**
 * Tasks that will be executed periodically.
 */
@Component
public class ScheduledTasks {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledTasks.class);

  @Autowired
  private PersistenceService persistenceService;

  @Resource(name = "channelMap")
  private Map<String, ChannelData> channelMap;

  @Autowired
  private ChannelService channelService;

  /**
   * Task for saving channels.
   */
  @Scheduled(fixedRateString = "${persistence.interval}")
  public void persistTask() {
    persistenceService.saveChannels();
  }

  /**
   * Task for removing obsolete channels.
   */
  @Scheduled(fixedRate = 600000)
  public void cleanUpTask() {
    Iterator<Map.Entry<String, ChannelData>> iterator = channelMap.entrySet().iterator();

    while(iterator.hasNext()) {
      Map.Entry<String, ChannelData> entry = iterator.next();

      if(channelService.isObsoleteChannel(entry.getValue())) {
        LOGGER.debug("Removed obsolete channel {}", entry.getKey());
        iterator.remove();
      }
    }
  }
}
