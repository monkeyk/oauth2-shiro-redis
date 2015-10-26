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
package com.monkeyk.os.domain.rs;

import com.monkeyk.os.domain.oauth.AccessToken;
import com.monkeyk.os.domain.oauth.ClientDetails;
import com.monkeyk.os.domain.shared.Repository;

/**
 * 2015/10/26
 * <p/>
 * <p/>
 * Wrapper OAuthRSRepository, add cache support
 *
 * @author Shengzhao Li
 * @see com.monkeyk.os.domain.rs.OAuthRSRepository
 */
public interface OAuthRSCacheRepository extends Repository {


    AccessToken findAccessTokenByTokenId(String tokenId);

    ClientDetails findClientDetailsByClientIdAndResourceIds(String clientId, String resourceIds);

}
