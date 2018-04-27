package hg.dzpw.merchant.controller.dealer;

import hg.common.page.Pagination;
import hg.common.util.DwzJsonResultUtil;
import hg.common.util.file.FdfsFileInfo;
import hg.common.util.file.FdfsFileUtil;
import hg.common.util.file.SimpleFileUtil;
import hg.dzpw.app.common.SystemConfig;
import hg.dzpw.app.pojo.qo.DealerQo;
import hg.dzpw.app.pojo.qo.DealerScenicspotSettingQo;
import hg.dzpw.app.service.local.DealerLocalService;
import hg.dzpw.app.service.local.DealerScenicspotSettingLocalService;
import hg.dzpw.domain.model.dealer.Dealer;
import hg.dzpw.merchant.common.utils.StringFilterUtil;
import hg.dzpw.merchant.component.manager.MerchantSessionUserManager;
import hg.dzpw.merchant.controller.BaseController;
import hg.dzpw.pojo.command.merchant.dealer.ScenicspotModifyDealerSettingCommand;
import hg.dzpw.pojo.command.platform.dealer.PlatformCreateDealerCommand;
import hg.dzpw.pojo.command.platform.dealer.PlatformModifyDealerCommand;
import hg.dzpw.pojo.exception.DZPWException;
import hg.log.util.HgLogger;
import hg.system.model.meta.Province;
import hg.system.qo.AreaQo;
import hg.system.qo.CityQo;
import hg.system.qo.ProvinceQo;
import hg.system.service.impl.AreaServiceImpl;
import hg.system.service.impl.CityServiceImpl;
import hg.system.service.impl.ProvinceServiceImpl;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @类功能说明：经销商controller
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：liusong
 * @创建时间：2014-11-11下午2:55:54
 * @版本：V1.0
 */
@Controller
public class DealerController extends BaseController {

	@Autowired
	private DealerLocalService dealerService;
	@Autowired
	private DealerScenicspotSettingLocalService dealerScenicspotSetting;
	@Autowired
	private ProvinceServiceImpl provinceServiceImpl;
	@Autowired
	private CityServiceImpl cityServiceImpl;
	@Autowired
	private AreaServiceImpl areaServiceImpl;
	@Autowired
	private HgLogger hgLogger;

	private final static double FILE_MAX_SIZE = 2;// 图片大小上限(2M)

	/**
	 * @方法功能说明：经销商列表检索
	 * @修改者名字：liusong
	 * @修改时间：2014-11-12下午2:16:11
	 * @修改内容：
	 * @参数：@param pageNum
	 * @参数：@param pageSize
	 * @参数：@param name
	 * @参数：@param beginTimeStr
	 * @参数：@param endTimeStr
	 * @参数：@param status
	 * @参数：@return
	 * @return:ModelAndView
	 * @throws
	 */
	@RequestMapping("/dealer/list")
	public ModelAndView list(
			@RequestParam(value = "pageNum", required = false) Integer pageNum,
			@RequestParam(value = "numPerPage", required = false) Integer pageSize,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "key", required = false) String key,
			@RequestParam(value = "linkMan", required = false) String linkMan,
			@RequestParam(value = "telephone", required = false) String telephone,
			@RequestParam(value = "pledgeAmountMin", required = false) String pledgeAmountMin,
			@RequestParam(value = "pledgeAmountMax", required = false) String pledgeAmountMax,
			@RequestParam(value = "status", required = false) Integer status,
			HttpServletRequest request) {

		String scenicSpotId = MerchantSessionUserManager
				.getSessionUserId(request);// 获取登录用户id，即景区id

		Pagination pagination = new Pagination();
		DealerQo dealerQo = new DealerQo();
		DealerScenicspotSettingQo scenicspotSettingQo = new DealerScenicspotSettingQo();

		// 设置景区id
		if (StringUtils.isNotBlank(scenicSpotId)) {
			scenicspotSettingQo.setScenicSpotId(scenicSpotId);
		}

		if (StringUtils.isNotBlank(pledgeAmountMin)) {
			try {
				scenicspotSettingQo.setPledgeAmountMin(Double
						.parseDouble(pledgeAmountMin));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (StringUtils.isNotBlank(pledgeAmountMax)) {
			try {
				scenicspotSettingQo.setPledgeAmountMax(Double
						.parseDouble(pledgeAmountMax));
			} catch (Exception e) {
				e.printStackTrace();
				dealerQo.setCreateDateEnd(null);
			}
		}

		if (StringUtils.isNotBlank(name)) {
			dealerQo.setName(StringFilterUtil.reverseString(name));
			dealerQo.setNameLike(true);
		}
		if (StringUtils.isNotBlank(key)) {
			dealerQo.setKey(key);
		}
		if (StringUtils.isNotBlank(telephone)) {
			dealerQo.setTelephone(telephone);
		}
		if (StringUtils.isNotBlank(linkMan)) {
			dealerQo.setLinkMan(linkMan);
			dealerQo.setLinkManLike(true);
		}

		dealerQo.setStatus(1);
		if (status != null) {
			if (status == 1)
				scenicspotSettingQo.setUseable(true);
			else
				scenicspotSettingQo.setUseable(false);
		}

		if (pageNum != null) {
			pagination.setPageNo(pageNum);
		} else {
			pagination.setPageNo(1);
		}
		if (pageSize != null) {
			pagination.setPageSize(pageSize);
		} else {
			pagination.setPageSize(20);
		}

		if (scenicspotSettingQo != null) {
			dealerQo.setScenicspotSettingQo(scenicspotSettingQo);
		}
		pagination.setCondition(dealerQo);

		pagination = this.dealerService.queryPagination(pagination);

		ModelAndView mav = new ModelAndView("/dealer/list.html");
		mav.addObject("pagination", pagination);
		mav.addObject("pledgeAmountMin", pledgeAmountMin);
		mav.addObject("pledgeAmountMax", pledgeAmountMax);
		mav.addObject("name", StringFilterUtil.reverseString(name));
		mav.addObject("key", StringFilterUtil.reverseString(key));
		mav.addObject("telephone", StringFilterUtil.reverseString(telephone));
		mav.addObject("linkMan", StringFilterUtil.reverseString(linkMan));
		mav.addObject("status", status);
		return mav;
	}

	/**
	 * @描述 添加经销商，返回添加页面
	 * @修改时间 2015-11-24 15:02:55
	 * @作者 guotx
	 * @return
	 */
	@RequestMapping("/dealer/toAdd")
	public ModelAndView toAdd() {
		ModelAndView mav = new ModelAndView("/dealer/add_dealer.html");
		List<Province> provinceList = provinceServiceImpl
				.queryList(new ProvinceQo());
		mav.addObject("provinceList", provinceList);
		return mav;
	}

	/**
	 * @方法功能说明：添加经销商
	 * @修改者名字：yangkang
	 * @参数：@param command
	 * @参数：@param request
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping("/dealer/add")
	public String createDealer(
			@RequestParam(value = "dealerName", required = true) String dealerName,
			@RequestParam(value = "key", required = true) String key,
			@RequestParam(value = "intro", required = true) String intro,
			@RequestParam(value = "linkMan", required = true) String linkMan,
			@RequestParam(value = "telephone", required = true) String telephone,
			@RequestParam(value = "email", required = true) String email,
			@RequestParam(value = "provinceId", required = true) String provinceId,
			@RequestParam(value = "cityId", required = true) String cityId,
			@RequestParam(value = "areaId", required = true) String areaId,
			@RequestParam(value = "address", required = true) String address,
			@RequestParam(value = "dealerUrl", required = true) String dealerUrl,
			@RequestParam(value = "imgNames", required = false) String[] imgNames,
			@RequestParam(value = "secretKey", required = false) String secretKey,
			@RequestParam(value = "publishUrl", required = false) String publishUrl,
			@RequestParam(value = "publishAble", required = false) Boolean publishAble,
			@RequestParam(value = "settlementFee", required = false) Double settlementFee,
			@RequestParam(value = "adminLoginName", required = false) String adminLoginName,
			@RequestParam(value = "cstNo", required = false) String cstNo,
			@RequestParam(value = "operatorNo", required = false) String operatorNo) {

		PlatformCreateDealerCommand command = new PlatformCreateDealerCommand();
		JSONObject o = new JSONObject();

		// 是否有同名经销商或key
		DealerQo qo = new DealerQo();
		qo.setName(dealerName);
		if (this.dealerService.isExistDealer(qo) > 0) {
			o.put("message", "存在相同名称经销商");
			o.put("statusCode", DwzJsonResultUtil.STATUS_CODE_300);
			return o.toJSONString();
		}
		qo.setName(null);
		qo.setAdminLoginName(adminLoginName);
		qo.setAdminLoginNameLike(false);
		if (this.dealerService.isExistDealer(qo) > 0) {
			o.put("message", "此登录账号已使用");
			o.put("statusCode", DwzJsonResultUtil.STATUS_CODE_300);
			return o.toJSONString();
		}
		qo.setAdminLoginName(null);
		qo.setAdminLoginNameLike(null);
		qo.setKey(key);
		if (this.dealerService.isExistDealer(qo) > 0) {
			o.put("message", "存在相同经销商代码");
			o.put("statusCode", DwzJsonResultUtil.STATUS_CODE_300);
			return o.toJSONString();
		}
		/** 金额正则 */
		// String patten = "^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){1,2})?$";

		command.setName(dealerName);
		command.setKey(key);
		command.setIntro(intro);
		command.setLinkMan(linkMan);
		command.setTelephone(telephone);
		command.setEmail(email);
		command.setDealerWebsite(dealerUrl);
		command.setProvinceId(provinceId);
		command.setCityId(cityId);
		command.setAreaId(areaId);
		command.setAddress(address);
		command.setCreateDate(new Date());
		command.setSecretKey(secretKey);
		command.setPublishUrl(publishUrl);
		command.setPublishAble(publishAble);
		command.setCstNo(cstNo);
		command.setOperatorNo(operatorNo);
		command.setSettlementFee(settlementFee);
		command.setAdminLoginName(adminLoginName);
		command.setAdminPassword("1234567");

		String tempPath = SimpleFileUtil.getTempDirectoryPath();
		tempPath = tempPath.replace(File.separator + "temp", File.separator
				+ "webapps" + File.separator + "group0");

		for (int i = 0, len = imgNames.length; i < len; i++) {
			File tempFile = new File(tempPath + imgNames[i]);
			if (!SimpleFileUtil.isFile(tempFile)) {
				if (i == 0)
					o.put("message", "企业logo不能为空");
				else
					o.put("message", "资质图片不能为空");
				o.put("statusCode", DwzJsonResultUtil.STATUS_CODE_300);
				return o.toJSONString();
			}

			FdfsFileUtil.init();
			FdfsFileInfo fileInfo = FdfsFileUtil.upload(tempFile,
					new HashMap<String, String>());
			String uri = SystemConfig.imageHost + fileInfo.getUri();

			if (i == 0)
				command.setDealerLogo(uri);
			else if (i == 1)
				command.setBusinessLicense(uri);
			else if (i == 2)
				command.setTaxRegistrationCertificate(uri);
			else if (i == 3)
				command.setOrganizationCodeCertificate(uri);
			else
				command.setCorporateIDCard(uri);
		}

		try {
			dealerService.platformCreateDealer(command);
			o.put("message", "保存成功");
			o.put("statusCode", DwzJsonResultUtil.STATUS_CODE_200);
			o.put("callbackType",
					DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT);
			o.put("navTabId", "dealer");

			for (int i = 0, len = imgNames.length; i < len; i++) {
				File tempFile = new File(tempPath + imgNames[i]);
				// 删除临时图片
				tempFile.delete();
			}
		} catch (DZPWException e) {

			e.printStackTrace();
			o.put("message", e.getMessage());
			o.put("statusCode", DwzJsonResultUtil.STATUS_CODE_500);
			o.put("callbackType", null);
			o.put("navTabId", null);
		} catch (Exception e) {

			o.put("callbackType", null);
			o.put("navTabId", null);
			o.put("message", "修改失败");
			o.put("statusCode", DwzJsonResultUtil.STATUS_CODE_500);
		}
		return o.toJSONString();
	}

	/**
	 * @方法功能说明：跳转到修改经销商修改页面
	 * @修改者名字：guotx
	 * @参数：@param id
	 * @参数：@return
	 * @return:ModelAndView
	 */
	@RequestMapping("/dealer/toUpdate")
	public ModelAndView toUpdate(
			@RequestParam(value = "id", required = false) String id) {

		ModelAndView mav = new ModelAndView("/dealer/update_dealer.html");

		DealerQo qo = new DealerQo();
		qo.setId(id);
		qo.setProvinceQo(new ProvinceQo());
		qo.setCityQo(new CityQo());
		qo.setAreaQo(new AreaQo());
		Dealer dealer = this.dealerService.queryUnique(qo);

		List<Province> provinceList = provinceServiceImpl
				.queryList(new ProvinceQo());

		if (dealer.getBaseInfo().getProvince() != null) {
			CityQo cq = new CityQo();
			cq.setProvinceCode(dealer.getBaseInfo().getProvince().getId());
			mav.addObject("cityList", cityServiceImpl.queryList(cq));
		}
		if (dealer.getBaseInfo().getCity() != null) {
			AreaQo aq = new AreaQo();
			aq.setCityCode(dealer.getBaseInfo().getCity().getId());
			mav.addObject("areaList", areaServiceImpl.queryList(aq));
		}
		mav.addObject("provinceList", provinceList);
		mav.addObject("dealer", dealer);
		return mav;
	}

	/**
	 * @描述 修改经销商
	 * @添加时间 2015-11-24 下午3:43:29
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/dealer/update")
	public String update(
			@RequestParam(value = "dealerId", required = true) String dealerId,
			@RequestParam(value = "dealerName", required = true) String dealerName,
			@RequestParam(value = "key", required = true) String key,
			@RequestParam(value = "intro", required = true) String intro,
			@RequestParam(value = "linkMan", required = true) String linkMan,
			@RequestParam(value = "telephone", required = true) String telephone,
			@RequestParam(value = "email", required = true) String email,
			@RequestParam(value = "provinceId", required = true) String provinceId,
			@RequestParam(value = "cityId", required = true) String cityId,
			@RequestParam(value = "areaId", required = true) String areaId,
			@RequestParam(value = "address", required = true) String address,
			@RequestParam(value = "dealerUrl", required = true) String dealerUrl,
			@RequestParam(value = "imgNames", required = false) String[] imgNames,
			@RequestParam(value = "imgSrcs", required = false) String[] imgSrcs,
			@RequestParam(value = "secretKey", required = false) String secretKey,
			@RequestParam(value = "publishUrl", required = false) String publishUrl,
			@RequestParam(value = "publishAble", required = false) Boolean publishAble,
			@RequestParam(value = "settlementFee", required = false) Double settlementFee,
			@RequestParam(value = "cstNo", required = false) String cstNo,
			@RequestParam(value = "operatorNo", required = false) String operatorNo,
			@RequestParam(value = "accountType", required = false) Integer accountType) {

		PlatformModifyDealerCommand command = new PlatformModifyDealerCommand();
		JSONObject o = new JSONObject();

		// 修改时候校验是否有同名经销商
		DealerQo qo = new DealerQo();
		qo.setName(dealerName);
		List<Dealer> list = this.dealerService.queryList(qo);
		if (list != null) {
			if (list.size() > 1) {
				o.put("message", "存在相同名称经销商");
				o.put("statusCode", DwzJsonResultUtil.STATUS_CODE_300);
				return o.toJSONString();
			}
			if (list.size() == 1) {
				if (!list.get(0).getId().equals(dealerId)) {
					o.put("message", "存在相同名称经销商");
					o.put("statusCode", DwzJsonResultUtil.STATUS_CODE_300);
					return o.toJSONString();
				}
			}
		}
		qo.setName(null);
		qo.setKey(key);
		list = this.dealerService.queryList(qo);
		if (list != null) {
			if (list.size() > 1) {
				o.put("message", "存在相同经销商代码");
				o.put("statusCode", DwzJsonResultUtil.STATUS_CODE_300);
				return o.toJSONString();
			}
			if (list.size() == 1) {
				if (!list.get(0).getId().equals(dealerId)) {
					o.put("message", "存在相同经销商代码");
					o.put("statusCode", DwzJsonResultUtil.STATUS_CODE_300);
					return o.toJSONString();
				}
			}
		}

		command.setSecretKey(secretKey);
		command.setAddress(address);
		command.setAreaId(areaId);
		command.setCityId(cityId);
		command.setDealerId(dealerId);
		command.setDealerWebsite(dealerUrl);
		command.setEmail(email);
		command.setIntro(intro);
		command.setKey(key);
		command.setLinkMan(linkMan);
		command.setName(dealerName);
		command.setProvinceId(provinceId);
		command.setTelephone(telephone);
		command.setDealerLogo(imgSrcs[0]);
		command.setBusinessLicense(imgSrcs[1]);// 营业执照
		command.setTaxRegistrationCertificate(imgSrcs[2]);// 税务登记证
		command.setOrganizationCodeCertificate(imgSrcs[3]);// 组织代码证
		command.setCorporateIDCard(imgSrcs[4]);// 法人身份证
		command.setPublishUrl(publishUrl);
		command.setPublishAble(publishAble);
		command.setAccountType(accountType);
		command.setCstNo(cstNo);
		command.setOperatorNo(operatorNo);
		command.setSettlementFee(settlementFee);

		String tempPath = SimpleFileUtil.getTempDirectoryPath();
		tempPath = tempPath.replace(File.separator + "temp", File.separator
				+ "webapps" + File.separator + "group0");

		for (int i = 0, len = imgNames.length; i < len; i++) {
			if (StringUtils.isBlank(imgNames[i]))
				continue;

			File tempFile = new File(tempPath + imgNames[i]);
			if (!SimpleFileUtil.isFile(tempFile)) {
				if (i == 0)
					o.put("message", "企业logo不能为空");
				else
					o.put("message", "资质图片不能为空");
				o.put("statusCode", DwzJsonResultUtil.STATUS_CODE_300);
				return o.toJSONString();
			}

			FdfsFileUtil.init();
			FdfsFileInfo fileInfo = FdfsFileUtil.upload(tempFile,
					new HashMap<String, String>());
			String uri = SystemConfig.imageHost + fileInfo.getUri();
			if (i == 0)
				command.setDealerLogo(uri);
			else if (i == 1)
				command.setBusinessLicense(uri);
			else if (i == 2)
				command.setTaxRegistrationCertificate(uri);
			else if (i == 3)
				command.setOrganizationCodeCertificate(uri);
			else
				command.setCorporateIDCard(uri);

		}

		try {
			dealerService.platformModifyDealer(command);
			o.put("message", "修改成功");
			o.put("statusCode", DwzJsonResultUtil.STATUS_CODE_200);
			o.put("callbackType",
					DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT);
			o.put("navTabId", "dealer");

			for (int i = 0, len = imgNames.length; i < len; i++) {
				File tempFile = new File(tempPath + imgNames[i]);
				// 删除临时图片
				tempFile.delete();
			}
		} catch (DZPWException e) {

			e.printStackTrace();
			o.put("callbackType", null);
			o.put("navTabId", null);
			o.put("message", e.getMessage());
			o.put("statusCode", DwzJsonResultUtil.STATUS_CODE_500);
		} catch (Exception e) {

			e.printStackTrace();
			o.put("callbackType", null);
			o.put("navTabId", null);
			o.put("message", "修改失败");
			o.put("statusCode", DwzJsonResultUtil.STATUS_CODE_500);
		}

		return o.toJSONString();
	}

	/**
	 * @方法功能说明：启用/批量启用
	 * @修改者名字：liusong
	 * @修改时间：2014-11-12下午5:50:37
	 * @修改内容：
	 * @参数：@param ids
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping("/dealer/usableBatch")
	public String usableBatch(
			@RequestParam(value = "ids", required = false) String[] ids) {

		String msg = "启用成功";
		String code = DwzJsonResultUtil.STATUS_CODE_200;
		String navTabId = "dealer";
		String callbackType = null;

		if (ids != null && ids.length > 0) {
			for (String id : ids) {
				ScenicspotModifyDealerSettingCommand command = new ScenicspotModifyDealerSettingCommand();
				command.setDealerId(id);
				command.setUseable(true);
				dealerScenicspotSetting.modifyDealerSetting(command);
			}
		}

		return DwzJsonResultUtil.createJsonString(code, msg, callbackType,
				navTabId);
	}

	/**
	 * @方法功能说明：禁用/批量禁用
	 * @修改者名字：liusong
	 * @修改时间：2014-11-13上午11:15:11
	 * @修改内容：
	 * @参数：@param ids
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping("/dealer/disableBatch")
	public String disableBatch(
			@RequestParam(value = "ids", required = false) String[] ids) {
		String msg = "禁用成功";
		String code = DwzJsonResultUtil.STATUS_CODE_200;
		String navTabId = "dealer";
		String callbackType = null;

		if (ids != null && ids.length > 0) {
			for (String id : ids) {
				ScenicspotModifyDealerSettingCommand command = new ScenicspotModifyDealerSettingCommand();
				command.setDealerId(id);
				command.setUseable(false);
				dealerScenicspotSetting.modifyDealerSetting(command);
			}
		}
		return DwzJsonResultUtil.createJsonString(code, msg, callbackType,
				navTabId);
	}

	/**
	 * @方法功能说明：经销商查看详情
	 * @修改者名字：liusong
	 * @修改时间：2014-11-13上午11:15:11
	 * @修改内容：
	 * @参数：@param ids
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("/dealer/info")
	public ModelAndView info(
			@RequestParam(value = "id", required = false) String id,
			HttpServletRequest request) {
		String scenicSpotId = MerchantSessionUserManager
				.getSessionUserId(request);// 获取登录用户id，即景区id
		DealerQo qo = new DealerQo();
		DealerScenicspotSettingQo scenicspotSettingQo = new DealerScenicspotSettingQo();
		// 判断查看经销商详情的id是否为空
		if (StringUtils.isNotBlank(id)) {
			qo.setId(id);
		}
		// 设置景区id
		if (StringUtils.isNotBlank(scenicSpotId)) {
			scenicspotSettingQo.setScenicSpotId(scenicSpotId);
			qo.setScenicspotSettingQo(scenicspotSettingQo);
		}
		Dealer dealer = this.dealerService.queryUnique(qo, true);

		// 返回的视图对象
		ModelAndView mav = new ModelAndView("/dealer/info.html");
		mav.addObject("dealer", dealer);
		return mav;
	}

	@ResponseBody
	@RequestMapping("/dealer/auotsearch")
	public String autoSearch(
			@RequestParam(value = "dealerName", required = false) String dealerName,
			HttpServletRequest request) {

		JSONObject o = new JSONObject();

			DealerScenicspotSettingQo scenicspotSettingQo = new DealerScenicspotSettingQo();
			DealerQo qo = new DealerQo();

			qo.setName(dealerName);
			qo.setNameLike(true);
			qo.setStatus(Dealer.DEALER_STATUS_USASBLE);

			scenicspotSettingQo.setUseable(true);
			String scenicSpotId = MerchantSessionUserManager
					.getSessionUserId(request);// 获取登录用户id，即景区id
			// 设置景区id
			if (StringUtils.isNotBlank(scenicSpotId)) {
				scenicspotSettingQo.setScenicSpotId(scenicSpotId);
			}
			qo.setScenicspotSettingQo(scenicspotSettingQo);

			List<Dealer> list = this.dealerService.queryList(qo);

			if (list != null && list.size() > 0) {
				o.put("status", "1");
				StringBuffer sb = new StringBuffer();
				sb.append("<ul>");
				for (Dealer s : list) {
					sb.append("<li>")
							.append(s.getBaseInfo().getName())
							.append("<input id=\"dealerId\" type=\"hidden\" value=\""
									+ s.getId() + "\"/></li>");
				}
				sb.append("</ul>");
				o.put("html", sb.toString());
			} else {
				o.put("status", "0");
			}
			
		return o.toJSONString();
	}

	/**
	 * @方法功能说明: 图片上传
	 * @param file
	 * @param title
	 * @param authUser
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/imgUpload")
	public String imgUpload(
			@RequestParam(value = "file", required = false) CommonsMultipartFile file) {
		Map<String, Object> remap = new HashMap<String, Object>(3);
		try {
			if (null == file || null == file.getFileItem())
				throw new DZPWException(DZPWException.NEED_SCENICSPOT_NOTEXIST,
						"图片不存在或已删除！");

			/** 获取上传图片文件，拷贝至自定义临时目录下 */
			// 判断图片类型
			String orginName = file.getFileItem().getName();
			String fileExt = StringUtils.substringAfterLast(orginName, ".")
					.toLowerCase();
			if (!fileExt.endsWith("jpg") && !fileExt.endsWith("gif")
					&& !fileExt.endsWith("png") && !fileExt.endsWith("bmp"))
				throw new DZPWException(
						DZPWException.NEED_SCENICSPOT_NOILLEGALITY,
						"不能上传jpg、gif、png或bmp格式以外的图片");

			// 验证图片大小
			double size = file.getSize() / 1024.0 / 1024.0;
			if (size > FILE_MAX_SIZE)
				throw new DZPWException(
						DZPWException.NEED_SCENICSPOT_NOILLEGALITY, "上传图片不能大于"
								+ FILE_MAX_SIZE + "M");

			// 临时文件路径
			String tempPath = SimpleFileUtil.getPathToRename(orginName);
			tempPath = tempPath.replace(File.separator + "temp", File.separator
					+ "webapps" + File.separator + "group0");
			File tempFile = new File(tempPath);

			FileUtils.copyInputStreamToFile(file.getInputStream(), tempFile);

			// 返回json数据
			remap.put("rs", 1);
			remap.put("imgName", tempFile.getName());
		} catch (Exception e) {
			String showMsg = "未知异常";
			if (e instanceof DZPWException) {
				e = (DZPWException) e;
				showMsg = e.getMessage();
			}

			e.printStackTrace();
			hgLogger.error(DealerController.class, "dzpw-application",
					"[imgUpload] 经销商-图片上传失败 ：" + e.getMessage() + "\tfile:"
							+ JSON.toJSONString(file, true), e);
			remap.put("rs", 0);
			remap.put("msg", showMsg);
		}
		return JSON.toJSONString(remap,
				SerializerFeature.DisableCheckSpecialChar);
	}
}
