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
package com.monkeyk.os.domain.oauth;

import com.monkeyk.os.domain.shared.Repository;

/**
 * 2015/10/27
 *
 * @author Shengzhao Li
 */

public interface ClientDetailsRepository extends Repository {


    boolean saveClientDetails(ClientDetails clientDetails);

    ClientDetails findClientDetails(String clientId);

    boolean deleteByClientId(String clientId);
}