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


import com.monkeyk.os.domain.users.Users;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 2015/10/26
 *
 * @author Shengzhao Li
 */
public class UsersRowMapper implements RowMapper<Users> {


    @Override
    public Users mapRow(ResultSet rs, int rowNum) throws SQLException {
        Users users = new Users();

        users.id(rs.getInt("id"))
                .guid(rs.getString("guid"))
                .version(rs.getInt("version"))
                .archived(rs.getBoolean("archived"));

        users.createTime(rs.getTimestamp("create_time"));

        users.username(rs.getString("username"))
                .password(rs.getString("password"))
                .defaultUser(rs.getBoolean("default_user"))
                .lastLoginTime(rs.getTimestamp("last_login_time"));

        return users;
    }
}
