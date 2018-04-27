package hg.fx.spi.qo;

import java.util.Date;

import javax.persistence.Column;

import hg.framework.common.base.BaseSPIQO;

/**
 * @类功能说明：doto
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称： 浙江汇购科技有限公司
 * @部门： 技术部
 * @作者： zhouwei
 * @创建时间： 2016年6月1日 上午9:59:17
 * @版本： V1.0
 */
public class ImportHistorySQO extends BaseSPIQO {

	private static final long serialVersionUID = 1L;

	/** 导入时间 ： 开始时间(当天的传00：00的时间) */
	private Date beginImportDate;

	/** 导入时间 ： 结束时间 */
	private Date endImportDate;

	/** 导入人 */
	private String dstributorUserId;

	/** 商户ID */
	private String distributorID;

	/**
	 * 文件业务内容MD5串
	 */
	private String contentMD5;
	
	public Date getBeginImportDate() {
		return beginImportDate;
	}

	public void setBeginImportDate(Date beginImportDate) {
		this.beginImportDate = beginImportDate;
	}

	public Date getEndImportDate() {
		return endImportDate;
	}

	public void setEndImportDate(Date endImportDate) {
		this.endImportDate = endImportDate;
	}

	public String getDstributorUserId() {
		return dstributorUserId;
	}

	public void setDstributorUserId(String dstributorUserId) {
		this.dstributorUserId = dstributorUserId;
	}

	public String getDistributorID() {
		return distributorID;
	}

	public void setDistributorID(String distributorID) {
		this.distributorID = distributorID;
	}

	public String getContentMD5() {
		return contentMD5;
	}

	public void setContentMD5(String contentMD5) {
		this.contentMD5 = contentMD5;
	}

	
}
