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
        final String key1 = generateOauthCodeUsernameClientIdKey(oauthCode);

        cache.put(key, oauthCode);
        cache.put(key1, oauthCode);
        LOG.debug("Cache OauthCode[{}], key = {}, key1 = {}", oauthCode, key, key1);

        //persist to DB
        return oauthRepository.saveOauthCode(oauthCode);
    }

    @Override
    public OauthCode findOauthCodeByUsernameClientId(String username, String clientId) {
        final Cache oauthCodeCache = getOauthCodeCache();

        //try to get from cache
        final String key = generateOauthCodeUsernameClientIdKey(username, clientId);
        OauthCode oauthCode = (OauthCode) oauthCodeCache.get(key).get();

        if (oauthCode == null) {
            oauthCode = oauthRepository.findOauthCodeByUsernameClientId(username, clientId);
            oauthCodeCache.put(key, oauthCode);
            LOG.debug("Find OauthCode[{}] from DB and cache it, username = {},clientId = {}", oauthCode, username, clientId);
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
        final String key1 = generateAccessTokenUsernameClientIdAuthIdKey(accessToken);

        final Cache accessTokenCache = getAccessTokenCache();
        accessTokenCache.put(key, accessToken);
        accessTokenCache.put(key1, accessToken);
        LOG.debug("Cache AccessToken[{}], key = {}, key1 = {}", accessToken, key, key1);

        //refresh cache
        if (StringUtils.isNotEmpty(accessToken.refreshToken())) {
            final String key2 = generateAccessTokenRefreshKey(accessToken);
            accessTokenCache.put(key2, accessToken);
            LOG.debug("Cache AccessToken[{}] by refresh-token, key = {}", accessToken, key2);
        }

        return oauthRepository.saveAccessToken(accessToken);
    }

    @Override
    public AccessToken findAccessToken(String clientId, String username, String authenticationId) {
        final Cache accessTokenCache = getAccessTokenCache();

        final String key = generateAccessTokenUsernameClientIdAuthIdKey(username, clientId, authenticationId);
        AccessToken accessToken = (AccessToken) accessTokenCache.get(key).get();

        if (accessToken == null) {
            accessToken = oauthRepository.findAccessToken(clientId, username, authenticationId);
            accessTokenCache.put(key, accessToken);
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
        accessTokenCache.evict(key);
        accessTokenCache.evict(key1);
        LOG.debug("Evict AccessToken[{}] from cache, key = {}, key1 = {}", accessToken, key, key1);

        return oauthRepository.deleteAccessToken(accessToken);
    }

    @Override
    public OauthCode findOauthCode(String code, String clientId) {

        final Cache oauthCodeCache = getOauthCodeCache();

        final String key = generateOauthCodeKey(code, clientId);
        OauthCode oauthCode = (OauthCode) oauthCodeCache.get(key).get();

        if (oauthCode == null) {
            oauthCode = oauthRepository.findOauthCode(code, clientId);
            oauthCodeCache.put(key, oauthCode);
            LOG.debug("Load OauthCode[{}] from DB, code = {}, clientId = {}", oauthCode, code, clientId);
        }

        return oauthCode;
    }

    @Override
    public AccessToken findAccessTokenByRefreshToken(String refreshToken, String clientId) {

        final Cache accessTokenCache = getAccessTokenCache();

        final String key = generateAccessTokenRefreshKey(refreshToken, clientId);
        AccessToken accessToken = (AccessToken) accessTokenCache.get(key).get();

        if (accessToken == null) {
            accessToken = oauthRepository.findAccessTokenByRefreshToken(refreshToken, clientId);
            accessTokenCache.put(key, accessToken);
            LOG.debug("Load AccessToken[{}] from DB and cache it, refreshToken = {}, clientId = {}", accessToken, refreshToken, clientId);
        }

        return accessToken;
    }

    @Override
    public ClientDetails findClientDetails(String clientId) {

        final Cache clientDetailsCache = getClientDetailsCache();

        final String key = generateClientDetailsKey(clientId);
        ClientDetails clientDetails = (ClientDetails) clientDetailsCache.get(key).get();

        if (clientDetails == null) {
            clientDetails = oauthRepository.findClientDetails(clientId);
            clientDetailsCache.put(key, clientDetails);
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
