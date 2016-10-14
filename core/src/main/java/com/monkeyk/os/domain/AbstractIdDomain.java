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
package com.monkeyk.os.domain;

import com.monkeyk.os.domain.shared.GuidGenerator;

/**
 * 2015/10/26
 * <p/>
 * <p/>
 * Add  id, version  field and so on
 * 扩展 AbstractDomain 的抽象类, 增加 字段id, version, archived, guid
 *
 * @author Shengzhao Li
 */
public abstract class AbstractIdDomain extends AbstractDomain {

    private static final long serialVersionUID = 4653514788456221527L;


    protected int id;

    /*
    * 实现数据 乐观锁
    * */
    // Optimistic lock
    protected int version = 0;

    /**
     * 数据逻辑删除的 标记
     */
    // logic delete label
    protected boolean archived = false;

    /**
     * 数据业务ID
     */
    // business id
    protected String guid = GuidGenerator.generate();


    public AbstractIdDomain() {
    }

    public int id() {
        return id;
    }

    @SuppressWarnings("unchecked")
    public <T extends AbstractIdDomain> T id(int id) {
        this.id = id;
        return (T) this;
    }

    public int version() {
        return version;
    }

    @SuppressWarnings("unchecked")
    public <T extends AbstractIdDomain> T version(int version) {
        this.version = version;
        return (T) this;
    }

    public boolean archived() {
        return archived;
    }

    @SuppressWarnings("unchecked")
    public <T extends AbstractIdDomain> T archived(boolean archived) {
        this.archived = archived;
        return (T) this;
    }

    public String guid() {
        return guid;
    }

    @SuppressWarnings("unchecked")
    public <T extends AbstractIdDomain> T guid(String guid) {
        this.guid = guid;
        return (T) this;
    }
}
