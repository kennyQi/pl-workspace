package hg.dzpw.dealer.client.dto.ticket;

import hg.dzpw.dealer.client.common.BaseDTO;

/**
 * @类功能说明：门票激活返回实体
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yangkang
 * @创建时间：2015-1-21下午3:44:52
 * @版本：V1.0
 */
public class ActiveTicketDTO extends BaseDTO {

	private static final long serialVersionUID = -7820689378232991344L;
	
	/**
	 * 票号
	 */
	private String ticketNo;
	
	/**
	 * 游玩人姓名
	 */
	private String touristName;
	
	/**
	 * 手机号码
	 */
	private String telephone;
	
	/**
	 * 证件类型
	 */
	private Integer idType;
	
	/**
	 * 证件号
	 */
	private String idNo;
	
	/**
	 * 状态 0--未游玩
	 */
	private Integer status;

	public String getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}

	public String getTouristName() {
		return touristName;
	}

	public void setTouristName(String touristName) {
		this.touristName = touristName;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Integer getIdType() {
		return idType;
	}

	public void setIdType(Integer idType) {
		this.idType = idType;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
