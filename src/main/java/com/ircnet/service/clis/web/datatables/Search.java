package com.ircnet.service.clis.web.datatables;

import lombok.Data;

/**
 * https://datatables.net/manual/server-side
 */
@Data
public class Search {
  /**
   * Global search value. To be applied to all columns which have searchable as true.
   */
  private String value;

  /**
   * true if the global filter should be treated as a regular expression for advanced searching, false otherwise.
   * Note that normally server-side processing scripts will not perform regular expression searching for performance
   * reasons on large data sets, but it is technically possible and at the discretion of your script.
   */
  private Boolean regex;
}
