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
package com.monkeyk.os.service.busincess;

import com.monkeyk.os.domain.oauth.OauthCacheRepository;
import com.monkeyk.os.domain.oauth.OauthRepository;
import com.monkeyk.os.domain.shared.BeanProvider;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 2015/10/22
 *
 * @author Shengzhao Li
 */
public abstract class AbstractOAuthHolder {


    protected transient OauthRepository oauthRepository = BeanProvider.getBean(OauthRepository.class);

    protected transient OauthCacheRepository oauthCacheRepository = BeanProvider.getBean(OauthCacheRepository.class);


    /**
     * Return current login username
     *
     * @return Username
     */
    protected String currentUsername() {
        return (String) SecurityUtils.getSubject().getPrincipal();
    }

}
