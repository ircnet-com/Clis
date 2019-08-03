package com.ircnet.service.clis.service.impl;

import com.ircnet.service.clis.entity.ChannelEntity;
import com.ircnet.service.clis.repository.ChannelRepository;
import com.ircnet.service.clis.service.ChannelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO.
 */
@Component
public class ChannelServiceImpl implements ChannelService {
    private static Logger LOG = LoggerFactory.getLogger(ChannelServiceImpl.class);

    @Autowired
    private ChannelRepository channelRepository;

    @Override
    public void updateOrInsert(String name, int userCount) {
        Assert.isTrue(userCount > 0, "User count must be greater than zero");

        ChannelEntity channelEntity = channelRepository.findByNameIgnoreCase(name);

        if (channelEntity != null) {
            // Channel exists in database already. Update user count.
            channelEntity.setUserCount(userCount);
            channelRepository.save(channelEntity);
            LOG.debug("Set user count of channel {} to {}", channelEntity, userCount);
        } else {
            // Channel does not exist yet. Insert channel and user count.
            channelEntity = new ChannelEntity(name, userCount);
            channelRepository.save(channelEntity);
            LOG.debug("Inserted channel {} with user count {}", channelEntity.getName(), channelEntity.getUserCount());
        }
    }

    @Override
    public void remove(String name) {
        ChannelEntity channelEntity = channelRepository.findByNameIgnoreCase(name);

        if (channelEntity != null) {
            channelRepository.delete(channelEntity);
            LOG.debug("Channel {} has been removed", name);
        } else {
            LOG.error("Failed to remove channel {} - channel does not exist in database", name);
        }
    }

    @Override
    public void updateModes(String name, String modes) {
        ChannelEntity channelEntity = channelRepository.findByNameIgnoreCase(name);

        if (channelEntity != null) {
            channelEntity.setModes(modes);
            channelRepository.save(channelEntity);
            LOG.debug("Set modes of channel {} to {}", channelEntity.getName(), channelEntity.getModes());
        } else {
            LOG.error("Failed to update modes of channel {} - channel does not exist in database", name);
        }
    }

    @Override
    public void updateVisibility(String name, boolean visible) {
        ChannelEntity channelEntity = channelRepository.findByNameIgnoreCase(name);

        if (channelEntity != null) {
            channelEntity.setInvisible(!visible);
            channelRepository.save(channelEntity);
            LOG.debug("Set channel {} as invisible", channelEntity.getName());
        } else {
            LOG.error("Failed to set channel {} as invisible - channel does not exist in database", name);
        }
    }

    @Override
    public void updateTopic(String name, String topic, String from) {
        ChannelEntity channelEntity = channelRepository.findByNameIgnoreCase(name);

        if (channelEntity != null) {
            channelEntity.setTopic(topic);
            channelEntity.setTopicFrom(from);
            channelRepository.save(channelEntity);

            if (channelEntity.getTopicFrom() != null) {
                LOG.debug("Set topic of channel {} to '{}' (from {})", channelEntity.getName(), channelEntity.getTopic(), channelEntity.getTopicFrom());
            } else {
                LOG.debug("Set topic of channel {} to '{}'", channelEntity.getName(), channelEntity.getTopic());
            }
        } else {
            LOG.error("Failed to update topic of channel {} - channel does not exist in database", name);
        }
    }

    @Override
    public List<ChannelEntity> findAll(String topic, Integer minUsers, Integer maxUsers, String sortBy, String sortOrder) {
        // TODO: exclude invisible channels

        Sort sort = Sort.by(Sort.Direction.valueOf(sortOrder.toUpperCase()), sortBy);

        return channelRepository.findAll(new Specification<ChannelEntity>() {
            @Override
            public Predicate toPredicate(Root<ChannelEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                if (topic != null) {
                    predicates.add(criteriaBuilder.like(root.get("topic"), "%" + topic + "%"));
                }

                if(minUsers != null) {
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("userCount"), minUsers));
                }

                if(maxUsers != null) {
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("userCount"), maxUsers));
                }

                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }, sort);
    }
}
