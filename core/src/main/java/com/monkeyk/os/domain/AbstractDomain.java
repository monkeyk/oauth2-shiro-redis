package com.monkeyk.os.domain;

import com.monkeyk.os.infrastructure.DateUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 抽象的Domain实体类, 基于DDD设计模式
 *
 * @author Shengzhao Li
 * @serial
 */
public abstract class AbstractDomain implements Serializable {


    private static final long serialVersionUID = 7787898374385773471L;

    /**
     * 创建时间, 默认为当前时间值
     */
    protected Date createTime = DateUtils.now();

    public AbstractDomain() {
    }


    public Date createTime() {
        return createTime;
    }

    /**
     * 设置Domain的创建时间并返回实体本身.
     * Builder设计模式的变种
     *
     * @param createTime createTime
     * @param <T>        AbstractDomain subclass
     * @return Current domain
     */
    @SuppressWarnings("unchecked")
    public <T extends AbstractDomain> T createTime(Date createTime) {
        this.createTime = createTime;
        return (T) this;
    }

}