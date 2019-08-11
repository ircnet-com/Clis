package com.ircnet.service.clis;

import com.ircnet.service.clis.persistence.PersistenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Tasks that will be executed periodically.
 */
@Component
public class ScheduledTasks {
  private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledTasks.class);

  @Autowired
  private PersistenceService persistenceService;

  /**
   * Task for saving channels.
   */
  @Scheduled(fixedRateString = "${persistence.interval}")
  public void persistTask() {
    persistenceService.saveChannels();
  }
}
