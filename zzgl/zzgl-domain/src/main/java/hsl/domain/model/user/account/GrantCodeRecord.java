package hsl.domain.model.user.account;
import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;
import hsl.domain.model.M;
import hsl.pojo.command.account.GrantCodeRecordCommand;

import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.apache.commons.lang.StringUtils;
/**
 * @类功能说明：发放码记录
 * @类修改者：
 * @修改日期：2015年8月29日上午10:18:21
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：chenxy
 * @创建时间：2015年8月29日上午10:18:21
 */
@Entity
@Table(name=M.TABLE_PREFIX_HSL_ACCOUNT+"GRANT_CODE_RECORD")
public class GrantCodeRecord extends BaseModel{
	private static final long serialVersionUID = 1L;
	/**
	 * 所属的商业合作伙伴
	 */
	@ManyToOne(fetch=FetchType.EAGER,cascade={CascadeType.ALL})
 	@JoinColumn(name = "PARTNER_ID")
	private BusinessPartners businessPartners;
	/**
	 * 发放的充值码
	 */
	@OneToMany(fetch = FetchType.LAZY, mappedBy="grantCodeRecord",cascade={CascadeType.ALL})
    private List<PayCode> payCodes;
	
	/**
	 * 对接人
	 */
	private String dockingPerson;
	
	/**
	 * 对接人电话
	 */
	private String dockingPersonTel;
	
	/**
	 * 发放人姓名
	 */
	@Column(name="GRANTERNAME",length=64)
	private String granterName;
	/**
	 * 发放人电话
	 */
	@Column(name="GRANTERTEL",length=64)
	private String granterTel;
	/**
	 * 充值码金额
	 */
	@Column(name="PAYCODEMONEY",columnDefinition=M.MONEY_COLUM)
	private Double payCodeMoney;
	/**
	 * 充值码数量
	 */
	@Column(name="PAYCODENUM",columnDefinition=M.NUM_COLUM)
	private Integer payCodeNum;
	/**
	 * 创建日期
	 */
	@Column(name="CREATETIME",columnDefinition=M.DATE_COLUM)
	private Date createTime;
	/**
	 * 未审核 1
	 */
	public static final Integer GRANT_CODE_RECORD_NOCHECKED=1;
	/**
	 * 审核 通过2
	 */
	public static final Integer GRANT_CODE_RECORD_CHECKED=2;
	/**
	 * 作废 3
	 */
	public static final Integer GRANT_CODE_RECORD_CANCEL=3;
	/**
	 * 状态
	 */
	@Column(name="STATUS",columnDefinition=M.TYPE_NUM_COLUM)
	private Integer status;
	public BusinessPartners getBusinessPartners() {
		return businessPartners;
	}
	public void setBusinessPartners(BusinessPartners businessPartners) {
		this.businessPartners = businessPartners;
	}
	public String getGranterName() {
		return granterName;
	}
	public void setGranterName(String granterName) {
		this.granterName = granterName;
	}
	public String getGranterTel() {
		return granterTel;
	}
	public void setGranterTel(String granterTel) {
		this.granterTel = granterTel;
	}
	public Double getPayCodeMoney() {
		return payCodeMoney;
	}
	public void setPayCodeMoney(Double payCodeMoney) {
		this.payCodeMoney = payCodeMoney;
	}
	public Integer getPayCodeNum() {
		return payCodeNum;
	}
	public void setPayCodeNum(Integer payCodeNum) {
		this.payCodeNum = payCodeNum;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public List<PayCode> getPayCodes() {
		return payCodes;
	}
	public void setPayCodes(List<PayCode> payCodes) {
		this.payCodes = payCodes;
	}
	/**
	 * 创建充值码
	**/
	@SuppressWarnings("static-access")
	public void creatGrantCodeRecord(GrantCodeRecordCommand command){
		this.setId(UUIDGenerator.getUUID());
		this.setCreateTime(new Date());
		this.setGranterName(command.getGranterName());
		this.setDockingPerson(command.getDockingPerson());
		this.setDockingPersonTel(command.getDockingPersonTel());
		this.setGranterTel(command.getGranterTel());
		this.setPayCodeMoney(command.getPayCodeMoney());
		this.setPayCodeNum(command.getPayCodeNum());
		this.setStatus(this.GRANT_CODE_RECORD_NOCHECKED);
		businessPartners=new BusinessPartners();
		if(StringUtils.isBlank(command.getBusinessPartnersCommand().getId())){
			businessPartners.setId(UUIDGenerator.getUUID());
		}else{
			businessPartners.setId(command.getBusinessPartnersCommand().getId());
		}
		businessPartners.setCompanyName(command.getBusinessPartnersCommand().getCompanyName());
		businessPartners.setCompanyLinkName(command.getBusinessPartnersCommand().getCompanyLinkName());
		businessPartners.setCompanyLinkTel(command.getBusinessPartnersCommand().getCompanyLinkTel());
		businessPartners.setCreateTime(new Date());
		this.setBusinessPartners(businessPartners);
	}
	public String getDockingPerson() {
		return dockingPerson;
	}
	public void setDockingPerson(String dockingPerson) {
		this.dockingPerson = dockingPerson;
	}
	public String getDockingPersonTel() {
		return dockingPersonTel;
	}
	public void setDockingPersonTel(String dockingPersonTel) {
		this.dockingPersonTel = dockingPersonTel;
	}
	
	
	
}
