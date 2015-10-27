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
package com.monkeyk.os.web.controller;

import com.monkeyk.os.service.ClientDetailsService;
import com.monkeyk.os.service.dto.ClientDetailsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 2015/10/27
 *
 * @author Shengzhao Li
 */
@Controller()
@RequestMapping("/client_details/")
public class ClientDetailsController {


    @Autowired
    private ClientDetailsService clientDetailsService;


    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public boolean addClientDetails(ClientDetailsDto clientDetailsDto) {
        return clientDetailsService.addClientDetails(clientDetailsDto);
    }


    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public boolean deleteClientDetails(@RequestParam String clientId) {
        return clientDetailsService.removeClientDetails(clientId);
    }


    @RequestMapping(value = "find")
    @ResponseBody
    public ClientDetailsDto findClientDetails(@RequestParam String clientId) {
        return clientDetailsService.loadClientDetails(clientId);
    }


}
