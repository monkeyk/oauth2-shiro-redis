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
import com.monkeyk.os.domain.oauth.ClientDetails;
import com.monkeyk.os.domain.rs.OAuthRSCacheRepository;
import com.monkeyk.os.domain.rs.OAuthRSRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Repository;

import static com.monkeyk.os.infrastructure.cache.CacheKeyGenerator.generateAccessTokenKey;
import static com.monkeyk.os.infrastructure.cache.CacheKeyGenerator.generateClientDetailsResourceIdsKey;
import static com.monkeyk.os.infrastructure.cache.CacheNames.ACCESS_TOKEN_CACHE;
import static com.monkeyk.os.infrastructure.cache.CacheNames.CLIENT_DETAILS_CACHE;

/**
 * 2015/10/26
 *
 * @author Shengzhao Li
 */
@Repository("oauthRsRedisRepository")
public class OAuthRSRedisRepository implements OAuthRSCacheRepository {


    private static final Logger LOG = LoggerFactory.getLogger(OAuthRSRedisRepository.class);

    @Autowired
    private OAuthRSRepository oAuthRSRepository;

    @Autowired
    private CacheManager cacheManager;


    @Override
    public AccessToken findAccessTokenByTokenId(String tokenId) {

        final Cache accessTokenCache = getAccessTokenCache();

        final String key = generateAccessTokenKey(tokenId);
        AccessToken accessToken = (AccessToken) getFromCache(accessTokenCache, key);

        if (accessToken == null) {
            accessToken = oAuthRSRepository.findAccessTokenByTokenId(tokenId);
            accessTokenCache.put(key, accessToken);
            LOG.debug("Load AccessToken[{}] from DB and cache it, key = {}", accessToken, key);
        }

        return accessToken;
    }

    @Override
    public ClientDetails findClientDetailsByClientIdAndResourceIds(String clientId, String resourceIds) {

        final Cache clientDetailsCache = getClientDetailsCache();

        final String key = generateClientDetailsResourceIdsKey(clientId, resourceIds);
        ClientDetails clientDetails = (ClientDetails) getFromCache(clientDetailsCache, key);

        if (clientDetails == null) {
            clientDetails = oAuthRSRepository.findClientDetailsByClientIdAndResourceIds(clientId, resourceIds);
            clientDetailsCache.put(key, clientDetails);
            LOG.debug("Load ClientDetails[{}] from DB and cache it, key = {}", clientDetails, key);
        }

        return clientDetails;
    }


    private Object getFromCache(Cache cache, String key) {
        final Cache.ValueWrapper valueWrapper = cache.get(key);
        return valueWrapper == null ? null : valueWrapper.get();
    }


    private Cache getAccessTokenCache() {
        return cacheManager.getCache(ACCESS_TOKEN_CACHE);
    }

    private Cache getClientDetailsCache() {
        return cacheManager.getCache(CLIENT_DETAILS_CACHE);
    }

}
