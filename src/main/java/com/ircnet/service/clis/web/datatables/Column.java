package com.ircnet.service.clis.web.datatables;


/**
 * https://datatables.net/manual/server-side
 */
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

  public Column() {
  }

  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Boolean getSearchable() {
    return searchable;
  }

  public void setSearchable(Boolean searchable) {
    this.searchable = searchable;
  }

  public Boolean getOrderable() {
    return orderable;
  }

  public void setOrderable(Boolean orderable) {
    this.orderable = orderable;
  }

  public Search getSearch() {
    return search;
  }

  public void setSearch(Search search) {
    this.search = search;
  }
}
