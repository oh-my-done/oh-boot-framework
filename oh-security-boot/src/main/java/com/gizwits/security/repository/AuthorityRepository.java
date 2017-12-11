package com.gizwits.security.repository;

import com.gizwits.security.entity.AuthorityEntity;
import com.gizwits.service.BaseRepository;

/**
 * @author Feel
 * @date 2017/11/1
 * @email fye@gizwits.com
 * @since 1.0
 */
public interface AuthorityRepository extends BaseRepository<AuthorityEntity, Long> {

    AuthorityEntity findOneByName(String name);



}
