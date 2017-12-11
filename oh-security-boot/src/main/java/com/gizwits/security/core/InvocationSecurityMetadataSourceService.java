package com.gizwits.security.core;


import com.gizwits.security.entity.AuthorityEntity;
import com.gizwits.security.servcie.AuthorityService;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;


/**
 * @author Feel
 * @date 2017/11/1
 * @email fye@gizwits.com
 * @since 1.0
 */
@Service
@Slf4j
public class InvocationSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource {


    @Autowired
    private AuthorityService authorityService;

    private HashMap<UrlConfigAttribute, Set<ConfigAttribute>> authorityMap = null;

    /**
     * 加载资源中所有权限
     */
    private void loadResourceDefine() {

        authorityMap = new HashMap<>();

        List<AuthorityEntity> entities = authorityService.findAll();

        entities.stream().forEach(au -> {

            au.getResourcesEntities().stream().forEach(resource -> {

                UrlConfigAttribute key = new UrlConfigAttribute(resource.getUrl(), resource.getMethod());

                if (authorityMap.containsKey(key)) {

                    Set<ConfigAttribute> attributes = authorityMap.get(key);

                    if (attributes != null) {
                        attributes.add(new AuthorityAttribute(au.getName()));
                    }
                } else {

                    authorityMap.put(key, Sets.newHashSet(new AuthorityAttribute(au.getName())));
                }

            });
        });

    }

    /**
     * @param object
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {

        if (authorityMap == null) loadResourceDefine();

        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();

        String method = request.getMethod();

        for (UrlConfigAttribute urlConfigAttribute : authorityMap.keySet()) {
            RequestMatcher requestMatcher = new AntPathRequestMatcher(urlConfigAttribute.getUrl());
            if (requestMatcher.matches(request) && (urlConfigAttribute.getMethod().equalsIgnoreCase(method) || "ALL".equalsIgnoreCase(method))) {
                return authorityMap.get(urlConfigAttribute);
            }

        }

        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        loadResourceDefine();
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}