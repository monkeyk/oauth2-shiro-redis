package com.monkeyk.os.service.impl;

import com.monkeyk.os.domain.oauth.AccessToken;
import com.monkeyk.os.domain.oauth.ClientDetails;
import com.monkeyk.os.domain.oauth.OauthCacheRepository;
import com.monkeyk.os.domain.oauth.OauthCode;
import com.monkeyk.os.service.OauthService;
import com.monkeyk.os.service.busincess.*;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * 15-6-10
 *
 * @author Shengzhao Li
 */
@Service("oauthService")
public class OauthServiceImpl implements OauthService {

    private static final Logger LOG = LoggerFactory.getLogger(OauthServiceImpl.class);


    @Autowired
    private OauthCacheRepository oauthRepository;


    /**
     * Load  ClientDetails instance by clientId
     *
     * @param clientId clientId
     * @return ClientDetails
     */
    @Override
    public ClientDetails loadClientDetails(String clientId) {
        LOG.debug("Load ClientDetails by clientId: {}", clientId);
        return oauthRepository.findClientDetails(clientId);
    }


    /**
     * Retrieve an existed code, if it is existed , remove it and create a new one,
     * otherwise, create  a new one and return
     *
     * @param clientDetails ClientDetails
     * @return code
     * @throws OAuthSystemException
     */
    @Override
    public String retrieveAuthCode(ClientDetails clientDetails) throws OAuthSystemException {
        AuthCodeRetriever authCodeRetriever = new AuthCodeRetriever(clientDetails);
        return authCodeRetriever.retrieve();
    }


    @Override
    public AccessToken retrieveAccessToken(ClientDetails clientDetails, Set<String> scopes, boolean includeRefreshToken) throws OAuthSystemException {
        AccessTokenRetriever retriever = new AccessTokenRetriever(clientDetails, scopes, includeRefreshToken);
        return retriever.retrieve();
    }

    //Always return new AccessToken, exclude refreshToken
    @Override
    public AccessToken retrieveNewAccessToken(ClientDetails clientDetails, Set<String> scopes) throws OAuthSystemException {
        NewAccessTokenRetriever tokenRetriever = new NewAccessTokenRetriever(clientDetails, scopes);
        return tokenRetriever.retrieve();
    }

    @Override
    public OauthCode loadOauthCode(String code, ClientDetails clientDetails) {
        final String clientId = clientDetails.clientId();
        return oauthRepository.findOauthCode(code, clientId);
    }

    @Override
    public boolean removeOauthCode(String code, ClientDetails clientDetails) {
        final OauthCode oauthCode = loadOauthCode(code, clientDetails);
        final int rows = oauthRepository.deleteOauthCode(oauthCode);
        return rows > 0;
    }

    //Always return new AccessToken
    @Override
    public AccessToken retrieveNewAccessToken(ClientDetails clientDetails) throws OAuthSystemException {
        AccessTokenByClientDetailsRetriever tokenByClientDetailsRetriever = new AccessTokenByClientDetailsRetriever(clientDetails);
        return tokenByClientDetailsRetriever.retrieve();
    }

    //grant_type=password AccessToken
    @Override
    public AccessToken retrievePasswordAccessToken(ClientDetails clientDetails, Set<String> scopes, String username) throws OAuthSystemException {
        PasswordAccessTokenRetriever tokenRetriever = new PasswordAccessTokenRetriever(clientDetails, scopes, username);
        return tokenRetriever.retrieve();
    }


    //grant_type=client_credentials
    @Override
    public AccessToken retrieveClientCredentialsAccessToken(ClientDetails clientDetails, Set<String> scopes) throws OAuthSystemException {
        ClientCredentialsAccessTokenRetriever tokenRetriever = new ClientCredentialsAccessTokenRetriever(clientDetails, scopes);
        return tokenRetriever.retrieve();
    }

    @Override
    public AccessToken loadAccessTokenByRefreshToken(String refreshToken, String clientId) {
        LOG.debug("Load ClientDetails by refreshToken: {} and clientId: {}", refreshToken, clientId);
        return oauthRepository.findAccessTokenByRefreshToken(refreshToken, clientId);
    }

    /*
    * Get AccessToken
    * Generate a new AccessToken from existed(exclude token,refresh_token)
    * Update access_token,refresh_token, expired.
    * Save and remove old
    * */
    @Override
    public AccessToken changeAccessTokenByRefreshToken(String refreshToken, String clientId) throws OAuthSystemException {
        AccessTokenByRefreshTokenChanger refreshTokenChanger = new AccessTokenByRefreshTokenChanger(refreshToken, clientId);
        return refreshTokenChanger.change();
    }


}
