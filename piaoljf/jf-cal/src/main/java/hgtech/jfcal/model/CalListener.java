/**
 * @文件名称：CalListener.java
 * @类路径：hgtech.jfcal.model
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014年10月21日下午1:41:23
 */
package hgtech.jfcal.model;

/**
 * @类功能说明：
 * @类修改者：计算的听众
 * @修改日期：2014年10月21日下午1:41:23
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014年10月21日下午1:41:23
 *
 */
public interface CalListener {
	/**
	 * 
	 * @方法功能说明：计算完毕触发
	 * @修改者名字：xinglj
	 * @修改时间：2014年10月21日下午1:44:23
	 * @修改内容：
	 * @参数：@param result
	 * @return:void
	 * @throws
	 */
	public void calEnd(CalResult result);
}
