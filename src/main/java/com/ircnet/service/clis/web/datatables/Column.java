package com.ircnet.service.clis.web.datatables;


import lombok.Data;

/**
 * https://datatables.net/manual/server-side
 */
@Data
public class Column {
  /**
   * Column's data source, as defined by columns.data.
   */
  private String data;

  /**
   * Column's name, as defined by columns.name.
   */
  private String name;

  /**
   * Flag to indicate if this column is searchable (true) or not (false). This is controlled by columns.searchable.
   */
  private Boolean searchable;

  /**
   * Flag to indicate if this column is orderable (true) or not (false). This is controlled by columns.orderable.
   */
  private Boolean orderable;

  /**
   * Search to apply to this specific column.
   */
  private Search search;
}
