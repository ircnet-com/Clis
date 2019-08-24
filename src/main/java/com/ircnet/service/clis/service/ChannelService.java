package com.ircnet.service.clis.service;

import com.ircnet.service.clis.ChannelData;

import java.util.Collection;

/**
 * Finds and updates channels which are stored in memory.
 */
public interface ChannelService {
  /**
   * If the channel does not exist already, the channel and the user count will be inserted.
   * If the channel exists already, the user count will be updated.
   *
   * @param name Name of the channel
   * @param userCount User count
   */
  void updateOrInsert(String name, int userCount);

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
   * @param nick Nick of the one who set the topic, can be null
   */
  void updateTopic(String name, String topic, String nick);

  /**
   * Finds channels by given criteria.
   *
   * @param mask Channel mask (mandatory). Supports wildcards '*' and '?'. Examples: #*irc*, #?, *
   * @param topic Topic of the channel must contain this text. Can be null. Wildcards are not supported.
   * @param minUsers Minimum users. Can be null.
   * @param maxUsers Maximum users. Can be null.
   * @param sortBy Sort entries by this attribute (optional). Allowed values: "name" and "userCount"
   * @param sortOrder Defines the sort order (optional). Allowed values: "asc" and "desc"
   *
   * @return A list of channels, may be empty, never null
   */
  Collection<ChannelData> find(String mask, String topic, Integer minUsers, Integer maxUsers, String sortBy, String sortOrder);

  /**
   * Checks if a channel is obsolete.
   *
   * @param channelData A channel
   * @return true if the channel is obsolete, otherwise false
   */
  boolean isObsoleteChannel(ChannelData channelData);
}
