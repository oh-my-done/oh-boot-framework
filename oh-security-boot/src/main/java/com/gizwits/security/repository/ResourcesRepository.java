package com.gizwits.security.repository;

import com.gizwits.service.BaseRepository;
import com.gizwits.security.entity.ResourcesEntity;

/**
 * @author Feel
 * @date 2017/11/1
 * @email fye@gizwits.com
 * @since 1.0
 */
public interface ResourcesRepository extends BaseRepository<ResourcesEntity, Long> {
    ResourcesEntity findOneByName(String name);
}
