package com.ircnet.service.clis;

import com.ircnet.service.clis.persistence.PersistenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class ScheduledTasks {
  private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledTasks.class);

  @Autowired
  private PersistenceService persistenceService;

  @Scheduled(fixedRateString = "${persistence.interval}")
  public void persistTask() {
    persistenceService.saveChannels();
  }
}
