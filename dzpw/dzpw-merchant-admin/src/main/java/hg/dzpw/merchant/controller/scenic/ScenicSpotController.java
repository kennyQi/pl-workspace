package hg.dzpw.merchant.controller.scenic;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import hg.common.util.DwzJsonResultUtil;
import hg.common.util.UUIDGenerator;
import hg.common.util.file.FdfsFileInfo;
import hg.common.util.file.FdfsFileUtil;
import hg.common.util.file.SimpleFileUtil;
import hg.dzpw.app.common.SystemConfig;
import hg.dzpw.app.pojo.qo.ScenicSpotQo;
import hg.dzpw.app.service.local.ScenicSpotLocalService;
import hg.dzpw.app.service.local.ScenicSpotPicLocalService;
import hg.dzpw.domain.model.scenicspot.ScenicSpot;
import hg.dzpw.domain.model.scenicspot.ScenicSpotBaseInfo;
import hg.dzpw.domain.model.scenicspot.ScenicSpotPic;
import hg.dzpw.merchant.component.manager.MerchantSessionUserManager;
import hg.dzpw.merchant.controller.BaseController;
import hg.dzpw.pojo.command.merchant.scenicspot.ModifyLoginScenicspotCommand;
import hg.dzpw.pojo.common.DZPWConstants;
import hg.dzpw.pojo.exception.DZPWException;
import hg.log.util.HgLogger;
import hg.system.model.meta.Province;
import hg.system.qo.AreaQo;
import hg.system.qo.CityQo;
import hg.system.qo.ProvinceQo;
import hg.system.service.impl.AreaServiceImpl;
import hg.system.service.impl.CityServiceImpl;
import hg.system.service.impl.ProvinceServiceImpl;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


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
	@Autowired
	private HgLogger hgLogger;//MongoDB日志
	@Autowired
	private ScenicSpotLocalService  scenSer;
	/**行政区划_省市_service*/
	@Autowired
	private ProvinceServiceImpl provinceServiceImpl;
	@Autowired
	private CityServiceImpl cityServiceImpl;
	@Autowired
	private AreaServiceImpl areaServiceImpl;
	@Autowired
	private ScenicSpotPicLocalService scenicSpotPicLocalService;
	
	/**
	 * @方法功能说明: 点击景点信息看到该景点的详细信息
	 * @param scenQo
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/editInfo")
	public String info(Model model,HttpServletRequest request) {
	try {
			String scenicSpotId = MerchantSessionUserManager.getSessionUserId(request);//获取登录用户id，即景区id
			if(scenicSpotId != null && !"".equals(scenicSpotId)){
				ScenicSpotQo   scenicSpotQo = new ScenicSpotQo();
				scenicSpotQo.setId(scenicSpotId);
				scenicSpotQo.setCityFetchAble(true);
				scenicSpotQo.setProvinceFetchAble(true);
				scenicSpotQo.setAreaFetchAble(true);
				scenicSpotQo.setFetchAllDevices(true);
				scenicSpotQo.setFetchAllPic(true);
				
				//执行查询，根据景区id查询该景区的详细信息
				ScenicSpot scen = scenSer.queryUnique(scenicSpotQo);
				
				if(null == scen)
					throw new DZPWException(DZPWException.SCENICSPOT_NOT_EXISTS,"景区不存在或已删除！");
				
				Map<String, String> map = new HashMap<String, String>();
				for(Entry<Integer, String> entry:DZPWConstants.SCENICSPOT_TYPE_NAME.entrySet())
					map.put(entry.getKey().toString(), entry.getValue());
				model.addAttribute("scenTypeNameMap",map);
				model.addAttribute("scenThemeType",ScenicSpotBaseInfo.getThemeType());
				model.addAttribute("scen",scen);
				
				List<Province> provinceList = provinceServiceImpl.queryList(new ProvinceQo());
				if (scen.getBaseInfo().getProvince()!=null) {
					CityQo cityQo=new CityQo();
					cityQo.setProvinceCode(scen.getBaseInfo().getProvince().getCode());
					model.addAttribute("cityList",cityServiceImpl.queryList(cityQo));
				}
				if (scen.getBaseInfo().getArea()!=null) {
					AreaQo areaQo=new AreaQo();
					areaQo.setCityCode(scen.getBaseInfo().getCity().getCode());
					model.addAttribute("areaList",areaServiceImpl.queryList(areaQo));;
				}
				model.addAttribute("provinceList", provinceList);
			}
		} catch (Exception e) {
			e.printStackTrace();
			hgLogger.error(ScenicSpotController.class,"dzpw-application","[info] 查看详细："+e.getMessage(),e);
		}
		return "/scenic/spot/editInfo.html";
	}
	
	
	/**
	 * @方法功能说明: 景点信息修改
	 * @param command
	 * @param navTabid
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/editSubmit", method = { RequestMethod.POST })
	public String editSubmit(@ModelAttribute ModifyLoginScenicspotCommand command,
			 @RequestParam(value="imgNames",required=false) String[] imgNames,
		     @RequestParam(value="imgSrcs",required=false) String[] imgSrcs,
		     @RequestParam(value="scenPicUrls",required=false)List<String>scenPicUrls,
			@RequestParam(value="scenPicFlags",required=false)List<String>scenPicFlags,
			@RequestParam(value="scenPicIds",required=false)List<String>scenPicIds,
			@RequestParam(value="scenPicNames",required=false)List<String>scenPicNames,
			@RequestParam(value="navTabid",required=false) String navTabid) {
		try {
			
			if(command != null){
				command.setScenicSpotLogo(imgSrcs[0]);	//企业LOGO
				command.setBusinessLicense(imgSrcs[1]);//营业执照
				command.setTaxRegistrationCertificate(imgSrcs[2]);//税务登记证
				command.setOrganizationCodeCertificate(imgSrcs[3]);//组织代码证
				command.setCorporateIDCard(imgSrcs[4]);//法人身份证
				//上传资质图片
				String tempPath = SimpleFileUtil.getTempDirectoryPath();
				tempPath = tempPath.replace(File.separator+"temp",File.separator+"webapps"+File.separator+"group0");
				String errorMsg = null;
				for(int i = 0,len = imgNames.length;i < len;i++){
					if(StringUtils.isBlank(imgNames[i]))
						continue;
						
					File tempFile = new File(tempPath+imgNames[i]);
					if(!SimpleFileUtil.isFile(tempFile)){
						if(i==0)
							errorMsg="企业logo不能为空";
						else
							errorMsg="资质图片不能为空";
						return DwzJsonResultUtil.createSimpleJsonString(DwzJsonResultUtil.STATUS_CODE_500,"景点信息修改失败:"+errorMsg);
					}
					
					FdfsFileUtil.init();
					FdfsFileInfo fileInfo = FdfsFileUtil.upload(tempFile, new HashMap<String, String>());
					String uri = SystemConfig.imageHost+fileInfo.getUri();
					tempFile.delete();
					if(i == 0)
						command.setScenicSpotLogo(uri);
					else if(i == 1)
						command.setBusinessLicense(uri);
					else if(i == 2)
						command.setTaxRegistrationCertificate(uri);
					else if(i == 3)
						command.setOrganizationCodeCertificate(uri);
					else
						command.setCorporateIDCard(uri);
					//删除图片
					tempFile.delete();
				}
				//修改景点信息
				scenSer.modifyLoginScenicspot(command,imgNames,imgSrcs);
				if (scenPicFlags!=null) {
					for (int i = 0; i < scenPicFlags.size(); i++) {
						if (StringUtils.isNotBlank(scenPicFlags.get(i))&&scenPicFlags.get(i).equals("-")) {
							scenicSpotPicLocalService.deleteById(scenPicIds.get(i));
						}else if (StringUtils.isNotBlank(scenPicFlags.get(i))&&scenPicFlags.get(i).equals("+")) {
							ScenicSpotPic scenicSpotPic=new ScenicSpotPic();
							ScenicSpot scenicSpot=new ScenicSpot();
							scenicSpot.setId(command.getScenicSpotId());
							scenicSpotPic.setId(UUIDGenerator.getUUID());
							scenicSpotPic.setName(scenPicNames.get(i));
							scenicSpotPic.setScenicSpot(scenicSpot);
							scenicSpotPic.setUrl(scenPicUrls.get(i));
							scenicSpotPicLocalService.save(scenicSpotPic);
						}
					}
				}
			}else {
				return DwzJsonResultUtil.createSimpleJsonString(DwzJsonResultUtil.STATUS_CODE_500,"景点信息修改失败:系统异常");
			}
		} catch (Exception e) {
			String showMsg = "未知异常";
			if(e instanceof DZPWException){
				e = (DZPWException)e;
				showMsg = e.getMessage();
			}
			e.printStackTrace();
			hgLogger.error(ScenicSpotController.class,"dzpw-application","[editSubmit] 景点信息修改提交："+e.getMessage(),e);
			return DwzJsonResultUtil.createSimpleJsonString(DwzJsonResultUtil.STATUS_CODE_500,"景点信息修改失败:"+showMsg);
		}
		return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200,"景点信息修改成功",null,navTabid);
	}
}