/*
 * Copyright (c) 2015 MONKEYK Information Technology Co. Ltd
 * www.monkeyk.com
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * MONKEYK Information Technology Co. Ltd ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you
 * entered into with MONKEYK Information Technology Co. Ltd.
 */
package com.monkeyk.os.infrastructure;

import com.monkeyk.os.domain.users.Roles;
import com.monkeyk.os.domain.users.Users;
import com.monkeyk.os.domain.users.UsersAuthzRepository;
import com.monkeyk.os.domain.users.UsersRepository;
import com.monkeyk.os.infrastructure.cache.AbstractCacheSupport;

import static com.monkeyk.os.infrastructure.cache.CacheKeyGenerator.*;

import com.monkeyk.os.infrastructure.cache.CacheNames;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 2015/10/26
 *
 * @author Shengzhao Li
 */
@Repository("usersRedisRepository")
public class UsersRedisRepository extends AbstractCacheSupport implements UsersAuthzRepository {


    private static final Logger LOG = LoggerFactory.getLogger(UsersRedisRepository.class);


    @Autowired
    private CacheManager cacheManager;


    @Override
    public Users findUsersByUsername(String username) {
        LOG.debug("Call findUsersByUsername, username = {}", username);
        final Cache usersCache = getUsersCache();

        final String key = generateUsersKey(username);

        return (Users) getFromCache(usersCache, key);
    }

    @Override
    public List<Roles> findRolesByUsername(String username) {

        final Cache usersCache = getUsersCache();

        final String key = generateUserRolesKey(username);
        @SuppressWarnings("unchecked")
        List<Roles> rolesList = (List<Roles>) getFromCache(usersCache, key);

        return rolesList;
    }


    private Cache getUsersCache() {
        return cacheManager.getCache(CacheNames.USERS_CACHE);
    }
}
