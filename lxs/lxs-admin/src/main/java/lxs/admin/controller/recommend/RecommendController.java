package lxs.admin.controller.recommend;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lxs.app.service.app.RecommendService;
import lxs.app.service.line.LineService;
import lxs.app.service.mp.ScenicSpotService;
import lxs.domain.model.app.Recommend;
import lxs.domain.model.line.Line;
import lxs.domain.model.mp.ScenicSpot;
import lxs.pojo.command.app.AddRecommendCommand;
import lxs.pojo.command.app.DeleteRecommendCommand;
import lxs.pojo.command.app.ModifyRecommendCommand;
import lxs.pojo.command.app.ModifyRecommendSortCommand;
import lxs.pojo.dto.app.RecommendDTO;
import lxs.pojo.dto.line.LineDTO;
import lxs.pojo.dto.mp.ScenicSpotDTO;
import lxs.pojo.exception.app.RecommendException;
import lxs.pojo.qo.app.RecommendQO;
import lxs.pojo.qo.line.LineQO;
import lxs.pojo.qo.mp.ScenicSpotQO;
import hg.common.page.Pagination;
import hg.common.util.DwzJsonResultUtil;
import hg.common.util.EntityConvertUtils;
import hg.common.util.SysProperties;
import hg.common.util.file.FdfsFileInfo;
import hg.log.util.HgLogger;
import hg.service.image.command.image.CreateImageCommand;
import hg.service.image.pojo.dto.ImageDTO;
import hg.service.image.pojo.exception.ImageException;
import hg.service.image.pojo.qo.ImageQo;
import hg.service.image.spi.inter.ImageService_1;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("/recommend")
public class RecommendController {

	@Autowired
	private RecommendService recommendService;
	@Autowired
	private ImageService_1 spiImageServiceImpl_1;
	@Autowired
	private LineService lineService;
	@Autowired
	private ScenicSpotService scenicSpotService;
	
	/**
	 * @throws ParseException 
	 * @Title: recommendList 
	 * @author guok
	 * @Description: 推荐列表
	 * @Time 2015年9月14日下午2:18:43
	 * @param pageNo
	 * @param pageSize
	 * @param model
	 * @param recommendQO
	 * @return String 设定文件
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/recommendList")
	public String recommendList(HttpServletRequest request,@RequestParam(value="pageNum",required=false) Integer pageNo, 
			@RequestParam(value="numPerPage",required=false) Integer pageSize,
			Model model,RecommendQO recommendQO) throws ParseException {
		
		HgLogger.getInstance().info("lxs_dev", "【recommendList】"+"【recommendQO】" + JSON.toJSONString(recommendQO));
		if(pageNo==null){
			pageNo=1;
		}
		if(pageSize==null){
			pageSize=20;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (recommendQO.getStart() != null&& StringUtils.isNotBlank(recommendQO.getStart())) {
			recommendQO.setInCreateDate(sdf.parse(recommendQO.getStart()));
		}else {
			recommendQO.setInCreateDate(null);
		}
		
		if (recommendQO.getEnd() != null && StringUtils.isNotBlank(recommendQO.getEnd())) {
			recommendQO.setToCreateDate(sdf.parse(recommendQO.getEnd()));
			Calendar cal = Calendar.getInstance();
			cal.setTime(recommendQO.getToCreateDate());
			cal.add(Calendar.DATE, 1);
			recommendQO.setToCreateDate(cal.getTime());
		}else {
			recommendQO.setToCreateDate(null);
		}
		
		Pagination pagination = new Pagination();
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		pagination.setCondition(recommendQO);
		pagination= recommendService.queryPagination(pagination);
		List<RecommendDTO> recommendDTOs = new ArrayList<RecommendDTO>();
		for (Recommend recommend : (List<Recommend>)pagination.getList()) {
			RecommendDTO recommendDTO = new RecommendDTO();
			recommendDTO.setRecommendID(recommend.getId());
			if (recommend.getRecommendType() == Recommend.LINK) {
				recommendDTO.setRecommendAction(recommend.getRecommendAction());
			}else if (recommend.getRecommendType() == Recommend.LINE) {
				Line line = lineService.get(recommend.getRecommendAction());
				if(line==null){
					break;
				}
				recommendDTO.setRecommendAction(line.getBaseInfo().getName());
			}else if(recommend.getRecommendType() == Recommend.MENPIAO){
				ScenicSpot scenicSpot = scenicSpotService.get(recommend.getRecommendAction());
				if (scenicSpot == null) {
					break;
				}
				recommendDTO.setRecommendAction(scenicSpot.getBaseInfo().getName());
			}
			recommendDTO.setCreateDate(recommend.getCreateDate());
			recommendDTO.setNote(recommend.getNote());
			recommendDTO.setStatus(recommend.getStatus());
			recommendDTO.setRecommendName(recommend.getRecommendName());
			recommendDTO.setRecommendType(recommend.getRecommendType());
			recommendDTOs.add(recommendDTO);
			
		}
		pagination.setList(recommendDTOs);
		model.addAttribute("recommendQO", recommendQO);
		model.addAttribute("pagination", pagination);
		
		return "/recommend/recommendList.html";
	}
	
	/**
	 * @throws IOException 
	 * @Title: recommendSum 
	 * @author guok
	 * @Description: 查询推荐条数
	 * @Time 2015年9月14日下午2:26:43
	 * @param request
	 * @param model
	 * @return String 设定文件
	 * @throws
	 */
	@ResponseBody
	@RequestMapping("/recommendSum")
	public String recommendSum(HttpServletRequest request,HttpServletResponse response,Model model) throws IOException {
		Integer sum = 0;
		sum = recommendService.queryCount(new RecommendQO());
		
		if (sum>29) {
			response.setCharacterEncoding("UTF-8"); 
			response.getWriter().write("error"); 
			return null;
		}else{
			response.getWriter().write("/recommend/addRecommend.html"); 
			return null;
		}
		
	}
	
	/**
	 * @Title: addRecommend 
	 * @author guok
	 * @Description: 跳转添加
	 * @Time 2015年9月14日下午4:46:44
	 * @param request
	 * @param response
	 * @return String 设定文件
	 * @throws
	 */
	@RequestMapping("/addRecommend")
	public String addRecommend(HttpServletRequest request,HttpServletResponse response,Model model) {
		return "/recommend/addRecommend.html";
	}
	
	/**
	 * @throws IOException 
	 * @Title: queryLineList 
	 * @author guok
	 * @Description: 线路列表
	 * @Time 2015年9月15日上午9:19:25
	 * @param request
	 * @param model
	 * @param lineQO
	 * @param pageNo
	 * @param pageSize
	 * @return Pagination 设定文件
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/lineList")
	public String queryLineList(
			HttpServletRequest request,
			Model model,HttpServletResponse response,
			@ModelAttribute LineQO lineQO,
			@RequestParam(value = "pageNum", required = false) Integer pageNo,
			@RequestParam(value = "numPerPage", required = false) Integer pageSize,
			@RequestParam(value = "handleType", required = false) String handleType,
			@RequestParam(value = "recommendID", required = false) String recommendID) throws IOException {
		if (pageNo == null) {
			pageNo = 1;
		}
		if (pageSize == null) {
			pageSize = 20;
		}
		if(lineQO.getForSale()==0){
			lineQO.setForSaletype("yes");
		}
		
		RecommendQO recommendQO = new RecommendQO();
		recommendQO.setRecommendID(recommendID);
		Recommend recommend = recommendService.queryUnique(recommendQO);
		if (recommend == null) {
			recommend = new Recommend();
		}
		
		lineQO.setLocalStatus(LineQO.NOT_DEL);
		lineQO.setSort(LineQO.QUERY_WITH_TOP);
		Pagination pagination = new Pagination();
		pagination.setCondition(lineQO);
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		pagination = lineService.queryPagination(pagination);
		model.addAttribute("lineQO",lineQO);
		List<LineDTO> lines = EntityConvertUtils.convertEntityToDtoList(
				(List<LineDTO>) pagination.getList(), LineDTO.class);
		
		if (StringUtils.isNotBlank(recommendID) && lines != null
				&& lines.size() > 1) {
			for (LineDTO dto : lines) {
				if (recommendID.equals(dto.getId())) {
					lines.set(0, dto);
				}
			}
		}
		
		model.addAttribute("recommend", recommend);
		model.addAttribute("lines", lines);
		model.addAttribute("pagination", pagination);
		model.addAttribute("handleType", handleType);
		return "/recommend/lineLists.html";
	}
	
	/**
	 * @Title: saveRecomend 
	 * @author guok
	 * @Description: 保存推荐
	 * @Time 2015年9月15日上午9:16:25
	 * @param request
	 * @param response
	 * @param model
	 * @param command
	 * @return String 设定文件
	 * @throws
	 */
	@ResponseBody
	@RequestMapping("/saveRecomend")
	public String saveRecomend(HttpServletRequest request,HttpServletResponse response,Model model,
			@ModelAttribute AddRecommendCommand command) {
		
		HgLogger.getInstance().info("lxs_dev", "【saveRecomend】"+"【AddRecommendCommand】：" + JSON.toJSONString(command));
		// 1. 默认返回值
		String statusCode = DwzJsonResultUtil.STATUS_CODE_200;
		String message = "保存成功";
		String callbackType = DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT;
		
		
		CreateImageCommand createImageCommand = new CreateImageCommand();
		createImageCommand.setTitle(command.getRecommendName());
		//利旧URL存储file的json
		createImageCommand.setFileInfo(command.getImageInfo());
		createImageCommand.setFileName(command.getFileName());
		createImageCommand.setRemark("旅行社APP推荐");
		createImageCommand.setUseTypeId(SysProperties.getInstance().get("imageUseTypeId"));
		createImageCommand.setFromProjectEnvName(SysProperties.getInstance().get("imageEnvName"));
		createImageCommand.setFromProjectId(SysProperties.getInstance().get("imageProjectId"));
		createImageCommand.setAlbumId(SysProperties.getInstance().get("imageUseTypeId"));
		try {
			String imageID=spiImageServiceImpl_1.createImage_1(createImageCommand);
			HgLogger.getInstance().info("lxs_dev", "【saveRecomend】"+"新增推荐图片在图片服务中的ID：" + imageID);
			ImageQo imageQo = new ImageQo();
			imageQo.setId(imageID);
			ImageDTO imageDTO=spiImageServiceImpl_1.queryUniqueImage_1(imageQo);
			HgLogger.getInstance().info("lxs_dev", "【saveRecomend】"+"查询图片服务里得到结果：" + JSON.toJSONString(imageDTO));
			Map<String, String> map = new HashMap<String, String>();
			for (String key : imageDTO.getSpecImageMap().keySet()) {
				FdfsFileInfo fdfsFileInfo = JSON.parseObject(imageDTO.getSpecImageMap().get(key).getFileInfo(), FdfsFileInfo.class);
				map.put(key, fdfsFileInfo.getUri());
			}
			command.setImageURL(map.get(SysProperties.getInstance().get("adImageType")));
		} catch (ImageException e) {
			HgLogger.getInstance().info("lxs_dev", "【saveRecomend】"+"上传广告，图片服务报错"+HgLogger.getStackTrace(e));
			e.printStackTrace();
		} catch (IOException e) {
			HgLogger.getInstance().info("lxs_dev", "【saveRecomend】"+"上传广告，图片服务IO报错"+HgLogger.getStackTrace(e));
			e.printStackTrace();
		}
		
		try {
			recommendService.addRecommend(command);
		} catch (RecommendException e) {
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_300, e.getMessage(), null, "");
		}
		return DwzJsonResultUtil.createJsonString(statusCode, message, callbackType, "pageView");
	}
	
	/**
	 * @Title: deleteRecommend 
	 * @author guok
	 * @Description: 删除推荐
	 * @Time 2015年9月15日下午1:38:58
	 * @param request
	 * @param response
	 * @param model
	 * @param command
	 * @return String 设定文件
	 * @throws
	 */
	@ResponseBody
	@RequestMapping("/deleteRecommend")
	public String deleteRecommend(HttpServletRequest request,HttpServletResponse response,Model model,
			@ModelAttribute DeleteRecommendCommand command) {
		// 1. 默认返回值
		String statusCode = DwzJsonResultUtil.STATUS_CODE_200;
		String message = "删除成功";
		
		try {
			recommendService.deleteRecommend(command);
		} catch (RecommendException e) {
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_300, e.getMessage(), null, "");
		}
		
		return DwzJsonResultUtil.createJsonString(statusCode, message, null, "");
	}
	
	/**
	 * @Title: editRecommend 
	 * @author guok
	 * @Description: 跳转修改页
	 * @Time 2015年9月15日下午1:45:16
	 * @param request
	 * @param response
	 * @param tasklistWaitID
	 * @return String 设定文件
	 * @throws
	 */
	@RequestMapping("/editRecommend")
	public String editRecommend(HttpServletRequest request,HttpServletResponse response,Model model,
			@RequestParam(value = "recommendID") String recommendID) {
		RecommendQO recommendQO = new RecommendQO();
		recommendQO.setRecommendID(recommendID);
		Recommend recommend = recommendService.queryUnique(recommendQO);
		recommend.setImageURL(SysProperties.getInstance().get("fileUploadPath")+recommend.getImageURL());
		model.addAttribute("recommend", recommend);
		
		HgLogger.getInstance().info("lxs_dev", "【editRecommend】"+"【recommend】：" + JSON.toJSONString(recommend));
		return "/recommend/editRecommend.html";
	}
	
	/**
	 * @Title: modifyRecommend 
	 * @author guok
	 * @Description: 修改推荐
	 * @Time 2015年9月15日下午1:45:36
	 * @param request
	 * @param response
	 * @param model
	 * @param command
	 * @return String 设定文件
	 * @throws
	 */
	@RequestMapping("/modifyRecommend")
	@ResponseBody
	public String modifyRecommend(HttpServletRequest request,HttpServletResponse response,Model model,
			@ModelAttribute ModifyRecommendCommand command) {
		
		HgLogger.getInstance().info("lxs_dev", "【modifyRecommend】"+"【ModifyRecommendCommand】：" + JSON.toJSONString(command));
		// 1. 默认返回值
		String statusCode = DwzJsonResultUtil.STATUS_CODE_200;
		String message = "修改成功";
		String callbackType = DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT;
		
		
		CreateImageCommand createImageCommand = new CreateImageCommand();
		createImageCommand.setTitle(command.getRecommendName());
		//利旧URL存储file的json
		createImageCommand.setFileInfo(command.getImageInfo());
		createImageCommand.setFileName(command.getFileName());
		createImageCommand.setRemark("旅行社APP推荐");
		createImageCommand.setUseTypeId(SysProperties.getInstance().get("imageUseTypeId"));
		createImageCommand.setFromProjectEnvName(SysProperties.getInstance().get("imageEnvName"));
		createImageCommand.setFromProjectId(SysProperties.getInstance().get("imageProjectId"));
		createImageCommand.setAlbumId(SysProperties.getInstance().get("imageUseTypeId"));
		try {
			String imageID=spiImageServiceImpl_1.createImage_1(createImageCommand);
			HgLogger.getInstance().info("lxs_dev", "【modifyRecommend】"+"新增推荐图片在图片服务中的ID：" + imageID);
			ImageQo imageQo = new ImageQo();
			imageQo.setId(imageID);
			ImageDTO imageDTO=spiImageServiceImpl_1.queryUniqueImage_1(imageQo);
			HgLogger.getInstance().info("lxs_dev", "【modifyRecommend】"+"查询图片服务里得到结果：" + JSON.toJSONString(imageDTO));
			Map<String, String> map = new HashMap<String, String>();
			for (String key : imageDTO.getSpecImageMap().keySet()) {
				FdfsFileInfo fdfsFileInfo = JSON.parseObject(imageDTO.getSpecImageMap().get(key).getFileInfo(), FdfsFileInfo.class);
				map.put(key, fdfsFileInfo.getUri());
			}
			command.setImageURL(map.get(SysProperties.getInstance().get("adImageType")));
		} catch (ImageException e) {
			HgLogger.getInstance().info("lxs_dev", "【modifyRecommend】"+"上传广告，图片服务报错"+HgLogger.getStackTrace(e));
			e.printStackTrace();
		} catch (IOException e) {
			HgLogger.getInstance().info("lxs_dev", "【modifyRecommend】"+"上传广告，图片服务IO报错"+HgLogger.getStackTrace(e));
			e.printStackTrace();
		}
		
		try {
			recommendService.modfiyRecommend(command);
		} catch (RecommendException e) {
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_300, e.getMessage(), null, "");
		}
//		return DwzJsonResultUtil.createJsonString(statusCode, message, callbackType, "recommendList");
		return DwzJsonResultUtil.createJsonString(statusCode, message,	callbackType, "pageView");
	}
	
	/**
	 * @Title: changeStatus 
	 * @author guok
	 * @Description: 修改启用状态
	 * @Time 2015年9月16日上午9:33:21
	 * @param request
	 * @param response
	 * @param model
	 * @param recommendID
	 * @return String 设定文件
	 * @throws
	 */
	@ResponseBody
	@RequestMapping("changeStatus")
	public String changeStatus(HttpServletRequest request,HttpServletResponse response,Model model,
			@RequestParam(value = "recommendID") String recommendID) {
		// 1. 默认返回值
		String statusCode = DwzJsonResultUtil.STATUS_CODE_200;
		String message = "状态修改成功";
				
		try {
			recommendService.changeStatus(recommendID);
		} catch (RecommendException e) {
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_300, e.getMessage(), null, "");
		}
		
		return DwzJsonResultUtil.createJsonString(statusCode, message, null, "");
	}
	
	/**
	 * @Title: changeSort 
	 * @author guok
	 * @Description: 移动顺序
	 * @Time 2015年9月16日下午1:27:19
	 * @param request
	 * @param response
	 * @param model
	 * @param recommendID
	 * @param type
	 * @return String 设定文件
	 * @throws
	 */
	@ResponseBody
	@RequestMapping("/changeSort")
	public String changeSort(HttpServletRequest request,HttpServletResponse response,Model model,
			@RequestParam(value = "recommendID") String recommendID,@RequestParam(value = "type") String type) {
		// 1. 默认返回值
		String statusCode = DwzJsonResultUtil.STATUS_CODE_200;
		try {
			RecommendQO recommendQO = new RecommendQO();
			recommendQO.setRecommendID(recommendID);
			Recommend recommend = recommendService.queryUnique(recommendQO);
			HgLogger.getInstance().info("lxs_dev", "【changeSort】"+"【recommend】：" + JSON.toJSONString(recommend));
			List<Recommend> recommends = recommendService.queryList(new RecommendQO());
			int i=0;
			for (Recommend recommend2 : recommends) {
				if (StringUtils.equals(recommend2.getId(), recommend.getId())) {
					if(i==0&&StringUtils.equals("up", type)){
						return DwzJsonResultUtil.createJsonString("500", "该推荐已在最第一位",null,"");
					}else if((i+1)==recommends.size()&&StringUtils.equals("down", type)){
						return DwzJsonResultUtil.createJsonString("500", "该推荐已在最末尾",null,"");
					}else {
						if(StringUtils.equals("down", type)){
							ModifyRecommendSortCommand command = new ModifyRecommendSortCommand();
							command.setRecommendID(recommends.get(i).getId());
							command.setSort(recommends.get(i+1).getSort());
							recommendService.changeSort(command);
							command = new ModifyRecommendSortCommand();
							command.setRecommendID(recommends.get(i+1).getId());
							command.setSort(recommend.getSort());
							recommendService.changeSort(command);
							return DwzJsonResultUtil.createJsonString(statusCode, "下移成功",null,"");
						}else {
							ModifyRecommendSortCommand command = new ModifyRecommendSortCommand();
							command.setRecommendID(recommends.get(i).getId());
							command.setSort(recommends.get(i-1).getSort());
							recommendService.changeSort(command);
							command = new ModifyRecommendSortCommand();
							command.setRecommendID(recommends.get(i-1).getId());
							command.setSort(recommend.getSort());
							recommendService.changeSort(command);
							return DwzJsonResultUtil.createJsonString(statusCode, "上移成功",null,"");
						}
					}
				}else {
					i++;
				}
			}
		} catch (Exception e) {
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_300, e.getMessage(), null, "");
		}
		
		return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_500, "移动失败", null, "");
	}
	
	/**
	 * @Title: queryMpList 
	 * @author guok
	 * @Description: 景区门票列表
	 * @Time 2016年3月3日下午5:12:01
	 * @param request
	 * @param model
	 * @param response
	 * @param scenicSpotQO
	 * @param pageNo
	 * @param pageSize
	 * @param handleType
	 * @return
	 * @throws IOException String 设定文件
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/mpList")
	public String queryMpList(
			HttpServletRequest request,
			Model model,HttpServletResponse response,
			@ModelAttribute ScenicSpotQO scenicSpotQO,
			@RequestParam(value = "pageNum", required = false) Integer pageNo,
			@RequestParam(value = "numPerPage", required = false) Integer pageSize,
			@RequestParam(value = "handleType", required = false) String handleType,
			@RequestParam(value = "recommendID", required = false) String recommendID) throws IOException {
		if (pageNo == null) {
			pageNo = 1;
		}
		if (pageSize == null) {
			pageSize = 20;
		}
		RecommendQO recommendQO = new RecommendQO();
		recommendQO.setRecommendID(recommendID);
		Recommend recommend = recommendService.queryUnique(recommendQO);
		if (recommend == null) {
			recommend = new Recommend();
		}
//		scenicSpotQO.setLocalStatus(ScenicSpot.SHOW);
		Pagination pagination = new Pagination();
		pagination.setCondition(scenicSpotQO);
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		pagination = scenicSpotService.queryPagination(pagination);
		List<ScenicSpotDTO> scenicSpotList = EntityConvertUtils.convertEntityToDtoList(
				(List<ScenicSpotDTO>) pagination.getList(), ScenicSpotDTO.class);
		
		if (StringUtils.isNotBlank(recommendID) && scenicSpotList != null
				&& scenicSpotList.size() > 1) {
			for (ScenicSpotDTO scenicSpotDTO : scenicSpotList) {
				if (recommendID.equals(scenicSpotDTO.getId())) {
					scenicSpotList.set(0, scenicSpotDTO);
				}
			}
		}
		
		model.addAttribute("scenicSpotList", scenicSpotList);
		model.addAttribute("recommend", recommend);
		model.addAttribute("scenicSpotQO", scenicSpotQO);
		model.addAttribute("pagination", pagination);
		model.addAttribute("handleType", handleType);
		return "/recommend/mpLists.html";
	}
}
