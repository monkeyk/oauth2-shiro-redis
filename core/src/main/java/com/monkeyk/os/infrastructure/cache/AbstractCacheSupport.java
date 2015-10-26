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
package com.monkeyk.os.infrastructure.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;

/**
 * 2015/10/26
 *
 * @author Shengzhao Li
 */
public abstract class AbstractCacheSupport {


    private static final Logger LOG = LoggerFactory.getLogger(AbstractCacheSupport.class);


    protected Object getFromCache(Cache cache, String key) {
        final Cache.ValueWrapper valueWrapper = cache.get(key);
        return valueWrapper == null ? null : valueWrapper.get();
    }


    //If value is null , return false
    protected boolean putToCache(Cache cache, Object key, Object value) {
        if (value == null) {
            LOG.debug("Ignore put to cache[{}], key = {}, because value is null", cache, key);
            return false;
        }
        cache.put(key, value);
        LOG.debug("Put [{} = {}] to cache[{}]", key, value, cache);
        return true;
    }

    //Evict value from Cache
    protected boolean evictFromCache(Cache cache, Object key) {
        if (key == null) {
            LOG.debug("Ignore evict from cache[{}], because key is null", cache);
            return false;
        }
        cache.evict(key);
        LOG.debug("Evict key[{}] from cache[{}]", key, cache);
        return true;
    }


}
