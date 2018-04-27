package hg.demo.member.common.spi.qo.Security;

import hg.framework.common.base.BaseSPIQO;

/**
 * Created by admin on 2016/6/6.
 */
public class QueryAuthUserByIdSQO extends BaseSPIQO {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
