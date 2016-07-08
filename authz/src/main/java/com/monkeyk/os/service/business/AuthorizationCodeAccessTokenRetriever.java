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
package com.monkeyk.os.service.business;

import com.monkeyk.os.domain.oauth.AccessToken;
import com.monkeyk.os.domain.oauth.ClientDetails;
import com.monkeyk.os.domain.oauth.OauthCode;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 2015/10/26
 *
 * @author Shengzhao Li
 */
public class AuthorizationCodeAccessTokenRetriever extends AbstractAccessTokenHandler {


    private static final Logger LOG = LoggerFactory.getLogger(AuthorizationCodeAccessTokenRetriever.class);


    private ClientDetails clientDetails;
    private String code;

    public AuthorizationCodeAccessTokenRetriever(ClientDetails clientDetails, String code) {
        this.clientDetails = clientDetails;
        this.code = code;
    }


    //Always return new AccessToken
    public AccessToken retrieve() throws OAuthSystemException {

        final OauthCode oauthCode = loadOauthCode();
        final String username = oauthCode.username();

        final String clientId = clientDetails.clientId();
        final String authenticationId = authenticationIdGenerator.generate(clientId, username, null);

        AccessToken accessToken = oauthRepository.findAccessToken(clientId, username, authenticationId);
        if (accessToken != null) {
            LOG.debug("Delete existed AccessToken: {}", accessToken);
            oauthRepository.deleteAccessToken(accessToken);
        }
        accessToken = createAndSaveAccessToken(clientDetails, clientDetails.supportRefreshToken(), username, authenticationId);
        LOG.debug("Create a new AccessToken: {}", accessToken);

        return accessToken;
    }

    private OauthCode loadOauthCode() {
        final String clientId = clientDetails.clientId();
        return oauthRepository.findOauthCode(code, clientId);
    }
}
