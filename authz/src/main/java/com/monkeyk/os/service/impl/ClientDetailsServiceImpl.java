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
package com.monkeyk.os.service.impl;

import com.monkeyk.os.domain.oauth.ClientDetails;
import com.monkeyk.os.domain.oauth.ClientDetailsRepository;
import com.monkeyk.os.service.ClientDetailsService;
import com.monkeyk.os.service.dto.ClientDetailsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 2015/10/27
 *
 * @author Shengzhao Li
 */
@Service("clientDetailsService")
public class ClientDetailsServiceImpl implements ClientDetailsService {


    @Autowired
    private ClientDetailsRepository clientDetailsRepository;


    @Override
    public boolean addClientDetails(ClientDetailsDto clientDetailsDto) {
        ClientDetails clientDetails = clientDetailsDto.newDomain();
        return clientDetailsRepository.saveClientDetails(clientDetails);
    }

    @Override
    public ClientDetailsDto loadClientDetails(String clientId) {
        final ClientDetails clientDetails = clientDetailsRepository.findClientDetails(clientId);
        return clientDetails == null ? null : new ClientDetailsDto(clientDetails);
    }

    @Override
    public boolean removeClientDetails(String clientId) {
        return clientDetailsRepository.deleteByClientId(clientId);
    }
}
