package com.ircnet.service.clis.persistence.file;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 */
public class ChannelDataMixin {
  @JsonIgnore
  private int userCount;
}
