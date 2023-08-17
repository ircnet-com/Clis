package com.ircnet.service.clis.web.dto;

import com.ircnet.service.clis.ChannelData;
import lombok.Data;

/**
 * Channel object which will be exposed by the API.
 */
@Data
public class ChannelDTO {
  /**
   * Name of the channel.
   */
  private String name;

  /**
   * Topic of the channel. May be null.
   */
  private String topic;

  /**
   * Author of the topic. May be null.
   */
  private String topicFrom;

  /**
   * Modes of the channel. Starts with '+'.
   */
  private String modes;

  /**
   * User count of the channel.
   * The value is 0, if the channel is only stored to keep the topic.
   */
  private int userCount;

  public ChannelDTO() {
  }

  public ChannelDTO(ChannelData channelData) {
    this.name = channelData.getName();
    this.topic = channelData.getTopic();
    this.topicFrom = channelData.getTopicFrom();
    this.modes = channelData.getModes();
    this.userCount = channelData.getUserCount();
  }
}
