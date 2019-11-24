package com.ircnet.service.clis.web.dto;

import com.ircnet.service.clis.ChannelData;

/**
 * Channel object which will be exposed by the API.
 */
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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getTopic() {
    return topic;
  }

  public void setTopic(String topic) {
    this.topic = topic;
  }

  public String getTopicFrom() {
    return topicFrom;
  }

  public void setTopicFrom(String topicFrom) {
    this.topicFrom = topicFrom;
  }

  public String getModes() {
    return modes;
  }

  public void setModes(String modes) {
    this.modes = modes;
  }

  public int getUserCount() {
    return userCount;
  }

  public void setUserCount(int userCount) {
    this.userCount = userCount;
  }
}
