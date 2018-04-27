package hg.dzpw.domain.model.scenicspot;

import hg.common.component.BaseModel;
import hg.common.util.JSONUtils;
import hg.common.util.MD5HashUtil;
import hg.common.util.UUIDGenerator;
import hg.dzpw.domain.model.M;
import hg.dzpw.pojo.command.merchant.scenicspot.ModifyLoginScenicspotCommand;
import hg.dzpw.pojo.command.merchant.scenicspot.ModifyLoginScenicspotPasswordCommand;
import hg.dzpw.pojo.command.platform.scenicspot.PlatformActivateScenicSpotCommand;
import hg.dzpw.pojo.command.platform.scenicspot.PlatformCreateScenicSpotCommand;
import hg.dzpw.pojo.command.platform.scenicspot.PlatformForbiddenScenicSpotCommand;
import hg.dzpw.pojo.command.platform.scenicspot.PlatformModifyScenicSpotCommand;
import hg.dzpw.pojo.command.platform.scenicspot.PlatformModifyTicketDefaultValidDaysCommand;
import hg.dzpw.pojo.command.platform.scenicspot.PlatformRemoveScenicSpotCommand;
import hg.dzpw.pojo.exception.DZPWException;
import hg.system.model.meta.Area;
import hg.system.model.meta.City;
import hg.system.model.meta.Province;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;

/**
 * @类功能说明: 入盟的景区
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenys
 * @创建时间：2014-11-10 下午4:26:50
 * @版本：V1.0
 */
@Entity
@DynamicUpdate
@Table(name = M.TABLE_PREFIX + "SCENIC_SPOT")
public class ScenicSpot extends BaseModel {
	private static final long serialVersionUID = 1L;

	/**
	 * 景区基本信息
	 */
	@Embedded
	private ScenicSpotBaseInfo baseInfo;

	/**
	 * 景区联系信息
	 */
	@Embedded
	private ScenicSpotContactInfo contactInfo;

	/**
	 * 景区超级管理员帐号信息
	 */
	@Embedded
	private ScenicSpotSuperAdminInfo superAdmin;

	/**
	 * 景区资质信息
	 */
	@Embedded
	private ScenicSpotCertificateInfo certificateInfo;

	/**
	 * 景区状态
	 */
	@Embedded
	private ScenicSpotStatus status;
	
	/**
	 * 结算信息
	 */
	@Embedded
	private ScenicSpotSettleInfo settleInfo;

	/**
	 * 套票数（联票数）
	 */
	@Column(name = "GROUP_TICKET_NUMBER", columnDefinition = M.NUM_COLUM)
	private Integer groupTicketNumber = 0;

	/**
	 * 是否需要手动确认证件并核销
	 */
	@Type(type = "yes_no")
	@Column(name = "MANUAL_CHECK_CERTIFICATE")
	private Boolean manualCheckCertificate = false;

	/**
	 * 设备码
	 */
	@OneToMany(mappedBy = "scenicSpot")
	private List<ClientDevice> devices;
	
	/**
	 * 景区图片
	 */
	@OneToMany(mappedBy = "scenicSpot")
	private List<ScenicSpotPic> pics;

	
	/**
	 * @throws DZPWException
	 * @方法功能说明：创建景区
	 * @修改者名字：zhurz
	 * @修改时间：2014-11-12下午3:23:52
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void create(PlatformCreateScenicSpotCommand command, Province province,
			City city, Area area) throws DZPWException {

		// 字段检查
		String errorMessage = null;
		if (command.getAdminLoginName() == null)
			errorMessage = "登录账户不可为空";
		else if (command.getAdminLoginName().trim().length() < 4
				|| command.getAdminLoginName().trim().length() > 20)
			errorMessage = "登录账户长度为4-20";
		else if (!command.getAdminLoginName().matches("[\\w]+"))
			errorMessage = "登录账户只能为下划线、数字和英文字母组成";
		else if (StringUtils.isBlank(command.getAdminPassword()))
			errorMessage = "登录密码不能为空";
		else if (command.getAdminPassword().length() < 6
				|| command.getAdminPassword().length() > 20)
			errorMessage = "登录密码长度为6-20";
		else if (!command.getAdminPassword().matches("[0-9A-Za-z]+"))
			errorMessage = "登录密码只能为数字和英文字母组成";
		else if (StringUtils.isBlank(command.getName()))
			errorMessage = "景区名称不能为空";
		else if (StringUtils.isBlank(command.getShortName()))
			errorMessage = "景区简称不能为空";
//		else if (StringUtils.isBlank(command.getCode()))
//			errorMessage = "景区代码不能为空";
		else if (province == null)
			errorMessage = "所在地：请选择省";
		else if (city == null)
			errorMessage = "所在地：所在市不属于选择的省或为空";
		else if (area == null)
			errorMessage = "所在地：所在区不属于选择的市或为空";
		else if (StringUtils.isBlank(command.getIntro()))
			errorMessage = "景区介绍不能为空";
		else if (command.getIntro().trim().length() > 500)
			errorMessage = "景区介绍不能超过500字";
		else if (StringUtils.isBlank(command.getLinkMan()))
			errorMessage = "联系人不能为空";
		else if (StringUtils.isBlank(command.getTelephone()))
			errorMessage = "联系电话不能为空";
		else if (StringUtils.isBlank(command.getStreet()))
			errorMessage = "景区详细地址不能为空";
		else if (StringUtils.isBlank(command.getBusinessLicense()))
			errorMessage = "请上传营业执照";
		else if (StringUtils.isBlank(command.getTaxRegistrationCertificate()))
			errorMessage = "请上传税务登记证";
		else if (StringUtils.isBlank(command.getOrganizationCodeCertificate()))
			errorMessage = "请上传组织代码证";
		else if (StringUtils.isBlank(command.getCorporateIDCard()))
			errorMessage = "请上传法人身份证";
		else if (StringUtils.isBlank(command.getPreNotice()))
			errorMessage = "预定须知不能为空";
		else if(StringUtils.isBlank(command.getTraffic()))
			errorMessage = "交通指南不能空";
		else if (StringUtils.isBlank(command.getBuildLevel())) 
			errorMessage = "建筑部等级不能为空";
		else if (StringUtils.isBlank(command.getJob()))
			errorMessage = "联系人职位不能为空";
		if (errorMessage != null)
			throw new DZPWException(
					DZPWException.SCENICSPOT_COMMAND_FIELD_NOT_REQUIRE,
					errorMessage);

		// -----------------------------------------------------

		setId(UUIDGenerator.getUUID());
		// 登录账户
		getSuperAdmin().setAdminLoginName(command.getAdminLoginName());
		// 登录密码
		getSuperAdmin().setAdminPassword(
				MD5HashUtil.toMD5(command.getAdminPassword()));
		// 景区密钥
		getSuperAdmin().setSecretKey(command.getSecretKey());
		// 景区名称
		getBaseInfo().setName(command.getName());
		// 景区简称
		getBaseInfo().setShortName(command.getShortName());
		// 景区代码
		getBaseInfo().setCode(command.getCode());
		// 所在地 省市ID
		getBaseInfo().setProvince(province);
		getBaseInfo().setCity(city);
		getBaseInfo().setArea(area);
		getBaseInfo().setStreet(command.getStreet());
		// 景区级别
		getBaseInfo().setLevel(command.getLevel());
		// 景区网址
		getBaseInfo().setWebSite(command.getWebSite());
		// 景区介绍
		getBaseInfo().setIntro(command.getIntro());
		// 创建时间
		getBaseInfo().setCreateDate(new Date());
		// 管理员ID
		getBaseInfo().setCreateAdminId(command.getFromAdminId());
		//景区别名
		getBaseInfo().setAliasName(command.getAliasName());
		//开放时间
		getBaseInfo().setOpenTime(command.getOpenTime());
		//设置建筑级别
		getBaseInfo().setBuildLevel(command.getBuildLevel());
		//设置景区特色
		getBaseInfo().setFeature(command.getFeature());
		//设置主题类型
		if (command.getThemeValue()!=null&& command.getThemeValue().length>0) {
			getBaseInfo().setTheme(JSONUtils.c(command.getThemeValue()));
		}
		//预定须知
		getBaseInfo().setPreNotice(command.getPreNotice());
		// ----------------------结算信息-------------------------
		// 支付类型
		getSettleInfo().setAccountType(command.getAccountType());
		// 汇金宝用户编号
		getSettleInfo().setCstNo(command.getCstNo());
		// 汇金宝操作员编码
		getSettleInfo().setOperatorNo(command.getOperatorNo());
		//银行账户
		getSettleInfo().setDepositAccount(command.getDepositAccount());
		//开户银行
		getSettleInfo().setDepositBank(command.getDepositBank());
		//开户单位
		getSettleInfo().setDepositOrg(command.getDepositOrg());
		//手续费
		getSettleInfo().setSettlementFee(command.getSettlementFee());
		
		// --------------------- 景区联系信息 ---------------------
		// 联系人
		getContactInfo().setLinkMan(command.getLinkMan());
		//职位
		getContactInfo().setJob(command.getJob());
		// 联系电话
		getContactInfo().setTelephone(command.getTelephone());
		// 联系邮箱
		getContactInfo().setEmail(command.getEmail());
		// 联系QQ
		getContactInfo().setQq(command.getQq());
		// 公司传真
		getContactInfo().setFax(command.getFax());
		// 联系地址
		getContactInfo().setAddress(command.getAddress());
		// 邮政编码
		getContactInfo().setPostcode(command.getPostcode());
		//交通指南
		getContactInfo().setTraffic(command.getTraffic());
		//百度纬度
		getContactInfo().setLatitude(command.getLatitude());
		//百度经度
		getContactInfo().setLongitude(command.getLongitude());
		// --------------------- 资质信息 ---------------------
		// 营业执照
		getCertificateInfo().setBusinessLicense(command.getBusinessLicense());
		// 税务登记证
		getCertificateInfo().setTaxRegistrationCertificate(
				command.getTaxRegistrationCertificate());
		// 组织代码证
		getCertificateInfo().setOrganizationCodeCertificate(
				command.getOrganizationCodeCertificate());
		// 法人身份证
		getCertificateInfo().setCorporateIDCard(command.getCorporateIDCard());
		// 企业logo
		getCertificateInfo().setScenicSpotLogo(command.getScenicSpotLogo());
		//状态
		getStatus();
		// --------------------- 设备编号 ---------------------
		// 编号ID
		List<ClientDevice> devices = new ArrayList<ClientDevice>();
		setDevices(devices);
		if (command.getDeviceIds() != null) {
			for (String deviceId : command.getDeviceIds()) {
				ClientDevice device = new ClientDevice();
				device.setId(deviceId);
				device.setScenicSpot(this);
				devices.add(device);
			}
		}
	}

	/**
	 * @throws DZPWException
	 * @方法功能说明：修改景区
	 * @修改者名字：zhurz
	 * @修改时间：2014-11-12下午3:24:20
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void modify(PlatformModifyScenicSpotCommand command, Province province,
			City city, Area area) throws DZPWException {

		// 字段检查
		String errorMessage = null;
		if (command.getAdminLoginName() == null)
			errorMessage = "登录账户不可为空";
		else if (command.getAdminLoginName().trim().length() < 4
				|| command.getAdminLoginName().trim().length() > 20)
			errorMessage = "登录账户长度为4-20";
		else if (!command.getAdminLoginName().matches("[\\w]+"))
			errorMessage = "登录账户只能为下划线、数字和英文字母组成";
		else if (StringUtils.isBlank(command.getName()))
			errorMessage = "景区名称不能为空";
		else if (StringUtils.isBlank(command.getShortName()))
			errorMessage = "景区简称不能为空";
		else if (province == null)
			errorMessage = "所在地：请选择省";
		else if (city == null)
			errorMessage = "所在地：所在市不属于选择的省或为空";
		else if (StringUtils.isBlank(command.getLinkMan()))
			errorMessage = "联系人不能为空";
		else if (StringUtils.isBlank(command.getTelephone()))
			errorMessage = "联系电话不能为空";
		else if (StringUtils.isBlank(command.getStreet()))
			errorMessage = "景区详细地址不能为空";
		else if (StringUtils.isBlank(command.getBusinessLicense()))
			errorMessage = "请上传营业执照";
		else if (StringUtils.isBlank(command.getTaxRegistrationCertificate()))
			errorMessage = "请上传税务登记证";
		else if (StringUtils.isBlank(command.getOrganizationCodeCertificate()))
			errorMessage = "请上传组织代码证";
		else if (StringUtils.isBlank(command.getCorporateIDCard()))
			errorMessage = "请上传法人身份证";
//		else if (command.getAccountType()==null) 
//			errorMessage = "支付账户类型不能为空";
		else if (StringUtils.isBlank(command.getPreNotice()))
			errorMessage = "预定须知不能为空";
		else if(StringUtils.isBlank(command.getTraffic()))
			errorMessage = "交通指南不能空";
		else if (StringUtils.isBlank(command.getBuildLevel())) 
			errorMessage = "建筑部等级不能为空";
		else if (StringUtils.isBlank(command.getJob()))
			errorMessage = "联系人职位不能为空";
		/**
		else if(command.getAccountType()!=null){
			if (command.getAccountType()==1&&StringUtils.isBlank(command.getCstNo())) {
				errorMessage = "未取得汇金宝用户编号";
			}else if(command.getAccountType()==1&&StringUtils.isBlank(command.getOperatorNo())){
				errorMessage = "未取得汇金宝操作员编码";
			}else if(command.getAccountType()==2){
				//TODO 支付宝账户
			}
			
		}
		*/
		if (errorMessage != null)
			throw new DZPWException(
					DZPWException.SCENICSPOT_COMMAND_FIELD_NOT_REQUIRE,
					errorMessage);

		// -----------------------------------------------------

		// 登录账户
//		getSuperAdmin().setAdminLoginName(command.getAdminLoginName());
		// 登录密码
		if(StringUtils.isNotBlank(command.getAdminPassword()))
			getSuperAdmin().setAdminPassword(MD5HashUtil.toMD5(command.getAdminPassword()));
		// 景区密钥
//		getSuperAdmin().setSecretKey(command.getSecretKey());
		// 景区名称
		getBaseInfo().setName(command.getName());
		// 景区简称
		getBaseInfo().setShortName(command.getShortName());
		// 所在地 省市ID
		getBaseInfo().setProvince(province);
		getBaseInfo().setCity(city);
		getBaseInfo().setArea(area);
		getBaseInfo().setStreet(command.getStreet());
		// 景区级别
		getBaseInfo().setLevel(command.getLevel());
		// 景区网址
		getBaseInfo().setWebSite(command.getWebSite());
		// 景区介绍
		getBaseInfo().setIntro(command.getIntro());
		//景区别名
		getBaseInfo().setAliasName(command.getAliasName());
		//开放时间
		getBaseInfo().setOpenTime(command.getOpenTime());
		//设置建筑级别
		getBaseInfo().setBuildLevel(command.getBuildLevel());
		//设置景区特色
		getBaseInfo().setFeature(command.getFeature());
		//设置主题类型
		if (command.getThemeValue()!=null&& command.getThemeValue().length>0) {
			getBaseInfo().setTheme(JSONUtils.c(command.getThemeValue()));
		}
		//预定须知
		getBaseInfo().setPreNotice(command.getPreNotice());
		// --------------------- 景区联系信息 ---------------------
		// 联系人
		getContactInfo().setLinkMan(command.getLinkMan());
		//联系人职位
		getContactInfo().setJob(command.getJob());
		// 联系电话
		getContactInfo().setTelephone(command.getTelephone());
		// 联系邮箱
		getContactInfo().setEmail(command.getEmail());
		// 联系QQ
		getContactInfo().setQq(command.getQq());
		// 公司传真
		getContactInfo().setFax(command.getFax());
		// 联系地址
		getContactInfo().setAddress(command.getAddress());
		// 邮政编码
		getContactInfo().setPostcode(command.getPostcode());
		//交通指南
		getContactInfo().setTraffic(command.getTraffic());
		//百度纬度
		getContactInfo().setLatitude(command.getLatitude());
		//百度经度
		getContactInfo().setLongitude(command.getLongitude());
		// ----------------------结算信息-------------------------
		// 支付类型
		getSettleInfo().setAccountType(command.getAccountType());
		// 汇金宝用户编号
		getSettleInfo().setCstNo(command.getCstNo());
		// 汇金宝操作员编码
		getSettleInfo().setOperatorNo(command.getOperatorNo());
		//银行账户
		getSettleInfo().setDepositAccount(command.getDepositAccount());
		//开户银行
		getSettleInfo().setDepositBank(command.getDepositBank());
		//开户单位
		getSettleInfo().setDepositOrg(command.getDepositOrg());
		//手续费
		getSettleInfo().setSettlementFee(command.getSettlementFee());
		// --------------------- 资质信息 ---------------------
		// 营业执照
		getCertificateInfo().setBusinessLicense(command.getBusinessLicense());
		// 税务登记证
		getCertificateInfo().setTaxRegistrationCertificate(
				command.getTaxRegistrationCertificate());
		// 组织代码证
		getCertificateInfo().setOrganizationCodeCertificate(
				command.getOrganizationCodeCertificate());
		// 法人身份证
		getCertificateInfo().setCorporateIDCard(command.getCorporateIDCard());
		// 企业logo
		getCertificateInfo().setScenicSpotLogo(command.getScenicSpotLogo());
		// --------------------- 设备编号 ---------------------
		// 编号ID
		List<ClientDevice> devices = getDevices();
		Map<String, ClientDevice> map = new HashMap<String, ClientDevice>();
		for (ClientDevice d : devices) {
			map.put(d.getId(), d);
		}
		devices.clear();
		if (command.getDeviceIds() != null) {
			for (String deviceId : command.getDeviceIds()) {
				if (map.containsKey(deviceId))
					devices.add(map.get(deviceId));
				else {
					ClientDevice device = new ClientDevice();
					device.setId(deviceId);
					device.setScenicSpot(this);
					devices.add(device);
				}
			}
		}
	}

	/**
	 * @方法功能说明：禁用景区
	 * @修改者名字：zhurz
	 * @修改时间：2014-11-12下午3:24:26
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void forbidden(PlatformForbiddenScenicSpotCommand command) {
		getStatus().setActivated(false);
	}

	/**
	 * @方法功能说明：启用景区
	 * @修改者名字：zhurz
	 * @修改时间：2014-11-12下午3:24:48
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void activate(PlatformActivateScenicSpotCommand command) {
		getStatus().setActivated(true);
	}

	/**
	 * @throws DZPWException
	 * @方法功能说明：逻辑删除
	 * @修改者名字：zhurz
	 * @修改时间：2014-11-12下午3:24:56
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void remove(PlatformRemoveScenicSpotCommand command) throws DZPWException {
		if (getGroupTicketNumber() > 0)
			throw new DZPWException(
					DZPWException.SCENICSPOT_USED_CANNOT_REMOVE, String.format(
							"景区(%s)还有联票在使用无法被删除", getBaseInfo().getName()));
		getStatus().setRemoved(true);
	}

	/**
	 * @方法功能说明：
	 * @修改者名字：zhurz
	 * @修改时间：2014-11-12下午5:54:02
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void modifyTicketDefaultValidDays(
			PlatformModifyTicketDefaultValidDaysCommand command) {
		if (command.getDays() == null || command.getDays() < 1)
			return;
		getBaseInfo().setTicketDefaultValidDays(command.getDays());
	}
	
	/**
	 * @throws DZPWException 
	 * @方法功能说明：修改登录景区密码
	 * @修改者名字：zhurz
	 * @修改时间：2015-2-11下午3:24:38
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void modifyLoginScenicspotPassword(ModifyLoginScenicspotPasswordCommand command) throws DZPWException {
		if (StringUtils.isBlank(command.getOldpwd()))
			throw new DZPWException(DZPWException.MESSAGE_ONLY, "缺少原始密码");
		if (StringUtils.isBlank(command.getNewPwd()))
			throw new DZPWException(DZPWException.MESSAGE_ONLY, "缺少新密码");
		if (StringUtils.isBlank(command.getReNewPwd()))
			throw new DZPWException(DZPWException.MESSAGE_ONLY, "缺少重新输入的新密码");
		if (command.getNewPwd().length() < 6 || command.getNewPwd().length() > 20)
			throw new DZPWException(DZPWException.MESSAGE_ONLY, "登录密码长度为6-20");
		if (!command.getNewPwd().matches("[0-9A-Za-z]+"))
			throw new DZPWException(DZPWException.MESSAGE_ONLY, "登录密码只能为数字和英文字母组成");
		if (!StringUtils.equals(command.getNewPwd(), command.getReNewPwd()))
			throw new RuntimeException("两次输入的新密码不一致");
		if (!StringUtils.equalsIgnoreCase(DigestUtils.md5Hex(command.getOldpwd()), getSuperAdmin().getAdminPassword()))
			throw new RuntimeException("原始密码错误");
		if (StringUtils.equals(command.getOldpwd(), command.getNewPwd()))
			throw new RuntimeException("新密码不能和原始密码相同");
		getSuperAdmin().setAdminPassword(DigestUtils.md5Hex(command.getNewPwd()));
	}
	
	/**
	 * @方法功能说明：修改当前登录的景区信息
	 * @修改者名字：zhurz
	 * @修改时间：2015-2-11下午4:05:43
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@param province
	 * @参数：@param city
	 * @参数：@param area
	 * @参数：@throws DZPWException
	 * @return:void
	 * @throws
	 */
	public void modifyLoginScenicspot(ModifyLoginScenicspotCommand command, 
			Province province, City city, Area area) throws DZPWException {

		// 字段检查
		String errorMessage = null;
//		if (command.getClassify() == null || !DZPWConstants.SCENICSPOT_TYPE_NAME.containsKey(command.getClassify()))
//			errorMessage = "未知的景区分类";
//		else if (StringUtils.isBlank(command.getName()))
//			errorMessage = "缺少景区名称";
//		if (StringUtils.isBlank(command.getShortName()))
//			errorMessage = "缺少景区简称";
		if (StringUtils.isBlank(command.getIntro()))
			errorMessage = "缺少景区简介";
//		else if(StringUtils.isBlank(command.getSecretKey()))
//			errorMessage="缺少景区密钥";
		else if (command.getIntro().length() > 4000)
			errorMessage = "景区简介超过4000字符限制";
		else if (StringUtils.isBlank(command.getTraffic()))
			errorMessage = "缺少交通指南";
		else if (command.getTraffic().length() > 4000)
			errorMessage = "交通指南超过4000字符限制";
		else if (StringUtils.isBlank(command.getLinkMan()))
			errorMessage = "缺少联系人";
		else if (StringUtils.isBlank(command.getTelephone()))
			errorMessage = "缺少手机号";
		else if (StringUtils.isBlank(command.getEmail()))
			errorMessage = "缺少联系邮箱";
//		else if (StringUtils.isBlank(command.getJob())) {
//			errorMessage = "联系人职位不能空";
//		}
		else if (province == null)
			errorMessage = "所在地：请选择省";
		else if (city == null)
			errorMessage = "所在地：请选择市";
		else if (area == null)
			errorMessage = "所在地：请选择区";
		/*else if (command.getAccountType()==null) 
			errorMessage = "账户类型不能为空";
		else if (command.getAccountType()!=null&&command.getAccountType()==1) {
			if (StringUtils.isBlank(command.getCstNo())) 
				errorMessage = "缺少汇金宝用户编号";
			else if (StringUtils.isBlank(command.getOperatorNo())) {
				errorMessage = "缺少汇金宝操作员编号";
			}
		}*/
		else if (StringUtils.isBlank(command.getScenicSpotLogo())) {
			errorMessage = "企业LOGO图片不能为空";
		}else if (StringUtils.isBlank(command.getTaxRegistrationCertificate())) {
			errorMessage = "税务登记证图片不能为空";
		}else if (StringUtils.isBlank(command.getBusinessLicense())) {
			errorMessage = "营业执照图片不能为空";
		}else if (StringUtils.isBlank(command.getCorporateIDCard())) {
			errorMessage = "组织代码证图片不能为空";
		}else if (StringUtils.isBlank(command.getCorporateIDCard())) {
			errorMessage = "法人身份证图片不能空";
		}else if (StringUtils.isBlank(command.getFeature())) {
			errorMessage = "主题特色不能空";
		}else if (command.getThemeValue().length<1) {
			errorMessage = "至少选择一个主题类型";
		}else if (StringUtils.isBlank(command.getOpenTime())) {
			errorMessage = "开放时间不能空";
		}else if (StringUtils.isBlank(command.getPreNotice())) {
			errorMessage = "预定须知不能为空";
		}
		
		
		if (errorMessage != null)
			throw new DZPWException(
					DZPWException.SCENICSPOT_COMMAND_FIELD_NOT_REQUIRE,
					errorMessage);

		// -----------------------------------------------------

		// 景区分类
//		getBaseInfo().setClassify(command.getClassify());
		// 景区名称
//		getBaseInfo().setName(command.getName());
		// 景区简称
//		getBaseInfo().setShortName(command.getShortName());
		// 景区网址
		getBaseInfo().setWebSite(command.getWebSite());
		// 所在地 省市区街道
		getBaseInfo().setProvince(province);
		getBaseInfo().setCity(city);
		getBaseInfo().setArea(area);
		getBaseInfo().setStreet(command.getStreet());
		// 景区简介
		getBaseInfo().setIntro(command.getIntro());
		//设置景区特色
		getBaseInfo().setFeature(command.getFeature());
		//设置主题类型
		if (command.getThemeValue()!=null&& command.getThemeValue().length>0) {
			getBaseInfo().setTheme(JSONUtils.c(command.getThemeValue()));
		}
		//预定须知
		getBaseInfo().setPreNotice(command.getPreNotice());
		//开放时间
		getBaseInfo().setOpenTime(command.getOpenTime());
		//预定须知
		getBaseInfo().setPreNotice(command.getPreNotice());
		// 交通指南
		getContactInfo().setTraffic(command.getTraffic());
		// --------------------- 景区联系信息 ---------------------
		// 联系人
		getContactInfo().setLinkMan(command.getLinkMan());
		// 手机号
		getContactInfo().setTelephone(command.getTelephone());
		// 联系邮箱
		getContactInfo().setEmail(command.getEmail());
		//联系人职位
		getContactInfo().setJob(command.getJob());
		//纬度
		getContactInfo().setLatitude(command.getLatitude());
		//经度
		getContactInfo().setLongitude(command.getLongitude());
		//密钥
//		getSuperAdmin().setSecretKey(command.getSecretKey());
		//用户编号
//		getSettleInfo().setCstNo(command.getCstNo());
		//操作员编号
//		getSettleInfo().setOperatorNo(command.getOperatorNo());
		//支付账户类型
//		getSettleInfo().setAccountType(command.getAccountType());
		//开户行
//		getSettleInfo().setDepositBank(command.getDepositBank());
		//一行账户
//		getSettleInfo().setDepositAccount(command.getDepositAccount());
		//开户单位
//		getSettleInfo().setDepositOrg(command.getDepositOrg());
		//手续费费率
//		getCertificateInfo().setSettlementFee(command.getSettlementFee());
		//企业LOGO
		getCertificateInfo().setScenicSpotLogo(command.getScenicSpotLogo());
		//法人身份证
		getCertificateInfo().setCorporateIDCard(command.getCorporateIDCard());
		//组织代码证
		getCertificateInfo().setOrganizationCodeCertificate(command.getOrganizationCodeCertificate());
		//营业执照
		getCertificateInfo().setBusinessLicense(command.getBusinessLicense());
		//税务登记证
		getCertificateInfo().setTaxRegistrationCertificate(command.getTaxRegistrationCertificate());
		
	}

	public ScenicSpotBaseInfo getBaseInfo() {
		if (baseInfo == null)
			baseInfo = new ScenicSpotBaseInfo();
		return baseInfo;
	}

	public void setBaseInfo(ScenicSpotBaseInfo baseInfo) {
		this.baseInfo = baseInfo;
	}

	public ScenicSpotContactInfo getContactInfo() {
		if (contactInfo == null)
			contactInfo = new ScenicSpotContactInfo();
		return contactInfo;
	}

	public void setContactInfo(ScenicSpotContactInfo contactInfo) {
		this.contactInfo = contactInfo;
	}

	public ScenicSpotSuperAdminInfo getSuperAdmin() {
		if (superAdmin == null)
			superAdmin = new ScenicSpotSuperAdminInfo();
		return superAdmin;
	}

	public void setSuperAdmin(ScenicSpotSuperAdminInfo superAdmin) {
		this.superAdmin = superAdmin;
	}

	public ScenicSpotCertificateInfo getCertificateInfo() {
		if (certificateInfo == null)
			certificateInfo = new ScenicSpotCertificateInfo();
		return certificateInfo;
	}

	public void setCertificateInfo(ScenicSpotCertificateInfo certificateInfo) {
		this.certificateInfo = certificateInfo;
	}

	public ScenicSpotStatus getStatus() {
		if (status == null)
			status = new ScenicSpotStatus();
		return status;
	}

	public void setStatus(ScenicSpotStatus status) {
		this.status = status;
	}

	public Integer getGroupTicketNumber() {
		return groupTicketNumber;
	}

	public void setGroupTicketNumber(Integer groupTicketNumber) {
		this.groupTicketNumber = groupTicketNumber;
	}

	public List<ClientDevice> getDevices() {
		if (devices == null)
			devices = new ArrayList<ClientDevice>();
		return devices;
	}

	public void setDevices(List<ClientDevice> devices) {
		this.devices = devices;
	}

	public Boolean getManualCheckCertificate() {
		if (manualCheckCertificate == null)
			manualCheckCertificate = false;
		return manualCheckCertificate;
	}

	public void setManualCheckCertificate(Boolean manualCheckCertificate) {
		this.manualCheckCertificate = manualCheckCertificate;
	}

	public ScenicSpotSettleInfo getSettleInfo() {
		if (settleInfo==null) {
			settleInfo=new ScenicSpotSettleInfo();
		}
		return settleInfo;
	}

	public void setSettleInfo(ScenicSpotSettleInfo settleInfo) {
		this.settleInfo = settleInfo;
	}

	public List<ScenicSpotPic> getPics() {
		if (pics==null) {
			pics=new ArrayList<ScenicSpotPic>();
		}
		return pics;
	}

	public void setPics(List<ScenicSpotPic> pics) {
		this.pics = pics;
	}

}