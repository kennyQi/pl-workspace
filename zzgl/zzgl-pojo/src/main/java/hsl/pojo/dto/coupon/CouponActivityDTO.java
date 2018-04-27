package hsl.pojo.dto.coupon;

import hsl.pojo.dto.BaseDTO;

/**
 * @类功能说明：卡券活动DTO
 * @类修改者：wuyg
 * @修改日期：2014年10月15日下午2:10:34
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：wuyg
 * @创建时间：2014年10月15日下午2:10:34
 *
 */
public class CouponActivityDTO extends BaseDTO{
	
	/**
	 * @FieldsserialVersionUID:TODO
	 */
	private static final long serialVersionUID = 1L;

	private CouponActivityBaseInfoDTO baseInfo;
	
	private CouponIssueConditionInfoDTO issueConditionInfo;
	
	private CouponUseConditionInfoDTO useConditionInfo;
	
	private CouponActivityStatusDTO status;
	
	private CouponSendConditionInfoDTO sendConditionInfo;
	
	

	public CouponActivityBaseInfoDTO getBaseInfo() {
		return baseInfo;
	}

	public void setBaseInfo(CouponActivityBaseInfoDTO baseInfo) {
		this.baseInfo = baseInfo;
	}

	public CouponIssueConditionInfoDTO getIssueConditionInfo() {
		return issueConditionInfo;
	}

	public void setIssueConditionInfo(CouponIssueConditionInfoDTO issueConditionInfo) {
		this.issueConditionInfo = issueConditionInfo;
	}

	public CouponUseConditionInfoDTO getUseConditionInfo() {
		return useConditionInfo;
	}

	public void setUseConditionInfo(CouponUseConditionInfoDTO useConditionInfo) {
		this.useConditionInfo = useConditionInfo;
	}

	public CouponActivityStatusDTO getStatus() {
		return status;
	}

	public void setStatus(CouponActivityStatusDTO status) {
		this.status = status;
	}

	public CouponSendConditionInfoDTO getSendConditionInfo() {
		return sendConditionInfo;
	}

	public void setSendConditionInfo(CouponSendConditionInfoDTO sendConditionInfo) {
		this.sendConditionInfo = sendConditionInfo;
	}
	
	
}
