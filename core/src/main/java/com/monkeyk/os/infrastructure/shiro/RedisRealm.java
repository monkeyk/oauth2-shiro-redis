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
package com.monkeyk.os.infrastructure.shiro;

import com.monkeyk.os.domain.users.Roles;
import com.monkeyk.os.domain.users.RolesPermissions;
import com.monkeyk.os.domain.users.Users;
import com.monkeyk.os.domain.users.UsersRepository;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.util.List;

/**
 * 2015/10/27
 * <p/>
 * Support Redis
 *
 * @author Shengzhao Li
 */
public class RedisRealm extends AuthorizingRealm implements InitializingBean {


    private static final Logger LOG = LoggerFactory.getLogger(RedisRealm.class);


    protected UsersRepository usersRepository;


    public RedisRealm() {
        super();
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        String username = getUsername(token);

        // Null username is invalid
        if (username == null) {
            throw new AccountException("Null username are not allowed by this realm.");
        }

        Users users = usersRepository.findUsersByUsername(username);
        LOG.debug("Load Users[{}] by username: {}", users, username);

        SimpleAuthenticationInfo info = null;
        if (users != null) {
            info = new SimpleAuthenticationInfo(username, users.password().toCharArray(), getName());
        }

        return info;
    }

    protected String getUsername(AuthenticationToken token) {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        return upToken.getUsername();
    }


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        final String username = getUsername(principals);

        List<Roles> roles = usersRepository.findRolesByUsername(username);
        LOG.debug("Load Roles[{}] by username: {}", roles, username);

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        for (Roles role : roles) {
            info.addRole(role.roleName());
            for (RolesPermissions permissions : role.permissions()) {
                info.addStringPermission(permissions.permission());
            }
        }

        return info;
    }

    protected String getUsername(PrincipalCollection principals) {
        //null usernames are invalid
        if (principals == null) {
            throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
        }

        return (String) getAvailablePrincipal(principals);
    }


    public void setUsersRepository(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(usersRepository, "usersRepository is null");
    }
}
