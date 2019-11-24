package com.ircnet.service.clis.service;

import com.ircnet.service.clis.ChannelData;
import com.ircnet.service.clis.constant.MatchType;
import com.ircnet.service.clis.constant.SortBy;
import com.ircnet.service.clis.constant.SortOrder;

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
   * @param globalFilter Name or topic of the channel must contain this text.
   * @param channelFilter Name of the channel. chanFilterMatchType decides now this attribute is processed.
   * @param chanFilterMatchType Determines how to apply the channelFilter: e.g. via regular expressions
   * @param topicFilter Topic of the channel must contain this text.
   * @param minUsers Minimum users. Can be null.
   * @param maxUsers Maximum users. Can be null.
   * @param sortBy Sort entries by this attribute (optional). Allowed values: "name" and "userCount"
   * @param sortOrder Defines the sort order (optional). Allowed values: "asc" and "desc"
   *
   * @return A list of channels, may be empty, never null
   */
  Collection<ChannelData> find(String globalFilter, String channelFilter, MatchType chanFilterMatchType, String topicFilter, Integer minUsers, Integer maxUsers, SortBy sortBy, SortOrder sortOrder);

  /**
   * Checks if a channel is obsolete.
   *
   * @param channelData A channel
   * @return true if the channel is obsolete, otherwise false
   */
  boolean isObsoleteChannel(ChannelData channelData);
}
