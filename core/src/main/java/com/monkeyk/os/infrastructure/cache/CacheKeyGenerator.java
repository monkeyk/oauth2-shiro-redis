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


    //private, singleton
    private CacheKeyGenerator() {
    }


    /**
     * Default ClientDetails  key:  clientId
     *
     * @param clientId clientId
     * @return Cache key
     */
    public static String generateClientDetailsKey(String clientId) {
        return clientId;
    }


    /**
     * ClientDetails by resourceIds key:  clientId + resourceIds
     *
     * @param clientId    clientId
     * @param resourceIds resourceIds
     * @return Cache key
     */
    public static String generateClientDetailsResourceIdsKey(String clientId, String resourceIds) {
        return clientId + resourceIds;
    }


    /**
     * Default key is  code + clientId
     *
     * @param oauthCode OauthCode
     * @return Cache key
     */
    public static String generateOauthCodeKey(OauthCode oauthCode) {
        return generateOauthCodeKey(oauthCode.code(), oauthCode.clientId());
    }

    /**
     * Default key is  code + clientId
     *
     * @param code     code
     * @param clientId clientId
     * @return Cache key
     */
    public static String generateOauthCodeKey(String code, String clientId) {
        return code + clientId;
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
        return generateAccessTokenKey(accessToken.tokenId());
    }


    /**
     * Default AccessToken is tokenId
     *
     * @return Cache key
     */
    public static String generateAccessTokenKey(String tokenId) {
        return tokenId;
    }


    /**
     * Default AccessToken  refresh token, key is refreshToken + clientId
     *
     * @return Cache key
     */
    public static String generateAccessTokenRefreshKey(AccessToken accessToken) {
        return generateAccessTokenRefreshKey(accessToken.refreshToken(), accessToken.clientId());
    }


    /**
     * Default AccessToken  refresh token, key is refreshToken + clientId
     *
     * @return Cache key
     */
    public static String generateAccessTokenRefreshKey(String refreshToken, String clientId) {
        return refreshToken + clientId;
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
