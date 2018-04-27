package hg.service.ad.command.ad;

import hg.common.component.BaseCommand;

/**
 * 
 * @类功能说明：优先级上移一位
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuxx
 * @创建时间：2015年3月11日下午4:23:47
 *
 */
@SuppressWarnings("serial")
public class HideAdCommand extends BaseCommand {

	private String adId;
	
	public String getAdId() {
		return adId;
	}

	public void setAdId(String adId) {
		this.adId = adId;
	}

}
