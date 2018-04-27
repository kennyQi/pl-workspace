/**
 * @ResultDto.java Create on 2015年1月27日下午4:27:51
 * Copyright (c) 2012 by www.hg365.com。
 */
package hg.jf.client;

import java.io.Serializable;

/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：2015年1月27日下午4:27:51
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：<a href=xinglj@hgtech365.com>xinglj</a>
 * @创建时间：2015年1月27日下午4:27:51
 * @version：
 */
public class ResultDto <T> implements Serializable {
    //结果
    boolean ok;
//    结果详细信息
    String  text;
//    数据
    T data;
    /**
     * @return the ok
     */
    public boolean isOk() {
        return ok;
    }
    /**
     * @param ok the ok to set
     */
    public void setOk(boolean ok) {
        this.ok = ok;
    }
    /**
     * @return the text
     */
    public String getText() {
        return text;
    }
    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }
    /**
     * @return the data
     */
    public T getData() {
        return data;
    }
    /**
     * @param data the data to set
     */
    public void setData(T data) {
        this.data = data;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return ok +" "+text + " "+data;
    }
}
