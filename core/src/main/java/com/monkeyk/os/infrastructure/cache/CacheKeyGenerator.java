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
import com.monkeyk.os.domain.users.Users;

/**
 * 2015/10/22
 * <p/>
 * <p/>
 * Make sure any key is unique.
 *
 * @author Shengzhao Li
 * @see com.monkeyk.os.infrastructure.cache.CacheNames
 */
public abstract class CacheKeyGenerator {


    private static final String USER_ROLE_KEY_PREFIX = "useR_ROle.";

    private static final String USER_KEY_PREFIX = "useRs.";

    private static final String CLIENT_DETAILS_KEY_PREFIX = "cliEnt_Details.";

    private static final String CLIENT_DETAILS_RESOURCES_KEY_PREFIX = "cliEnt_DeTails_rs.";

    private static final String ACCESS_TOKEN_KEY_PREFIX = "Access_tOken.";

    private static final String ACCESS_TOKEN_AUTH_KEY_PREFIX = "Access_Token_auth.";

    private static final String OAUTH_CODE_UC_KEY_PREFIX = "Oauth_CODE_uc.";


    //private, singleton
    private CacheKeyGenerator() {
    }


    /**
     * Default Users key: username
     *
     * @param users Users
     * @return Cache key
     */
    public static String generateUsersKey(Users users) {
        return generateUsersKey(users.username());
    }

    /**
     * Users key: username
     *
     * @param username username
     * @return Cache key
     */
    public static String generateUsersKey(String username) {
        return USER_KEY_PREFIX + username;
    }

    /**
     * Default User role  key
     *
     * @param username username
     * @return Cache key
     */
    public static String generateUserRolesKey(String username) {
        return USER_ROLE_KEY_PREFIX + username;
    }

    /**
     * Default ClientDetails  key:  clientId
     *
     * @param clientId clientId
     * @return Cache key
     */
    public static String generateClientDetailsKey(String clientId) {
        return CLIENT_DETAILS_KEY_PREFIX + clientId;
    }


    /**
     * ClientDetails by resourceIds key:  clientId + resourceIds
     *
     * @param clientId    clientId
     * @param resourceIds resourceIds
     * @return Cache key
     */
    public static String generateClientDetailsResourceIdsKey(String clientId, String resourceIds) {
        return CLIENT_DETAILS_RESOURCES_KEY_PREFIX + clientId + resourceIds;
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
        return OAUTH_CODE_UC_KEY_PREFIX + username + clientId;
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
        return ACCESS_TOKEN_KEY_PREFIX + tokenId;
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
        return ACCESS_TOKEN_AUTH_KEY_PREFIX + username + clientId + authenticationId;
    }

}
