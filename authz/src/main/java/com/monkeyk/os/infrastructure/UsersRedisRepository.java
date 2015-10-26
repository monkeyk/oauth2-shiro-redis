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
import com.monkeyk.os.domain.users.UsersRepository;
import com.monkeyk.os.infrastructure.cache.AbstractCacheSupport;

import static com.monkeyk.os.infrastructure.cache.CacheKeyGenerator.*;

import com.monkeyk.os.infrastructure.cache.CacheNames;
import com.monkeyk.os.infrastructure.jdbc.UsersJdbcRepository;
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
public class UsersRedisRepository extends AbstractCacheSupport implements UsersRepository {


    private static final Logger LOG = LoggerFactory.getLogger(UsersRedisRepository.class);

    @Autowired
    private UsersJdbcRepository usersRepository;

    @Autowired
    private CacheManager cacheManager;


    @Override
    public Users findUsersByUsername(String username) {

        final Cache usersCache = getUsersCache();

        final String key = generateUsersKey(username);
        Users users = (Users) getFromCache(usersCache, key);

        if (users == null) {
            users = usersRepository.findUsersByUsername(username);
            putToCache(usersCache, key, users);
            LOG.debug("Load Users[{}] from DB and cache it, key = {}", username);
        }

        return users;
    }

    @Override
    public List<Roles> findRolesByUsername(String username) {



        return usersRepository.findRolesByUsername(username);
    }


    private Cache getUsersCache() {
        return cacheManager.getCache(CacheNames.USERS_CACHE);
    }
}
