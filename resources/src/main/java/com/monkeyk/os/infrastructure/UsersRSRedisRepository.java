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
import com.monkeyk.os.infrastructure.cache.CacheNames;
import com.monkeyk.os.infrastructure.jdbc.UsersRSJdbcRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.monkeyk.os.infrastructure.cache.CacheKeyGenerator.generateUserRolesKey;
import static com.monkeyk.os.infrastructure.cache.CacheKeyGenerator.generateUsersKey;

/**
 * 2015/10/27
 *
 * @author Shengzhao Li
 */
@Repository("usersRSRedisRepository")
public class UsersRSRedisRepository extends AbstractCacheSupport implements UsersRepository {


    private static final Logger LOG = LoggerFactory.getLogger(UsersRSRedisRepository.class);

    @Autowired
    private UsersRSJdbcRepository usersRepository;

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
            LOG.debug("Load Users[{}] from DB and cache it, key = {}", users, key);
        }

        return users;
    }

    @Override
    public List<Roles> findRolesByUsername(String username) {

        final Cache usersCache = getUsersCache();

        final String key = generateUserRolesKey(username);
        @SuppressWarnings("unchecked")
        List<Roles> rolesList = (List<Roles>) getFromCache(usersCache, key);

        if (rolesList == null) {
            rolesList = usersRepository.findRolesByUsername(username);
            putToCache(usersCache, key, rolesList);
            LOG.debug("Load User roles[{}] from DB and cache it, key = {}", rolesList, key);
        }

        return rolesList;
    }


    private Cache getUsersCache() {
        return cacheManager.getCache(CacheNames.USERS_CACHE);
    }

}
