/**
 * @SatatePath.java Create on 2015年1月15日上午10:06:22
 * Copyright (c) 2012 by www.hg365.com。
 */
package hg.state;

import java.io.Serializable;

/**
 * @类功能说明：状态转移的路径
 * @类修改者：
 * @修改日期：2015年1月15日上午10:06:22
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：<a href=xinglj@hgtech365.com>xinglj</a>
 * @创建时间：2015年1月15日上午10:06:22
 * @version：
 */
public abstract class StatePath implements Serializable{
	public String pathName, pathText;
	/**
	 * 是否程序能够自动执行这个路径
	 */
	public boolean isAuto;
	public State toState;
	
	public StatePath(String pathName, State toState){
		this.pathName=pathName;
		this.toState=toState;
		StateDao.instance.pathes.put(pathName, this);
	}
	/**
	 * @方法功能说明：
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年1月15日下午2:10:16
	 * @version：
	 * @修改内容：
	 * @参数：@param o
	 * @return:void
	 * @throws
	 */
	public abstract void action(Object o) ;
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return pathName+"/"+pathText;
	}
}
