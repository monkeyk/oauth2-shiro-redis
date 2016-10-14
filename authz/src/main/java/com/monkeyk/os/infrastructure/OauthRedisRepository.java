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


import com.monkeyk.os.domain.oauth.*;

import static com.monkeyk.os.infrastructure.cache.CacheKeyGenerator.*;

import com.monkeyk.os.infrastructure.cache.AbstractCacheSupport;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import org.springframework.stereotype.Repository;

import static com.monkeyk.os.infrastructure.cache.CacheNames.*;

/**
 * 2015/10/22
 * <p/>
 * 使用Redis实现的 对OAuth的操作
 *
 * @author Shengzhao Li
 */
@Repository("oauthRedisRepository")
public class OauthRedisRepository extends AbstractCacheSupport implements OauthCacheRepository {


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
        final String key1 = generateOauthCodeUsernameClientIdKey(oauthCode);

        putToCache(cache, key, oauthCode);
        putToCache(cache, key1, oauthCode);
        LOG.debug("Cache OauthCode[{}], key = {}, key1 = {}", oauthCode, key, key1);

        //persist to DB
        return oauthRepository.saveOauthCode(oauthCode);
    }

    @Override
    public OauthCode findOauthCodeByUsernameClientId(String username, String clientId) {
        final Cache oauthCodeCache = getOauthCodeCache();

        //try to get from cache
        final String key = generateOauthCodeUsernameClientIdKey(username, clientId);
        OauthCode oauthCode = (OauthCode) getFromCache(oauthCodeCache, key);

        if (oauthCode == null) {
            oauthCode = oauthRepository.findOauthCodeByUsernameClientId(username, clientId);
            final boolean result = putToCache(oauthCodeCache, key, oauthCode);
            LOG.debug("Load OauthCode[{}] from DB and cache it, username = {},clientId = {} result: {}", oauthCode, username, clientId, result);
        }
        return oauthCode;
    }

    @Override
    public int deleteOauthCode(OauthCode oauthCode) {

        //clean cache
        final Cache oauthCodeCache = getOauthCodeCache();
        final String key = generateOauthCodeUsernameClientIdKey(oauthCode);
        final String key1 = generateOauthCodeKey(oauthCode);

        evictFromCache(oauthCodeCache, key);
        evictFromCache(oauthCodeCache, key1);
        LOG.debug("Evict OauthCode[{}] cache values, key = {}, key1 = {}", oauthCode, key, key1);

        return oauthRepository.deleteOauthCode(oauthCode);
    }

    @Override
    public int saveAccessToken(AccessToken accessToken) {

        //add to cache
        final String key = generateAccessTokenKey(accessToken);
        final String key1 = generateAccessTokenUsernameClientIdAuthIdKey(accessToken);

        final Cache accessTokenCache = getAccessTokenCache();
        putToCache(accessTokenCache, key, accessToken);
        putToCache(accessTokenCache, key1, accessToken);
        LOG.debug("Cache AccessToken[{}], key = {}, key1 = {}", accessToken, key, key1);

        //refresh cache
        if (StringUtils.isNotEmpty(accessToken.refreshToken())) {
            final String key2 = generateAccessTokenRefreshKey(accessToken);
            putToCache(accessTokenCache, key2, accessToken);
            LOG.debug("Cache AccessToken[{}] by refresh-token, key = {}", accessToken, key2);
        }

        return oauthRepository.saveAccessToken(accessToken);
    }

    @Override
    public AccessToken findAccessToken(String clientId, String username, String authenticationId) {
        final Cache accessTokenCache = getAccessTokenCache();

        final String key = generateAccessTokenUsernameClientIdAuthIdKey(username, clientId, authenticationId);
        AccessToken accessToken = (AccessToken) getFromCache(accessTokenCache, key);

        if (accessToken == null) {
            accessToken = oauthRepository.findAccessToken(clientId, username, authenticationId);
            putToCache(accessTokenCache, key, accessToken);
            LOG.debug("Load AccessToken[{}] from DB and cache it, clientId = {}, username = {}, authenticationId = {}", accessToken, clientId, username, authenticationId);
        }

        return accessToken;
    }

    @Override
    public int deleteAccessToken(AccessToken accessToken) {

        //clean from cache
        final String key = generateAccessTokenKey(accessToken);
        final String key1 = generateAccessTokenUsernameClientIdAuthIdKey(accessToken);

        final Cache accessTokenCache = getAccessTokenCache();
        evictFromCache(accessTokenCache, key);
        evictFromCache(accessTokenCache, key1);
        LOG.debug("Evict AccessToken[{}] from cache, key = {}, key1 = {}", accessToken, key, key1);

        return oauthRepository.deleteAccessToken(accessToken);
    }

    @Override
    public OauthCode findOauthCode(String code, String clientId) {

        final Cache oauthCodeCache = getOauthCodeCache();

        final String key = generateOauthCodeKey(code, clientId);
        OauthCode oauthCode = (OauthCode) getFromCache(oauthCodeCache, key);

        if (oauthCode == null) {
            oauthCode = oauthRepository.findOauthCode(code, clientId);
            putToCache(oauthCodeCache, key, oauthCode);
            LOG.debug("Load OauthCode[{}] from DB and cache it, code = {}, clientId = {}", oauthCode, code, clientId);
        }

        return oauthCode;
    }

    @Override
    public AccessToken findAccessTokenByRefreshToken(String refreshToken, String clientId) {

        final Cache accessTokenCache = getAccessTokenCache();

        final String key = generateAccessTokenRefreshKey(refreshToken, clientId);
        AccessToken accessToken = (AccessToken) getFromCache(accessTokenCache, key);

        if (accessToken == null) {
            accessToken = oauthRepository.findAccessTokenByRefreshToken(refreshToken, clientId);
            putToCache(accessTokenCache, key, accessToken);
            LOG.debug("Load AccessToken[{}] from DB and cache it, refreshToken = {}, clientId = {}", accessToken, refreshToken, clientId);
        }

        return accessToken;
    }

    @Override
    public ClientDetails findClientDetails(String clientId) {

        final Cache clientDetailsCache = getClientDetailsCache();

        final String key = generateClientDetailsKey(clientId);
        ClientDetails clientDetails = (ClientDetails) getFromCache(clientDetailsCache, key);

        if (clientDetails == null) {
            clientDetails = oauthRepository.findClientDetails(clientId);
            putToCache(clientDetailsCache, key, clientDetails);
            LOG.debug("Load ClientDetails[{}] from DB and cache it, clientId = {}", clientDetails, clientId);
        }

        return clientDetails;
    }


    private Cache getOauthCodeCache() {
        return cacheManager.getCache(OAUTH_CODE_CACHE);
    }

    private Cache getAccessTokenCache() {
        return cacheManager.getCache(ACCESS_TOKEN_CACHE);
    }

    private Cache getClientDetailsCache() {
        return cacheManager.getCache(CLIENT_DETAILS_CACHE);
    }

}
