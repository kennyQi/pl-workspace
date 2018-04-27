package hgria.admin.controller.hgCommon.spi.useType;


import hg.common.component.RemoteConfigurer;
import hg.common.page.Pagination;
import hg.common.util.DwzJsonResultUtil;
import hg.log.util.HgLogger;
import hg.service.image.command.image.usetype.CreateImageUseTypeCommand;
import hg.service.image.command.image.usetype.DeleteImageUseTypeCommand;
import hg.service.image.command.image.usetype.ModifyImageUseTypeCommand;
import hg.service.image.pojo.dto.ImageSpecKeyDTO;
import hg.service.image.pojo.dto.ImageUseTypeDTO;
import hg.service.image.pojo.qo.AlbumQo;
import hg.service.image.pojo.qo.ImageUseTypeQo;
import hg.service.image.spi.inter.ImageUseTypeService_1;
import hgria.admin.controller.BaseController;
import hgria.admin.manager.HGSessionUserManager;
import hgria.domain.model.project.Project;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @类功能说明：用途控制器_spi
 * @类修改者：zzb
 * @修改日期：2014年11月14日下午4:24:24
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzb
 * @创建时间：2014年11月14日下午4:24:24
 */
@Controller
@RequestMapping(value="/hg/spiUseType/spiUseType")
public class SpiUseTypeController extends BaseController {
	
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(SpiUseTypeController.class);
	
	/**
	 * 用途service
	 */
	@Resource(name="spiImageUseTypeServiceImpl_1")
	private ImageUseTypeService_1 imageUseTypeService;
	
	/**
	 * 项目环境
	 */
	@Resource(name="propertyConfigurer")
	private RemoteConfigurer propertyConfigurer;
	
	
	public final static Integer LARGE_NUM 	= 99999;
	
	/**
	 * 用途主页面
	 */
	public final static String PAGE_VIEW 	= "/content/spiUseType/useType.html";
	
	/**
	 * 用途添加页面
	 */
	public final static String PAGE_ADD 	= "/content/spiUseType/useTypeAdd.html";
	
	/**
	 * 用途编辑页面
	 */
	public final static String PAGE_EDIT 	= "/content/spiUseType/useTypeEdit.html";
	
	
	/**
	 * @方法功能说明：用途主页面
	 * @修改者名字：zzb
	 * @修改时间：2014年11月14日下午5:30:18
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param albumQo
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/view")
	public String index(HttpServletRequest request, Model model, 
			@ModelAttribute AlbumQo albumQo) {
		
		Project project = HGSessionUserManager.getSessionProject(request);
		if (project == null)
			return null;
		
		return PAGE_VIEW;
	}
	
	
	/**
	 * @方法功能说明：用途列表
	 * @修改者名字：zzb
	 * @修改时间：2014年11月14日下午5:40:07
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param albumQo
	 * @参数：@return
	 * @return:Pagination
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/useTypelist")
	public Pagination albumlist(HttpServletRequest request, 
			@ModelAttribute ImageUseTypeQo imageSpecKeyUseTypeQo) {
		
		HgLogger.getInstance().info("zzb", 
				"进入用途列表查询方法:imageSpecKeyUseTypeQo【" 
						+ JSON.toJSONString(imageSpecKeyUseTypeQo) + "】");

		// 1. 设置属性值
		imageSpecKeyUseTypeQo.setTitleLike(true);
		Project project = HGSessionUserManager.getSessionProject(request);
		if (project == null)
			return null;
		imageSpecKeyUseTypeQo.setProjectId(project.getId());
		imageSpecKeyUseTypeQo.setEnvName(project.getEnvName());
		
		// 2. 设置pagination并查询
		Pagination pagination = imageUseTypeService.queryImageSpecKeyUseTypePagination_1(imageSpecKeyUseTypeQo);
		if (pagination.getList() == null) 
			return null;
		
		pagination.setCondition(null);

		return pagination;
	}


	/**
	 * @方法功能说明：添加跳转
	 * @修改者名字：zzb
	 * @修改时间：2014年11月14日下午4:01:40
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param albumQo
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/add")
	public String add(HttpServletRequest request, HttpServletResponse response, 
			Model model, @ModelAttribute ImageUseTypeQo imageSpecKeyUseTypeQo) {

		HgLogger.getInstance().info("zzb", "进入用途添加跳转方法:imageSpecKeyUseTypeQo【" 
				+ JSON.toJSONString(imageSpecKeyUseTypeQo, true) + "】");
		
		Project project = HGSessionUserManager.getSessionProject(request);
		if (project == null)
			return null;
		
		return PAGE_ADD;
	}
	
	
	/**
	 * @方法功能说明：添加保存
	 * @修改者名字：zzb
	 * @修改时间：2014年11月14日下午6:05:25
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param imageUseTypeCommand
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value="/save")
	public String save(HttpServletRequest request, HttpServletResponse response, Model model,
			String title, String remark, String imageSpecKeysJson) {
		
		HgLogger.getInstance().info("zzb", "进入用途新增保存方法:imageUseTypeCommand【" 
				+ "title:" + title + ",remark:" + remark + ",imageSpecKeysJson:" 
				+ JSON.toJSONString(imageSpecKeysJson, true) + "】");

		// 1. 默认返回值
		String statusCode = DwzJsonResultUtil.STATUS_CODE_200;
		String message = "保存成功";
		String callbackType = DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT;
		
		Project project = HGSessionUserManager.getSessionProject(request);
		if (project == null) {
			HgLogger.getInstance().error("zzb", "用途新增保存失败:imageSpecKeysJson【" + JSON.toJSONString(imageSpecKeysJson, true) + "】");
			statusCode = DwzJsonResultUtil.STATUS_CODE_500;
			message = "没有管理的工程, 不能新建用途！";
			return DwzJsonResultUtil.createJsonString(statusCode, message, callbackType, "spiUseType");
		}
		
		// 2. 保存
		CreateImageUseTypeCommand imageUseTypeCommand = new CreateImageUseTypeCommand();
		try {
			imageUseTypeCommand.setUseTypeId(request.getParameter("useTypeId"));
			// 2.1 参数设置
			imageUseTypeCommand.setTitle(title);
			imageUseTypeCommand.setRemark(remark);
			JSONArray arry = JSONArray.parseArray(imageSpecKeysJson);
			
			List<ImageSpecKeyDTO> imageSpecKeys = new ArrayList<ImageSpecKeyDTO>();
			for (Iterator<Object> iterator = arry.iterator(); iterator.hasNext();) {
				JSONObject obj = (JSONObject) iterator.next();
				
				ImageSpecKeyDTO imageSpecKeyDTO = new ImageSpecKeyDTO();
				imageSpecKeyDTO.setKey(obj.getString("key"));
				imageSpecKeyDTO.setRemark(obj.getString("remark"));
				imageSpecKeyDTO.setWidth(obj.getInteger("width") == null ? 0 : obj.getInteger("width"));
				imageSpecKeyDTO.setHeight(obj.getInteger("height") == null ? 0 : obj.getInteger("height"));
				imageSpecKeys.add(imageSpecKeyDTO);
			}
			imageUseTypeCommand.setImageSpecKeys(imageSpecKeys);
			imageUseTypeCommand.setFromProjectId(project.getId());
			imageUseTypeCommand.setFromProjectEnvName(project.getEnvName());
			
			// 2.2 保存
			imageUseTypeService.createImageSpecKeyUseType_1(imageUseTypeCommand);
		} catch (Exception e) {
			HgLogger.getInstance().error("zzb", "用途新增保存失败:album【" + JSON.toJSONString(imageUseTypeCommand, true) + "】");
			e.printStackTrace();
			statusCode = DwzJsonResultUtil.STATUS_CODE_500;
			message = e.getMessage();
		}
		
		return DwzJsonResultUtil.createJsonString(statusCode, message, callbackType, "spiUseType");
	}
	
	
	/**
	 * @方法功能说明：用途编辑跳转
	 * @修改者名字：zzb
	 * @修改时间：2014年11月17日下午2:59:01
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param imageSpecKeyUseTypeQo
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/edit")
	public String edit(HttpServletRequest request, HttpServletResponse response,
					   Model model, @ModelAttribute ImageUseTypeQo imageSpecKeyUseTypeQo) {

		HgLogger.getInstance().info("zzb", "进入用途编辑跳转方法:imageSpecKeyUseTypeQo【" 
				+ JSON.toJSONString(imageSpecKeyUseTypeQo, true) + "】");
		
		Project project = HGSessionUserManager.getSessionProject(request);
		if (project == null)
			return null;
		
		// 1. 空值判断
		if (imageSpecKeyUseTypeQo == null || StringUtils.isBlank(imageSpecKeyUseTypeQo.getId()))
			return null;

		// 2. 查出 该对象 和 全部相册
		ImageUseTypeDTO imageUseTypeDTO = imageUseTypeService
				.queryUniqueImageSpecKeyUseType_1(imageSpecKeyUseTypeQo);

		if (imageUseTypeDTO == null)
			return null;
		
		// 3. 设置返回值
		model.addAttribute("imageSpecKeyUseType", imageUseTypeDTO);
		
		return PAGE_EDIT;
	}
	
	
	/**
	 * @方法功能说明：用途编辑保存
	 * @修改者名字：zzb
	 * @修改时间：2014年11月17日下午3:29:52
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param imageSpecKeyUseTypeId
	 * @参数：@param title
	 * @参数：@param remark
	 * @参数：@param imageSpecKeysJson
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value="/update")
	public String save(HttpServletRequest request, HttpServletResponse response, Model model,
			String imageSpecKeyUseTypeId, String title, String remark, String imageSpecKeysJson) {
		
		HgLogger.getInstance().info("zzb", "进入用途新增保存方法:imageUseTypeCommand【" 
				+ "title:" + title + ",remark:" + remark + ",imageSpecKeysJson:" 
				+ JSON.toJSONString(imageSpecKeysJson, true) + "】");

		// 1. 默认返回值
		String statusCode = DwzJsonResultUtil.STATUS_CODE_200;
		String message = "保存成功";
		String callbackType = DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT;

		Project project = HGSessionUserManager.getSessionProject(request);
		if (project == null) {
			HgLogger.getInstance().error("zzb", "用途编辑保存失败:imageSpecKeysJson【" + JSON.toJSONString(imageSpecKeysJson, true) + "】");
			statusCode = DwzJsonResultUtil.STATUS_CODE_500;
			message = "没有管理的工程, 不能编辑用途！";
			return DwzJsonResultUtil.createJsonString(statusCode, message, callbackType, "spiUseType");
		}
		
		// 2. 保存
		ModifyImageUseTypeCommand imageUseTypeCommand = new ModifyImageUseTypeCommand();
		try {
			// 2.1 参数设置
			imageUseTypeCommand.setUseTypeId(imageSpecKeyUseTypeId);
			imageUseTypeCommand.setTitle(title);
			imageUseTypeCommand.setRemark(remark);
			JSONArray arry = JSONArray.parseArray(imageSpecKeysJson);

			List<ImageSpecKeyDTO> imageSpecKeys = new ArrayList<ImageSpecKeyDTO>();
			for (Iterator<Object> iterator = arry.iterator(); iterator.hasNext();) {
				JSONObject obj = (JSONObject) iterator.next();
				
				ImageSpecKeyDTO imageSpecKeyDTO = new ImageSpecKeyDTO();
				imageSpecKeyDTO.setKey(obj.getString("key"));
				imageSpecKeyDTO.setRemark(obj.getString("remark"));
				imageSpecKeyDTO.setWidth(obj.getInteger("width") == null ? 0 : obj.getInteger("width"));
				imageSpecKeyDTO.setHeight(obj.getInteger("height") == null ? 0 : obj.getInteger("height"));
				imageSpecKeys.add(imageSpecKeyDTO);
			}
			imageUseTypeCommand.setImageSpecKeys(imageSpecKeys);
			imageUseTypeCommand.setFromProjectId(project.getId());
			imageUseTypeCommand.setFromProjectEnvName(project.getEnvName());
			
			// 2.2 保存
			imageUseTypeService.modifyImageSpecKeyUseType_1(imageUseTypeCommand);
		} catch (Exception e) {
			HgLogger.getInstance().error("zzb", "用途编辑保存失败:album【" + JSON.toJSONString(imageUseTypeCommand, true) + "】");
			e.printStackTrace();
			statusCode = DwzJsonResultUtil.STATUS_CODE_500;
			message = e.getMessage();
		}
		
		return DwzJsonResultUtil.createJsonString(statusCode, message, callbackType, "spiUseType");
	}
	
	
	/**
	 * @方法功能说明：用途删除
	 * @修改者名字：zzb
	 * @修改时间：2014年11月17日下午3:38:45
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param command
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping("/del")
	public String del(HttpServletRequest request, HttpServletResponse response, Model model,
			@ModelAttribute DeleteImageUseTypeCommand command) {
		
		HgLogger.getInstance().info("zzb", "进入用途删除方法:command【" + JSON.toJSONString(command, true) + "】");
		
		try {
			imageUseTypeService.deleteImageSpecKeyUseType_1(command);
			return DwzJsonResultUtil.createJsonString("200", "删除成功!", null, "spiUseType");
		} catch (Exception e) {
			HgLogger.getInstance().error("zzb", "用途删除失败:command【" + JSON.toJSONString(command, true) + "】");
			e.printStackTrace();
			return DwzJsonResultUtil.createJsonString("300", e.getMessage(), null, "");
	
		}
	}
	

}
