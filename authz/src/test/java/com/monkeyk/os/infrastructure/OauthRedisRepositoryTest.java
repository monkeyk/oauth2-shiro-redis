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
package com.monkeyk.os.infrastructure;

import com.monkeyk.os.ContextTest;
import com.monkeyk.os.domain.oauth.OauthCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/*
  * @author Shengzhao Li
  */
public class OauthRedisRepositoryTest extends ContextTest {

    @Autowired
    private OauthRedisRepository oauthRedisRepository;


    @Test
    public void findOauthCodeByUsernameClientId() {

        final OauthCode oauthCode = oauthRedisRepository.findOauthCodeByUsernameClientId("usd", "client");
        assertNull(oauthCode);

    }


}