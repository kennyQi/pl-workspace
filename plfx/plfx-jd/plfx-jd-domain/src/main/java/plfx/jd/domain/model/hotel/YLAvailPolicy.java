package plfx.jd.domain.model.hotel;

import java.util.Date;

import javax.persistence.Column;
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
public class YLAvailPolicy{
	/***
	 * 开始日期
	 */
	@Column(name = "STARTDATES")
	public Date StartDate;
	/***
	 * 结束日期
	 */
	@Column(name = "ENDDATES")
	public Date EndDate;
	/***
	 * 温馨提示
	 */
	@Column(name = "AVAILPOLICYDESCRPTION",columnDefinition ="TEXT")
	public String availPolicyDescrption;

	public Date getStartDate() {
		return StartDate;
	}

	public void setStartDate(Date startDate) {
		StartDate = startDate;
	}

	public Date getEndDate() {
		return EndDate;
	}

	public void setEndDate(Date endDate) {
		EndDate = endDate;
	}

	public String getAvailPolicyDescrption() {
		return availPolicyDescrption;
	}

	public void setAvailPolicyDescrption(String availPolicyDescrption) {
		this.availPolicyDescrption = availPolicyDescrption;
	}
	
	
	
}
