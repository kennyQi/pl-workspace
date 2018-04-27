package hg.dzpw.pojo.command.platform.ticket.singleTicket;

import hg.dzpw.pojo.common.BaseCommand;

/**
 * 修改单票
 * @author CaiHuan
 *
 * 日期:2015-12-11
 */
public class PlatformModifySingleTicketCommand extends BaseCommand {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 单票id
	 */
	private String singleTicketId;
	
	/**
	 * 单票状态
	 */
	private Integer status;
	
	/**
	 * 游客姓名
	 */
	private String touristName;
	
	/**
	 * 证件类型
	 */
	private Integer idType;
	
	/**
	 * 证件号
	 */
	private String idNo;

	public String getSingleTicketId() {
		return singleTicketId;
	}

	public void setSingleTicketId(String singleTicketId) {
		this.singleTicketId = singleTicketId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getTouristName() {
		return touristName;
	}

	public void setTouristName(String touristName) {
		this.touristName = touristName;
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
	
}
