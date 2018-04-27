package slfx.mp.pojo.dto.supplierpolicy;

import java.util.List;

import slfx.mp.pojo.dto.BaseMpDTO;

/**
 * 同城政策须知
 * 
 * @author Administrator
 */
public class TCPolicyNoticeDTO extends BaseMpDTO {
	private static final long serialVersionUID = 1L;

	/**
	 * 购票需知
	 */
	private String buyNotie;
	
	/**
	 * 全部以JSON形式保存
	 */
	private List<NoticeTypeDTO> noticeTypes;

	public List<NoticeTypeDTO> getNoticeTypes() {
		return noticeTypes;
	}

	public void setNoticeTypes(List<NoticeTypeDTO> noticeTypes) {
		this.noticeTypes = noticeTypes;
	}

	public String getBuyNotie() {
		return buyNotie;
	}

	public void setBuyNotie(String buyNotie) {
		this.buyNotie = buyNotie;
	}

}