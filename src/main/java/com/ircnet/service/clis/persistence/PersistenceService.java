package com.ircnet.service.clis.persistence;

/**
 * Interface for persisting channels.
 */
public interface PersistenceService {
  /**
   * Saves channels.
   * This method will be executed periodically.
   */
  void saveChannels();

  /**
   * Loads channels.
   * This method will be executed at startup.
   */
  void loadChannels();
}
