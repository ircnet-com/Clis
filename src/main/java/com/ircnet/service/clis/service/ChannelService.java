package com.ircnet.service.clis.service;

import com.ircnet.service.clis.entity.ChannelEntity;

import java.util.List;

/**
 * TODO.
 */
public interface ChannelService {
  /**
   * If the channel does not exist already, the channel and the user account will be inserted.
   * If the channel exists already, the user count will be updated.
   *
   * @param name Name of the channel
   * @param userCount User count
   */
  void updateOrInsert(String name, int userCount);

  /**
   * Removes a channel.
   *
   * @param name Name of the channel
   */
  void remove(String name);

  /**
   * Updates the modes of a channel.
   *
   * @param name Name of the channel
   * @param modes New channel modes
   */
  void updateModes(String name, String modes);

  /**
   * Updates the topic of a channel.
   *
   * @param name Name of the channel
   * @param topic New topic
   * @param nick Nick of the one who set the topic (optional)
   */
  void updateTopic(String name, String topic, String nick);

  /**
   * Finds channels by given criteria.
   */
  List<ChannelEntity> findAll(String topic, Integer minUsers, Integer maxUsers, String sortBy, String sortOrder);

  /**
   * Updates the visibility status.
   *
   * @param name Name of the channel
   * @param visible true if the channel is visible, otherwise false
   */
  void updateVisibility(String name, boolean visible);
}
