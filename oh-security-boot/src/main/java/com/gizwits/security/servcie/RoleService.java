package com.gizwits.security.servcie;

import com.gizwits.security.entity.RoleEntity;
import com.gizwits.security.repository.RoleRepository;
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
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public RoleEntity save(RoleEntity roleEntity) {

        RoleEntity oneByRoleName = roleRepository.findOneByRoleName(roleEntity.getRoleName());
        if (oneByRoleName == null) {
            return roleRepository.save(roleEntity);
        }
        return null;
    }

    public RoleEntity findByRoleName(String roleName) {
        return roleRepository.findOneByRoleName(roleName);
    }

    public List<RoleEntity> findAll() {
        return roleRepository.findAll();
    }
}
