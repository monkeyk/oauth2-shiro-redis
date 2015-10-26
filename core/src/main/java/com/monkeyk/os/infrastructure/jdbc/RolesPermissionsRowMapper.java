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
package com.monkeyk.os.infrastructure.jdbc;

import com.monkeyk.os.domain.users.RolesPermissions;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 2015/10/26
 *
 * @author Shengzhao Li
 */
public class RolesPermissionsRowMapper implements RowMapper<RolesPermissions> {


    @Override
    public RolesPermissions mapRow(ResultSet rs, int rowNum) throws SQLException {
        RolesPermissions permissions = new RolesPermissions();

        permissions.createTime(rs.getTimestamp("create_time"));
        permissions.rolesId(rs.getInt("roles_id"));
        permissions.permission(rs.getString("permission"));

        return permissions;
    }
}
