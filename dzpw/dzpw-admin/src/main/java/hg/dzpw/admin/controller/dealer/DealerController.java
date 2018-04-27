package hg.dzpw.admin.controller.dealer;

import hg.common.page.Pagination;
import hg.common.util.DwzJsonResultUtil;
import hg.common.util.UUIDGenerator;
import hg.common.util.file.FdfsFileInfo;
import hg.common.util.file.FdfsFileUtil;
import hg.common.util.file.SimpleFileUtil;
import hg.dzpw.admin.common.util.StringFilterUtil;
import hg.dzpw.admin.controller.BaseController;
import hg.dzpw.app.common.SystemConfig;
import hg.dzpw.app.pojo.qo.DealerQo;
import hg.dzpw.app.service.local.DealerLocalService;
import hg.dzpw.domain.model.dealer.Dealer;
import hg.dzpw.pojo.command.platform.dealer.PlatformCreateDealerCommand;
import hg.dzpw.pojo.command.platform.dealer.PlatformModifyDealerCommand;
import hg.dzpw.pojo.exception.DZPWException;
import hg.log.util.HgLogger;
import hg.system.model.meta.Province;
import hg.system.qo.AreaQo;
import hg.system.qo.CityQo;
import hg.system.qo.ProvinceQo;
import hg.system.service.impl.ProvinceServiceImpl;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 * @作者：yangkang
 * @创建时间：2014-11-11下午2:55:54
 * @版本：V1.0
 */
@Controller
public class DealerController extends BaseController{
	
	@Autowired
	private DealerLocalService dealerService;
	
	@Autowired
	private HgLogger hgLogger;//MongoDB日志
	
	/**行政区划_省市_service*/
	@Autowired
	private ProvinceServiceImpl provinceServiceImpl;
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private final static double FILE_MAX_SIZE = 2;//图片大小上限(2M)
	
	private final static String navTabId = "dealer";
	
	/**金额正则*/
	private static final String patten = "^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){1,2})?$";
	
	/**
	 * @方法功能说明：经销商列表
	 * @修改者名字：yangkang
	 * @修改时间：2014-11-12下午2:16:11
	 * @修改内容：
	 * @参数：@param pageNum
	 * @参数：@param pageSize
	 * @参数：@param name
	 * @参数：@param beginTimeStr
	 * @参数：@param endTimeStr
	 * @参数：@param status
	 * @参数：@param linkMan
	 * @参数：@param telephone
	 * @参数：@param email
	 * @参数：@return
	 * @return:ModelAndView
	 * @throws
	 */
	@RequestMapping("/dealer/list")
	public ModelAndView list(@RequestParam(value="pageNum", required = false)Integer pageNum,
			                 @RequestParam(value="numPerPage", required = false)Integer pageSize,
			                 @RequestParam(value="name", required = false)String name,
			                 @RequestParam(value="beginTimeStr", required = false)String beginTimeStr,
			                 @RequestParam(value="endTimeStr", required = false)String endTimeStr,
			                 @RequestParam(value="status", required = false)Integer status,
			                 @RequestParam(value="linkMan", required = false)String linkMan,
			                 @RequestParam(value="telephone", required = false)String telephone,
			                 @RequestParam(value="email", required = false)String email,
			                 @RequestParam(value="key", required=false)String key){
		
		Pagination pagination = new Pagination();
		DealerQo dealerQo = new DealerQo();
		
		if(StringUtils.isNotBlank(name)){
			dealerQo.setName(StringFilterUtil.reverseString(name));
			dealerQo.setNameLike(true);
		}
		
		if(StringUtils.isNotBlank(beginTimeStr)){
			try {
				dealerQo.setCreateDateBegin(sdf.parse(beginTimeStr+" 00:00:00"));
			} catch (ParseException e) {
				e.printStackTrace();
				dealerQo.setCreateDateBegin(null);
			}
		}

		if(StringUtils.isNotBlank(endTimeStr)){
			try {
				dealerQo.setCreateDateEnd(sdf.parse(endTimeStr+" 23:59:59"));
			} catch (ParseException e) {
				e.printStackTrace();
				dealerQo.setCreateDateEnd(null);
			}
		}
	
		if(StringUtils.isNotBlank(linkMan)){
			dealerQo.setLinkMan(StringFilterUtil.reverseString(linkMan));
			dealerQo.setLinkManLike(true);
		}

		if(StringUtils.isNotBlank(telephone)){
			dealerQo.setTelephone(telephone);
		}
		
		if(StringUtils.isNotBlank(email)){
			dealerQo.setEmail(email);
		}
		
		if(StringUtils.isNotBlank(key)){
			dealerQo.setKey(key);
		}
		
		if(status!=null){
			dealerQo.setStatus(status);
		}
		
		if(pageNum!=null){
			pagination.setPageNo(pageNum);
		}else{
			pagination.setPageNo(1);
		}
		if(pageSize!=null){
			pagination.setPageSize(pageSize);
		}else{
			pagination.setPageSize(20);
		}
		
		pagination.setCondition(dealerQo);
		
		pagination = this.dealerService.queryPagination(pagination);
		
		ModelAndView mav = new ModelAndView("/dealer/list.html");
		mav.addObject("pagination", pagination);
		mav.addObject("beginTimeStr", beginTimeStr);
		mav.addObject("endTimeStr", endTimeStr);
		mav.addObject("name", StringFilterUtil.reverseString(name));
		mav.addObject("status", status);
		mav.addObject("key", key);
		mav.addObject("linkMan", StringFilterUtil.reverseString(linkMan));
		mav.addObject("email", StringFilterUtil.reverseString(email));
		mav.addObject("telephone", StringFilterUtil.reverseString(telephone));
		return mav;
	}
	
	
	@RequestMapping("/dealer/toAdd")
	public ModelAndView toAdd(){
		ModelAndView mav = new ModelAndView("/dealer/add_dealer.html");
		List<Province> provinceList = provinceServiceImpl.queryList(new ProvinceQo());
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
	public String createDealer(@RequestParam(value="dealerName", required=true)String dealerName,
								@RequestParam(value="intro", required=true)String intro,
								@RequestParam(value="linkMan", required=true)String linkMan,
								@RequestParam(value="telephone", required=true)String telephone,
								@RequestParam(value="email", required=true)String email,
								@RequestParam(value="provinceId", required=true)String provinceId,
								@RequestParam(value="cityId", required=true)String cityId,
								@RequestParam(value="areaId", required=true)String areaId,
								@RequestParam(value="address", required=true)String address,
								@RequestParam(value="dealerUrl", required=true)String dealerUrl,
			                    @RequestParam(value="fileNames",required=false) String[] fileNames,
							    @RequestParam(value="imgNames",required=false) String[] imgNames,
							    @RequestParam(value="publishUrl", required=false)String publishUrl,
							    @RequestParam(value="publishAble", required=false)Boolean publishAble,
				                @RequestParam(value="accountType", required=false)Integer accountType,
				                @RequestParam(value="settlementFee", required=false)Double settlementFee,
				                @RequestParam(value="adminLoginName",required=false)String adminLoginName
				                ){
		
		PlatformCreateDealerCommand command = new PlatformCreateDealerCommand();
		JSONObject o = new JSONObject(); 
		
		// 是否有同名经销商或key
		DealerQo qo = new DealerQo();
		qo.setName(dealerName);
		if(this.dealerService.isExistDealer(qo)>0){
			o.put("message", "存在相同名称经销商");
			o.put("statusCode", DwzJsonResultUtil.STATUS_CODE_300);
			return o.toJSONString();
		}
		qo.setName(null);
		qo.setAdminLoginName(adminLoginName);
		qo.setAdminLoginNameLike(false);
		if (this.dealerService.isExistDealer(qo)>0) {
			o.put("message", "此登录账号已使用");
			o.put("statusCode", DwzJsonResultUtil.STATUS_CODE_300);
			return o.toJSONString();
		}
//		qo.setAdminLoginName(null);
//		qo.setAdminLoginNameLike(null);
//		if(this.dealerService.isExistDealer(qo)>0){
//			o.put("message", "存在相同经销商代码");
//			o.put("statusCode", DwzJsonResultUtil.STATUS_CODE_300);
//			return o.toJSONString();
//		}
		command.setName(dealerName);
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
		command.setSecretKey(UUIDGenerator.getUUID());
		command.setPublishUrl(publishUrl);
		command.setPublishAble(publishAble);
		command.setAccountType(accountType);
		command.setSettlementFee(settlementFee);
		command.setAdminLoginName(adminLoginName);
		command.setAdminPassword("1234567");
		
		String tempPath = SimpleFileUtil.getTempDirectoryPath();
		tempPath = tempPath.replace(File.separator+"temp",File.separator+"webapps"+File.separator+"group0");
		
		for(int i = 0,len = imgNames.length;i < len;i++){
			if (StringUtils.isBlank(imgNames[i])) {
				continue;
			}
			File tempFile = new File(tempPath+imgNames[i]);
			if(!SimpleFileUtil.isFile(tempFile)){
				if(i==0)
				o.put("message", "企业logo不能为空");
				else
				o.put("message", "资质图片不能为空");
				o.put("statusCode", DwzJsonResultUtil.STATUS_CODE_300);
				return o.toJSONString();
			}
			
			FdfsFileUtil.init();
			FdfsFileInfo fileInfo = FdfsFileUtil.upload(tempFile, new HashMap<String, String>());
			//设置元数据集
//			Map<String, String> metaMap = new HashMap<String, String>(1);
//			metaMap.put("md5",Md5FileUtil.getMD5(tempFile));
//			fileInfo.setMetaMap(metaMap);
			String uri = SystemConfig.imageHost+fileInfo.getUri();
			
			if(i == 0)
				command.setDealerLogo(uri);
			else if(i == 1)
				command.setBusinessLicense(uri);
			else if(i == 2)
				command.setTaxRegistrationCertificate(uri);
			else if(i == 3)
				command.setOrganizationCodeCertificate(uri);
			else
				command.setCorporateIDCard(uri);
			
			//删除临时图片
			tempFile.delete();
		}
		
		try {
			dealerService.platformCreateDealer(command);
			o.put("message", "保存成功");
			o.put("statusCode", DwzJsonResultUtil.STATUS_CODE_200);
			o.put("callbackType", DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT);
			o.put("navTabId", navTabId);
			
			for(int i = 0,len = imgNames.length;i < len;i++){
				File tempFile = new File(tempPath+imgNames[i]);
				//删除临时图片
				tempFile.delete();
			}
		} catch (DZPWException e) {
			
			e.printStackTrace();
			o.put("message", e.getMessage());
			o.put("statusCode", DwzJsonResultUtil.STATUS_CODE_500);
			o.put("callbackType", null);
			o.put("navTabId", null);
		} catch(Exception e){
			
			o.put("callbackType", null);
			o.put("navTabId", null);
			o.put("message", "修改失败");
			o.put("statusCode", DwzJsonResultUtil.STATUS_CODE_500);
		}
		return o.toJSONString();
	}
	
	
	/**
	 * @方法功能说明：跳转到修改经销商修改页面
	 * @修改者名字：yangkang
	 * @参数：@param id
	 * @参数：@return
	 * @return:ModelAndView
	 */
	@RequestMapping("/dealer/toUpdate")
	public ModelAndView toUpdate(@RequestParam(value="id", required = false)String id){
		
		ModelAndView mav = new ModelAndView("/dealer/newUpdate.html");
		
		DealerQo qo = new DealerQo(); 
		qo.setId(id);
		qo.setProvinceQo(new ProvinceQo());
		qo.setCityQo(new CityQo());
		qo.setAreaQo(new AreaQo());
		Dealer dealer = this.dealerService.queryUnique(qo);
		
		List<Province> provinceList = provinceServiceImpl.queryList(new ProvinceQo());
		mav.addObject("provinceList", provinceList);
		mav.addObject("dealer", dealer);
		return mav;
	}
	
	
	@ResponseBody
	@RequestMapping("/dealer/update")
	public String update(@RequestParam(value="dealerId", required=true)String dealerId,
						 @RequestParam(value="dealerName", required=true)String dealerName,
						 @RequestParam(value="intro", required=true)String intro,
						 @RequestParam(value="linkMan", required=true)String linkMan,
						 @RequestParam(value="telephone", required=true)String telephone,
						 @RequestParam(value="email", required=true)String email,
						 @RequestParam(value="provinceId", required=true)String provinceId,
						 @RequestParam(value="cityId", required=true)String cityId,
						 @RequestParam(value="areaId", required=true)String areaId,
						 @RequestParam(value="address", required=true)String address,
						 @RequestParam(value="dealerUrl", required=true)String dealerUrl,
			             @RequestParam(value="fileNames",required=false) String[] fileNames,
					     @RequestParam(value="imgNames",required=false) String[] imgNames,
					     @RequestParam(value="imgSrcs",required=false) String[] imgSrcs,
					     @RequestParam(value="secretKey", required=false)String secretKey,
					     @RequestParam(value="publishUrl", required=false)String publishUrl,
					     @RequestParam(value="publishAble", required=false)Boolean publishAble,
					     @RequestParam(value="settlementFee", required=false)Double settlementFee,
		                 @RequestParam(value="accountType", required=false)Integer accountType,
		                 @RequestParam(value="adminLoginName",required=false)String adminLoginName,
		                 @RequestParam(value="adminPassword",required=false)String adminPassword){
		
		PlatformModifyDealerCommand command = new PlatformModifyDealerCommand();
		JSONObject o = new JSONObject(); 
		
		//修改时候校验是否有同名经销商
		DealerQo qo = new DealerQo();
		qo.setName(dealerName);
		List<Dealer> list = this.dealerService.queryList(qo);
		if(list!=null){
			if(list.size()>1){
				o.put("message", "存在相同名称经销商");
				o.put("statusCode", DwzJsonResultUtil.STATUS_CODE_300);
				return o.toJSONString();
			}
			if(list.size()==1){
				if(!list.get(0).getId().equals(dealerId)){
					o.put("message", "存在相同名称经销商");
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
		command.setLinkMan(linkMan);
		command.setName(dealerName);
		command.setProvinceId(provinceId);
		command.setTelephone(telephone);
		command.setDealerLogo(imgSrcs[0]);
		command.setBusinessLicense(imgSrcs[1]);//营业执照
		command.setTaxRegistrationCertificate(imgSrcs[2]);//税务登记证
		command.setOrganizationCodeCertificate(imgSrcs[3]);//组织代码证
		command.setCorporateIDCard(imgSrcs[4]);//法人身份证
		command.setPublishUrl(publishUrl);
		command.setPublishAble(publishAble);
		command.setSettlementFee(settlementFee);
		command.setAccountType(accountType);
		command.setAdminLoginName(adminLoginName);
		command.setAdminPassword(adminPassword);
		
		String tempPath = SimpleFileUtil.getTempDirectoryPath();
		tempPath = tempPath.replace(File.separator+"temp",File.separator+"webapps"+File.separator+"group0");
		
		for(int i = 0,len = imgNames.length;i < len;i++){
			if(StringUtils.isBlank(imgNames[i]))
				continue;
				
			File tempFile = new File(tempPath+imgNames[i]);
			if(!SimpleFileUtil.isFile(tempFile)){
				if(i==0)
				o.put("message", "企业logo不能为空");
				else
				o.put("message", "资质图片不能为空");
				o.put("statusCode", DwzJsonResultUtil.STATUS_CODE_300);
				return o.toJSONString();
			}
			
			FdfsFileUtil.init();
			FdfsFileInfo fileInfo = FdfsFileUtil.upload(tempFile, new HashMap<String, String>());
			String uri = SystemConfig.imageHost+fileInfo.getUri();
			if(i == 0)
				command.setDealerLogo(uri);
			else if(i == 1)
				command.setBusinessLicense(uri);
			else if(i == 2)
				command.setTaxRegistrationCertificate(uri);
			else if(i == 3)
				command.setOrganizationCodeCertificate(uri);
			else
				command.setCorporateIDCard(uri);
			
			//删除临时图片
			tempFile.delete();
		}
		
		try {
			dealerService.platformModifyDealer(command);
			o.put("message", "修改成功");
			o.put("statusCode", DwzJsonResultUtil.STATUS_CODE_200);
			o.put("callbackType", DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT);
			o.put("navTabId", navTabId);
			
			for(int i = 0,len = imgNames.length;i < len;i++){
				File tempFile = new File(tempPath+imgNames[i]);
				//删除临时图片
				tempFile.delete();
			}
		} catch (DZPWException e) {
			
			e.printStackTrace();
			o.put("callbackType", null);
			o.put("navTabId", null);
			o.put("message", e.getMessage());
			o.put("statusCode", DwzJsonResultUtil.STATUS_CODE_500);
		} catch(Exception e){
			
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
	 * @修改者名字：yangkang
	 * @修改时间：2014-11-12下午5:50:37
	 * @修改内容：
	 * @参数：@param ids
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping("/dealer/usableBatch")
	public String usableBatch(@RequestParam(value="ids", required =false)String[] ids){
		
		String msg = "启用成功";
		String code = DwzJsonResultUtil.STATUS_CODE_200;
		String navTabId = "dealer";
		String callbackType = null;
		
		if(ids!=null && ids.length>0){
			try {
				this.dealerService.usableDealer(ids);
			} catch (DZPWException e) {
				e.printStackTrace();
				code = DwzJsonResultUtil.STATUS_CODE_500;
				msg = "启用失败";
				navTabId = null;
			}
		}
		
		return DwzJsonResultUtil.createJsonString(code, msg, callbackType, navTabId);
	}
	
	
	
	/**
	 * @方法功能说明：禁用/批量禁用
	 * @修改者名字：yangkang
	 * @修改时间：2014-11-13上午11:15:11
	 * @修改内容：
	 * @参数：@param ids
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping("/dealer/disableBatch")
	public String disableBatch(@RequestParam(value="ids", required =false)String[] ids){
		String msg = "禁用成功";
		String code = DwzJsonResultUtil.STATUS_CODE_200;
		String navTabId = "dealer";
		String callbackType = null;
		
		if(ids!=null && ids.length>0){
			try {
				this.dealerService.disableDealer(ids);
			} catch (DZPWException e) {
				e.printStackTrace();
				code = DwzJsonResultUtil.STATUS_CODE_500;
				msg = "禁用失败";
				navTabId = null;
			}
		}
		return DwzJsonResultUtil.createJsonString(code, msg, callbackType, navTabId);
	}
	
	
	
	/**
	 * @方法功能说明：删除/批量删除
	 * @修改者名字：yangkang
	 * @修改时间：2014-11-12下午5:50:59
	 * @修改内容：
	 * @参数：@param ids
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping("/dealer/delBatch")
	public String delBatch(@RequestParam(value="ids", required =false)String[] ids){
		String msg = "删除成功";
		String code = DwzJsonResultUtil.STATUS_CODE_200;
		String navTabId = "dealer";
		String callbackType = null;
		
		if(ids!=null&&ids.length>0){
			try {
				/**检查被删经销商是否启用*/
				ArrayList<String> l = new ArrayList<String>();
				for(String id : ids){
					l.add(id);
				}
				DealerQo dqo = new DealerQo();
				dqo.setIds(l);
				List<Dealer> dl = this.dealerService.queryList(dqo);
				
				for(Dealer d : dl){
					if(d.getBaseInfo().getStatus()==Dealer.DEALER_STATUS_USASBLE)
						return DwzJsonResultUtil.createJsonString("300", "不能删除启用经销商", callbackType, navTabId);
				}
				/***************/
				this.dealerService.removeDealer(ids);
			} catch (DZPWException e) {
				e.printStackTrace();
				code = DwzJsonResultUtil.STATUS_CODE_500;
				if(e.getCode()==DZPWException.DEALER_HAS_SALE_POLICY){
					msg = "有经销商已关联价格政策,删除失败";
				}else{
					msg = "删除失败";
				}
				navTabId = null;
			}
		}
		return DwzJsonResultUtil.createJsonString(code, msg, callbackType, navTabId);
	}
	
	
	
	/**
	 * @方法功能说明：查询经销商详情
	 * @修改者名字：yangkang
	 * @参数：@param dealerId
	 * @参数：@return
	 * @return:ModelAndView
	 */
	@RequestMapping("/dealer/viewInfo")
	public ModelAndView viewInfo(@RequestParam(value="dealerId", required=true)String dealerId){
		
		ModelAndView mav = new ModelAndView("/dealer/view_info.html");
		if(StringUtils.isNotBlank(dealerId)){
			DealerQo qo = new DealerQo(); 
			qo.setProvinceQo(new ProvinceQo());
			qo.setCityQo(new CityQo());
			qo.setAreaQo(new AreaQo());
			qo.setId(dealerId);
			Dealer dealer = dealerService.queryUnique(qo);
			if(dealer!=null)
				mav.addObject("dealer", dealer);
		}
		return mav;
	}
	
	
	
	@ResponseBody
	@RequestMapping("/dealer/ajax/findDealer")
	public String ajaxFindDealer(@RequestParam(value="name", required=true)String name){
		
		JSONObject o = new JSONObject();
		DealerQo dealerQo = new DealerQo();
		dealerQo.setStatus(1);//启用状态
		dealerQo.setName(name);
		dealerQo.setNameLike(true);
		
		List<Dealer> list = dealerService.queryList(dealerQo);
		if(list!=null && list.size()>0){
			o.put("status", "1");
			StringBuffer sb = new StringBuffer();
			sb.append("<ul>");
			for(Dealer s : list){
				sb.append("<li>").append(s.getBaseInfo().getName()).append("<input id=\"dealerId\" type=\"hidden\" value=\""+s.getId()+"\"/></li>");
			}
			sb.append("</ul>");
			o.put("html", sb.toString());
		}else{
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
			@RequestParam(value="file",required = false) CommonsMultipartFile file
		){
		Map<String,Object> remap = new HashMap<String, Object>(3);
		try {
			if(null == file || null == file.getFileItem())
				throw new DZPWException(DZPWException.NEED_SCENICSPOT_NOTEXIST,"图片不存在或已删除！");
			
			/**获取上传图片文件，拷贝至自定义临时目录下*/
			//判断图片类型
			String orginName = file.getFileItem().getName();
			String fileExt = StringUtils.substringAfterLast(orginName, ".").toLowerCase();
			if(!fileExt.endsWith("jpg") && !fileExt.endsWith("gif")
					&& !fileExt.endsWith("png") && !fileExt.endsWith("bmp"))
				throw new DZPWException(DZPWException.NEED_SCENICSPOT_NOILLEGALITY,"不能上传jpg、gif、png或bmp格式以外的图片");
			
			//验证图片大小
			double size = file.getSize()/1024.0/1024.0;
			if(size > FILE_MAX_SIZE)
				throw new DZPWException(DZPWException.NEED_SCENICSPOT_NOILLEGALITY,"上传图片不能大于"+FILE_MAX_SIZE+"M");
			
			//临时文件路径
			String tempPath = SimpleFileUtil.getPathToRename(orginName);
			tempPath = tempPath.replace(File.separator+"temp",File.separator+"webapps"+File.separator+"group0");
			File tempFile = new File(tempPath);
			
			FileUtils.copyInputStreamToFile(file.getInputStream(),tempFile);
			
			// 返回json数据
			remap.put("rs", 1);
			remap.put("imgName",tempFile.getName());
		} catch (Exception e) {
			String showMsg = "未知异常";
			if(e instanceof DZPWException){
				e = (DZPWException)e;
				showMsg = e.getMessage();
			}
			
			e.printStackTrace();
			hgLogger.error(DealerController.class,"dzpw-application","[imgUpload] 经销商-图片上传失败 ："+e.getMessage()+"\tfile:"+JSON.toJSONString(file, true),e);
			remap.put("rs",0);
			remap.put("msg",showMsg);
		}
		return JSON.toJSONString(remap,SerializerFeature.DisableCheckSpecialChar);
	}
	
	
}
