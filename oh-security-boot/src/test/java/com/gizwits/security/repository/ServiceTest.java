package com.gizwits.security.repository;

import com.gizwits.security.entity.AccountEntity;
import com.gizwits.security.entity.AuthorityEntity;
import com.gizwits.security.entity.ResourcesEntity;
import com.gizwits.security.entity.RoleEntity;
import com.gizwits.security.main.App;
import com.gizwits.security.servcie.AccountService;
import com.gizwits.security.servcie.AuthorityService;
import com.gizwits.security.servcie.ResourcesService;
import com.gizwits.security.servcie.RoleService;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.DigestUtils;

import static org.junit.Assert.assertTrue;

/**
 * @author Feel
 * @date 2017/11/1
 * @email fye@gizwits.com
 * @since 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
@Slf4j
public class ServiceTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ResourcesService resourcesService;

    @Autowired
    private AuthorityService authorityService;


    @Test
    public void saveTest() throws Exception {
        testSaveResource();
        testSaveAuthority();
        testSaveRole();
        testSaveAccount();
        testfindAccount();
    }

    @Test
    public void testSaveResource() throws Exception {

        ResourcesEntity resourcesEntity = new ResourcesEntity();
        resourcesEntity.setName("index");
        resourcesEntity.setUrl("/api/v1/index/**");
        resourcesEntity.setMethod("GET");
        resourcesService.save(resourcesEntity);

        ResourcesEntity resourcesEntity1 = new ResourcesEntity();
        resourcesEntity1.setName("product");
        resourcesEntity1.setUrl("/api/v1/product/**");
        resourcesEntity1.setMethod("GET");
        resourcesService.save(resourcesEntity1);

        ResourcesEntity resourcesEntity2 = new ResourcesEntity();
        resourcesEntity2.setName("order");
        resourcesEntity2.setUrl("/api/v1/order/**");
        resourcesEntity2.setMethod("GET");
        resourcesService.save(resourcesEntity2);


        ResourcesEntity resourcesEntity3 = new ResourcesEntity();
        resourcesEntity3.setName("agency");
        resourcesEntity3.setUrl("/api/v1/agency/**");
        resourcesEntity3.setMethod("GET");
        resourcesService.save(resourcesEntity3);

        ResourcesEntity resourcesEntity4 = new ResourcesEntity();
        resourcesEntity4.setName("sale");
        resourcesEntity4.setUrl("/api/v1/sale/**");
        resourcesEntity4.setMethod("GET");
        resourcesService.save(resourcesEntity4);

        ResourcesEntity resourcesEntity5 = new ResourcesEntity();
        resourcesEntity5.setName("filter");
        resourcesEntity5.setUrl("/api/v1/filter/**");
        resourcesEntity5.setMethod("GET");
        resourcesService.save(resourcesEntity5);


        ResourcesEntity resourcesEntity6 = new ResourcesEntity();
        resourcesEntity6.setName("message");
        resourcesEntity6.setUrl("/api/v1/message/**");
        resourcesEntity6.setMethod("GET");
        resourcesService.save(resourcesEntity6);

        ResourcesEntity resourcesEntity7 = new ResourcesEntity();
        resourcesEntity7.setName("meal");
        resourcesEntity7.setUrl("/api/v1/meal/**");
        resourcesEntity7.setMethod("GET");
        resourcesService.save(resourcesEntity7);


        ResourcesEntity resourcesEntity8 = new ResourcesEntity();
        resourcesEntity8.setName("sys");
        resourcesEntity8.setUrl("/api/v1/sys/**");
        resourcesEntity8.setMethod("GET");
        resourcesService.save(resourcesEntity8);
    }

    @Test
    public void testSaveAuthority() throws Exception {

        AuthorityEntity authorityEntity = new AuthorityEntity();
        authorityEntity.setMenu("首页");
        authorityEntity.setName("index");

        authorityEntity.setResourcesEntities(Sets.newHashSet(
                resourcesService.findByResourcesName("index")
        ));

        AuthorityEntity save = authorityService.save(authorityEntity);

        log.info("---testSaveAuthority-----{}", save);


        AuthorityEntity authorityEntity2 = new AuthorityEntity();
        authorityEntity2.setMenu("产品管理");
        authorityEntity2.setName("product");

        authorityEntity2.setResourcesEntities(Sets.newHashSet(
                resourcesService.findByResourcesName("product")
        ));

        AuthorityEntity save2 = authorityService.save(authorityEntity2);

        log.info("---testSaveAuthority-----{}", save2);


        AuthorityEntity authorityEntity3 = new AuthorityEntity();
        authorityEntity3.setMenu("订单管理");
        authorityEntity3.setName("order");

        authorityEntity3.setResourcesEntities(Sets.newHashSet(
                resourcesService.findByResourcesName("order")
        ));

        AuthorityEntity save3 = authorityService.save(authorityEntity3);

        log.info("---testSaveAuthority-----{}", save3);


        AuthorityEntity authorityEntity4 = new AuthorityEntity();
        authorityEntity4.setMenu("经销商管理");
        authorityEntity4.setName("agency");

        authorityEntity4.setResourcesEntities(Sets.newHashSet(
                resourcesService.findByResourcesName("agency")
        ));

        AuthorityEntity save4 = authorityService.save(authorityEntity4);

        log.info("---testSaveAuthority-----{}", save4);


        AuthorityEntity authorityEntity5 = new AuthorityEntity();
        authorityEntity5.setMenu("售后管理");
        authorityEntity5.setName("sale");

        authorityEntity5.setResourcesEntities(Sets.newHashSet(
                resourcesService.findByResourcesName("sale")
        ));

        AuthorityEntity save5 = authorityService.save(authorityEntity5);

        log.info("---testSaveAuthority-----{}", save5);

        AuthorityEntity authorityEntity6 = new AuthorityEntity();
        authorityEntity6.setMenu("滤芯管理");
        authorityEntity6.setName("filter");

        authorityEntity6.setResourcesEntities(Sets.newHashSet(
                resourcesService.findByResourcesName("filter")
        ));

        AuthorityEntity save6 = authorityService.save(authorityEntity6);

        log.info("---testSaveAuthority-----{}", save6);


        AuthorityEntity authorityEntity7 = new AuthorityEntity();
        authorityEntity7.setMenu("消息推送");
        authorityEntity7.setName("message");

        authorityEntity7.setResourcesEntities(Sets.newHashSet(
                resourcesService.findByResourcesName("message")
        ));

        AuthorityEntity save7 = authorityService.save(authorityEntity7);

        log.info("---testSaveAuthority-----{}", save7);


        AuthorityEntity authorityEntity8 = new AuthorityEntity();
        authorityEntity8.setMenu("套餐管理");
        authorityEntity8.setName("meal");

        authorityEntity8.setResourcesEntities(Sets.newHashSet(
                resourcesService.findByResourcesName("meal")
        ));

        AuthorityEntity save8 = authorityService.save(authorityEntity8);

        log.info("---testSaveAuthority-----{}", save8);


        AuthorityEntity authorityEntity9 = new AuthorityEntity();
        authorityEntity9.setMenu("系统管理");
        authorityEntity9.setName("sys");

        authorityEntity9.setResourcesEntities(Sets.newHashSet(
                resourcesService.findByResourcesName("sys")
        ));


        AuthorityEntity save9 = authorityService.save(authorityEntity9);

        log.info("---testSaveAuthority-----{}", save9);

    }


    @Test
    public void testSaveRole() throws Exception {

        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRoleName("ROLE_ADMIN");
        roleEntity.setNickName("超级管理员");

        roleEntity.setAuthorityEntities(Sets.newHashSet(
                authorityService.findByAuthorityName("index"),
                authorityService.findByAuthorityName("product"),
                authorityService.findByAuthorityName("order"),
                authorityService.findByAuthorityName("agency"),
                authorityService.findByAuthorityName("sale"),
                authorityService.findByAuthorityName("filter"),
                authorityService.findByAuthorityName("message"),
                authorityService.findByAuthorityName("meal"),
                authorityService.findByAuthorityName("sys")
        ));

        RoleEntity save = roleService.save(roleEntity);

        log.info("---testSaveRole-----{}", save);

        RoleEntity roleEntity2 = new RoleEntity();
        roleEntity2.setRoleName("ROLE_USER");
        roleEntity2.setNickName("普通用户");

        roleEntity2.setAuthorityEntities(Sets.newHashSet(
                authorityService.findByAuthorityName("index"),
                authorityService.findByAuthorityName("product"),
                authorityService.findByAuthorityName("order")
        ));

        RoleEntity save2 = roleService.save(roleEntity2);
        log.info("---testSaveRole-----{}", save2);

    }

    @Test
    public void testSaveAccount() throws Exception {
        
        RoleEntity roleAdmin = roleService.findByRoleName("ROLE_ADMIN");
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setUsername("admin");
        accountEntity.setEmail("fye@gizwits.com");
        accountEntity.setNickName("超级管理员");
        accountEntity.setPassword(DigestUtils.md5DigestAsHex(("admin").getBytes()));
        accountEntity.setRoleEntities(Sets.newHashSet(roleAdmin));
        AccountEntity save = accountService.save(accountEntity);
        log.info("---testSaveAccount-----{}", save);

        RoleEntity roleUser = roleService.findByRoleName("ROLE_USER");
        AccountEntity accountEntity2 = new AccountEntity();
        accountEntity2.setUsername("feel");
        accountEntity2.setEmail("fye@gizwits.com");
        accountEntity2.setNickName("普通用户");
        accountEntity2.setRoleEntities(Sets.newHashSet(roleUser));
        accountEntity2.setPassword(DigestUtils.md5DigestAsHex(("feel").getBytes()));
        System.out.println(accountEntity2);
        AccountEntity save2 = accountService.save(accountEntity2);
        log.info("---testSaveAccount-----{}", save2);

    }

    @Test
    public void testfindAccount() throws Exception {

        AccountEntity usernameAndPassword = accountService.findUsernameAndPassword("admin", DigestUtils.md5DigestAsHex(("admin").getBytes()));

        log.info("---AccountEntity----;{}", usernameAndPassword);
        assertTrue(usernameAndPassword.getUsername().equals("admin"));
    }


}