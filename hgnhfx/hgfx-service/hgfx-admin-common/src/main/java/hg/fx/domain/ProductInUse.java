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
 * @类功能说明：商户使用的商品
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yangkang
 * @创建时间：2016-5-31下午5:17:07
 * @版本：V1.0
 *
 */
@Entity
@Table(name = O.FX_TABLE_PREFIX + "PRODUCT_IN_USE")
public class ProductInUse extends BaseStringIdModel {

	private static final long serialVersionUID = 1L;
	
	/** 0--试用中 */
	public static final Integer STATUS_OF_ON_TRIAL  = 0;
	/** 1--申请中 */
	public static final Integer STATUS_OF_APPLYING = 1;
	/** 2--使用中 */
	public static final Integer STATUS_OF_IN_USE = 2;
	/** 3--已拒绝  */
	public static final Integer STATUS_OF_REFUSE = 3;
	/** 4--停用中 */
	public static final Integer STATUS_OF_DISABLE = 4;
	
	
	/**
	 * 使用状态
	 * 0--试用中  
	 * 1--申请中  
	 * 2--使用中  
	 * 3--已拒绝  
	 * 4--停用中
	 */
	@Column(name = "STATUS", columnDefinition = O.TYPE_NUM_COLUM)
	private Integer status;
	
	/**
	 * 被使用的商品
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PROD_ID")
	private Product product;
	
	/**
	 * 使用的商户
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "DISTRIBUTOR_ID")
	private Distributor distributor;
	
	/**
	 * 联系QQ
	 */
	@Column(name = "QQ", length = 16)
	private String qq;
	
	/**
	 * 联系电话  手机、座机都可以
	 */
	@Column(name = "PHONE", length = 16)
	private String phone;
	
	/**
	 * 试用时间
	 */
	@Column(name = "TRIAL_DATE", columnDefinition = O.DATE_COLUM)
	private Date trialDate;
	
	/**
	 * 启用时间
	 */
	@Column(name = "USE_DATE", columnDefinition = O.DATE_COLUM)
	private Date useDate;
	
	/**
	 * 正式申请时盖了公章的电子协议
	 */
	@Column(name = "AGREEMENT_PATH", length = 1024)
	private String agreementPath;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getTrialDate() {
		return trialDate;
	}

	public void setTrialDate(Date trialDate) {
		this.trialDate = trialDate;
	}

	public Date getUseDate() {
		return useDate;
	}

	public void setUseDate(Date useDate) {
		this.useDate = useDate;
	}

	public String getAgreementPath() {
		return agreementPath;
	}

	public void setAgreementPath(String agreementPath) {
		this.agreementPath = agreementPath;
	}

	public Distributor getDistributor() {
		return distributor;
	}

	public void setDistributor(Distributor distributor) {
		this.distributor = distributor;
	}
	
}
