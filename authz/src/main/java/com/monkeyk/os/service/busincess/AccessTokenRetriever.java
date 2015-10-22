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
 * 2015/10/22
 *
 * @author Shengzhao Li
 */
public class AccessTokenRetriever extends AbstractAccessTokenHandler {

    private static final Logger LOG = LoggerFactory.getLogger(AccessTokenRetriever.class);

    private final ClientDetails clientDetails;
    private final Set<String> scopes;
    private final boolean includeRefreshToken;

    public AccessTokenRetriever(ClientDetails clientDetails, Set<String> scopes, boolean includeRefreshToken) {
        this.clientDetails = clientDetails;
        this.scopes = scopes;
        this.includeRefreshToken = includeRefreshToken;
    }

    public AccessToken retrieve() throws OAuthSystemException {

        String scope = OAuthUtils.encodeScopes(scopes);
        final String username = currentUsername();
        final String clientId = clientDetails.getClientId();

        final String authenticationId = authenticationIdGenerator.generate(clientId, username, scope);

        AccessToken accessToken = oauthCacheRepository.findAccessToken(clientId, username, authenticationId);
        if (accessToken == null) {
            accessToken = createAndSaveAccessToken(clientDetails, includeRefreshToken, username, authenticationId);
            LOG.debug("Create a new AccessToken: {}", accessToken);
        }

        return accessToken;
    }
}
