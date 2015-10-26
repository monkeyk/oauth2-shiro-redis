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
package com.monkeyk.os.domain.users;

import com.monkeyk.os.domain.AbstractDomain;
import com.monkeyk.os.domain.AbstractIdDomain;
import com.monkeyk.os.domain.shared.GuidGenerator;

import java.util.Date;

/**
 * 2015/10/26
 * <p/>
 * Table: users
 *
 * @author Shengzhao Li
 */
public class Users extends AbstractIdDomain {


    private static final long serialVersionUID = -3990278799194556012L;


    //unique
    private String username;

    private String password;

    /**
     * Label default user or not
     */
    private boolean defaultUser = false;


    private Date lastLoginTime;


    public Users() {
    }

    public String username() {
        return username;
    }

    public Users username(String username) {
        this.username = username;
        return this;
    }

    public String password() {
        return password;
    }

    public Users password(String password) {
        this.password = password;
        return this;
    }

    public boolean defaultUser() {
        return defaultUser;
    }

    public Users defaultUser(boolean defaultUser) {
        this.defaultUser = defaultUser;
        return this;
    }

    public Date lastLoginTime() {
        return lastLoginTime;
    }

    public Users lastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
        return this;
    }


}
