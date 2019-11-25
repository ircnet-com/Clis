package com.ircnet.service.clis.web.controller;

import com.ircnet.service.clis.ChannelData;
import com.ircnet.service.clis.constant.FilterBy;
import com.ircnet.service.clis.constant.MatchType;
import com.ircnet.service.clis.constant.SortBy;
import com.ircnet.service.clis.constant.SortOrder;
import com.ircnet.service.clis.web.datatables.*;
import com.ircnet.service.clis.service.ChannelService;
import com.ircnet.service.clis.web.dto.ChannelDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * REST controller.
 */
@RestController
public class ClisRestController {
    @Autowired
    private ChannelService channelService;

    /**
     *  Finds channels by given criteria.
     *
     * @return JSON response containing a list of channels and some meta data
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    @CrossOrigin(origins = "*")
    public DTResponse postList(@RequestBody DTRequest input) {
      String channelFilter = null;
      String topicFilter = null;
      SortBy sortBy = SortBy.NAME;
      SortOrder sortOrder = SortOrder.ASC;

      try {
        Order order = input.getOrder().get(0);
        Integer orderColumn = order.getColumn();

        if (orderColumn != null) {
          Column column = input.getColumns().get(orderColumn);

          if (column != null) {
            sortBy = SortBy.valueOf(StringUtils.upperCase(column.getData()));
            sortOrder = SortOrder.valueOf(StringUtils.upperCase(input.getOrder().get(0).getDir()));
          }
        }
      }
      catch (Exception e) {
        e.printStackTrace();
      }

      String searchTerm = null;

      // global search
      if (input.getSearch() != null && !StringUtils.isBlank(input.getSearch().getValue())) {
        searchTerm = input.getSearch().getValue();
      }

      // column specific search
      for (Column column : input.getColumns()) {
        Search search = column.getSearch();

        if (search != null && !StringUtils.isBlank(search.getValue())) {
          if (column.getData().equalsIgnoreCase(FilterBy.NAME.name())) {
            channelFilter = search.getValue();
          }

          else if (column.getData().equalsIgnoreCase(FilterBy.TOPIC.name())) {
            topicFilter = search.getValue();
          }
        }
      }

      Collection<ChannelData> channels = channelService.find(searchTerm, channelFilter, MatchType.CONTAINS, topicFilter, null, null, sortBy, sortOrder);
      List<ChannelData> sublist = new ArrayList<>(channels).subList(input.getStart(), Math.min(input.getStart() + channels.size() - input.getStart(), input.getStart() + input.getLength()));
      List<ChannelDTO> channelDTOs = sublist.stream().map(e -> new ChannelDTO(e)).collect(Collectors.toList());
      return new DTResponse<>(input.getDraw(), channels.size(), channels.size(), channelDTOs);
    }

  /**
   *  Finds channels by given criteria.
   *
   * @param channelFilter Channel mask (mandatory). Supports wildcards '*' and '?'. Examples: #*irc*, #?, *
   * @param topicFilter Topic of the channel must contain this text (optional). Wildcards are not supported.
   * @param minUsersFilter Minimum users (optional)
   * @param maxUsersFilter Maximum users (optional)
   * @param start Paging first record indicator. This is the start point in the current data set (0 index based - i.e. 0 is the first record).
   * @param size Maximum number of channels to return
   * @param sortByParam Sort entries by this attribute (optional). Allowed values: "name" and "userCount"
   * @param sortOrderParam Defines the sort order (optional). Allowed values: "asc" and "desc"
   *
   * @return A list of channels
   */
  @RequestMapping(value = "/", method = RequestMethod.GET)
  public DTResponse getList(@RequestParam(name = "name", required = false) String channelFilter,
                                         @RequestParam(name = "topic", required = false) String topicFilter,
                                         @RequestParam(name = "min", required = false) Integer minUsersFilter,
                                         @RequestParam(name = "max", required = false) Integer maxUsersFilter,
                                         @RequestParam(name = "start", required = false, defaultValue = "0") Integer start,
                                         @RequestParam(name = "size", required = false) Integer size,
                                         @RequestParam(name = "sortby", required = false) String sortByParam,
                                         @RequestParam(name = "order", required = false) String sortOrderParam) {
    SortBy sortBy = null;
    SortOrder sortOrder = null;

    try {
      sortBy = SortBy.valueOf(StringUtils.upperCase(sortByParam));
      sortOrder = SortOrder.valueOf(StringUtils.upperCase(sortOrderParam));
    }
    catch (Exception e) {
    }

    Collection<ChannelData> channels = channelService.find(null, channelFilter, MatchType.CONTAINS, topicFilter, minUsersFilter, maxUsersFilter, sortBy, sortOrder);

    if(size == null) {
      size = channels.size();
    }

    List<ChannelData> sublist = new ArrayList<>(channels).subList(start, Math.min(start + channels.size() - start, start + size));
    List<ChannelDTO> channelDTOs = sublist.stream().map(e -> new ChannelDTO(e)).collect(Collectors.toList());

    return new DTResponse<>(0, channels.size(), channelDTOs.size(), channelDTOs);
  }
}
