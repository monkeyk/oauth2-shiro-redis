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

import com.monkeyk.os.domain.oauth.OauthCode;

/**
 * 2015/10/22
 *
 * @author Shengzhao Li
 */
public abstract class CacheKeyGenerator {

    private CacheKeyGenerator() {
    }


    /**
     * Default key is  code + clientId
     *
     * @param oauthCode OauthCode
     * @return Cache key
     */
    public static String generateOauthCodeKey(OauthCode oauthCode) {
        return oauthCode.code() + oauthCode.clientId();
    }

    /**
     * Custom key is  username + clientId
     *
     * @param oauthCode OauthCode
     * @return Cache key
     */
    public static String generateOauthCodeUsernameClientIdKey(OauthCode oauthCode) {
        return generateOauthCodeUsernameClientIdKey(oauthCode.username(), oauthCode.clientId());
    }

    /**
     * Custom key is  username + clientId
     *
     * @return Cache key
     */
    public static String generateOauthCodeUsernameClientIdKey(String username, String clientId) {
        return username + clientId;
    }
}
