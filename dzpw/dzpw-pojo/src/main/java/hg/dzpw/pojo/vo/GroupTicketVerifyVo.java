package hg.dzpw.pojo.vo;

import java.io.Serializable;

/**
 * @类功能说明：联票审核日志VO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yangkang
 * @创建时间：2015-1-8下午1:53:53
 * @版本：V1.0
 *
 */
public class GroupTicketVerifyVo implements Serializable {
	
	private static final long serialVersionUID = -7252272622898388343L;

	/**
	 * 审核时间
	 */
	private String checkDate;
	
	/**
	 * 审核人
	 */
	private String checkPersonName;
	
	/**
	 * 审核说明
	 */
	private String checkRemark;
	
	/**
	 * 审核结果
	 */
	private String checkResult;

	public String getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}

	public String getCheckPersonName() {
		return checkPersonName;
	}

	public void setCheckPersonName(String checkPersonName) {
		this.checkPersonName = checkPersonName;
	}

	public String getCheckRemark() {
		return checkRemark;
	}

	public void setCheckRemark(String checkRemark) {
		this.checkRemark = checkRemark;
	}

	public String getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}
	
}
