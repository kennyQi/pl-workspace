package hg.dzpw.pojo.api.appv1.dto;

import java.io.Serializable;

/**
 * @类功能说明：门票
 * @类修改者：
 * @修改日期：2014-11-18下午3:30:41
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-11-18下午3:30:41
 */
public class TicketDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/** 证件类型 1、 身份证 */
	public final static String CER_TYPE_IDENTITY = "1";
	/** 证件类型 2、 军官证 */
	public final static String CER_TYPE_OFFICER = "2";
	/** 验票方式 3、 驾驶证 */
	public final static String CER_TYPE_DRIVING = "3";
	/** 证件类型 4、 护照 */
	public final static String CER_TYPE_PASSPORT = "4";

	/**
	 * 票号
	 */
	private String ticketNo;
	
	/**
	 * 游玩人姓名
	 */
	private String touristName;
	
	/**
	 * 证件类型(1、	身份证；2、	军官证；3、	驾驶证；4、	护照)
	 */
	private String cerType;
	
	/**
	 * 证件号
	 */
	private String idNo;

	/**
	 * 门票政策名称
	 */
	private String ticketPolicyName;

	/**
	 * 包含的景区名称，eg：华山、衡山、恒山、嵩山、泰山
	 */
	private String remark;

//	/**
//	 * 门票核销代码  1-门票有效  -1-联票未到使用期或过期  -2-门票使用过
//	 */
//	private String resultCode;
	
	/**
	 * resultCode 对应的消息
	 */
	private String resultMsg;
	
	/**
	 * 门票类型  1-单票   2-联票
	 */
	private Integer type;
	
	/**
	 * 有效期范围   如：2015.10.10-2015.12.12
	 */
	private String expDate;
	
	
	public TicketDto(){}
	public TicketDto(String ticketNo, String ticketPolicyName, String remark) {
		this.ticketNo = ticketNo;
		this.ticketPolicyName = ticketPolicyName;
		this.remark = remark;
	}
	public TicketDto(String ticketNo, String touristName, String cerType,
			String idNo, String ticketPolicyName, String remark, Integer type,String expDate) {
		this.ticketNo = ticketNo;
		this.touristName = touristName;
		this.setCerType(cerType);
		this.idNo = idNo;
		this.ticketPolicyName = ticketPolicyName;
		this.remark = remark;
		this.type = type;
		this.expDate=expDate;
	}
	
	public String getTicketNo() {
		return ticketNo;
	}
	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo == null ? null : ticketNo.trim();
	}
	public String getTicketPolicyName() {
		return ticketPolicyName;
	}
	public void setTicketPolicyName(String ticketPolicyName) {
		this.ticketPolicyName = ticketPolicyName == null ? null : ticketPolicyName.trim();
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}
	public String getTouristName() {
		return touristName;
	}
	public void setTouristName(String touristName) {
		this.touristName = touristName == null ? null : touristName.trim();
	}
	public String getCerType() {
		return cerType;
	}
	public void setCerType(String cerType) {
		if(cerType == null)
			return;
		if(CER_TYPE_IDENTITY.equals(cerType))
			this.cerType = "身份证";
		if(CER_TYPE_OFFICER.equals(cerType))
			this.cerType = "军官证";
		if(CER_TYPE_DRIVING.equals(cerType))
			this.cerType = "驾驶证";
		if(CER_TYPE_PASSPORT.equals(cerType))
			this.cerType = "护照";
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo == null ? null : idNo.trim();
	}
//	public String getResultCode() {
//		return resultCode;
//	}
//	public void setResultCode(String resultCode) {
//		this.resultCode = resultCode;
//	}
	
	public String getResultMsg() {
		return resultMsg;
	}
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getExpDate() {
		return expDate;
	}
	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}
	
}