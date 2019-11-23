package com.ircnet.service.clis.controller.datatables;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

  /**
   *
   * @return a {@link Map} of {@link Column} indexed by name
   */
/*  public Map<String, Column> getColumnsAsMap() {
    Map<String, Column> map = new HashMap<>();
    for (Column column : columns) {
      map.put(column.getData(), column);
    }
    return map;
  }*/

  /**
   * Find a column by its name
   *
   * @param columnName the name of the column
   * @return the given Column, or <code>null</code> if not found
   */
/*  public Column getColumn(String columnName) {
    if (columnName == null) {
      return null;
    }
    for (Column column : columns) {
      if (columnName.equals(column.getData())) {
        return column;
      }
    }
    return null;
  }*/

  /**
   * Add a new column
   *
   * @param columnName the name of the column
   * @param searchable whether the column is searchable or not
   * @param orderable whether the column is orderable or not
   * @param searchValue if any, the search value to apply
   */
/*  public void addColumn(String columnName, boolean searchable, boolean orderable,
                        String searchValue) {
    this.columns.add(new Column(columnName, "", searchable, orderable,
        new Search(searchValue, false)));
  }*/

  /**
   * Add an order on the given column
   *
   * @param columnName the name of the column
   * @param ascending whether the sorting is ascending or descending
   */
/*  public void addOrder(String columnName, boolean ascending) {
    if (columnName == null) {
      return;
    }
    for (int i = 0; i < columns.size(); i++) {
      if (!columnName.equals(columns.get(i).getData())) {
        continue;
      }
      order.add(new Order(i, ascending ? "asc" : "desc"));
    }
  }*/

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
