package zzpl.api.client.request.jp;

import java.util.List;

import zzpl.api.client.base.ApiPayload;
import zzpl.api.client.dto.jp.GNFlightListDTO;

/****
 * 
 * @类功能说明：生成订单command
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年6月24日上午9:41:20
 * @版本：V1.0
 * 
 */
@SuppressWarnings("serial")
public class GNTeamBookCommand extends ApiPayload {

	/**
	 * 登录用户ID
	 */
	private String userID;

	/**
	 * 流程ID
	 */
	private String workflowID;

	/**
	 * 下一步执行人ID（办结时，不需要该字段）
	 */
	private List<String> nextUserIDs;

	/**
	 * 当前环节
	 */
	private Integer currentStepNO;

	/**
	 * 下一步环节
	 */
	private Integer nextStepNO;

	/**
	 * 联系人姓名
	 */
	private String linkName;

	/**
	 * 联系人电话
	 */
	private String linkTelephone;

	/**
	 * 联系人邮箱
	 */
	private String linkEmail;

	/**
	 * 乘客集合
	 */
	private List<GNTeamPassengerDTO> teamPassengerDTOs;

	/**
	 * 备注
	 */
	private String note;

	/**
	 * 航班信息
	 */
	private GNFlightListDTO flightGNListDTO;

	/**
	 * 报销凭证
	 */
	private Integer reimbursementStatus;

	/**
	 * 支付类型 1:代扣 2:客户支付 (前期意义为这两种 后期有可能 1：代扣 2：支付宝 3：微信 ..........)
	 */
	private String payType;

	public static final Integer PAY_BY_TRAVEL = 1;
	public static final Integer PAY_BY_SELF = 2;

	public class GNTeamPassengerDTO {
		/***
		 * 乘客姓名
		 */
		private String passengerName;
		/***
		 * 乘客类型
		 */
		private String passengerType;
		/**
		 * 证件类型
		 */
		private String idType;
		/***
		 * 证件号
		 */
		private String idNO;
		/**
		 * 电话
		 */
		private String telephone;

		/**
		 * 成本中心
		 */
		private String costCenterID;

		public String getCostCenterID() {
			return costCenterID;
		}

		public void setCostCenterID(String costCenterID) {
			this.costCenterID = costCenterID;
		}

		public String getPassengerName() {
			return passengerName;
		}

		public void setPassengerName(String passengerName) {
			this.passengerName = passengerName;
		}

		public String getPassengerType() {
			return passengerType;
		}

		public void setPassengerType(String passengerType) {
			this.passengerType = passengerType;
		}

		public String getIdType() {
			return idType;
		}

		public void setIdType(String idType) {
			this.idType = idType;
		}

		public String getIdNO() {
			return idNO;
		}

		public void setIdNO(String idNO) {
			this.idNO = idNO;
		}

		public String getTelephone() {
			return telephone;
		}

		public void setTelephone(String telephone) {
			this.telephone = telephone;
		}

	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getWorkflowID() {
		return workflowID;
	}

	public void setWorkflowID(String workflowID) {
		this.workflowID = workflowID;
	}

	public List<String> getNextUserIDs() {
		return nextUserIDs;
	}

	public void setNextUserIDs(List<String> nextUserIDs) {
		this.nextUserIDs = nextUserIDs;
	}

	public Integer getCurrentStepNO() {
		return currentStepNO;
	}

	public void setCurrentStepNO(Integer currentStepNO) {
		this.currentStepNO = currentStepNO;
	}

	public Integer getNextStepNO() {
		return nextStepNO;
	}

	public void setNextStepNO(Integer nextStepNO) {
		this.nextStepNO = nextStepNO;
	}

	public String getLinkName() {
		return linkName;
	}

	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}

	public String getLinkTelephone() {
		return linkTelephone;
	}

	public void setLinkTelephone(String linkTelephone) {
		this.linkTelephone = linkTelephone;
	}

	public String getLinkEmail() {
		return linkEmail;
	}

	public void setLinkEmail(String linkEmail) {
		this.linkEmail = linkEmail;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public GNFlightListDTO getFlightGNListDTO() {
		return flightGNListDTO;
	}

	public void setFlightGNListDTO(GNFlightListDTO flightGNListDTO) {
		this.flightGNListDTO = flightGNListDTO;
	}

	public Integer getReimbursementStatus() {
		return reimbursementStatus;
	}

	public void setReimbursementStatus(Integer reimbursementStatus) {
		this.reimbursementStatus = reimbursementStatus;
	}

	public List<GNTeamPassengerDTO> getTeamPassengerDTOs() {
		return teamPassengerDTOs;
	}

	public void setTeamPassengerDTOs(List<GNTeamPassengerDTO> teamPassengerDTOs) {
		this.teamPassengerDTOs = teamPassengerDTOs;
	}

}
