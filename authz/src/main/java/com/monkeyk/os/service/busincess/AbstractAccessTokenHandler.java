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
import com.monkeyk.os.domain.oauth.AuthenticationIdGenerator;
import com.monkeyk.os.domain.oauth.ClientDetails;
import com.monkeyk.os.domain.shared.BeanProvider;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 2015/10/22
 *
 * @author Shengzhao Li
 */
public abstract class AbstractAccessTokenHandler extends AbstractOAuthHolder {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractAccessTokenHandler.class);


    protected transient AuthenticationIdGenerator authenticationIdGenerator = BeanProvider.getBean(AuthenticationIdGenerator.class);


    protected AccessToken createAndSaveAccessToken(ClientDetails clientDetails, boolean includeRefreshToken, String username, String authenticationId) throws OAuthSystemException {
        AccessToken accessToken = new AccessToken()
                .clientId(clientDetails.getClientId())
                .username(username)
                .tokenId(oAuthIssuer.accessToken())
                .authenticationId(authenticationId)
                .updateByClientDetails(clientDetails);

        if (includeRefreshToken) {
            accessToken.refreshToken(oAuthIssuer.refreshToken());
        }

        this.oauthCacheRepository.saveAccessToken(accessToken);
        LOG.debug("Save AccessToken: {}", accessToken);
        return accessToken;
    }

}
