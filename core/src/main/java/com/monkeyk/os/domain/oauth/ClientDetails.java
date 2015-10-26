package com.monkeyk.os.domain.oauth;

import com.monkeyk.os.domain.AbstractDomain;
import org.apache.oltu.oauth2.common.message.types.GrantType;


/**
 * 15-6-12
 * <p/>
 * DBTable: oauth_client_details
 *
 * @author Shengzhao Li
 */
public class ClientDetails extends AbstractDomain {


    private static final long serialVersionUID = -3267897492642972969L;

    private String resourceIds;

    private String scope;

    private String grantTypes;

    /*
   * Shiro roles
   * */
    private String roles;

    private Integer accessTokenValidity;

    private Integer refreshTokenValidity;

    private boolean trusted = false;

    private boolean archived = false;


    private String name;
    private String clientId;
    private String clientSecret;
    private String redirectUri;
    private String clientUri;
    private String description;
    private String iconUri;

    public ClientDetails() {
    }


    public String clientId() {
        return clientId;
    }

    public String clientSecret() {
        return clientSecret;
    }

    public String redirectUri() {
        return redirectUri;
    }


    public String name() {
        return name;
    }


    public String iconUri() {
        return iconUri;
    }


    public String clientUri() {
        return clientUri;
    }


    public String description() {
        return description;
    }

    public ClientDetails clientUri(String clientUri) {
        this.clientUri = clientUri;
        return this;
    }

    public ClientDetails name(String name) {
        this.name = name;
        return this;
    }

    public ClientDetails clientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public ClientDetails clientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
        return this;
    }

    public ClientDetails redirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
        return this;
    }

    public ClientDetails iconUri(String iconUri) {
        this.iconUri = iconUri;
        return this;
    }

    public ClientDetails description(String description) {
        this.description = description;
        return this;
    }


    public String resourceIds() {
        return resourceIds;
    }

    public ClientDetails resourceIds(String resourceIds) {
        this.resourceIds = resourceIds;
        return this;
    }

    public String scope() {
        return scope;
    }

    public ClientDetails scope(String scope) {
        this.scope = scope;
        return this;
    }

    public String grantTypes() {
        return grantTypes;
    }

    public ClientDetails grantTypes(String grantTypes) {
        this.grantTypes = grantTypes;
        return this;
    }

    public String roles() {
        return roles;
    }

    public ClientDetails roles(String roles) {
        this.roles = roles;
        return this;
    }

    public Integer accessTokenValidity() {
        return accessTokenValidity;
    }

    public ClientDetails accessTokenValidity(Integer accessTokenValidity) {
        this.accessTokenValidity = accessTokenValidity;
        return this;
    }

    public Integer refreshTokenValidity() {
        return refreshTokenValidity;
    }

    public ClientDetails refreshTokenValidity(Integer refreshTokenValidity) {
        this.refreshTokenValidity = refreshTokenValidity;
        return this;
    }

    public boolean trusted() {
        return trusted;
    }

    public ClientDetails trusted(boolean trusted) {
        this.trusted = trusted;
        return this;
    }

    public boolean archived() {
        return archived;
    }

    public ClientDetails archived(boolean archived) {
        this.archived = archived;
        return this;
    }


    public boolean supportRefreshToken() {
        return this.grantTypes != null && this.grantTypes.contains(GrantType.REFRESH_TOKEN.toString());
    }
}
