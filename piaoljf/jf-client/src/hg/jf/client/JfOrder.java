/**
 * @JfOrder.java Create on 2015年1月30日下午2:13:50
 * Copyright (c) 2012 by www.hg365.com。
 */
package hg.jf.client;

/**
 * @类功能说明：发放的积分数据
 * @类修改者：
 * @修改日期：2015年1月30日下午2:13:50
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：<a href=xinglj@hgtech365.com>xinglj</a>
 * @创建时间：2015年1月30日下午2:13:50
 * @version：
 */
public class JfOrder {
    String userId; 
    /**
     * @类名：JfOrder.java Created on 2015年1月30日下午2:30:22
     * 
     * @Copyright (c) 2012 by www.hg365.com。
     */
    public JfOrder(String userId, int jf, String remark) {
	super();
	this.userId = userId;
	this.jf = jf;
	this.remark = remark;
    }
    int jf; 
    String remark;
    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }
    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }
    /**
     * @return the jf
     */
    public int getJf() {
        return jf;
    }
    /**
     * @param jf the jf to set
     */
    public void setJf(int jf) {
        this.jf = jf;
    }
    /**
     * @return the remark
     */
    public String getRemark() {
        return remark;
    }
    /**
     * @param remark the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}
