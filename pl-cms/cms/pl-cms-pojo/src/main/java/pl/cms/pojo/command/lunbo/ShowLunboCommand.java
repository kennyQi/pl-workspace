package pl.cms.pojo.command.lunbo;

import pl.cms.pojo.command.AdminBaseCommand;

/**
 * 
 * @类功能说明：启用轮播图
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuxx
 * @创建时间：2015年3月11日下午3:55:47
 * 
 */
@SuppressWarnings("serial")
public class ShowLunboCommand extends AdminBaseCommand {

	private String lunboId;

	public String getLunboId() {
		return lunboId;
	}

	public void setLunboId(String lunboId) {
		this.lunboId = lunboId;
	}

}
