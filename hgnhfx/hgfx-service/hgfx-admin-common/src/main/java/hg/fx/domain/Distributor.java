package hg.fx.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;




import hg.demo.member.common.domain.model.def.O;
import hg.framework.common.base.BaseStringIdModel;
import hg.fx.command.distributor.CreateDistributorCommand;
import hg.fx.command.distributor.ModifyDistributorCommand;
import hg.framework.common.util.UUIDGenerator;
/**
 * 商户
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = O.FX_TABLE_PREFIX + "DISTRIBUTOR")
public class Distributor extends BaseStringIdModel {
	/** 审核状态   1--已通过*/
	public static final int CHECK_STATUS_PASS = 1;

	/** 审核状态  -1--已拒绝  */
	public static final int CHECK_STATUS_REFUSE = -1;
	
	/** 审核状态     0--待审核 */
	public static final int CHECK_STATUS_WATTING = 0;
	
	/** 启用 */
	public static final Integer STATUS_OF_IN_USE = 1;
	/** 禁用 */
	public static final Integer STATUS_OF_DISABLE = 0;
	
	/** 折扣--定价模式 */
	public static final int DISCOUNT_TYPE_FIXED_PRICE = 1;
	
	/** 折扣--返利模式 */
	public static final int DISCOUNT_TYPE_REBATE = 2;
	
	
	/**
	 * 公司名称
	 */
	@Column(name="COMPANY_NAME", length = 256)
	private String name;

	/**
	 * 创建日期
	 */
	@Column(name = "CREATE_DATE", columnDefinition = O.DATE_COLUM)
	private Date createDate;

	/**
	 * 分销商编号
	 */
	@Column(name = "MER_CODE", length = 32)
	private String code;

	/**
	 * 联系人
	 */
	@Column(name = "LINK_MAN", length = 32)
	private String linkMan;

	/**
	 * 联系电话(手机号)
	 */
	@Column(name = "MOBILE", length = 16)
	private String phone;

	/**
	 * 使用状态 
	 * 0--禁用  
	 * 1--启用
	 */
	@Column(name = "STATUS", columnDefinition =O.NUM_COLUM)
	private Integer status;

	/**
	 * 公司网址
	 * @param command
	 */
	@Column(name="WEB_SITE", length = 1024)
	private String webSite;
	/**
	 * 商户首字母
	 * 商户名字第一个字符如是数字或其他符合的 统一用保存@
	 */
	@Column(name="FIRST_LETTER", length = 8)
	private String firstLetter;
	/**
	 * API签名KEY
	 * 
	 */
	@Column(name="SIGN_KEY", length = 64)
	private String signKey;
	
	/**
	 * 使用的商品数量
	 */
	@Column(name="PROD_NUM", columnDefinition = O.NUM_COLUM)
	private Integer prodNum;
	
	/**
	 *  审核状态  
	 *  -1--已拒绝  
	 *  0--待审核  
	 *  1--已通过
	 */
	@Column(name="CHECK_STATUS", columnDefinition = O.TYPE_NUM_COLUM)
	private Integer checkStatus;
	
	/**
	 * 商户里程余额信息
	 */
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "RESERVEINFO_ID")
	private ReserveInfo reserveInfo;
	
	/**
	 * 折扣类型
	 * 1--定价模式
	 * 2--返利模式
	 */
	@Column(name = "DISCOUNT_TYPE", columnDefinition = O.TYPE_NUM_COLUM)
	private int discountType;
	
	
	final public static int CHECKSTATUS_CHECKING=0,
			CHECKSTATUS_PASS=1,
			CHECKSTATUS_NO_PASS=-1;
	
	public void create(CreateDistributorCommand command) {
		createDate = new Date();
		status = CHECK_STATUS_PASS;
		setName(command.getCompanyName());
		phone = command.getPhone();
		linkMan = command.getLinkMan();

		
		code = command.getCode();
		setId(UUIDGenerator.getUUID());
	}

	public void modify(ModifyDistributorCommand command) {
		setName( command.getName() );
		setLinkMan( command.getLinkMan() );
		setPhone( command.getPhone() );
		
	}


	public int getDiscountType() {
		return discountType;
	}

	public void setDiscountType(int discountType) {
		this.discountType = discountType;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public String getCode() {
		return code;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public String getPhone() {
		return phone;
	}

	public Integer getStatus() {
		return status;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public String getFirstLetter() {
		return firstLetter;
	}

	public void setFirstLetter(String firstLetter) {
		this.firstLetter = firstLetter;
	}

	public String getSignKey() {
		return signKey;
	}

	public void setSignKey(String signKey) {
		this.signKey = signKey;
	}

	public Integer getProdNum() {
		return prodNum;
	}

	public void setProdNum(Integer prodNum) {
		this.prodNum = prodNum;
	}

	public Integer getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(Integer checkStatus) {
		this.checkStatus = checkStatus;
	}

	public ReserveInfo getReserveInfo() {
		return reserveInfo;
	}

	public void setReserveInfo(ReserveInfo reserveInfo) {
		this.reserveInfo = reserveInfo;
	}
	
}
