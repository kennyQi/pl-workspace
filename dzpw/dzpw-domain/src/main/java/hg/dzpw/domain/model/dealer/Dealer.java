package hg.dzpw.domain.model.dealer;

import hg.common.component.BaseModel;
import hg.common.util.MD5HashUtil;
import hg.common.util.UUIDGenerator;
import hg.dzpw.domain.model.M;
import hg.dzpw.pojo.command.platform.dealer.PlatformCreateDealerCommand;
import hg.dzpw.pojo.command.platform.dealer.PlatformModifyDealerCommand;
import hg.dzpw.pojo.exception.DZPWException;
import hg.system.model.meta.Area;
import hg.system.model.meta.City;
import hg.system.model.meta.Province;

import java.util.Date;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

/**
 * @类功能说明：经销商
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yangkang
 * @创建时间：2014-11-10下午2:10:05
 * @版本：V1.0
 */
@Entity
@Table(name = M.TABLE_PREFIX + "DEALER")
@SuppressWarnings("serial")
public class Dealer extends BaseModel {

	/**
	 * 启用状态
	 */
	public final static Integer DEALER_STATUS_USASBLE = 1;

	/**
	 * 禁用状态
	 */
	public final static Integer DEALER_STATUS_DISABLE = 0;

	/**
	 * 删除状态
	 */
	public final static Integer DEALER_STATUS_REMOVE = -1;
	
	
	/**
	 * 经销商结算类型--汇金宝账户
	 */
	public final static Integer DEALER_ACCOUNT_TYPE_HJB = 1;
	
	/**
	 * 经销商结算类型--支付宝账户
	 */
	public final static Integer DEALER_ACCOUNT_TYPE_ALIPAY = 2;
	
	/**
	 * 经销商结算类型--快付通
	 */
	public final static Integer DEALER_ACCOUNT_TYPE_KUAIFUTONG = 3;
	

	/**
	 * 经销商基础信息
	 */
	@Embedded
	private DealerBaseInfo baseInfo;

	/**
	 * 经销商客户端信息
	 */
	@Embedded
	private DealerClientInfo clientInfo;
	
	/**
	 * 经销商资质信息
	 */
	@Embedded
	private DealerCertificateInfo certificateInfo;

	/**
	 * 经销商结算信息
	 */
	@Embedded
	private DealerAccountInfo accountInfo;
	
	
	/**
	 * 经销商的景区设置
	 */
	@Transient
	private DealerScenicspotSetting setting;

	/**
	 * @throws DZPWException 
	 * @方法功能说明：运营后台创建经销商
	 * @修改者名字：zhurz
	 * @修改时间：2015-3-24下午3:31:01
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@param province
	 * @参数：@param city
	 * @参数：@param area
	 * @return:void
	 * @throws
	 */
	public void platformCreateDealer(PlatformCreateDealerCommand command,
			Province province, City city, Area area) throws DZPWException {

		if (province == null)
			throw new DZPWException(DZPWException.MESSAGE_ONLY, "缺少所在省份");
		if (city == null)
			throw new DZPWException(DZPWException.MESSAGE_ONLY, "缺少所在城市");
		if (area == null)
			throw new DZPWException(DZPWException.MESSAGE_ONLY, "缺少所在区");

		setId(UUIDGenerator.getUUID());
		getBaseInfo().setName(command.getName());
		getClientInfo().setAdminLoginName(command.getAdminLoginName());
		getClientInfo().setAdminPassword(MD5HashUtil.toMD5(command.getAdminPassword()));
		getClientInfo().setSecretKey(command.getSecretKey());
		getClientInfo().setDealerWebsite(command.getDealerWebsite());
		
		getBaseInfo().setIntro(command.getIntro());
		getCertificateInfo().setBusinessLicense(command.getBusinessLicense());
		getCertificateInfo().setTaxRegistrationCertificate(
				command.getTaxRegistrationCertificate());
		getCertificateInfo().setOrganizationCodeCertificate(
				command.getOrganizationCodeCertificate());
		getCertificateInfo().setCorporateIDCard(command.getCorporateIDCard());
		getCertificateInfo().setDealerLogo(command.getDealerLogo());
		getBaseInfo().setLinkMan(command.getLinkMan());
		getBaseInfo().setTelephone(command.getTelephone());
		getBaseInfo().setEmail(command.getEmail());
		getBaseInfo().setProvince(province);
		getBaseInfo().setCity(city);
		getBaseInfo().setArea(area);
		getBaseInfo().setAddress(command.getAddress());
		getBaseInfo().setCreateAdminId(command.getFromAdminId());
		getBaseInfo().setCreateDate(new Date());
		getBaseInfo().setStatus(1);
		
		if (command.getPublishAble() != null && command.getPublishAble()
				&& !command.getPublishUrl().matches("^http[s]{0,1}://.+$")) {
			throw new DZPWException(DZPWException.MESSAGE_ONLY, "消息推送地址错误");
		}
		getClientInfo().setPublishUrl(command.getPublishUrl());
		getClientInfo().setPublishAble(command.getPublishAble());
		
		getAccountInfo().setAccountNumber(command.getAccountNumber());
		getAccountInfo().setAccountType(command.getAccountType());
		getAccountInfo().setAdvancePayment(command.getAdvancePayment());
		getAccountInfo().setWarnBalance(command.getWarnBalance());
		getAccountInfo().setAccountType(command.getAccountType());
		getAccountInfo().setOperatorNo(command.getOperatorNo());
		getAccountInfo().setCstNo(command.getCstNo());
		getAccountInfo().setBusinessId(command.getBusinessId());
		getAccountInfo().setBusinessKey(command.getBusinessKey());
		getAccountInfo().setSettlementFee(command.getSettlementFee());
	}
	
	/**
	 * @throws DZPWException 
	 * @方法功能说明：运营后台修改经销商
	 * @修改者名字：zhurz
	 * @修改时间：2015-3-24下午3:33:09
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@param province
	 * @参数：@param city
	 * @参数：@param area
	 * @return:void
	 * @throws
	 */
	public void platformModifyDealer(PlatformModifyDealerCommand command,
			Province province, City city, Area area) throws DZPWException {

		if (province == null)
			throw new DZPWException(DZPWException.MESSAGE_ONLY, "缺少所在省份");
		if (city == null)
			throw new DZPWException(DZPWException.MESSAGE_ONLY, "缺少所在城市");
		if (area == null)
			throw new DZPWException(DZPWException.MESSAGE_ONLY, "缺少所在区");
		if (command.getAdminLoginName() == null)
			throw new DZPWException(DZPWException.MESSAGE_ONLY, "登录账户不可为空");
		else if (command.getAdminLoginName().trim().length() < 4
				|| command.getAdminLoginName().trim().length() > 20)
			throw new DZPWException(DZPWException.MESSAGE_ONLY, "登录账户长度为4-20");
		else if (!command.getAdminLoginName().matches("[\\w]+"))
			throw new DZPWException(DZPWException.MESSAGE_ONLY,"登录账户只能为下划线、数字和英文字母组成");
		
//		getBaseInfo().setName(command.getName());
//		getClientInfo().setSecretKey(command.getSecretKey());
//		getClientInfo().setAdminLoginName(command.getAdminLoginName());
		if (StringUtils.isNotBlank(command.getAdminPassword())) {
			getClientInfo().setAdminPassword(MD5HashUtil.toMD5(command.getAdminPassword()));
		}
		getBaseInfo().setIntro(command.getIntro());
		getCertificateInfo().setDealerLogo(command.getDealerLogo());
		getCertificateInfo().setBusinessLicense(command.getBusinessLicense());
		getCertificateInfo().setTaxRegistrationCertificate(
				command.getTaxRegistrationCertificate());
		getCertificateInfo().setOrganizationCodeCertificate(
				command.getOrganizationCodeCertificate());
		getCertificateInfo().setCorporateIDCard(command.getCorporateIDCard());
		getBaseInfo().setLinkMan(command.getLinkMan());
		getBaseInfo().setTelephone(command.getTelephone());
		getBaseInfo().setEmail(command.getEmail());
		getBaseInfo().setProvince(province);
		getBaseInfo().setCity(city);
		getBaseInfo().setArea(area);
		getBaseInfo().setAddress(command.getAddress());
		getClientInfo().setDealerWebsite(command.getDealerWebsite());
		
		if (command.getPublishAble() != null && command.getPublishAble()
				&& !command.getPublishUrl().matches("^http[s]{0,1}://.+$")) {
			throw new DZPWException(DZPWException.MESSAGE_ONLY, "消息推送地址错误");
		}
		getClientInfo().setPublishUrl(command.getPublishUrl());
		getClientInfo().setPublishAble(command.getPublishAble());
		
		getAccountInfo().setAccountType(command.getAccountType());
		getAccountInfo().setSettlementFee(command.getSettlementFee());
//		getAccountInfo().setCstNo(command.getCstNo());
//		getAccountInfo().setOperatorNo(command.getOperatorNo());
	}
	
	/**
	 * @方法功能说明：启用经销商
	 * @修改者名字：yangkang
	 * @修改时间：2014-11-13上午10:21:05
	 */
	public void active() {
		this.baseInfo.setStatus(Dealer.DEALER_STATUS_USASBLE);
	}

	/**
	 * @方法功能说明：禁用经销商
	 * @修改者名字：yangkang
	 * @修改时间：2014-11-13上午10:20:21
	 */
	public void fobidden() {
		this.baseInfo.setStatus(Dealer.DEALER_STATUS_DISABLE);
	}

	/**
	 * @方法功能说明：逻辑删除经销商
	 * @修改者名字：yangkang
	 * @修改时间：2014-11-13上午10:18:08
	 */
	public void remove() {
		this.getBaseInfo().setStatus(Dealer.DEALER_STATUS_REMOVE);
	}

	public DealerBaseInfo getBaseInfo() {
		if (baseInfo == null)
			baseInfo = new DealerBaseInfo();
		return baseInfo;
	}

	public void setBaseInfo(DealerBaseInfo baseInfo) {
		this.baseInfo = baseInfo;
	}

	public DealerClientInfo getClientInfo() {
		if (clientInfo == null)
			clientInfo = new DealerClientInfo();
		return clientInfo;
	}

	public void setClientInfo(DealerClientInfo clientInfo) {
		this.clientInfo = clientInfo;
	}

	public DealerScenicspotSetting getSetting() {
		if (setting == null)
			setting = new DealerScenicspotSetting();
		return setting;
	}

	public void setSetting(DealerScenicspotSetting setting) {
		this.setting = setting;
	}

	public DealerCertificateInfo getCertificateInfo() {
		if (certificateInfo == null)
			certificateInfo = new DealerCertificateInfo();
		return certificateInfo;
	}

	public void setCertificateInfo(DealerCertificateInfo certificateInfo) {
		this.certificateInfo = certificateInfo;
	}

	public DealerAccountInfo getAccountInfo() {
		if(accountInfo == null)
			accountInfo = new DealerAccountInfo();
		return accountInfo;
	}

	public void setAccountInfo(DealerAccountInfo accountInfo) {
		this.accountInfo = accountInfo;
	}

}