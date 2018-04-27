package pl.cms.pojo.command;

import hg.common.component.BaseCommand;

/**
 * 
 * @类功能说明：来自微信端的命令基类
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuxx
 * @创建时间：2015年3月10日下午2:24:27
 * 
 */
@SuppressWarnings("serial")
public class WXBaseCommand extends BaseCommand {

	/**
	 * 微信用户openId，可为空
	 */
	private String wxUserOpenId;

	public String getWxUserOpenId() {
		return wxUserOpenId;
	}

	public void setWxUserOpenId(String wxUserOpenId) {
		this.wxUserOpenId = wxUserOpenId;
	}

}
