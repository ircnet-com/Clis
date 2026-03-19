package com.ircnet.service.clis;

import com.ircnet.service.clis.persistence.PersistenceService;
import com.ircnet.service.clis.service.ChannelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Map;

/**
 * Tasks that will be executed periodically.
 */
@Component
@Slf4j
public class ScheduledTasks {
  private final PersistenceService persistenceService;
  private final Map<String, ChannelData> channelMap;
  private final ChannelService channelService;

  public ScheduledTasks(PersistenceService persistenceService,
                        @Qualifier("channelMap") Map<String, ChannelData> channelMap,
                        ChannelService channelService) {
    this.persistenceService = persistenceService;
    this.channelMap = channelMap;
    this.channelService = channelService;
  }

  /**
   * Task for saving channels.
   */
  @Scheduled(fixedRateString = "${service.persistence.interval}")
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
        log.debug("Removed obsolete channel {}", entry.getKey());
        iterator.remove();
      }
    }
  }
}
