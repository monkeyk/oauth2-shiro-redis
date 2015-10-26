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
package com.monkeyk.os.infrastructure.shiro;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 2015/10/27
 *
 * @author Shengzhao Li
 */
public class AuthzRedisRealm extends RedisRealm {


    private static final Logger LOG = LoggerFactory.getLogger(AuthzRedisRealm.class);


    public AuthzRedisRealm() {
        LOG.debug("Initial Authz Realm: {}", this);
    }


}
