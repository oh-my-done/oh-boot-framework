package com.gizwits.security.core;

import com.gizwits.common.entity.ResponseCode;
import com.gizwits.security.entity.AccountEntity;
import com.gizwits.security.entity.RoleEntity;
import com.gizwits.security.servcie.AccountService;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author Feel
 * @date 2017/11/1
 * @email fye@gizwits.com
 * @since 1.0
 */
@Service
@Slf4j
public class JpaUserDetailsService implements UserDetailsService {


    @Autowired
    private AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AccountEntity byUsername = accountService.findByUsername(username);

        if (byUsername != null) {

            Set<RoleEntity> roleEntities = byUsername.getRoleEntities();

            final Set<GrantedAuthority> grantedAuthorities = Sets.newHashSet();

            roleEntities.stream().forEach(r -> {
                r.getAuthorityEntities().stream().forEach(au -> grantedAuthorities.add(new SimpleGrantedAuthority(au.getName())));
            });
            
            return new User(byUsername.getUsername(), byUsername.getPassword(), grantedAuthorities);

        } else {
            throw new UsernameNotFoundException(ResponseCode.LOGIN_USERNAME_ERROR.getName());
        }
    }
}
