package com.ircnet.service.clis.web.datatables;

import lombok.Data;

/**
 * https://datatables.net/manual/server-side
 */
@Data
public class Order {
  /**
   * Column to which ordering should be applied. This is an index reference to the columns array of information that is
   * also submitted to the server.
   */
  private Integer column;

  /**
   * Ordering direction for this column. It will be asc or desc to indicate ascending ordering or descending ordering,
   * respectively.
   */
  private String dir;
}
