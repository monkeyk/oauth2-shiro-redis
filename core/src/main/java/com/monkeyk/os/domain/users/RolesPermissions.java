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

/**
 * 2015/10/26
 * <p/>
 * Table: roles_permissions
 *
 * @author Shengzhao Li
 */
public class RolesPermissions extends AbstractDomain {

    private static final long serialVersionUID = 4919225497795040502L;


    // Refer to Roles
    private int rolesId;

    private String permission;


    public RolesPermissions() {
    }

    public int rolesId() {
        return rolesId;
    }

    public RolesPermissions rolesId(int rolesId) {
        this.rolesId = rolesId;
        return this;
    }

    public String permission() {
        return permission;
    }

    public RolesPermissions permission(String permission) {
        this.permission = permission;
        return this;
    }
}
