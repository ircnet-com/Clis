package com.ircnet.service.clis.persistence.file;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Excludes attributes from being saved as JSON.
 */
public class ChannelDataMixin {
  /**
   * Do not save user counts.
   * Initially all loaded channels will have user count 0 and will not be returned to in queries. It is mainly used
   * to save topics.
   */
  @JsonIgnore
  private int userCount;
}
