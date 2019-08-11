package com.ircnet.service.clis.controller;

import com.ircnet.service.clis.ChannelData;
import com.ircnet.service.clis.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Optional;

/**
 * REST controller.
 *
 */
@RestController
public class ClisRestController {
    @Autowired
    private ChannelService channelService;

    /**
     *  Finds channels by given criteria.
     *
     * @param mask Channel mask (mandatory). Supports wildcards '*' and '?'. Examples: #*irc*, #?, *
     * @param topic Topic of the channel must contain this text (optional). Wildcards are not supported.
     * @param minUsers Minimum users (optional)
     * @param maxUsers Maximum users (optional)
     * @param sortBy Sort entries by this attribute (optional). Allowed values TODO
     * @param sortOrder Defines the sort order (optional). Allowed values: ASC and DESC
     *
     * @return A list of channels
     */
    @RequestMapping(value = { "/{mask}" })
    public Collection<ChannelData> getList(@PathVariable(required = false) Optional<String> mask,
                                           @RequestParam(name = "topic", required = false) String topic,
                                           @RequestParam(name = "min", required = false) Integer minUsers,
                                           @RequestParam(name = "max", required = false) Integer maxUsers,
                                           @RequestParam(name = "sortby", required = false, defaultValue = "name") String sortBy,
                                           @RequestParam(name = "order", required = false, defaultValue = "ASC") String sortOrder) {
        // TODO: validate sortBy and sortOrder
        return channelService.find(mask.isPresent() ? mask.get() : null, topic, minUsers, maxUsers, sortBy, sortOrder);
    }
}
