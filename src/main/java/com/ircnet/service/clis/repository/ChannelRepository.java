package com.ircnet.service.clis.repository;

import com.ircnet.service.clis.entity.ChannelEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * TODO.
 */
public interface ChannelRepository extends JpaRepository<ChannelEntity, String>,JpaSpecificationExecutor<ChannelEntity> {
  ChannelEntity findByNameIgnoreCase(String name);
}
