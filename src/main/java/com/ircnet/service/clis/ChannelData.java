package com.ircnet.service.clis;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Contains channel information.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChannelData {
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

  public ChannelData() {
  }

  public ChannelData(String name) {
    this.name = name;
  }

  public ChannelData(String name, String topic, String topicFrom, String modes, int userCount) {
    this.name = name;
    this.topic = topic;
    this.topicFrom = topicFrom;
    this.modes = modes;
    this.userCount = userCount;
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
