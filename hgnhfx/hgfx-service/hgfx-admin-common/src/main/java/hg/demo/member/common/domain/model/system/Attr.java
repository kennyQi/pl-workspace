/**
 * @Attr.java Create on 2016-5-23下午3:37:20
 * Copyright (c) 2012 by www.hg365.com。
 */
package hg.demo.member.common.domain.model.system;

import java.io.Serializable;

import org.apache.commons.collections.KeyValue;

/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：2016-5-23下午3:37:20
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：<a href=gaoce@hgtech365.com>gaoce</a>
 * @创建时间：2016-5-23下午3:37:20
 * @version：
 */
public class Attr implements KeyValue, Serializable {

	private static final long serialVersionUID = -6969400281629759587L;

	private Object key;
	private Object value;

	public Attr(Object key, Object value) {
		super();
		this.key = key;
		this.value = value;
	}

	@Override
	public Object getKey() {
		return key;
	}

	@Override
	public Object getValue() {
		return value;
	}

	public void setKey(Object key) {
		this.key = key;
	}

	public void setValue(Object value) {
		this.value = value;
	}

}

