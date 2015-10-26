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

import com.monkeyk.os.domain.users.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 2015/10/26
 *
 * @author Shengzhao Li
 */
@Repository("usersJdbcRepository")
public class UsersJdbcRepository extends AbstractJdbcRepository implements UsersRepository {


    private static UsersRowMapper usersRowMapper = new UsersRowMapper();

    private static RolesRowMapper rolesRowMapper = new RolesRowMapper();

    private RolesPermissionsRowMapper rolesPermissionsRowMapper = new RolesPermissionsRowMapper();


    @Override
    public Users findUsersByUsername(String username) {
        final String sql = " select u.* from users u where u.archived = false and u.username = ? ";
        final List<Users> usersList = jdbcTemplate.query(sql, usersRowMapper, username);
        return usersList.isEmpty() ? null : usersList.get(0);
    }


    @Override
    public List<Roles> findRolesByUsername(String username) {
        final String roleSql = " select r.* from roles r, user_roles ur, users u where u.archived = false " +
                " and u.id = ur.users_id and ur.roles_id = r.id and u.username = ? ";
        final List<Roles> rolesList = jdbcTemplate.query(roleSql, rolesRowMapper, username);

        //load permissions
        for (Roles roles : rolesList) {
            loadRolesPermissions(roles);
        }

        return rolesList;
    }

    private void loadRolesPermissions(Roles roles) {
        final String pSql = " select p.* from roles_permissions p where p.roles_id = ? ";
        final List<RolesPermissions> list = jdbcTemplate.query(pSql, rolesPermissionsRowMapper, roles.id());
        roles.permissions().addAll(list);
    }
}
