package com.ircnet.service.clis;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

/**
 * Contains channel information.
 */
@Data
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

  /**
   * Modification date.
   * Used to remove obsolete channels.
   */
  private Date modificationDate;

  public ChannelData() {
    this.modes = "+";
  }

  public ChannelData(String name) {
    this();
    this.name = name;
  }
}
