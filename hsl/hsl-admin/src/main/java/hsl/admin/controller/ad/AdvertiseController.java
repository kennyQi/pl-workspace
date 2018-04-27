package hsl.admin.controller.ad;

import hg.common.page.Pagination;
import hg.common.util.DwzJsonResultUtil;
import hsl.app.component.config.SysProperties;
import hg.log.util.HgLogger;
import hg.service.ad.command.ad.CreateAdCommand;
import hg.service.ad.command.ad.DeleteAdCommand;
import hg.service.ad.pojo.dto.ad.AdDTO;
import hg.service.ad.pojo.qo.ad.AdQO;
import hg.service.ad.spi.inter.AdService;
import hsl.app.service.spi.preferential.PreferentialServiceImpl;
import hsl.domain.model.mp.ad.SpecialOfferMp;
import hsl.pojo.command.CreatePreferentialCommand;
import hsl.pojo.command.UpdatePreferentialCommand;
import hsl.pojo.command.ad.CreatePCHotSecnicSpotCommand;
import hsl.pojo.command.ad.CreateSpecCommand;
import hsl.pojo.command.ad.DeleteSpecCommand;
import hsl.pojo.command.ad.HslCreateAdCommand;
import hsl.pojo.command.ad.HslDeleteAdCommand;
import hsl.pojo.command.ad.HslUpdateAdCommand;
import hsl.pojo.command.ad.HslUpdateAdStatusCommand;
import hsl.pojo.command.ad.UpdateSpecCommand;
import hsl.pojo.dto.ad.HslAdConstant;
import hsl.pojo.dto.ad.HslAdDTO;
import hsl.pojo.dto.ad.HslAdPositionDTO;
import hsl.pojo.dto.mp.PCScenicSpotDTO;
import hsl.pojo.dto.mp.SpecialOfferMpDTO;
import hsl.pojo.dto.preferential.PreferentialDTO;
import hsl.pojo.dto.programa.ProgramaDTO;
import hsl.pojo.exception.MPException;
import hsl.pojo.qo.ad.HslAdPositionQO;
import hsl.pojo.qo.ad.HslAdQO;
import hsl.pojo.qo.mp.HslMPScenicSpotQO;
import hsl.pojo.qo.mp.HslSpecOfferMpQO;
import hsl.pojo.qo.preferential.HslPreferentialQO;
import hsl.pojo.qo.programa.HslProgramaQO;
import hsl.spi.inter.ad.HslAdPositionService;
import hsl.spi.inter.ad.HslAdService;
import hsl.spi.inter.mp.MPScenicSpotService;
import hsl.spi.inter.programa.HslProgramaService;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 *               
 * @类功能说明：广告控制器
 * @类修改者：
 * @修改日期：2014年12月12日上午11:17:23
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhuxy
 * @创建时间：2014年12月12日上午11:17:23
 *
 */
@Controller
@RequestMapping("advertisement")
public class AdvertiseController {
	@Autowired
	private MPScenicSpotService mpScenicSpotService;
	
	@Autowired
	private HslAdService hslAdService;
	
	@Autowired
	private HslAdPositionService hslAdPositionService;
	
	@Autowired
	private  PreferentialServiceImpl preferentialServiceImpl;
	
	@Autowired
	private HslProgramaService hslProgramaService;
	@Autowired
	private AdService adService;
	
	public static final String PAGE_ADLIST = "advertisement/ad_list.html";
	public static final String PAGE_ADDAD = "advertisement/ad_addAd.html";
	public static final String PAGE_PATH_EDIT ="advertisement/ad_edit.html";
	public static final String PAGE_H5ADLIST = "advertisement/ad_h5List.html";
	public static final String PAGE_ADDH5AD = "advertisement/ad_addH5Ad.html";
	public static final String PAGE_PATH_H5EDIT = "advertisement/ad_h5Edit.html";
	//客户端类型 pc端
	public static int CLIENTTYPE_PC=1;
	//客户端类型H5端
	public static int CLIENTTYPE_H5=2;
	
	public  static final  String SPEC_POSITION_ID="hsl_tjmp";
	
	/**
	 * PC管理广告列表
	 * @return
	 */
	@RequestMapping("/list")
	public String list(@RequestParam(value="pageNum",required=false) Integer pageNo, 
			@RequestParam(value="numPerPage",required=false) Integer pageSize,
			Model model,HslAdQO hslAdQO){
		//查询广告位置
		HslAdPositionQO hslAdPositionQO = new HslAdPositionQO();
		//设置查询广告位置的所用工程的id
		hslAdPositionQO.setProjectId(SysProperties.getInstance().get("adProjectId"));
		hslAdPositionQO.setIsCommon(true);
		hslAdPositionQO.setClientType(CLIENTTYPE_PC);
		List<HslAdPositionDTO> positions = hslAdService.getPositionList(hslAdPositionQO);
		if(StringUtils.isBlank(hslAdQO.getPositionId()) && positions.size() > 0){
			hslAdQO.setPositionId(positions.get(0).getId());
		}
		Pagination pagination = new Pagination();
		pageNo=pageNo==null?new Integer(1):pageNo;
		pageSize=pageSize==null?new Integer(20):pageSize;
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		pagination.setCondition(hslAdQO);
		pagination = hslAdService.queryPagination(pagination);
		
		model.addAttribute("hslAdQO", hslAdQO);
		model.addAttribute("pagination", pagination);
		model.addAttribute("positions", positions);

		return PAGE_ADLIST;
	}
	
	/**
	 * H5管理广告列表
	 * @return
	 */
	@RequestMapping("/h5List")
	public String h5List(@RequestParam(value="pageNum",required=false) Integer pageNo, 
			@RequestParam(value="numPerPage",required=false) Integer pageSize,
			Model model,HslAdQO hslAdQO){
		//查询广告位置
		HslAdPositionQO hslAdPositionQO = new HslAdPositionQO();
		//设置查询广告位置的所用工程的id
		hslAdPositionQO.setProjectId(SysProperties.getInstance().get("adProjectId"));
		hslAdPositionQO.setIsCommon(true);
		hslAdPositionQO.setClientType(CLIENTTYPE_H5);
		List<HslAdPositionDTO> positions = hslAdService.getPositionList(hslAdPositionQO);
		if(StringUtils.isBlank(hslAdQO.getPositionId()) && positions.size() > 0){
			hslAdQO.setPositionId(positions.get(0).getId());
		}
		Pagination pagination = new Pagination();
		pageNo=pageNo==null?new Integer(1):pageNo;
		pageSize=pageSize==null?new Integer(20):pageSize;
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		pagination.setCondition(hslAdQO);
		pagination = hslAdService.queryPagination(pagination);
		
		model.addAttribute("hslAdQO", hslAdQO);
		model.addAttribute("pagination", pagination);
		model.addAttribute("positions", positions);
		
		return PAGE_H5ADLIST;
	}
	
	/**
	 * 跳转到添加广告页面
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/addAdView")
	public String addView(Model model, @RequestParam("positionId") String positionId){
		HslAdPositionQO hslAdPositionQO = new HslAdPositionQO();
		//设置查询广告位置的所用工程的id
		hslAdPositionQO.setProjectId(SysProperties.getInstance().get("adProjectId"));
		hslAdPositionQO.setId(positionId);
		HslAdPositionDTO hslAdPositionDTO = hslAdPositionService.queryAdPosition(hslAdPositionQO);
		model.addAttribute("position", hslAdPositionDTO);
		model.addAttribute("positionId", positionId);
		return PAGE_ADDAD;
	}
	
	/**
	 * 跳转到添加广告页面
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/addH5AdView")
	public String addH5AdView(Model model, @RequestParam("positionId") String positionId){
		HslAdPositionQO hslAdPositionQO = new HslAdPositionQO();
		//设置查询广告位置的所用工程的id
		hslAdPositionQO.setProjectId(SysProperties.getInstance().get("adProjectId"));
		hslAdPositionQO.setId(positionId);
		HslAdPositionDTO hslAdPositionDTO = hslAdPositionService.queryAdPosition(hslAdPositionQO);
		model.addAttribute("position", hslAdPositionDTO);
		model.addAttribute("positionId", positionId);
		return PAGE_ADDH5AD;
	}
	
	/**
	 * 添加PC广告
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("/addAd")
	public String addAd(Model model, HslCreateAdCommand command){
		// 1. 默认返回值
		String statusCode = DwzJsonResultUtil.STATUS_CODE_200;
		String message = "保存成功";
		String callbackType = DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT;
		try {
			hslAdService.createAd(command);
		} catch (Exception e) {
			HgLogger.getInstance().error("yuqz", "广告新增保存失败:command【" + JSON.toJSONString(command, true) + "】");
			e.printStackTrace();
			statusCode = DwzJsonResultUtil.STATUS_CODE_500;
			message = e.getMessage();
		}
		return DwzJsonResultUtil.createJsonString(statusCode, message, callbackType, "ADList");
	}
	
	/**
	 * 添加H5广告
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("/addH5Ad")
	public String addH5Ad(Model model, HslCreateAdCommand command){
		// 1. 默认返回值
		String statusCode = DwzJsonResultUtil.STATUS_CODE_200;
		String message = "保存成功";
		String callbackType = DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT;
		try {
			hslAdService.createAd(command);
		} catch (Exception e) {
			HgLogger.getInstance().error("yuqz", "广告新增保存失败:command【" + JSON.toJSONString(command, true) + "】");
			e.printStackTrace();
			statusCode = DwzJsonResultUtil.STATUS_CODE_500;
			message = e.getMessage();
		}
		return DwzJsonResultUtil.createJsonString(statusCode, message, callbackType, "H5ADList");
	}
	
	/**
	 * 
	 * @方法功能说明：跳转到修改广告页面
	 * @修改者名字：yuqz
	 * @修改时间：2014年12月23日下午2:40:26
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param id
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/editAd")
	public String editAd(HttpServletRequest request, HttpServletResponse response,
					   Model model, @RequestParam(value="id", required=false)String id){
		HslAdQO qo = new HslAdQO();
		qo.setId(id);
		HslAdDTO dto = hslAdService.queryAd(qo);
		model.addAttribute("ad", dto);
		return PAGE_PATH_EDIT;
	}
	
	/**
	 * 
	 * @方法功能说明：跳转到H5修改广告页面
	 * @修改者名字：yuqz
	 * @修改时间：2014年12月23日下午2:40:26
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param id
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/editH5Ad")
	public String editH5Ad(HttpServletRequest request, HttpServletResponse response,
					   Model model, @RequestParam(value="id", required=false)String id){
		HslAdQO qo = new HslAdQO();
		qo.setId(id);
		HslAdDTO dto = hslAdService.queryAd(qo);
		model.addAttribute("ad", dto);
		return PAGE_PATH_H5EDIT;
	}
	
	/**
	 * 
	 * @方法功能说明：修改广告
	 * @修改者名字：yuqz
	 * @修改时间：2014年12月23日下午2:38:20
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value="/modifyAd")
	public String modifyAd(HslUpdateAdCommand command){
		// 1. 默认返回值
		String statusCode = DwzJsonResultUtil.STATUS_CODE_200;
		String message = "修改成功";
		String callbackType = DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT;
		try {
			HslAdDTO dto = hslAdService.modifyAd(command);
			HgLogger.getInstance().info("chenxy", "修改广告成功"+JSON.toJSONString(dto));
		} catch (Exception e) {
			HgLogger.getInstance().error("yuqz", "修改广告失败:command【" + JSON.toJSONString(command, true) + "】");
			e.printStackTrace();
			statusCode = DwzJsonResultUtil.STATUS_CODE_500;
			message = e.getMessage();
		}
		return DwzJsonResultUtil.createJsonString(statusCode, message, callbackType, "ADList");
	}
	
	/**
	 * 
	 * @方法功能说明：修改H5广告
	 * @修改者名字：yuqz
	 * @修改时间：2014年12月23日下午2:38:20
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value="/modifyH5Ad")
	public String modifyH5Ad(HslUpdateAdCommand command){
		// 1. 默认返回值
		String statusCode = DwzJsonResultUtil.STATUS_CODE_200;
		String message = "修改成功";
		String callbackType = DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT;
		try {
			HslAdDTO dto = hslAdService.modifyAd(command);
			HgLogger.getInstance().info("chenxy", "修改H5广告成功"+JSON.toJSONString(dto));
		} catch (Exception e) {
			HgLogger.getInstance().error("yuqz", "修改广告失败:command【" + JSON.toJSONString(command, true) + "】");
			e.printStackTrace();
			statusCode = DwzJsonResultUtil.STATUS_CODE_500;
			message = e.getMessage();
		}
		return DwzJsonResultUtil.createJsonString(statusCode, message, callbackType, "H5ADList");
	}
	
	/**
	 * 
	 * @方法功能说明：修改广告状态
	 * @修改者名字：yuqz
	 * @修改时间：2014年12月26日下午6:08:37
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value="/modifyAdStatus")
	public String modifyAdStatus(HslUpdateAdStatusCommand command, HslAdQO hslAdQO, Model model){
		// 1. 默认返回值
		String statusCode = DwzJsonResultUtil.STATUS_CODE_200;
		String message = null;
		String callbackType = null;
		try {
			HslAdDTO dto = hslAdService.modifyAdStatus(command);
			HgLogger.getInstance().info("chenxy", "修改H5广告状态成功"+JSON.toJSONString(dto));
		} catch (Exception e) {
			HgLogger.getInstance().error("yuqz", "修改广告失败:command【" + JSON.toJSONString(command, true) + "】");
			e.printStackTrace();
			statusCode = DwzJsonResultUtil.STATUS_CODE_500;
			message = e.getMessage();
		}
		model.addAttribute("hslAdQO", hslAdQO);
		return DwzJsonResultUtil.createJsonString(statusCode, message, callbackType, "");
	}
	
	/**
	 * 
	 * @方法功能说明：修改H5广告状态
	 * @修改者名字：yuqz
	 * @修改时间：2014年12月26日下午6:08:37
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value="/modifyH5AdStatus")
	public String modifyH5AdStatus(HslUpdateAdStatusCommand command){
		// 1. 默认返回值
		String statusCode = DwzJsonResultUtil.STATUS_CODE_200;
		String message = null;
		String callbackType = null;
		try {
			HslAdDTO dto = hslAdService.modifyAdStatus(command);
			HgLogger.getInstance().info("chenxy", "修改H5广告状态成功"+JSON.toJSONString(dto));
		} catch (Exception e) {
			HgLogger.getInstance().error("yuqz", "修改广告失败:command【" + JSON.toJSONString(command, true) + "】");
			e.printStackTrace();
			statusCode = DwzJsonResultUtil.STATUS_CODE_500;
			message = e.getMessage();
		}
		return DwzJsonResultUtil.createJsonString(statusCode, message, callbackType, "");
	}
	
	/**
	 * 删除普通广告
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deleteAd")
	public String deleteAd(HslDeleteAdCommand command){
		try{
			hslAdService.deletAd(command);
		}catch(Exception e){
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_500, "广告删除失败", null, "");
		}
		return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "广告删除成功", null, "");
	}
	
	/**
	 * 删除H5普通广告
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deleteH5Ad")
	public String deleteH5Ad(HslDeleteAdCommand command){
		try{
			hslAdService.deletAd(command);
		}catch(Exception e){
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_500, "广告删除失败", null, "");
		}
		return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "广告删除成功", null, "");
	}
	
	/**
	 * 
	 * @方法功能说明：批量删除广告
	 * @修改者名字：yuqz
	 * @修改时间：2014年12月24日下午2:15:23
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping("/deleteAds")
	public String deleteAds(HslDeleteAdCommand command){
		List<String> ids = command.getIds();
		try{
			for(String id : ids){
				command.setAdId(id);
				hslAdService.deletAd(command);
			}
		}catch(Exception e){
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_500, "广告删除失败", null, "");
		}
		return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "广告删除成功", null, "");
	}
	
	/**
	 * 
	 * @方法功能说明：批量删除H5广告
	 * @修改者名字：yuqz
	 * @修改时间：2014年12月24日下午2:15:23
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping("/deleteH5Ads")
	public String deleteH5Ads(HslDeleteAdCommand command){
		List<String> ids = command.getIds();
		try{
			for(String id : ids){
				command.setAdId(id);
				hslAdService.deletAd(command);
			}
		}catch(Exception e){
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_500, "广告删除失败", null, "");
		}
		return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "广告删除成功", null, "");
	}
	
	
	/*
	 * 
	 ***********************************************************分割***********************************************************************/
	
	/**
	 * 特价门票列表
	 * @return
	 */
	@RequestMapping(value="/specList")
	public String specList(Model model,
			@RequestParam(value="pageNum",required=false) Integer pageNo, 
			@RequestParam(value="numPerPage",required=false) Integer pageSize,
			HslSpecOfferMpQO specOfferMpQO){
		Pagination pagination = new Pagination();
		pageNo=pageNo==null?new Integer(1):pageNo;
		pageSize=pageSize==null?new Integer(20):pageSize;
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		specOfferMpQO.setIsShow(true);
		
	
		specOfferMpQO.setPositionId(SPEC_POSITION_ID);
		pagination.setCondition(specOfferMpQO);
		pagination = mpScenicSpotService.getSpecialList(pagination);
		
		
		//栏目内容
		String programaId="3619a6b5-7ac4-4ae6-b62c-ae50e6ba6a52";
		HslProgramaQO qo=new HslProgramaQO();
		qo.setId(programaId);
		ProgramaDTO programaDTO=this.hslProgramaService.queryUnique(qo);
				
				
		model.addAttribute("pagination",pagination);
		model.addAttribute("specOfferMpQO", specOfferMpQO);
		
		model.addAttribute("programaId", programaId);
		model.addAttribute("programaContentDTOList", programaDTO.getProgramaContentList());
		return "spec/spec_list.html";
	}
	
	/**
	 * 历史特价门票列表
	 * @return
	 */
	@RequestMapping(value="/specListHistory")
	public String specListHistory(Model model,
			@RequestParam(value="pageNum",required=false) Integer pageNo, 
			@RequestParam(value="numPerPage",required=false) Integer pageSize,
			HslSpecOfferMpQO specOfferMpQO){
		Pagination pagination = new Pagination();
		pageNo=pageNo==null?new Integer(1):pageNo;
		pageSize=pageSize==null?new Integer(20):pageSize;
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		/*if(StringUtils.isBlank(specOfferMpQO.getPositionId())){
			specOfferMpQO.setPositionId(SpecialOfferMp.ZTLY);
		}*/
		specOfferMpQO.setPositionId(SPEC_POSITION_ID);
		specOfferMpQO.setIsShow(false);
		pagination.setCondition(specOfferMpQO);
		pagination = mpScenicSpotService.getSpecialList(pagination);
		
		//栏目内容
		String programaId="3619a6b5-7ac4-4ae6-b62c-ae50e6ba6a52";
		HslProgramaQO qo=new HslProgramaQO();
		qo.setId(programaId);
		ProgramaDTO programaDTO=this.hslProgramaService.queryUnique(qo);
				
		model.addAttribute("pagination",pagination);
		model.addAttribute("specOfferMpQO", specOfferMpQO);
		
		model.addAttribute("programaId", programaId);
		model.addAttribute("programaContentDTOList", programaDTO.getProgramaContentList());
		
		return "spec/hspec_list.html";
	}

	/**
	 * 添加特价门票
	 * @return
	 */
	@RequestMapping(value="/addSpec",method=RequestMethod.POST)
	@ResponseBody
	public String addSpec(Model model,CreateSpecCommand command){
		try{
			command.setPositionId(SPEC_POSITION_ID);
			mpScenicSpotService.createSpecScenic(command);
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "添加成功","openNew", "specList");
		}catch(Exception e){
			e.printStackTrace();
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_500, "添加失败",null, null);
		}
	}

	/**
	 * 添加特价门票
	 * @return
	 */
	@RequestMapping(value="/addSpec",method=RequestMethod.GET)
	public String addSpec(HttpServletRequest request,Model model){
		
		//栏目内容
		String programaId=request.getParameter("programaId");
		HslProgramaQO qo=new HslProgramaQO();
		if(StringUtils.isNotBlank(programaId)){
			qo.setId(programaId);
		}
		ProgramaDTO programaDTO=this.hslProgramaService.queryUnique(qo);
		model.addAttribute("programaContentDTOList", programaDTO.getProgramaContentList());
		
		return "spec/spec_add.html";
	}
	
	/**
	 * 修改特价门票
	 * @param model
	 * @param command
	 * @return
	 */
	@RequestMapping(value="/updateSpec",method=RequestMethod.GET)
	public String updateSpec(HttpServletRequest request,Model model,@RequestParam("id")String id,@RequestParam("tab")String tab){
		SpecialOfferMpDTO dto = mpScenicSpotService.getSpecialOfferMp(id);
		
		//栏目内容
		String programaId=request.getParameter("programaId");
		HslProgramaQO qo=new HslProgramaQO();
		if(StringUtils.isNotBlank(programaId)){
			qo.setId(programaId);
		}
		ProgramaDTO programaDTO=this.hslProgramaService.queryUnique(qo);
		model.addAttribute("programaContentDTOList", programaDTO.getProgramaContentList());
		
		
		model.addAttribute("dto",dto);
		model.addAttribute("tab", tab);
		return "spec/spec_edit.html";
	}
	
	/**
	 * 修改特价门票
	 * @param model
	 * @param command
	 * @return
	 */
	@RequestMapping(value="/updateSpec",method=RequestMethod.POST)
	@ResponseBody
	public String updateSpec(Model model,UpdateSpecCommand command,@RequestParam("tab")String tab){
		try {
			mpScenicSpotService.updateSpec(command);
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "修改成功",DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT,tab);
		} catch (Exception e) {
			e.printStackTrace();
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_500, "修改失败",DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT,null);
		}
	}
	
	/**
	 * 修改特价门票的状态
	 * @param command
	 * @return
	 */
	@RequestMapping(value="/changeSpecStatus")
	@ResponseBody
	public String updateSpecStatus(UpdateSpecCommand command){
		HslUpdateAdStatusCommand adStatusCommand = new HslUpdateAdStatusCommand();
		adStatusCommand.setId(command.getAdId());
		adStatusCommand.setIsShow(command.getIsShow());
		try{
			hslAdService.modifyAdStatus(adStatusCommand);
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "修改成功",null,"");
		}catch(Exception e){
			e.printStackTrace();
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_500, "修改失败",null,"");
		}
	}
	
	/**
	 * 立即展示或隐藏所有特价门票
	 * @param model
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="/showSpecs")
	@ResponseBody
	public String showSpecs(Model model,@RequestParam("ids") String ids,@RequestParam("op") Boolean op){
		HslUpdateAdStatusCommand adStatusCommand = new HslUpdateAdStatusCommand();
		adStatusCommand.setIsShow(op);
		if(StringUtils.isNotBlank(ids)){
			String[] idArray = ids.split(",");
			for(String id : idArray){
				//因为是分批调用远程服务，所以无法保证批量处理的原子性
				try {
					adStatusCommand.setId(id.split("#")[1]);
					hslAdService.modifyAdStatus(adStatusCommand);
				} catch (Exception e) {
					e.printStackTrace();
					return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_500, "修改失败",null,"");
				}
			}
		}
		return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "修改成功",null,"");
	}
	
	
	/**
	 * 删除特价门票
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/deleteSpec")
	@ResponseBody
	public String deleteSpec(Model model,DeleteSpecCommand command){
		try {
			mpScenicSpotService.deleteSpec(command);
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "删除成功",null,null);
		} catch (Exception e) {
			e.printStackTrace();
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_500, "删除失败",null,null);
		}
	}
	
	/**
	 * 批量删除特价门票
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="/deleteSpecs")
	@ResponseBody
	public String deleteSpecs(@RequestParam("ids")String ids){
		//删除特价门票的id由两个id组成{id}#{adId}
		String[] combs = ids.split(",");
		if(combs!=null&&combs.length>0){
			DeleteSpecCommand command = new DeleteSpecCommand();
			for(String comb : combs){
				try{
					String[] id = comb.split("#");
					command.setId(id[0]);
					command.setAdId(id[1]);
					mpScenicSpotService.deleteSpec(command);
				}catch(Exception e){
					e.printStackTrace();
					return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_500, "删除失败",null,"");
				}
			}
		}
		return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "删除成功",null,"");
	}
	
	
	/**
	 * 热门景点列表
	 * @param pageNo
	 * @param pageSize
	 * @param model
	 * @param hslAdQO
	 * @return
	 */
	@RequestMapping(value="/hotList")
	public String hotList(@RequestParam(value="pageNum",required=false) Integer pageNo, 
			@RequestParam(value="numPerPage",required=false) Integer pageSize,
			Model model,HslAdQO hslAdQO){
		hslAdQO.setPositionId(HslAdConstant.HOT);//默认查首页轮播广告列表
		Pagination pagination = new Pagination();
		pageNo=pageNo==null?new Integer(1):pageNo;
		pageSize=pageSize==null?new Integer(20):pageSize;
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		pagination.setCondition(hslAdQO);
		pagination = hslAdService.queryPagination(pagination);//这个方法需要重写
		model.addAttribute("hslAdQO", hslAdQO);
		model.addAttribute("pagination", pagination);
		return "hot/hot_list.html";
	}

	/**
	 * 添加热门景点推荐
	 * @return
	 */
	@RequestMapping(value="/addHot",method=RequestMethod.GET)
	public String addHot(Model model){
		model.addAttribute("positionId",HslAdConstant.HOT);
		return "hot/hot_add.html";
	}
	
	/**
	 * 添加热门景点推荐
	 * @return
	 */
	@RequestMapping(value="/addHot",method=RequestMethod.POST)
	@ResponseBody
	public String addHot(Model model,CreatePCHotSecnicSpotCommand command){
		if(StringUtils.isBlank(command.getTitle())){
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_500, "热门景点添加失败，没有此景区信息", "closeCurrent", "hotcurrentspot");
		}
		List<PCScenicSpotDTO> scenicSpotDTOList=queryScenicSpot(command.getTitle());
		if (scenicSpotDTOList != null && scenicSpotDTOList.size() > 0) {
			// 要设置为热门景点的景点信息
			PCScenicSpotDTO scenicSpotDTO = scenicSpotDTOList.get(0);
			
//			//代码保存文件获取imageinfo
//			File file = null;
//			try {
//				file = DownloadUtil.saveToFile(scenicSpotDTO.getScenicSpotBaseInfo().getImage());
//			} catch (MalformedURLException e1) {
//				e1.printStackTrace();
//			} catch (URISyntaxException e1) {
//				e1.printStackTrace();
//			} catch (IOException e1) {
//				e1.printStackTrace();
//			}
//			
//			if(file==null){
//				return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_500, "热门景点图片下载失败", "closeCurrent", "hotcurrentspot");
//			}
//			
//			// 2. 上传图片
//			FdfsFileInfo fileInfo = null;
//			String imageName = file.getName();
//			String imageType = imageName.substring(imageName.lastIndexOf(".") + 1);
//			try {
//				FdfsFileUtil.init();
//				fileInfo = FdfsFileUtil.upload(new FileInputStream(file) , imageType, null);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			String imageUrl = SysProperties.getInstance().get("fileUploadPath") + fileInfo.getUri();
//			command.setImageInfo(JSON.toJSONString(fileInfo));
//			command.setImagePath(imageUrl);
//			command.setFileName(imageName);
			
			command.setAddress(scenicSpotDTO.getScenicSpotGeographyInfo().getAddress());
			command.setAlias(scenicSpotDTO.getScenicSpotBaseInfo().getAlias());
			command.setCity(scenicSpotDTO.getScenicSpotGeographyInfo().getCityName());
			command.setCreateDate(new Date());
			command.setGrade(scenicSpotDTO.getScenicSpotBaseInfo().getGrade());
			command.setImage(scenicSpotDTO.getScenicSpotBaseInfo().getImage());
			command.setIntro(scenicSpotDTO.getScenicSpotBaseInfo().getIntro());
			command.setName(scenicSpotDTO.getScenicSpotBaseInfo().getName());
			command.setProvince(scenicSpotDTO.getScenicSpotGeographyInfo().getProvinceName());
			command.setScenicSpotId(scenicSpotDTO.getId());
			command.setTraffic(scenicSpotDTO.getScenicSpotGeographyInfo().getTraffic());
			
			command.setScenicSpotId(scenicSpotDTO.getId());
			try {
				mpScenicSpotService.createPCHot(command);
			} catch (MPException e) {
				e.printStackTrace();
				return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_500, e.getMessage(), "closeCurrent", "hotcurrentspot");
			}
			return DwzJsonResultUtil.createJsonString("200", "热门景点添加成功", "openNew", "hotList");
		}
	
		
		return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_500, "热门景点添加失败，没有此景区信息", "closeCurrent", "hotcurrentspot");
	}
	
	/**
	 * 
	 * @方法功能说明：修改热门景点
	 * @修改者名字：yuqz
	 * @修改时间：2014年12月23日下午2:38:20
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value="/modifyHot")
	public String modifyHot(HslUpdateAdStatusCommand command){
		// 1. 默认返回值
		String statusCode = DwzJsonResultUtil.STATUS_CODE_200;
		String message = null;
		String callbackType = null;
		try {
			HslAdDTO dto = hslAdService.modifyAdStatus(command);
			HgLogger.getInstance().info("chenxy", "修改H5广告成功"+JSON.toJSONString(dto));
		} catch (Exception e) {
			HgLogger.getInstance().error("yuqz", "修改广告失败:command【" + JSON.toJSONString(command, true) + "】");
			e.printStackTrace();
			statusCode = DwzJsonResultUtil.STATUS_CODE_500;
			message = e.getMessage();
		}
		return DwzJsonResultUtil.createJsonString(statusCode, message, callbackType, "hotList");
	}
	
	/**
	 * 删除热门景点
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deleteHot")
	public String deleteHot(HslDeleteAdCommand command){
		try{
			hslAdService.deletAd(command);
		}catch(Exception e){
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_500, "广告删除失败", null, "hotList");
		}
		return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "广告删除成功", null, "hotList");
	}
	
	/**
	 * 
	 * @方法功能说明：批量删除热门景点
	 * @修改者名字：yuqz
	 * @修改时间：2014年12月24日下午2:15:23
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping("/deleteHots")
	public String deleteHots(HslDeleteAdCommand command){
		List<String> ids = command.getIds();
		command.setPositionId(HslAdConstant.HOT);
		try{
			for(String id : ids){
				command.setAdId(id);
				hslAdService.deletAd(command);
			}
		}catch(Exception e){
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_500, "广告删除失败", null, "hotList");
		}
		return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "广告删除成功", null, "hotList");
	}
	
	/**
	 * 通过slfx接口查询景区信息
	 * @param spotsname
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<PCScenicSpotDTO> queryScenicSpot(String spotsname){
		// 按照景区名称精确查询景区信息
		HslMPScenicSpotQO mpScenicSpotsQO = new HslMPScenicSpotQO();
		mpScenicSpotsQO.setByName(true);
		mpScenicSpotsQO.setByArea(false);
		mpScenicSpotsQO.setContent(spotsname);
		mpScenicSpotsQO.setTcPolicyNoticeFetchAble(true);

		List<PCScenicSpotDTO> scenicSpotDTOList=new ArrayList<PCScenicSpotDTO>();
		Map<String,Object> scenicSpotMap = new HashMap<String,Object>();
		try {
			// slfx返回的景区信息
			scenicSpotMap = mpScenicSpotService.queryScenicSpot(mpScenicSpotsQO);
			scenicSpotDTOList= (List<PCScenicSpotDTO>) scenicSpotMap.get("dto");
		} catch (MPException e) {
			HgLogger.getInstance().error("zhangka", "MPHotScenicSpotController->queryScenicSpot->exception:" + HgLogger.getStackTrace(e));
			return null;
		}
		
		return scenicSpotDTOList;
	}
	/**
	 * 
	 * @方法功能说明：查询特惠专区列表
	 * @创建者名字：zhaows
	 * @创建时间：2015年4月29日上午9:18:46
	 * @参数：@param qo
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("/preferentiallist")
	public String queryPagination(HslPreferentialQO qo,HttpServletRequest request,Model model,
			@RequestParam(value="pageNum",required=false) Integer pageNo, 
			@RequestParam(value="numPerPage",required=false) Integer pageSize){
		try {
			Pagination pagination = new Pagination();
			pageNo=pageNo==null?new Integer(1):pageNo;
			pageSize=pageSize==null?new Integer(20):pageSize;
			pagination.setPageNo(pageNo);
			pagination.setPageSize(pageSize);
			qo.setIsShow(true);
			pagination.setCondition(qo);
			pagination=preferentialServiceImpl.queryPagination(pagination);
			model.addAttribute("pagination", pagination);
			model.addAttribute("HslPreferentialQO", qo);
		} catch (Exception e) {
			HgLogger.getInstance().error("zhaows", "queryPagination-->特惠专区分页查询失败" + HgLogger.getStackTrace(e));
			e.printStackTrace();
		}
			return "preferential/preferential_list.html";
		
	}
	/**
	 * 
	 * @方法功能说明：查询历史特惠专区列表
	 * @创建者名字：zhaows
	 * @创建时间：2015年4月29日上午14:55:46
	 * @参数：@param qo
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("/historypreferentiallist")
	public String queryhistoryPagination(HslPreferentialQO qo,HttpServletRequest request,Model model,
			@RequestParam(value="pageNum",required=false) Integer pageNo, 
			@RequestParam(value="numPerPage",required=false) Integer pageSize){
		try {
			Pagination pagination = new Pagination();
			pageNo=pageNo==null?new Integer(1):pageNo;
			pageSize=pageSize==null?new Integer(20):pageSize;
			pagination.setPageNo(pageNo);
			pagination.setPageSize(pageSize);
			qo.setIsShow(false);
			pagination.setCondition(qo);
			pagination=preferentialServiceImpl.queryPagination(pagination);
			model.addAttribute("pagination", pagination);
			model.addAttribute("HslPreferentialQO", qo);
		} catch (Exception e) {
			HgLogger.getInstance().error("zhaows", "queryPagination-->查询历史特惠专区列表" + HgLogger.getStackTrace(e));
			e.printStackTrace();
		}
		return "preferential/historypreferential_list.html";
	
		}
	/**
	 * 
	 * @方法功能说明：跳转到添加特惠专区页面
	 * @创建者名字：zhaows
	 * @创建时间：2015年4月29日上午9:19:12
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/addPreferential",method=RequestMethod.GET)
	public String addPreferential(Model model){
		
		//model.addAttribute("typeMap",SpecialOfferMp.getTypeMap());   任风修改   暂时屏蔽
		return "preferential/preferential_add.html";
	}
	/**
	 * 
	 * @方法功能说明：特惠专区添加
	 * @创建者名字：zhaows
	 * @创建时间：2015年4月29日上午9:34:22
	 * @参数：@param model
	 * @参数：@param command
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/addPreferential",method=RequestMethod.POST)
	@ResponseBody
	public String addPreferential(Model model,CreatePreferentialCommand command){
		try{
			preferentialServiceImpl.createPreferential(command);
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "添加成功","openNew", "preferentialList");
		}catch(Exception e){
			e.printStackTrace();
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_500, "添加失败",null, null);
		}
	}
	/**
	 * 
	 * @方法功能说明：修改特惠专区状态
	 * @创建者名字：zhaows
	 * @创建时间：2015年4月29日下午3:13:57
	 * @参数：@param command
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/updatePreferentialStatus")
	@ResponseBody
	public String updatePreferentialStatus(UpdatePreferentialCommand command){
		HslUpdateAdStatusCommand adStatusCommand = new HslUpdateAdStatusCommand();
		adStatusCommand.setId(command.getAdId());
		adStatusCommand.setIsShow(command.getIsShow());
		try{
			hslAdService.modifyAdStatus(adStatusCommand);
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "修改成功",null,"");
		}catch(Exception e){
			e.printStackTrace();
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_500, "修改失败",null,"");
		}
	}
	/**
	 * 
	 * @方法功能说明：删除特惠专区
	 * @创建者名字：zhaows
	 * @创建时间：2015年4月29日上午10:34:53
	 * @参数：@param model
	 * @参数：@param command
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/deletePreferential")
	@ResponseBody
	public String deletePreferential(Model model,UpdatePreferentialCommand command){
		try {
			preferentialServiceImpl.deletePreferential(command);
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "删除成功",null,"");
		} catch (Exception e) {
			e.printStackTrace();
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_500, "删除失败",null,"");
		}
	}
	/**
	 * 
	 * @方法功能说明：修改特惠专区
	 * @创建者名字：zhaows
	 * @创建时间：2015年4月29日下午3:13:39
	 * @参数：@param model
	 * @参数：@param id
	 * @参数：@param tab
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/updatePreferential",method=RequestMethod.GET)
	public String updatePreferential(Model model,@RequestParam("id")String id,@RequestParam("tab")String tab){
		HslPreferentialQO qo=new HslPreferentialQO();
		qo.setId(id);
		PreferentialDTO dto = preferentialServiceImpl.queryUnique(qo);
		model.addAttribute("dto",dto);
		model.addAttribute("tab", tab);
		return "preferential/preferential_edit.html";
	}
	
	/**
	 * 
	 * @方法功能说明：修改特惠专区
	 * @创建者名字：zhaows
	 * @创建时间：2015年4月29日下午4:13:39
	 * @参数：@param model
	 * @参数：@param id
	 * @参数：@param tab
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/updatePreferential",method=RequestMethod.POST)
	@ResponseBody
	public String updatePreferential(Model model,UpdatePreferentialCommand command,@RequestParam("tab")String tab){
		try {
			preferentialServiceImpl.modifyPreferential(command);
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "修改成功",DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT,"preferentiallist");
		} catch (Exception e) {
			e.printStackTrace();
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_500, "修改失败",DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT,null);
		}
	}
	/**
	 * 
	 * @方法功能说明：删除特惠专区
	 * @创建者名字：zhaows
	 * @创建时间：2015年4月29日下午1:49:49
	 * @参数：@param ids
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/deletePreferentials")
	@ResponseBody
	public String deletePreferentials(@RequestParam("ids")String ids){
		//删除特价门票的id由两个id组成{id}#{adId}
		String[] combs = ids.split(",");
		if(combs!=null&&combs.length>0){
			UpdatePreferentialCommand command = new UpdatePreferentialCommand();
			for(String comb : combs){
				try{
					String[] id = comb.split("#");
					command.setId(id[0]);
					command.setAdId(id[1]);
					preferentialServiceImpl.deletePreferential(command);
				}catch(Exception e){
					e.printStackTrace();
					return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_500, "删除失败",null,"");
				}
			}
		}
		return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "删除成功",null,"");
	}
	/**
	 * 
	 * @方法功能说明：立即展示或隐藏所有特惠专区
	 * @创建者名字：zhaows
	 * @创建时间：2015年4月29日下午2:19:16
	 * @参数：@param model
	 * @参数：@param ids
	 * @参数：@param op
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/showPreferential")
	@ResponseBody
	public String showPreferential(Model model,@RequestParam("ids") String ids,@RequestParam("op") Boolean op){
		HslUpdateAdStatusCommand adStatusCommand = new HslUpdateAdStatusCommand();
		adStatusCommand.setIsShow(op);
		if(StringUtils.isNotBlank(ids)){
			String[] idArray = ids.split(",");
			for(String id : idArray){
				//因为是分批调用远程服务，所以无法保证批量处理的原子性
				try {
					adStatusCommand.setId(id.split("#")[1]);
					hslAdService.modifyAdStatus(adStatusCommand);
				} catch (Exception e) {
					e.printStackTrace();
					return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_500, "修改失败",null,"");
				}
			}
		}
		return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "修改成功",null,"");
	}
	/**
	 * 
	 * @方法功能说明：线路列表页广告
	 * @创建者名字：zhaows
	 * @创建时间：2015年5月26日上午10:51:53
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/getlList")
	public String getlList(HttpServletRequest request,Model model,
			@RequestParam(value="pageNum",required=false) Integer pageNo, 
			@RequestParam(value="numPerPage",required=false) Integer pageSize,
			AdQO adQO){
		try {
			Pagination pagination = new Pagination();
			pageNo=pageNo==null?new Integer(1):pageNo;
			pageSize=pageSize==null?new Integer(20):pageSize;
			pagination.setPageNo(pageNo);
			pagination.setPageSize(pageSize);
			//设置广告未Id
			
			if(StringUtils.isBlank(adQO.getPositionId())){
				//默认头部广告
				adQO.setPositionId("hsl_spec_ztly");
			}
			pagination.setCondition(adQO);
			pagination=adService.queryPagination(pagination);
			HgLogger.getInstance().info("zhaows", "线路列表页广告查询成功"+JSON.toJSONString(pagination));
			Map<String,String> TYPEMAP = new HashMap<String,String>();
			TYPEMAP.put("hsl_spec_ztly", "头部广告");
			TYPEMAP.put("lxs_app", "底部广告");
			model.addAttribute("lineList", TYPEMAP);
			model.addAttribute("pagination", pagination);
			model.addAttribute("adQO", adQO);
		} catch (Exception e) {
			e.printStackTrace();
			HgLogger.getInstance().error("zhaows", "getlList-->线路列表页广告查询失败" + HgLogger.getStackTrace(e));
		}
		return "lineListAd/list_lineListAd.html";
	}
	/**
	 * 
	 * @方法功能说明：返回到添加页面
	 * @创建者名字：zhaows
	 * @创建时间：2015年5月26日下午2:31:11
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/addLineAd",method=RequestMethod.GET)
	public String addLineAd(Model model){
		Map<String,String> TYPEMAP = new HashMap<String,String>();
			TYPEMAP.put("hsl_spec_ztly", "头部广告");
			TYPEMAP.put("lxs_app", "底部广告");

		model.addAttribute("lineList", TYPEMAP);
		return "lineListAd/add_lineAd.html";
	}
	/**
	 * 
	 * @方法功能说明：添加线路list页面广告
	 * @创建者名字：zhaows
	 * @创建时间：2015年5月26日下午2:39:54
	 * @参数：@param model
	 * @参数：@param command
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/addLineAd",method=RequestMethod.POST)
	@ResponseBody
	public String addLineAd(Model model,CreateAdCommand command){
		try{
			adService.createAd(command);
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "添加成功","openNew", "getlList");
		}catch(Exception e){
			e.printStackTrace();
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_500, "添加失败",null, null);
		}
	}
	/**
	 * 
	 * @方法功能说明：删除线路list页面广告
	 * @创建者名字：zhaows
	 * @创建时间：2015年5月26日下午4:19:41
	 * @参数：@param model
	 * @参数：@param command
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/deleteLineAd")
	@ResponseBody
	public String deleteLineAd(@RequestParam("ids")String ids){
		//删除特价门票的id由两个id组成{id}#{adId}
				String[] combs = ids.split(",");
				if(combs!=null&&combs.length>0){
					DeleteAdCommand command = new DeleteAdCommand();
					for(String comb : combs){
						try{
							String[] id = comb.split("#");
							command.setAdId(id[0]);
							adService.deletAd(command);
						}catch(Exception e){
							e.printStackTrace();
							return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_500, "删除失败",null,"");
						}
					}
				}
				return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "删除成功",null,"");
	}
	/**
	 * 
	 * @方法功能说明：跳转到修改线路list页面广告
	 * @创建者名字：zhaows
	 * @创建时间：2015年5月26日下午4:34:49
	 * @参数：@param model
	 * @参数：@param id
	 * @参数：@param tab
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/updateLineAd",method=RequestMethod.GET)
	public String updateLineAd(Model model,@RequestParam("id")String id,@RequestParam("tab")String tab){
		AdQO qo=new AdQO();
		qo.setId(id);
		AdDTO adDTO=adService.queryUnique(qo);
		Map<String,String> TYPEMAP = new HashMap<String,String>();
		TYPEMAP.put("hsl_spec_ztly", "头部广告");
		TYPEMAP.put("lxs_app", "底部广告");
		model.addAttribute("lineList", TYPEMAP);
		model.addAttribute("adDTO",adDTO);
		model.addAttribute("tab", tab);
		return "lineListAd/edit_lineListAd.html";
	}
}
