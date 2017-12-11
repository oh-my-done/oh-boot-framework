package com.gizwits.security.servcie;

import com.gizwits.security.entity.AuthorityEntity;
import com.gizwits.security.repository.AuthorityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author Feel
 * @date 2017/11/1
 * @email fye@gizwits.com
 * @since 1.0
 */
@Service
@Transactional
@Slf4j
public class AuthorityService {

    @Autowired
    private AuthorityRepository authorityRepository;

    public AuthorityEntity save(AuthorityEntity authorityEntity) {

        AuthorityEntity oneByName = authorityRepository.findOneByName(authorityEntity.getName());
        if (oneByName == null) {
            return authorityRepository.save(authorityEntity);
        }
        return null;
    }

    public AuthorityEntity findByAuthorityName(String name) {
        return authorityRepository.findOneByName(name);
    }

    public List<AuthorityEntity> findAll() {
        return authorityRepository.findAll();
    }

    public void findMenus() {


        List<AuthorityEntity> menusTotals = authorityRepository.findAll();


    }

}
