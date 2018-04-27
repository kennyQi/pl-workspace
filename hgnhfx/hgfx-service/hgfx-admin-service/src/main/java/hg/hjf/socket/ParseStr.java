/**
 * @ParseStr.java Create on 2015年5月28日下午3:04:11
 * Copyright (c) 2012 by www.hg365.com。
 */
package hg.hjf.socket;

/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：2015年5月28日下午3:04:11
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：<a href=xinglj@hgtech365.com>xinglj</a>
 * @创建时间：2015年5月28日下午3:04:11
 * @version：
 */
public interface ParseStr {
    	/**
    	 * 解析为对象
    	 */
	public void parseStr(String str);
	/**
	 * 对象转为文本
	 * @方法功能说明：
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年5月28日下午3:04:33
	 * @version：
	 * @修改内容：
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	public String toStr();
}
