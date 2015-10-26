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

import com.monkeyk.os.domain.oauth.AccessToken;
import com.monkeyk.os.domain.oauth.OauthCode;

/**
 * 2015/10/22
 *
 * @author Shengzhao Li
 * @see com.monkeyk.os.infrastructure.cache.CacheNames
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


    /**
     * Default AccessToken is tokenId
     *
     * @return Cache key
     */
    public static String generateAccessTokenKey(AccessToken accessToken) {
        return accessToken.tokenId();
    }

    /**
     * AccessToken cache, key:  username+clientId+authId
     *
     * @return Cache key
     */
    public static String generateAccessTokenUsernameClientIdAuthIdKey(AccessToken accessToken) {
        return generateAccessTokenUsernameClientIdAuthIdKey(accessToken.username(), accessToken.clientId(), accessToken.authenticationId());
    }


    /**
     * AccessToken cache, key:  username+clientId+authId
     *
     * @return Cache key
     */
    public static String generateAccessTokenUsernameClientIdAuthIdKey(String username, String clientId, String authenticationId) {
        return username + clientId + authenticationId;
    }

}
