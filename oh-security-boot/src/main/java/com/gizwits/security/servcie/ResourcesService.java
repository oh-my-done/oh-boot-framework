package com.gizwits.security.servcie;

import com.gizwits.security.entity.ResourcesEntity;
import com.gizwits.security.repository.ResourcesRepository;
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
public class ResourcesService {

    @Autowired
    private ResourcesRepository resourcesRepository;

    public ResourcesEntity save(ResourcesEntity resourcesEntity) {

        ResourcesEntity oneByName = resourcesRepository.findOneByName(resourcesEntity.getName());
        if (oneByName == null) {
            return resourcesRepository.save(resourcesEntity);
        }
        return null;
    }

    public ResourcesEntity findByResourcesName(String name) {
        return resourcesRepository.findOneByName(name);
    }

    public List<ResourcesEntity> findAll() {
        return resourcesRepository.findAll();
    }
}
