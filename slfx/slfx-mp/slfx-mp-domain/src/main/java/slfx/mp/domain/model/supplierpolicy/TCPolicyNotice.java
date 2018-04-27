package slfx.mp.domain.model.supplierpolicy;

import hg.common.component.BaseModel;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import slfx.mp.domain.model.M;
import slfx.mp.domain.model.scenicspot.TCScenicSpots;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * 同城政策须知
 * 
 * @author Administrator
 */
@Entity
@Table(name = M.TABLE_PREFIX + "TC_POLICY_NOTICE")
public class TCPolicyNotice extends BaseModel {
	private static final long serialVersionUID = 1L;

	/**
	 * 同程景区
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private TCScenicSpots tcScenicSpots;
	
	/**
	 * 购票需知
	 */
	@Column(name = "BUY_NOTIE", columnDefinition = M.TEXT_COLUM)
	private String buyNotie;
	
	
	@JSONField(serialize = false)
	@Column(name = "NOTICE_JSON", columnDefinition = M.TEXT_COLUM)
	private String noticeJson;

	/**
	 * 全部以JSON形式保存
	 */
	@Transient
	private List<NoticeType> noticeTypes;

	public String getNoticeJson() {
		if (noticeJson == null && noticeTypes != null) {
			noticeJson = JSON.toJSONString(noticeTypes);
		}
		return noticeJson;
	}

	public void setNoticeJson(String noticeJson) {
		this.noticeJson = noticeJson;
	}
	
	public List<NoticeType> getNoticeTypes() {
		if (noticeTypes == null && noticeJson != null) {
			try {
				noticeTypes = JSON.parseArray(noticeJson, NoticeType.class);
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage() + "-->noticeJson:" + noticeJson);
			}
		}
		return noticeTypes;
	}

	public void setNoticeTypes(List<NoticeType> noticeTypes) {
		this.noticeTypes = noticeTypes;
	}

	public TCScenicSpots getTcScenicSpots() {
		return super.getProperty(tcScenicSpots, TCScenicSpots.class);
	}

	public void setTcScenicSpots(TCScenicSpots tcScenicSpots) {
		this.tcScenicSpots = tcScenicSpots;
	}

	public String getBuyNotie() {
		return buyNotie;
	}

	public void setBuyNotie(String buyNotie) {
		this.buyNotie = buyNotie;
	}

}