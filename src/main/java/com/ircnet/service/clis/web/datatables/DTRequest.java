package com.ircnet.service.clis.web.datatables;

import java.util.List;

/**
 * TODO
 */
public class DTRequest {
  /**
   * Draw counter. This is used by DataTables to ensure that the Ajax returns from server-side processing requests are
   * drawn in sequence by DataTables (Ajax requests are asynchronous and thus can return out of sequence).
   * This is used as part of the draw return parameter (see below).
   */
  private Integer draw;

  /**
   * Paging first record indicator. This is the start point in the current data set (0 index based - i.e. 0 is the
   * first record).
   */
  private Integer start;

  /**
   * Number of records that the table can display in the current draw. It is expected that the number of records
   * returned will be equal to this number, unless the server has fewer records to return. Note that this can be -1 to
   * indicate that all records should be returned (although that negates any benefits of server-side processing!)
   */
  private Integer length;

  /**
   * TODO.
   */
  private Search search;

  /**
   * TODO.
   */
  private List<Order> order;

  /**
   * TODO.
   */
  private List<Column> columns;

  public DTRequest() {
  }

  public Integer getDraw() {
    return draw;
  }

  public void setDraw(Integer draw) {
    this.draw = draw;
  }

  public Integer getStart() {
    return start;
  }

  public void setStart(Integer start) {
    this.start = start;
  }

  public Integer getLength() {
    return length;
  }

  public void setLength(Integer length) {
    this.length = length;
  }

  public Search getSearch() {
    return search;
  }

  public void setSearch(Search search) {
    this.search = search;
  }

  public List<Order> getOrder() {
    return order;
  }

  public void setOrder(List<Order> order) {
    this.order = order;
  }

  public List<Column> getColumns() {
    return columns;
  }

  public void setColumns(List<Column> columns) {
    this.columns = columns;
  }
}
