package com.ircnet.service.clis.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * TODO.
 */
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChannelEntity {
  @Id
  private String name;
  private String topic;
  private String topicFrom;
  private String modes;
  private int userCount;
  private boolean invisible;

  public ChannelEntity() {
  }

  public ChannelEntity(String name, int userCount) {
    this.name = name;
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

  public boolean isInvisible() {
    return invisible;
  }

  public void setInvisible(boolean invisible) {
    this.invisible = invisible;
  }
}
