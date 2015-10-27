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

import com.monkeyk.os.domain.oauth.ClientDetails;
import com.monkeyk.os.domain.oauth.ClientDetailsRepository;
import com.monkeyk.os.infrastructure.cache.AbstractCacheSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Repository;

import static com.monkeyk.os.infrastructure.cache.CacheKeyGenerator.generateClientDetailsKey;
import static com.monkeyk.os.infrastructure.cache.CacheKeyGenerator.generateClientDetailsResourceIdsKey;
import static com.monkeyk.os.infrastructure.cache.CacheNames.CLIENT_DETAILS_CACHE;

/**
 * 2015/10/27
 *
 * @author Shengzhao Li
 */
@Repository("clientDetailsRedisRepository")
public class ClientDetailsRedisRepository extends AbstractCacheSupport implements ClientDetailsRepository {


    private static final Logger LOG = LoggerFactory.getLogger(ClientDetailsRedisRepository.class);


    @Autowired
    private CacheManager cacheManager;


    @Override
    public boolean saveClientDetails(ClientDetails clientDetails) {
        final Cache clientDetailsCache = getClientDetailsCache();

        final String key = generateClientDetailsKey(clientDetails);
        final String rsKey = generateClientDetailsResourceIdsKey(clientDetails);

        putToCache(clientDetailsCache, key, clientDetails);
        putToCache(clientDetailsCache, rsKey, clientDetails);
        LOG.debug("Cache ClientDetails[{}], key = {}, rsKey = {}", clientDetails, key, rsKey);

        return true;
    }

    @Override
    public ClientDetails findClientDetails(String clientId) {
        final Cache cache = getClientDetailsCache();

        final String key = generateClientDetailsKey(clientId);
        return (ClientDetails) getFromCache(cache, key);
    }

    @Override
    public boolean deleteByClientId(String clientId) {
        final Cache clientDetailsCache = getClientDetailsCache();

        String key = generateClientDetailsKey(clientId);
        ClientDetails clientDetails = (ClientDetails) getFromCache(clientDetailsCache, key);

        if (clientDetails != null) {
            final String rsKey = generateClientDetailsResourceIdsKey(clientId, clientDetails.resourceIds());
            evictFromCache(clientDetailsCache, key);
            evictFromCache(clientDetailsCache, rsKey);
            LOG.debug("Evict ClientDetails from Cache, key = {}, rsKey = {}", key, rsKey);
            return true;
        }

        return false;
    }


    private Cache getClientDetailsCache() {
        return cacheManager.getCache(CLIENT_DETAILS_CACHE);
    }

}
