package lxs.domain.model.app;

import hg.common.component.BaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 * @类功能说明：功能介绍
 * @类修改者：
 * @修改日期：2015年9月14日上午9:26:17
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：jinyy
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX_APP + "FunctionIntroduction")
public class FunctionIntroduction extends BaseModel {
	/**
	 * 版本号
	 */

	@Column(name = "versionnumber", length = 50)
	private String versionNumber;

	/**
	 * 版本说明
	 */
	@Column(name = "versioncontent", columnDefinition = "LONGTEXT")
	private String versionContent;

	public String getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}

	public String getVersionContent() {
		return versionContent;
	}

	public void setVersionContent(String versionContent) {
		this.versionContent = versionContent;
	}

}
