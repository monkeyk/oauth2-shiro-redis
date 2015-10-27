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
package com.monkeyk.os.service;

import com.monkeyk.os.domain.oauth.ClientDetails;
import com.monkeyk.os.service.dto.ClientDetailsDto;

/**
 * 2015/10/27
 *
 * @author Shengzhao Li
 */

public interface ClientDetailsService {


    boolean addClientDetails(ClientDetailsDto clientDetailsDto);

    ClientDetails loadClientDetails(String clientId);

    boolean removeClientDetails(String clientId);


}