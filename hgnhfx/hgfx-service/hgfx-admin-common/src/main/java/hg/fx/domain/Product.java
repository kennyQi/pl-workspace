package hg.fx.domain;

import java.util.Date;

import hg.demo.member.common.domain.model.def.O;
import hg.framework.common.base.BaseStringIdModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @类功能说明：商品 v0.1 只有南航里程
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yangkang
 * @创建时间：2016-5-31下午4:41:53
 * @版本：V1.0
 *
 */
@Entity
@Table(name = O.FX_TABLE_PREFIX + "PRODUCT")
public class Product extends BaseStringIdModel {

	private static final long serialVersionUID = 1L;
	
	/** 1--接口文档  */
	public static final Integer PRODUCT_TYPE_DOCUMENT = 1;
		
	/**
	 * 商品编码
	 */
	@Column(name = "PROD_CODE", length = 16)
	private String prodCode;	
	
	/**
	 * 商品名称
	 */
	@Column(name = "PROD_NAME", length = 32)
	private String prodName;	
	
	/**
	 * 商品名称
	 * 1--接口文档
	 */
	@Column(name = "TYPE", columnDefinition =O.TYPE_NUM_COLUM)
	private Integer type;	
	
	/**
	 * 文档路径
	 */
	@Column(name = "DOCUMENT_PATH", length = 1024)
	private String documentPath;
	
	/**
	 * 协议路径
	 */
	@Column(name = "AGREEMENT_PATH", length = 1024)
	private String agreementPath;
	
	/**
	 * 创建日期
	 */
	@Column(name = "CREATE_DATE", columnDefinition = O.DATE_COLUM)
	private Date createDate;
	
	/**
	 * 商品所属渠道
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CHANNEL_ID")
	private Channel channel;
	
	/**
	 * 商品订单模板表头字段
	 * */
	@Column(name = "EXCEL_HEAD_JSON", length = 1024)
	private String excelHeadJson;
	

	public String getProdCode() {
		return prodCode;
	}

	public void setProdCode(String prodCode) {
		this.prodCode = prodCode;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getDocumentPath() {
		return documentPath;
	}

	public void setDocumentPath(String documentPath) {
		this.documentPath = documentPath;
	}

	public String getAgreementPath() {
		return agreementPath;
	}

	public void setAgreementPath(String agreementPath) {
		this.agreementPath = agreementPath;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getExcelHeadJson() {
		return excelHeadJson;
	}

	public void setExcelHeadJson(String excelHeadJson) {
		this.excelHeadJson = excelHeadJson;
	}
	
}
