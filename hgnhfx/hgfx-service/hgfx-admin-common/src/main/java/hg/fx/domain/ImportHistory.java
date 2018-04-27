package hg.fx.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import hg.demo.member.common.domain.model.def.O;
import hg.framework.common.base.BaseStringIdModel;

/**
 * @类功能说明：商户订单导入历史
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yangkang
 * @创建时间：2016-5-31下午7:28:18
 * @版本：V1.0
 *
 */
@Entity
@Table(name = O.FX_TABLE_PREFIX + "IMPORT_HISTORY")
public class ImportHistory extends BaseStringIdModel {

	private static final long serialVersionUID = 1L;

	/**
	 * 记录所属商户
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "DISTRIBUTOR_ID")
	private Distributor distributor;
	
	/**
	 * 导入时间
	 */
	@Column(name = "IMPORT_DATE", columnDefinition = O.DATE_COLUM)
	private Date importDate;
	
	/**
	 *  保存文件名
	 */
	@Column(name = "FILE_NAME", length = 1024)
	private String fileName;
	
	/**
	 *  保存文件路径
	 */
	@Column(name = "FILE_PATH", length = 1024)
	private String filePath;
	
	/**
	 * 导入人
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "DISTRIBUTOR_USER_ID")
	private DistributorUser dstributorUser;
	
	/**
	 * 文件业务内容MD5串
	 */
	@Column(name = "CONTENT_MD5", length=1024)
	private String contentMD5;
	
	/**
	 * 文件中正常订单数
	 */
	@Column(name = "NORMAL_NUM", columnDefinition = O.NUM_COLUM)
	private Integer normalNum;
	
	/**
	 * 文件中异常订单数
	 */
	@Column(name = "ERROR_NUM", columnDefinition = O.NUM_COLUM)
	private Integer errorNUM;
	
	/**
	 * 正常订单总里程数
	 */
	@Column(name = "NORMAL_MILEAGES", columnDefinition = O.NUM_COLUM)
	private Long normalMileages;
	/**
	 * 异常订单总里程数
	 * */
	@Column(name = "ERROR_MILEAGES", columnDefinition = O.NUM_COLUM)
	private Long errorMileages;
	
	
	public Distributor getDistributor() {
		return distributor;
	}
	public void setDistributor(Distributor distributor) {
		this.distributor = distributor;
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
	public DistributorUser getDstributorUser() {
		return dstributorUser;
	}
	public void setDstributorUser(DistributorUser dstributorUser) {
		this.dstributorUser = dstributorUser;
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
	public String getContentMD5() {
		return contentMD5;
	}
	public void setContentMD5(String contentMD5) {
		this.contentMD5 = contentMD5;
	}

	
}
