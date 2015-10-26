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

import com.monkeyk.os.ContextTest;
import com.monkeyk.os.domain.users.Roles;
import com.monkeyk.os.domain.users.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;

/*
  * @author Shengzhao Li
  */
public class UsersJdbcRepositoryTest extends ContextTest {


    @Autowired
    private UsersJdbcRepository usersJdbcRepository;


    @Test
    public void findUsersByUsername() {

        final Users users = usersJdbcRepository.findUsersByUsername("abc");
        assertNull(users);
    }


    @Test
    public void findRolesByUsername() {

        final List<Roles> list = usersJdbcRepository.findRolesByUsername("abcd");
        assertTrue(list.isEmpty());
    }

}