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
 *
 * @author Shengzhao Li
 */
public class ClientCredentialsAccessTokenRetriever extends AbstractAccessTokenHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ClientCredentialsAccessTokenRetriever.class);


    private final ClientDetails clientDetails;
    private final Set<String> scopes;

    public ClientCredentialsAccessTokenRetriever(ClientDetails clientDetails, Set<String> scopes) {
        this.clientDetails = clientDetails;
        this.scopes = scopes;
    }


    public AccessToken retrieve() throws OAuthSystemException {

        String scope = OAuthUtils.encodeScopes(scopes);
        final String clientId = clientDetails.clientId();
        //username = clientId

        final String authenticationId = authenticationIdGenerator.generate(clientId, clientId, scope);
        AccessToken accessToken = oauthRepository.findAccessToken(clientId, clientId, authenticationId);

        boolean needCreated = needCreated(clientId, accessToken);

        if (needCreated) {
            //Ignore refresh_token
            accessToken = createAndSaveAccessToken(clientDetails, false, clientId, authenticationId);
            LOG.debug("Create a new AccessToken: {}", accessToken);
        }

        return accessToken;
    }

    private boolean needCreated(String clientId, AccessToken accessToken) {
        boolean needCreate = false;
        if (accessToken == null) {
            needCreate = true;
            LOG.debug("Not found AccessToken from repository, will create a new one, client_id: {}", clientId);
        } else if (accessToken.tokenExpired()) {
            LOG.debug("Delete expired AccessToken: {} and create a new one, client_id: {}", accessToken, clientId);
            oauthRepository.deleteAccessToken(accessToken);
            needCreate = true;
        } else {
            LOG.debug("Use existed AccessToken: {}, client_id: {}", accessToken, clientId);
        }
        return needCreate;
    }
}
