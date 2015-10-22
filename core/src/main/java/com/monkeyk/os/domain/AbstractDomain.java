package com.monkeyk.os.domain;

import com.monkeyk.os.infrastructure.DateUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Shengzhao Li
 */
public abstract class AbstractDomain implements Serializable {


    private static final long serialVersionUID = 7787898374385773471L;
    /**
     * The domain create time.
     */
    protected Date createTime = DateUtils.now();

    public AbstractDomain() {
    }


    public Date createTime() {
        return createTime;
    }

    @SuppressWarnings("unchecked")
    public <T extends AbstractDomain> T createTime(Date createTime) {
        this.createTime = createTime;
        return (T) this;
    }

}