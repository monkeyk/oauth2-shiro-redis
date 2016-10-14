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

import com.monkeyk.os.domain.AbstractIdDomain;

import java.util.HashSet;
import java.util.Set;

/**
 * 2015/10/26
 * <p/>
 * Table: roles
 *
 * @author Shengzhao Li
 */
public class Roles extends AbstractIdDomain {


    private static final long serialVersionUID = 8762398291767207066L;

    //unique
    private String roleName;

    //permission
    private Set<RolesPermissions> permissions = new HashSet<>();


    public Roles() {
    }

    public Set<RolesPermissions> permissions() {
        return permissions;
    }

    public String roleName() {
        return roleName;
    }

    public Roles roleName(String roleName) {
        this.roleName = roleName;
        return this;
    }
}
