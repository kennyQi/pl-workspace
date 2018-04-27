package plfx.jd.pojo.dto.plfx.hotel;

import java.util.Date;

import plfx.jd.pojo.dto.ylclient.order.BaseJDDTO;
/****
 * 
 * @类功能说明：政策
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年7月15日上午10:46:05
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class YLAvailPolicyDTO extends BaseJDDTO{
	/***
	 * 开始日期
	 */

	public Date startDate;
	/***
	 * 结束日期
	 */

	public Date endDate;
	/***
	 * 温馨提示
	 */

	public String availPolicyDescrption;

	
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getAvailPolicyDescrption() {
		return availPolicyDescrption;
	}

	public void setAvailPolicyDescrption(String availPolicyDescrption) {
		this.availPolicyDescrption = availPolicyDescrption;
	}
	
	
	
}
