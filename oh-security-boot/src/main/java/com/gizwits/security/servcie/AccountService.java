package com.gizwits.security.servcie;

import com.gizwits.security.entity.AccountEntity;
import com.gizwits.security.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author Feel
 * @date 2017/11/1
 * @email fye@gizwits.com
 * @since 1.0
 */
@Service
@Slf4j
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    /**
     * 新增用户
     *
     * @param accountEntity
     * @return
     */
    @Transactional
    public AccountEntity save(AccountEntity accountEntity) {

        AccountEntity oneByUsername = accountRepository.findOneByUsername(accountEntity.getUsername());

        if (oneByUsername == null) {
            return accountRepository.save(accountEntity);
        }
        return null;
    }

    /**
     * 更新角色信息
     *
     * @param accountEntity
     * @return
     */
    @Transactional
    public AccountEntity updateRoleEnity(AccountEntity accountEntity) {

        AccountEntity oneByUsername = accountRepository.findOneByUsername(accountEntity.getUsername());
        if (oneByUsername != null) {
            oneByUsername.setRoleEntities(accountEntity.getRoleEntities());
            return accountRepository.save(oneByUsername);
        }
        return null;
    }

    /**
     * 根据用户名密码查询账户信息
     *
     * @param usename
     * @param email
     * @return
     */
    public AccountEntity findUsernameAndPassword(String usename, String email) {

        return accountRepository.findOneByUsernameAndPassword(usename, email);
    }

    /**
     * 更加用户名查询账户信息
     *
     * @param usename
     * @return
     */
    public AccountEntity findByUsername(String usename) {

        return accountRepository.findOneByUsername(usename);
    }

}
