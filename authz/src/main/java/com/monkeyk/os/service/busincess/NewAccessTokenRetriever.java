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
import org.apache.oltu.oauth2.common.utils.OAuthUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * 2015/10/26
 * <p/>
 * Always return new AccessToken, exclude refreshToken
 *
 * @author Shengzhao Li
 */
public class NewAccessTokenRetriever extends AbstractAccessTokenHandler {


    private static final Logger LOG = LoggerFactory.getLogger(NewAccessTokenRetriever.class);


    private final ClientDetails clientDetails;
    private final Set<String> scopes;

    public NewAccessTokenRetriever(ClientDetails clientDetails, Set<String> scopes) {
        this.clientDetails = clientDetails;
        this.scopes = scopes;
    }

    //Always return new AccessToken, exclude refreshToken
    public AccessToken retrieve() throws OAuthSystemException {

        String scopeAsText = getScope();
        final String username = currentUsername();
        final String clientId = clientDetails.clientId();

        final String authenticationId = authenticationIdGenerator.generate(clientId, username, scopeAsText);

        AccessToken accessToken = oauthRepository.findAccessToken(clientId, username, authenticationId);
        if (accessToken != null) {
            LOG.debug("Delete existed AccessToken: {}", accessToken);
            oauthRepository.deleteAccessToken(accessToken);
        }
        accessToken = createAndSaveAccessToken(clientDetails, false, username, authenticationId);
        LOG.debug("Create a new AccessToken: {}", accessToken);

        return accessToken;
    }

    private String getScope() {
        if (scopes != null) {
            return OAuthUtils.encodeScopes(scopes);
        } else {
            return null;
        }
    }
}
