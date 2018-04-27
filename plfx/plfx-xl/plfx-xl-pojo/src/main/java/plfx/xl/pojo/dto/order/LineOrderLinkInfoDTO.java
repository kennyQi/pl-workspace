package plfx.xl.pojo.dto.order;

import plfx.xl.pojo.dto.BaseXlDTO;

/**
 * 
 * @类功能说明：订单联系人信息DTO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年12月17日下午3:00:50
 * 
 */
@SuppressWarnings("serial")
public class LineOrderLinkInfoDTO extends BaseXlDTO{
	/**
	 * 联系人姓名
	 */
	private String linkName;
	
	/**
	 * 联系人手机号
	 */
	private String linkMobile;

	public String getLinkName() {
		return linkName;
	}

	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}

	public String getLinkMobile() {
		return linkMobile;
	}

	public void setLinkMobile(String linkMobile) {
		this.linkMobile = linkMobile;
	}

}