package com.gizwits.service;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

/**
 * @author Feel
 * @date 2017/11/1
 * @email fye@gizwits.com
 * @since 1.0
 */
@Configuration
@EnableJpaRepositories(basePackages = {"com.gizwits.**.repository", "com.gizwits.repository"})
@EntityScan(basePackages = {"com.gizwits.module.**.entity", "com.gizwits.entity"})
@EnableSpringDataWebSupport
public class JpaRepositoriesConfig {
}
