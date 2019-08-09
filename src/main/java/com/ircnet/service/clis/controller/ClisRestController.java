package com.ircnet.service.clis.controller;

import com.ircnet.service.clis.ChannelData;
import com.ircnet.service.clis.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class ClisRestController {
    @Autowired
    private ChannelService channelService;

    @RequestMapping("/")
    public Collection<ChannelData> getList(@RequestParam(name = "topic", required = false) String topic,
                                           @RequestParam(name = "min", required = false) Integer minUsers,
                                           @RequestParam(name = "max", required = false) Integer maxUsers,
                                           @RequestParam(name = "sortby", required = false, defaultValue = "name") String sortBy,
                                           @RequestParam(name = "order", required = false, defaultValue = "ASC") String sortOrder) {
        // TODO: validate sortBy and sortOrder
        return channelService.findAll(topic, minUsers, maxUsers, sortBy, sortOrder);
    }
}
