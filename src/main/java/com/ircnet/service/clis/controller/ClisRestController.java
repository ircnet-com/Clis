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

@RestController
public class ClisRestController {
    @Autowired
    private ChannelService channelService;

    @RequestMapping(value = { "/", "/{mask}" })
    public Collection<ChannelData> getList(@RequestParam(name = "topic", required = false) String topic,
                                           @RequestParam(name = "min", required = false) Integer minUsers,
                                           @RequestParam(name = "max", required = false) Integer maxUsers,
                                           @RequestParam(name = "sortby", required = false, defaultValue = "name") String sortBy,
                                           @RequestParam(name = "order", required = false, defaultValue = "ASC") String sortOrder,
                                           @PathVariable(required = false) Optional<String> mask) {
        // TODO: validate sortBy and sortOrder
        return channelService.find(mask.isPresent() ? mask.get() : null, topic, minUsers, maxUsers, sortBy, sortOrder);
    }
}
