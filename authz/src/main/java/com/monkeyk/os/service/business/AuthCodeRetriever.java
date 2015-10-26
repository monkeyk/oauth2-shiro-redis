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

import com.monkeyk.os.domain.oauth.ClientDetails;
import com.monkeyk.os.domain.oauth.OauthCode;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 2015/10/22
 *
 * @author Shengzhao Li
 */
public class AuthCodeRetriever extends AbstractOAuthHolder {

    private static final Logger LOG = LoggerFactory.getLogger(AuthCodeRetriever.class);



    private ClientDetails clientDetails;

    public AuthCodeRetriever(ClientDetails clientDetails) {
        this.clientDetails = clientDetails;
    }

    public String retrieve() throws OAuthSystemException {

        final String clientId = clientDetails.clientId();
        final String username = currentUsername();

        OauthCode oauthCode = oauthRepository.findOauthCodeByUsernameClientId(username, clientId);
        if (oauthCode != null) {
            //Always delete exist
            LOG.debug("OauthCode ({}) is existed, remove it and create a new one", oauthCode);
            oauthRepository.deleteOauthCode(oauthCode);
        }
        //create a new one
        oauthCode = createOauthCode();

        return oauthCode.code();
    }


    private OauthCode createOauthCode() throws OAuthSystemException {
        final String authCode = oAuthIssuer.authorizationCode();

        LOG.debug("Save OauthCode authorizationCode '{}' of ClientDetails '{}'", authCode, clientDetails);
        final String username = currentUsername();
        OauthCode oauthCode = new OauthCode().code(authCode).username(username).clientId(clientDetails.clientId());

        oauthRepository.saveOauthCode(oauthCode);
        return oauthCode;
    }


}
