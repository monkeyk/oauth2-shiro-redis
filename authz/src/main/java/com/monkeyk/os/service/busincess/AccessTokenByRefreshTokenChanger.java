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
package com.monkeyk.os.service.busincess;

import com.monkeyk.os.domain.oauth.AccessToken;
import com.monkeyk.os.domain.oauth.ClientDetails;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 2015/10/26
 *
 * @author Shengzhao Li
 */
public class AccessTokenByRefreshTokenChanger extends AbstractAccessTokenHandler {


    private static final Logger LOG = LoggerFactory.getLogger(AccessTokenByRefreshTokenChanger.class);


    private final String refreshToken;
    private final String clientId;

    public AccessTokenByRefreshTokenChanger(String refreshToken, String clientId) {
        this.refreshToken = refreshToken;
        this.clientId = clientId;
    }

    /**
     * Get AccessToken
     * Generate a new AccessToken from existed(exclude token,refresh_token)
     * Update access_token,refresh_token, expired.
     * Save and remove old
     */
    public AccessToken change() throws OAuthSystemException {

        final AccessToken oldToken = oauthCacheRepository.findAccessTokenByRefreshToken(refreshToken, clientId);

        AccessToken newAccessToken = oldToken.cloneMe();
        LOG.debug("Create new AccessToken: {} from old AccessToken: {}", newAccessToken, oldToken);

        ClientDetails details = oauthCacheRepository.findClientDetails(clientId);
        newAccessToken.updateByClientDetails(details);

        final String authId = authenticationIdGenerator.generate(clientId, oldToken.username(), null);
        newAccessToken.authenticationId(authId)
                .tokenId(oAuthIssuer.accessToken())
                .refreshToken(oAuthIssuer.refreshToken());

        oauthCacheRepository.deleteAccessToken(oldToken);
        LOG.debug("Delete old AccessToken: {}", oldToken);

        oauthCacheRepository.saveAccessToken(newAccessToken);
        LOG.debug("Save new AccessToken: {}", newAccessToken);

        return newAccessToken;
    }
}
