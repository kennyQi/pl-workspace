package hg.fx.command.importHistory;

import java.util.Date;

import javax.persistence.Column;

import hg.framework.common.base.BaseSPICommand;

/**
 * @类功能说明：创建导入记录命令
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称： 浙江汇购科技有限公司
 * @部门： 技术部
 * @作者： zhouwei
 * @创建时间： 2016年6月1日 下午3:04:20
 * @版本： V1.0
 */
public class CreateImportHistoryCommand extends BaseSPICommand {

	private static final long serialVersionUID = 1L;

	/**
	 * 导入时间
	 */
	private Date importDate;

	/**
	 * 保存文件名
	 */
	private String fileName;

	/**
	 * 保存文件路径
	 */
	private String filePath;

	/**
	 * 导入人id
	 */
	private String dstributorUserId;

	/**
	 * 文件中正常订单数
	 */
	private Integer normalNum;

	/**
	 * 文件中异常订单数
	 */
	private Integer errorNUM;

	/**
	 * 正常订单总里程数
	 */
	private Long normalMileages;

	/**
	 * 异常订单总里程数
	 */
	private Long errorMileages;

	/**
	 * 文件业务内容MD5串
	 */
	private String contentMD5;

	public String getContentMD5() {
		return contentMD5;
	}

	public void setContentMD5(String contentMD5) {
		this.contentMD5 = contentMD5;
	}

	public Date getImportDate() {
		return importDate;
	}

	public void setImportDate(Date importDate) {
		this.importDate = importDate;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getDstributorUserId() {
		return dstributorUserId;
	}

	public void setDstributorUserId(String dstributorUserId) {
		this.dstributorUserId = dstributorUserId;
	}

	public Integer getNormalNum() {
		return normalNum;
	}

	public void setNormalNum(Integer normalNum) {
		this.normalNum = normalNum;
	}

	public Integer getErrorNUM() {
		return errorNUM;
	}

	public void setErrorNUM(Integer errorNUM) {
		this.errorNUM = errorNUM;
	}

	public Long getNormalMileages() {
		return normalMileages;
	}

	public void setNormalMileages(Long normalMileages) {
		this.normalMileages = normalMileages;
	}

	public Long getErrorMileages() {
		return errorMileages;
	}

	public void setErrorMileages(Long errorMileages) {
		this.errorMileages = errorMileages;
	}

}
