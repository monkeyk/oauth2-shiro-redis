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
package com.monkeyk.os.oauth.shiro;

import com.monkeyk.os.domain.oauth.AccessToken;
import com.monkeyk.os.domain.oauth.ClientDetails;
import com.monkeyk.os.infrastructure.shiro.RedisRealm;
import com.monkeyk.os.service.OAuthRSService;
import org.apache.shiro.authc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * 2015/10/27
 *
 * @author Shengzhao Li
 */
public class RSRedisRealm extends RedisRealm {


    private static final Logger LOG = LoggerFactory.getLogger(RSRedisRealm.class);


    private OAuthRSService rsService;


    public RSRedisRealm() {
        super();
        setAuthenticationTokenClass(OAuth2Token.class);
        LOG.debug("Initial Resources-Realm: {}, set authenticationTokenClass = {}", this, getAuthenticationTokenClass());
    }


    @Override
    public AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        OAuth2Token upToken = (OAuth2Token) token;
        final String accessToken = (String) upToken.getCredentials();

        if (StringUtils.isEmpty(accessToken)) {
            throw new OAuth2AuthenticationException("Invalid access_token: " + accessToken);
        }
        //Validate access token
        AccessToken aToken = rsService.loadAccessTokenByTokenId(accessToken);
        validateToken(accessToken, aToken);

        //Validate client details by resource-id
        final ClientDetails clientDetails = rsService.loadClientDetails(aToken.clientId(), upToken.getResourceId());
        validateClientDetails(accessToken, aToken, clientDetails);

        String username = aToken.username();

        // Null username is invalid
        if (username == null) {
            throw new AccountException("Null usernames are not allowed by this realm.");
        }

        return new SimpleAuthenticationInfo(username, accessToken, getName());
    }


    private void validateToken(String token, AccessToken accessToken) throws OAuth2AuthenticationException {
        if (accessToken == null) {
            LOG.debug("Invalid access_token: {}, because it is null", token);
            throw new OAuth2AuthenticationException("Invalid access_token: " + token);
        }
        if (accessToken.tokenExpired()) {
            LOG.debug("Invalid access_token: {}, because it is expired", token);
            throw new OAuth2AuthenticationException("Invalid access_token: " + token);
        }
    }

    private void validateClientDetails(String token, AccessToken accessToken, ClientDetails clientDetails) throws OAuth2AuthenticationException {
        if (clientDetails == null || clientDetails.archived()) {
            LOG.debug("Invalid ClientDetails: {} by client_id: {}, it is null or archived", clientDetails, accessToken.clientId());
            throw new OAuth2AuthenticationException("Invalid client by token: " + token);
        }
    }


    public void setRsService(OAuthRSService rsService) {
        this.rsService = rsService;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
        Assert.notNull(this.rsService, "rsService is null");
    }
}
