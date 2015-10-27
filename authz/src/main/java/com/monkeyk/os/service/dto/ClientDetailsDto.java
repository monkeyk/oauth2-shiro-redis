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
package com.monkeyk.os.service.dto;

import com.monkeyk.os.domain.oauth.ClientDetails;
import com.monkeyk.os.infrastructure.DateUtils;

import java.io.Serializable;

/**
 * 2015/10/27
 *
 * @author Shengzhao Li
 */
public class ClientDetailsDto implements Serializable {

    private static final long serialVersionUID = 6642623244436765992L;


    private String name;
    private String clientId;
    private String clientSecret;
    private String redirectUri;
    private String clientUri;
    private String description;
    private String iconUri;


    private String resourceIds;

    private String scope;

    private String grantTypes;

    /*
   * Shiro roles
   * */
    private String roles = "22";

    private Integer accessTokenValidity = -1;

    private Integer refreshTokenValidity = -1;


    private String createTime;

    private boolean trusted;

    public ClientDetailsDto() {
    }

    public ClientDetailsDto(ClientDetails details) {
        this.createTime = DateUtils.toDateTime(details.createTime());
        this.roles = details.roles();
        this.trusted = details.trusted();

        this.accessTokenValidity = details.accessTokenValidity();
        this.refreshTokenValidity = details.refreshTokenValidity();
        this.grantTypes = details.grantTypes();

        this.scope = details.scope();
        this.resourceIds = details.resourceIds();
        this.name = details.name();

        this.clientId = details.clientId();
        this.clientSecret = details.clientSecret();
        this.description = details.description();

        this.clientUri = details.clientUri();
        this.iconUri = details.iconUri();
        this.redirectUri = details.redirectUri();
    }


    public boolean isTrusted() {
        return trusted;
    }

    public void setTrusted(boolean trusted) {
        this.trusted = trusted;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public String getClientUri() {
        return clientUri;
    }

    public void setClientUri(String clientUri) {
        this.clientUri = clientUri;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIconUri() {
        return iconUri;
    }

    public void setIconUri(String iconUri) {
        this.iconUri = iconUri;
    }

    public String getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(String resourceIds) {
        this.resourceIds = resourceIds;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getGrantTypes() {
        return grantTypes;
    }

    public void setGrantTypes(String grantTypes) {
        this.grantTypes = grantTypes;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public Integer getAccessTokenValidity() {
        return accessTokenValidity;
    }

    public void setAccessTokenValidity(Integer accessTokenValidity) {
        this.accessTokenValidity = accessTokenValidity;
    }

    public Integer getRefreshTokenValidity() {
        return refreshTokenValidity;
    }

    public void setRefreshTokenValidity(Integer refreshTokenValidity) {
        this.refreshTokenValidity = refreshTokenValidity;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public ClientDetails newDomain() {
        ClientDetails details = new ClientDetails().clientId(clientId).clientSecret(clientSecret);

        details.clientUri(clientUri).redirectUri(redirectUri).description(description)
                .trusted(trusted).name(name).accessTokenValidity(accessTokenValidity)
                .refreshTokenValidity(refreshTokenValidity).grantTypes(grantTypes)
                .resourceIds(resourceIds).iconUri(iconUri).roles(roles).scope(scope);

        return details;
    }
}
