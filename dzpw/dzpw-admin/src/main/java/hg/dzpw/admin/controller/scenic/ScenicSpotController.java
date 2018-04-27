package hg.dzpw.admin.controller.scenic;

import hg.common.model.qo.DwzPaginQo;
import hg.common.page.Pagination;
import hg.common.util.DwzJsonResultUtil;
import hg.common.util.UUIDGenerator;
import hg.common.util.file.FdfsFileInfo;
import hg.common.util.file.FdfsFileUtil;
import hg.common.util.file.SimpleFileUtil;
import hg.dzpw.admin.controller.BaseController;
import hg.dzpw.app.common.SystemConfig;
import hg.dzpw.app.pojo.qo.ScenicSpotQo;
import hg.dzpw.app.service.local.ClientDeviceLocalService;
import hg.dzpw.app.service.local.ScenicSpotLocalService;
import hg.dzpw.app.service.local.ScenicSpotPicLocalService;
import hg.dzpw.domain.model.scenicspot.ScenicSpot;
import hg.dzpw.domain.model.scenicspot.ScenicSpotBaseInfo;
import hg.dzpw.domain.model.scenicspot.ScenicSpotPic;
import hg.dzpw.pojo.command.platform.scenicspot.PlatformActivateScenicSpotCommand;
import hg.dzpw.pojo.command.platform.scenicspot.PlatformCreateScenicSpotCommand;
import hg.dzpw.pojo.command.platform.scenicspot.PlatformForbiddenScenicSpotCommand;
import hg.dzpw.pojo.command.platform.scenicspot.PlatformModifyScenicSpotCommand;
import hg.dzpw.pojo.command.platform.scenicspot.PlatformRemoveScenicSpotCommand;
import hg.dzpw.pojo.exception.DZPWException;
import hg.log.util.HgLogger;
import hg.system.model.auth.AuthUser;
import hg.system.model.meta.Province;
import hg.system.qo.AreaQo;
import hg.system.qo.CityQo;
import hg.system.qo.ProvinceQo;
import hg.system.service.impl.AreaServiceImpl;
import hg.system.service.impl.CityServiceImpl;
import hg.system.service.impl.ProvinceServiceImpl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @类功能说明: 景区管理Controller
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenys
 * @创建时间：2014-11-11 上午9:57:47
 */
@Controller
@RequestMapping("/scenic-spot")
public class ScenicSpotController extends BaseController {
	public final static double FILE_MAX_SIZE = 2;// 图片大小上限(2M)

	@Autowired
	private HgLogger hgLogger;// MongoDB日志
	@Autowired
	private ScenicSpotLocalService scenSer;
	/** 行政区划_省市_service */
	@Autowired
	private ProvinceServiceImpl provinceServiceImpl;
	@Autowired
	private CityServiceImpl cityServiceImpl;
	@Autowired
	private AreaServiceImpl areaServiceImpl;
	@Autowired
	private ClientDeviceLocalService deviceLocalServiceImpl;
	@Autowired
	private ScenicSpotPicLocalService scenicSpotPicLocalService;

	/**
	 * @方法功能说明: 景区列表
	 * @param scenQo
	 * @param dwzQo
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list")
	public String list(@ModelAttribute("scenQo") ScenicSpotQo scenQo,
			@ModelAttribute DwzPaginQo dwzQo, Model model) {
		scenQo.setAdminLoginNameLike(true);
		scenQo.setNameLike(true);
		scenQo.setShortNameLike(true);// 简称不适用模糊查询
		scenQo.setLinkManLike(true);
		scenQo.setTelephoneLike(true);
		scenQo.setProvinceFetchAble(true);
		scenQo.setCityFetchAble(true);
		scenQo.setCrateDateSort(-1);
		try {
			Pagination pagination = createPagination(dwzQo, scenQo);
			pagination = scenSer.queryPagination(pagination);
			model.addAttribute("pagination", pagination);

			model.addAttribute("provinceList",
					provinceServiceImpl.queryList(new ProvinceQo()));

			CityQo cqo = new CityQo();
			cqo.setProvinceCode(scenQo.getProvinceQo().getId());
			model.addAttribute("cityList", cityServiceImpl.queryList(cqo));
		} catch (Exception e) {
			e.printStackTrace();
			hgLogger.error(ScenicSpotController.class, "dzpw-application",
					"[list] 景区列表：" + e.getMessage(), e);
		}
		return "/scenic/spot/list.html";
	}

	/**
	 * @方法功能说明: 查看详细
	 * @param scenQo
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/info")
	public String info(@ModelAttribute("scenQo") ScenicSpotQo scenQo,
			Model model) {
		try {
			if (null == scenQo.getId())
				throw new DZPWException(
						DZPWException.NEED_SCENICSPOT_WITHOUTPARAM, "景区ID不能为空！");
			// --------------------
			scenQo.setProvinceFetchAble(true);
			scenQo.setCityFetchAble(true);
			scenQo.setAreaFetchAble(true);
			scenQo.setFetchAllDevices(true);
			scenQo.setFetchAllPic(true);
			ScenicSpot scen = scenSer.queryUnique(scenQo);
			if (null == scen)
				throw new DZPWException(DZPWException.SCENICSPOT_NOT_EXISTS,
						"景区不存在或已删除！");

			model.addAttribute("scen", scen);
			model.addAttribute("deviceList", scen.getDevices());
			model.addAttribute("scenicSpotPic", scen.getPics());
			model.addAttribute("scenThemeType",
					ScenicSpotBaseInfo.getThemeType());
			model.addAttribute("listSize", scen.getDevices().isEmpty() ? 0
					: scen.getDevices().size());
		} catch (Exception e) {
			e.printStackTrace();
			hgLogger.error(ScenicSpotController.class, "dzpw-application",
					"[info] 查看详细：" + e.getMessage(), e);
		}
		return "/scenic/spot/new_info.html";
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

			// 获取上传图片文件，拷贝至自定义临时目录下
			String imgName = this.imageHandler(file);

			// 返回json数据
			remap.put("rs", 1);
			remap.put("imgName", imgName);
		} catch (Exception e) {
			String showMsg = "未知异常";
			if (e instanceof DZPWException) {
				e = (DZPWException) e;
				showMsg = e.getMessage();
			}

			e.printStackTrace();
			hgLogger.error(ScenicSpotController.class, "dzpw-application",
					"[imgUpload] 景区-图片上传失败 ：" + e.getMessage() + "\tfile:"
							+ JSON.toJSONString(file, true), e);
			remap.put("rs", 0);
			remap.put("msg", showMsg);
		}
		return JSON.toJSONString(remap,
				SerializerFeature.DisableCheckSpecialChar);
	}

	/**
	 * @方法功能说明: 跳转到景区新增页面
	 * @return
	 */
	@RequestMapping(value = "/add")
	public String add(Model model) {
		List<Province> provinceList = provinceServiceImpl
				.queryList(new ProvinceQo());
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("scenThemeType", ScenicSpotBaseInfo.getThemeType());
		return "/scenic/spot/new_add.html";
	}

	/**
	 * @方法功能说明: 景区新增提交参数校验
	 * @param command
	 * @throws DZPWException
	 */
	public void addSubmitValidate(PlatformCreateScenicSpotCommand command,
			String[] titles, String[] fileNames, String[] imgNames)
			throws DZPWException {

		if (StringUtils.isBlank(command.getAdminLoginName()))
			throw new DZPWException(DZPWException.NEED_SCENICSPOT_WITHOUTPARAM,
					"登录账户不能为空！");

		if (StringUtils.isBlank(command.getName()))
			throw new DZPWException(DZPWException.NEED_SCENICSPOT_WITHOUTPARAM,
					"景区名称不能为空！");

		if (StringUtils.isBlank(command.getShortName()))
			throw new DZPWException(DZPWException.NEED_SCENICSPOT_WITHOUTPARAM,
					"景区简称不能为空！");

		if (StringUtils.isBlank(command.getIntro()))
			throw new DZPWException(DZPWException.NEED_SCENICSPOT_WITHOUTPARAM,
					"景区简介不能为空！");

		if (StringUtils.isBlank(command.getLevel()))
			throw new DZPWException(DZPWException.NEED_SCENICSPOT_WITHOUTPARAM,
					"景区级别不能为空！");

		if (StringUtils.isBlank(command.getProvinceId())
				|| StringUtils.isBlank(command.getCityId())
				|| StringUtils.isBlank(command.getAreaId()))
			throw new DZPWException(DZPWException.NEED_SCENICSPOT_WITHOUTPARAM,
					"省市区不能为空！");

		if (StringUtils.isBlank(command.getStreet()))
			throw new DZPWException(DZPWException.NEED_SCENICSPOT_WITHOUTPARAM,
					"景区详细地址不能为空！");

		if (StringUtils.isBlank(command.getLinkMan()))
			throw new DZPWException(DZPWException.NEED_SCENICSPOT_WITHOUTPARAM,
					"联系人不能为空！");

		if (StringUtils.isBlank(command.getTelephone()))
			throw new DZPWException(DZPWException.NEED_SCENICSPOT_WITHOUTPARAM,
					"联系电话不能为空！");

		if (StringUtils.isBlank(command.getEmail()))
			throw new DZPWException(DZPWException.NEED_SCENICSPOT_WITHOUTPARAM,
					"联系邮箱不能为空！");

		if (titles.length != fileNames.length
				&& fileNames.length != imgNames.length)
			throw new DZPWException(DZPWException.NEED_SCENICSPOT_WITHOUTPARAM,
					"资质图片不能为空！");

		if (StringUtils.isBlank(titles[0]) || StringUtils.isBlank(fileNames[0])
				|| StringUtils.isBlank(imgNames[0]))
			throw new DZPWException(DZPWException.NEED_SCENICSPOT_WITHOUTPARAM,
					"企业LOGO不能为空！");

		if (StringUtils.isBlank(titles[1]) || StringUtils.isBlank(fileNames[1])
				|| StringUtils.isBlank(imgNames[1]))
			throw new DZPWException(DZPWException.NEED_SCENICSPOT_WITHOUTPARAM,
					"营业执照不能为空！");

		if (StringUtils.isBlank(titles[2]) || StringUtils.isBlank(fileNames[2])
				|| StringUtils.isBlank(imgNames[2]))
			throw new DZPWException(DZPWException.NEED_SCENICSPOT_WITHOUTPARAM,
					"税务登记证不能为空！");

		if (StringUtils.isBlank(titles[3]) || StringUtils.isBlank(fileNames[3])
				|| StringUtils.isBlank(imgNames[3]))
			throw new DZPWException(DZPWException.NEED_SCENICSPOT_WITHOUTPARAM,
					"组织代码证不能为空！");

		if (StringUtils.isBlank(titles[4]) || StringUtils.isBlank(fileNames[4])
				|| StringUtils.isBlank(imgNames[4]))
			throw new DZPWException(DZPWException.NEED_SCENICSPOT_WITHOUTPARAM,
					"法人身份证不能为空！");
		if (command.getThemeValue()==null || command.getThemeValue().length<1) {
			throw new DZPWException(DZPWException.NEED_SCENICSPOT_WITHOUTPARAM,
					"至少选择一个主题类型！");
		}
		if (null == command.getDeviceIds()) {
//			throw new DZPWException(DZPWException.NEED_SCENICSPOT_WITHOUTPARAM,
//					"设备编码不能为空！");
			// 设备编码唯一性校验
		} else {
			List<String> strList = command.getDeviceIds();
			Collections.sort(strList);
			for (int i = 1, len = strList.size(); i < len; i++) {
				if (strList.get(i - 1).equals(strList.get(i)))
					throw new DZPWException(
							DZPWException.NEED_SCENICSPOT_WITHOUTPARAM,
							"设备编码不能重复！");
			}
		}
	}

	/**
	 * @方法功能说明: 景区新增提交
	 * @param command
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addSubmit", method = { RequestMethod.POST })
	public String addSubmit(
			@ModelAttribute PlatformCreateScenicSpotCommand command,
			@RequestParam(value = "titles", required = false) String[] titles,
			@RequestParam(value = "fileNames", required = false) String[] fileNames,
			@RequestParam(value = "imgNames", required = false) String[] imgNames,
			@RequestParam(value = "scenPicUrls", required = false) List<String> scenPicUrls,
			@RequestParam(value = "scenPicNames", required = false) List<String> scenPicNames) {

		try {

			command.setAdminPassword("1234567");
			command.setSecretKey(UUIDGenerator.getUUID());
			this.addSubmitValidate(command, titles, fileNames, imgNames);

			// 获取文件并上传至DFS
			String tempPath = SimpleFileUtil.getTempDirectoryPath();
			tempPath = tempPath.replace(File.separator + "temp", File.separator
					+ "webapps" + File.separator + "group0");
			for (int i = 0, len = imgNames.length; i < len; i++) {
				File tempFile = new File(tempPath + imgNames[i]);
				if (!SimpleFileUtil.isFile(tempFile))
					throw new DZPWException(
							DZPWException.NEED_SCENICSPOT_WITHOUTPARAM,
							"资质图片不能为空！");

				FdfsFileUtil.init();
				FdfsFileInfo fileInfo = FdfsFileUtil.upload(tempFile,
						new HashMap<String, String>());

				String uri = SystemConfig.imageHost + fileInfo.getUri();
				// 判断，赋值
				if (i == 0) {
					command.setScenicSpotLogo(uri);
				} else if (i == 1)
					command.setBusinessLicense(uri);
				else if (i == 2)
					command.setTaxRegistrationCertificate(uri);
				else if (i == 3)
					command.setOrganizationCodeCertificate(uri);
				else
					command.setCorporateIDCard(uri);

			}

			// 添加景区数据
			String spotId = scenSer.createScenicSpot(command);
			for (int i = 0, len = imgNames.length; i < len; i++) {
				if (StringUtils.isBlank(imgNames[i])) {
					continue;
				}
				File tempFile = new File(tempPath + imgNames[i]);
				// 删除临时图片
				tempFile.delete();
			}
			if (scenPicUrls!=null && StringUtils.isNotBlank(spotId)) {
				ScenicSpotPic pic = null;
				ScenicSpot scenicSpot = new ScenicSpot();
				scenicSpot.setId(spotId);

				for (int i = 0; i < scenPicUrls.size(); i++) {
					pic = new ScenicSpotPic();
					pic.setScenicSpot(scenicSpot);
					pic.setUrl(scenPicUrls.get(i));
					pic.setId(UUIDGenerator.getUUID());
					pic.setName(scenPicNames.get(i));
					scenicSpotPicLocalService.save(pic);
				}
			}

		} catch (Exception e) {
			String showMsg = "未知异常";
			if (e instanceof DZPWException) {
				e = (DZPWException) e;
				showMsg = e.getMessage();
			}
			e.printStackTrace();
			hgLogger.error(ScenicSpotController.class, "dzpw-application",
					"[addSubmit] 景区新增提交：" + e.getMessage(), e);
			return DwzJsonResultUtil.createSimpleJsonString(
					DwzJsonResultUtil.STATUS_CODE_500, "景区新增失败:" + showMsg);
		}
		return DwzJsonResultUtil.createJsonString(
				DwzJsonResultUtil.STATUS_CODE_200, "景区新增成功",
				DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "scenic-spot");
	}

	/**
	 * @方法功能说明: 跳转到景区编辑页面
	 * @param scenQo
	 * @param model
	 */
	@RequestMapping(value = "/edit")
	public String edit(@ModelAttribute("scenQo") ScenicSpotQo scenQo,
			Model model) {
		try {
			if (null == scenQo.getId())
				throw new DZPWException(
						DZPWException.NEED_SCENICSPOT_WITHOUTPARAM, "景区ID不能为空！");

			List<Province> provinceList = provinceServiceImpl
					.queryList(new ProvinceQo());
			model.addAttribute("provinceList", provinceList);

			scenQo.setProvinceFetchAble(true);
			scenQo.setCityFetchAble(true);
			scenQo.setAreaFetchAble(true);
			scenQo.setFetchAllDevices(true);
			scenQo.setFetchAllPic(true);
			ScenicSpot scen = scenSer.queryUnique(scenQo);
			if (null == scen)
				throw new DZPWException(DZPWException.SCENICSPOT_NOT_EXISTS,
						"景区不存在或已删除！");

			CityQo cq = new CityQo();
			cq.setProvinceCode(scen.getBaseInfo().getProvince().getCode());
			model.addAttribute("cityList", cityServiceImpl.queryList(cq));

			AreaQo aq = new AreaQo();
			aq.setCityCode(scen.getBaseInfo().getCity().getCode());
			model.addAttribute("areaList", areaServiceImpl.queryList(aq));

			model.addAttribute("scen", scen);
			model.addAttribute("scenicSpotPic", scen.getPics());
			model.addAttribute("scenThemeType",
					ScenicSpotBaseInfo.getThemeType());
			model.addAttribute("deviceList", scen.getDevices());
			model.addAttribute("listSize", scen.getDevices().isEmpty() ? 0
					: scen.getDevices().size());
		} catch (Exception e) {
			e.printStackTrace();
			hgLogger.error(ScenicSpotController.class, "dzpw-application",
					"[edit] 景区编辑：" + e.getMessage(), e);
		}
		return "/scenic/spot/new_edit.html";
	}

	/**
	 * @方法功能说明: 景区编辑提交的基本校验
	 * @param command
	 * @throws DZPWException
	 */
	public void editSubmitValidate(PlatformModifyScenicSpotCommand command)
			throws DZPWException {
		if (null == command.getScenicSpotId())
			throw new DZPWException(DZPWException.NEED_SCENICSPOT_WITHOUTPARAM,
					"景区ID不能为空！");

		if (StringUtils.isBlank(command.getAdminLoginName()))
			throw new DZPWException(DZPWException.NEED_SCENICSPOT_WITHOUTPARAM,
					"登录账户不能为空！");

		if (StringUtils.isNotBlank(command.getAdminPassword())
				&& command.getAdminPassword().length() >= 30)
			throw new DZPWException(DZPWException.NEED_SCENICSPOT_WITHOUTPARAM,
					"登录密码不正确！");

		if (StringUtils.isBlank(command.getName()))
			throw new DZPWException(DZPWException.NEED_SCENICSPOT_WITHOUTPARAM,
					"景区名称不能为空！");

		if (StringUtils.isBlank(command.getShortName()))
			throw new DZPWException(DZPWException.NEED_SCENICSPOT_WITHOUTPARAM,
					"景区简称不能为空！");

		if (StringUtils.isBlank(command.getProvinceId())
				|| StringUtils.isBlank(command.getCityId())
				|| StringUtils.isBlank(command.getAreaId()))
			throw new DZPWException(DZPWException.NEED_SCENICSPOT_WITHOUTPARAM,
					"省市区不能为空！");

		if (StringUtils.isBlank(command.getLevel()))
			throw new DZPWException(DZPWException.NEED_SCENICSPOT_WITHOUTPARAM,
					"景区级别不能为空！");

		if (StringUtils.isBlank(command.getStreet()))
			throw new DZPWException(DZPWException.NEED_SCENICSPOT_WITHOUTPARAM,
					"景区详细地址不能为空！");

		if (StringUtils.isBlank(command.getLinkMan()))
			throw new DZPWException(DZPWException.NEED_SCENICSPOT_WITHOUTPARAM,
					"联系人不能为空！");

		if (StringUtils.isBlank(command.getTelephone()))
			throw new DZPWException(DZPWException.NEED_SCENICSPOT_WITHOUTPARAM,
					"联系电话不能为空！");

		if (StringUtils.isBlank(command.getEmail()))
			throw new DZPWException(DZPWException.NEED_SCENICSPOT_WITHOUTPARAM,
					"联系邮箱不能为空！");
		if (command.getThemeValue()==null || command.getThemeValue().length<1) {
			throw new DZPWException(DZPWException.NEED_SCENICSPOT_WITHOUTPARAM,
					"至少选择一个主题类型！");
		}
		if (null == command.getDeviceIds()) {
//			throw new DZPWException(DZPWException.NEED_SCENICSPOT_WITHOUTPARAM,
//					"设备编码不能为空！");

			// 设备编码唯一性校验
		} else {
			List<String> strList = command.getDeviceIds();
			Collections.sort(strList);
			for (int i = 1, len = strList.size(); i < len; i++) {
				if (strList.get(i - 1).equals(strList.get(i)))
					throw new DZPWException(
							DZPWException.NEED_SCENICSPOT_WITHOUTPARAM,
							"设备编码不能重复！");
			}
		}
	}

	/**
	 * @方法功能说明: 景区编辑提交
	 * @param command
	 * @param navTabid
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/editSubmit", method = { RequestMethod.POST })
	public String editSubmit(
			@ModelAttribute PlatformModifyScenicSpotCommand command,
			@RequestParam(value = "titles", required = false) String[] titles,
			@RequestParam(value = "fileNames", required = false) String[] fileNames,
			@RequestParam(value = "imgNames", required = false) String[] imgNames,
			@RequestParam(value = "imgSrcs", required = false) String[] imgSrcs,
			@RequestParam(value = "scenPicUrls", required = false) List<String> scenPicUrls,
			@RequestParam(value = "scenPicFlags", required = false) List<String> scenPicFlags,
			@RequestParam(value = "scenPicIds", required = false) List<String> scenPicIds,
			@RequestParam(value = "scenPicNames", required = false) List<String> scenPicNames) {
		try {
			command.setScenicSpotLogo(imgSrcs[0]);// 企业LOGO
			command.setBusinessLicense(imgSrcs[1]);// 营业执照
			command.setTaxRegistrationCertificate(imgSrcs[2]);// 税务登记证
			command.setOrganizationCodeCertificate(imgSrcs[3]);// 组织代码证
			command.setCorporateIDCard(imgSrcs[4]);// 法人身份证
			this.editSubmitValidate(command);

			List<FdfsFileInfo> infoList = new ArrayList<FdfsFileInfo>(4);
			// 获取文件并上传至DFS
			String tempPath = SimpleFileUtil.getTempDirectoryPath();
			tempPath = tempPath.replace(File.separator + "temp", File.separator
					+ "webapps" + File.separator + "group0");
			for (int i = 0; i < imgNames.length; i++) {
				if (StringUtils.isBlank(imgNames[i])) {
					continue;
				}
				File tempFile = new File(tempPath + imgNames[i]);
				if (!SimpleFileUtil.isFile(tempFile)) {
					infoList.add(null);
					continue;
				}

				FdfsFileUtil.init();
				FdfsFileInfo fileInfo = FdfsFileUtil.upload(tempFile,
						new HashMap<String, String>());

				String uri = SystemConfig.imageHost + fileInfo.getUri();
				// 判断，赋值
				if (i == 0) {
					command.setScenicSpotLogo(uri);
				}
				if (i == 1)
					command.setBusinessLicense(uri);
				else if (i == 2)
					command.setTaxRegistrationCertificate(uri);
				else if (i == 3)
					command.setOrganizationCodeCertificate(uri);
				else
					command.setCorporateIDCard(uri);

				tempFile.delete();
			}

			// 修改数据
			scenSer.modifyScenicSpot(command);
			if(scenPicFlags!=null && scenPicFlags.size()>0){
				for (int i = 0; i < scenPicFlags.size(); i++) {
					if (StringUtils.isNotBlank(scenPicFlags.get(i))
							&& scenPicFlags.get(i).equals("-")) {
						scenicSpotPicLocalService.deleteById(scenPicIds.get(i));
					} else if (StringUtils.isNotBlank(scenPicFlags.get(i))
							&& scenPicFlags.get(i).equals("+")) {
						ScenicSpotPic scenicSpotPic = new ScenicSpotPic();
						ScenicSpot scenicSpot = new ScenicSpot();
						scenicSpot.setId(command.getScenicSpotId());
						scenicSpotPic.setId(UUIDGenerator.getUUID());
						scenicSpotPic.setName(scenPicNames.get(i));
						scenicSpotPic.setScenicSpot(scenicSpot);
						scenicSpotPic.setUrl(scenPicUrls.get(i));
						scenicSpotPicLocalService.save(scenicSpotPic);
					}
				}
			}
		} catch (Exception e) {
			String showMsg = "未知异常";
			if (e instanceof DZPWException) {
				e = (DZPWException) e;
				showMsg = e.getMessage();
			}

			e.printStackTrace();
			hgLogger.error(ScenicSpotController.class, "dzpw-application",
					"[editSubmit] 景区修改提交：" + e.getMessage(), e);
			return DwzJsonResultUtil.createSimpleJsonString(
					DwzJsonResultUtil.STATUS_CODE_500, "景区修改失败:" + showMsg);
		}
		return DwzJsonResultUtil.createJsonString(
				DwzJsonResultUtil.STATUS_CODE_200, "景区修改成功",
				DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "scenic-spot");
	}

	/**
	 * @方法功能说明: 景区删除 -逻辑删除
	 * @param command
	 * @param navTabid
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delete")
	public String delete(
			@ModelAttribute PlatformRemoveScenicSpotCommand command,
			@ModelAttribute AuthUser authUser,
			@RequestParam(value = "navTabid", required = false) String navTabid) {
		try {
			if (null == command.getScenicSpotId())
				throw new DZPWException(
						DZPWException.NEED_SCENICSPOT_WITHOUTPARAM, "景区ID不能为空！");

			// 删除
			scenSer.removeScenicSpot(command, authUser.getId());
		} catch (Exception e) {
			String showMsg = "未知异常";
			if (e instanceof DZPWException) {
				e = (DZPWException) e;
				showMsg = e.getMessage();
			}

			e.printStackTrace();
			hgLogger.error(ScenicSpotController.class, "dzpw-application",
					"[delete] 景区删除：" + e.getMessage(), e);
			return DwzJsonResultUtil.createSimpleJsonString(
					DwzJsonResultUtil.STATUS_CODE_500, "景区删除失败:" + showMsg);
		}
		return DwzJsonResultUtil.createJsonString(
				DwzJsonResultUtil.STATUS_CODE_200, "景区删除成功", null, navTabid);
	}

	/**
	 * @方法功能说明: 景区状态更改 -启用景区
	 * @param command
	 * @param navTabid
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/change1")
	public String change1(
			@ModelAttribute PlatformActivateScenicSpotCommand command,
			@RequestParam(value = "navTabid", required = false) String navTabid) {
		try {
			if (null == command.getScenicSpotId())
				throw new DZPWException(
						DZPWException.NEED_SCENICSPOT_WITHOUTPARAM, "景区ID不能为空！");
			// --------------------
			// 启用
			scenSer.activateScenicSpot(command);
		} catch (Exception e) {
			String showMsg = "未知异常";
			if (e instanceof DZPWException) {
				e = (DZPWException) e;
				showMsg = e.getMessage();
			}

			e.printStackTrace();
			hgLogger.error(ScenicSpotController.class, "dzpw-application",
					"[change1] 景区状态更改-启用景区 ：" + e.getMessage(), e);
			return DwzJsonResultUtil.createSimpleJsonString(
					DwzJsonResultUtil.STATUS_CODE_500, "启用景区失败:" + showMsg);
		}
		return DwzJsonResultUtil.createJsonString(
				DwzJsonResultUtil.STATUS_CODE_200, "启用景区成功", null, navTabid);
	}

	/**
	 * @方法功能说明: 景区状态更改 -禁用景区
	 * @param command
	 * @param navTabid
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/change2")
	public String change2(
			@ModelAttribute PlatformForbiddenScenicSpotCommand command,
			@RequestParam(value = "navTabid", required = false) String navTabid) {
		try {
			if (null == command.getScenicSpotId())
				throw new DZPWException(
						DZPWException.NEED_SCENICSPOT_WITHOUTPARAM, "景区ID不能为空！");
			// --------------------
			// 禁用
			scenSer.forbiddenScenicSpot(command);
		} catch (Exception e) {
			String showMsg = "未知异常";
			if (e instanceof DZPWException) {
				e = (DZPWException) e;
				showMsg = e.getMessage();
			}

			e.printStackTrace();
			hgLogger.error(ScenicSpotController.class, "dzpw-application",
					"[change2] 景区状态更改-禁用景区 ：" + e.getMessage(), e);
			return DwzJsonResultUtil.createSimpleJsonString(
					DwzJsonResultUtil.STATUS_CODE_500, "禁用景区失败:" + showMsg);
		}
		return DwzJsonResultUtil.createJsonString(
				DwzJsonResultUtil.STATUS_CODE_200, "禁用景区成功", null, navTabid);
	}

	/**
	 * @方法功能说明: 图片处理
	 * @param file
	 * @return
	 * @throws IOException
	 * @throws DZPWException
	 */
	public String imageHandler(CommonsMultipartFile file) throws IOException,
			DZPWException {
		if (null == file || null == file.getFileItem())
			return null;

		// 判断图片类型
		String orginName = file.getFileItem().getName();
		String fileExt = StringUtils.substringAfterLast(orginName, ".")
				.toLowerCase();
		if (!fileExt.endsWith("jpg") && !fileExt.endsWith("gif")
				&& !fileExt.endsWith("png") && !fileExt.endsWith("bmp"))
			throw new DZPWException(DZPWException.NEED_SCENICSPOT_NOILLEGALITY,
					"不能上传jpg、gif、png或bmp格式以外的图片");

		// 验证图片大小
		double size = file.getSize() / 1024.0 / 1024.0;
		if (size > FILE_MAX_SIZE)
			throw new DZPWException(DZPWException.NEED_SCENICSPOT_NOILLEGALITY,
					"上传图片不能大于" + FILE_MAX_SIZE + "M");

		// 临时文件路径
		String tempPath = SimpleFileUtil.getPathToRename(orginName);
		tempPath = tempPath.replace(File.separator + "temp", File.separator
				+ "webapps" + File.separator + "group0");
		File tempFile = new File(tempPath);

		FileUtils.copyInputStreamToFile(file.getInputStream(), tempFile);
		// 返回文件名
		return tempFile.getName();
	}

	@ResponseBody
	@RequestMapping("/ajax/findjq")
	public String findJQ(
			@RequestParam(value = "name", required = true) String name) {

		JSONObject o = new JSONObject();
		ScenicSpotQo scenQo = new ScenicSpotQo();
		scenQo.setName(name);
		scenQo.setNameLike(true);
		scenQo.setCrateDateSort(-1);
		scenQo.setActivated(true);// 已启用的景区

		List<ScenicSpot> list = scenSer.queryList(scenQo);
		if (list != null && list.size() > 0) {
			o.put("status", "1");
			StringBuffer sb = new StringBuffer();
			sb.append("<ul>");
			for (ScenicSpot s : list) {
				sb.append("<li>")
						.append(s.getBaseInfo().getName())
						.append("<input id=\"scenicSpotId\" type=\"hidden\" value=\""
								+ s.getId() + "\"/></li>");
			}
			sb.append("</ul>");
			o.put("html", sb.toString());
		} else {
			o.put("status", 0);
		}

		return o.toJSONString();
	}

	@ResponseBody
	@RequestMapping("/device/del")
	public String delDevice(
			@RequestParam(value = "deviceId", required = true) String deviceId) {

		JSONObject o = new JSONObject();
		try {
			if (StringUtils.isNotBlank(deviceId)) {
				deviceLocalServiceImpl.deleteById(deviceId);
				o.put("status", "1");
			} else {
				o.put("status", "-1");
			}
		} catch (Exception e) {
			e.printStackTrace();
			o.put("status", "0");
		}
		return o.toJSONString();
	}
}