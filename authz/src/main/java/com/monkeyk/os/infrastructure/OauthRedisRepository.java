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


import com.monkeyk.os.domain.oauth.AccessToken;
import com.monkeyk.os.domain.oauth.OauthCacheRepository;
import com.monkeyk.os.domain.oauth.OauthCode;
import com.monkeyk.os.domain.oauth.OauthRepository;

import static com.monkeyk.os.infrastructure.cache.CacheKeyGenerator.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import org.springframework.stereotype.Repository;

import static com.monkeyk.os.infrastructure.cache.CacheNames.*;

/**
 * 2015/10/22
 *
 * @author Shengzhao Li
 */
@Repository("oauthRedisRepository")
public class OauthRedisRepository implements OauthCacheRepository {


    private static final Logger LOG = LoggerFactory.getLogger(OauthRedisRepository.class);

    @Autowired
    private OauthRepository oauthRepository;

    @Autowired
    private CacheManager cacheManager;


    @Override
    public int saveOauthCode(OauthCode oauthCode) {

        //cache to redis
        final Cache cache = getOauthCodeCache();
        final String key = generateOauthCodeKey(oauthCode);
        cache.put(key, oauthCode);
        final String key1 = generateOauthCodeUsernameClientIdKey(oauthCode);
        cache.put(key1, oauthCode);
        LOG.debug("Cache OauthCode[{}], key = {}, key1 = {}", oauthCode, key, key1);
        //persist to DB
        return oauthRepository.saveOauthCode(oauthCode);
    }

    private Cache getOauthCodeCache() {
        return cacheManager.getCache(OAUTH_CODE_CACHE);
    }

    private Cache getAccessTokenCache() {
        return cacheManager.getCache(ACCESS_TOKEN_CACHE);
    }

    @Override
    public OauthCode findOauthCodeByUsernameClientId(String username, String clientId) {
        //try to get from cache
        OauthCode oauthCode = (OauthCode) getOauthCodeCache().get(generateOauthCodeUsernameClientIdKey(username, clientId)).get();
        if (oauthCode == null) {
            LOG.debug("Find OauthCode from DB, username = {},clientId = {}", username, clientId);
            oauthCode = oauthRepository.findOauthCodeByUsernameClientId(username, clientId);
        }
        return oauthCode;
    }

    @Override
    public int deleteOauthCode(OauthCode oauthCode) {

        //clean cache
        final Cache oauthCodeCache = getOauthCodeCache();
        oauthCodeCache.evict(generateOauthCodeUsernameClientIdKey(oauthCode));
        oauthCodeCache.evict(generateOauthCodeKey(oauthCode));
        LOG.debug("Evict OauthCode[{}] cache values", oauthCode);

        return oauthRepository.deleteOauthCode(oauthCode);
    }

    @Override
    public int saveAccessToken(AccessToken accessToken) {

        //add to cache
        final String key = generateAccessTokenKey(accessToken);
        getAccessTokenCache().put(key, accessToken);
        LOG.debug("Cache AccessToken[{}], key = {}", accessToken, key);

        return oauthRepository.saveAccessToken(accessToken);
    }

    @Override
    public AccessToken findAccessToken(String clientId, String username, String authenticationId) {


        return oauthRepository.findAccessToken(clientId, username, authenticationId);
    }
}
