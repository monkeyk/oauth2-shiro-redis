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

import com.monkeyk.os.domain.oauth.OauthCacheRepository;
import com.monkeyk.os.domain.oauth.OauthCode;
import com.monkeyk.os.domain.oauth.OauthRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

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
    private RedisTemplate redisTemplate;


    @Override
    public int saveOauthCode(OauthCode oauthCode) {

        //cache to redis

        //persist to DB
        return oauthRepository.saveOauthCode(oauthCode);
    }
}
