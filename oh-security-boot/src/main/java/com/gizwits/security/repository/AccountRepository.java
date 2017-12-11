package com.gizwits.security.repository;

import com.gizwits.service.BaseRepository;
import com.gizwits.security.entity.AccountEntity;

/**
 * @author Feel
 * @date 2017/11/1
 * @email fye@gizwits.com
 * @since 1.0
 */
public interface AccountRepository extends BaseRepository<AccountEntity, Long> {

    AccountEntity findOneByUsername(String username);

    AccountEntity findOneByUsernameAndPassword(String username, String password);
}
