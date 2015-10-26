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


import java.util.HashSet;
import java.util.Set;

/**
 * 2015/10/26
 * <p/>
 * Table: user_roles
 *
 * @author Shengzhao Li
 */
public class UserRoles extends AbstractDomain {

    private static final long serialVersionUID = 8722613219109153850L;

    private int usersId;

    // roles_id
    private int rolesId;


    public UserRoles() {
    }

    public int usersId() {
        return usersId;
    }

    public UserRoles usersId(int usersId) {
        this.usersId = usersId;
        return this;
    }

    public int rolesId() {
        return rolesId;
    }

    public UserRoles rolesId(int rolesId) {
        this.rolesId = rolesId;
        return this;
    }
}
