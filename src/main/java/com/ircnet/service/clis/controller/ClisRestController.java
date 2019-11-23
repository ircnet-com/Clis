package com.ircnet.service.clis.controller;

import com.ircnet.service.clis.ChannelData;
import com.ircnet.service.clis.constant.FilterBy;
import com.ircnet.service.clis.constant.SortBy;
import com.ircnet.service.clis.constant.SortOrder;
import com.ircnet.service.clis.controller.datatables.*;
import com.ircnet.service.clis.service.ChannelService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;


/**
 * REST controller.
 */
@RestController
public class ClisRestController {
    @Autowired
    private ChannelService channelService;

    @Resource(name = "channelMap")
    private Map<String, ChannelData> channelMap;

    /**
     *  Finds channels by given criteria.
     *
     * @return JSON response containing a list of channels and some meta data
     */
    @RequestMapping(value = "/")
    @CrossOrigin(origins = "*")
    public DTResponse list(@RequestBody DTRequest input) {
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
        if(input.getSearch() != null && !StringUtils.isBlank(input.getSearch().getValue())) {
            searchTerm = input.getSearch().getValue();
        }

        // column specific search
        for(Column column : input.getColumns()) {
            Search search = column.getSearch();

            if(search != null && !StringUtils.isBlank(search.getValue())) {
                if(column.getData().equalsIgnoreCase(FilterBy.NAME.name())) {
                    channelFilter = search.getValue();
                }

                else if(column.getData().equalsIgnoreCase(FilterBy.TOPIC.name())) {
                    topicFilter = search.getValue();
                }
            }
        }

        Collection<ChannelData> channels = channelService.filterDataTable(searchTerm, channelFilter, topicFilter, sortBy, sortOrder);

        List<ChannelData> sublist = new ArrayList<>(channels).subList(input.getStart(), Math.min(input.getStart() + channels.size()-input.getStart(), input.getStart() + input.getLength()));

        return new DTResponse<>(input.getDraw(), channels.size(), channels.size(), sublist.toArray(new ChannelData[0]));
    }
}
