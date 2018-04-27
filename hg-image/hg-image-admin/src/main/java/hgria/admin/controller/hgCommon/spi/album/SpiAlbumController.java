package hgria.admin.controller.hgCommon.spi.album;


import hg.common.component.RemoteConfigurer;
import hg.common.page.Pagination;
import hg.common.util.DwzJsonResultUtil;
import hg.log.util.HgLogger;
import hg.service.image.command.album.CreateAlbumCommand;
import hg.service.image.command.album.DeleteAlbumCommand;
import hg.service.image.command.album.ModifyAlbumCommand;
import hg.service.image.pojo.dto.AlbumDTO;
import hg.service.image.pojo.qo.AlbumQo;
import hg.service.image.pojo.qo.ImageUseTypeQo;
import hg.service.image.spi.inter.AlbumService_1;
import hg.service.image.spi.inter.ImageUseTypeService_1;
import hgria.admin.controller.BaseController;
import hgria.admin.manager.HGSessionUserManager;
import hgria.domain.model.project.Project;

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

/**
 * @类功能说明：相册控制器_spi
 * @类修改者：zzb
 * @修改日期：2014年11月18日上午10:44:40
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzb
 * @创建时间：2014年11月18日上午10:44:40
 */
@Controller
@RequestMapping(value="/hg/spiAlbum/spiAlbum")
public class SpiAlbumController extends BaseController {
	
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(SpiAlbumController.class);
	
	/**
	 * 相册service
	 */
	@Resource(name="spiAlbumServiceImpl_1")
	private AlbumService_1 albumService;
	
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
	
	
	public final static Integer START_PAGE 	= 1;
	
	public final static Integer LARGE_NUM 	= 99999;
	
	/**
	 * 相册主页面(包含树节点 和 list页面)
	 */
	public final static String PAGE_VIEW 	= "/content/spiAlbum/album.html";
	
	/**
	 * 相册添加页面
	 */
	public final static String PAGE_ADD 	= "/content/spiAlbum/albumAdd.html";
	
	/**
	 * 相册编辑页面
	 */
	public final static String PAGE_EDIT 	= "/content/spiAlbum/albumEdit.html";
	
	
	/**
	 * 
	 * @方法功能说明：相册主页面
	 * @修改者名字：zzb
	 * @修改时间：2014年9月1日下午4:26:22
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
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
		
		HgLogger.getInstance().info("zzb", "进入相册主页面方法:albumQo【" + JSON.toJSONString(albumQo) + "】");
		
		ImageUseTypeQo imageSpecKeyUseTypeQo = new ImageUseTypeQo();
		imageSpecKeyUseTypeQo.setPageNo(START_PAGE);
		imageSpecKeyUseTypeQo.setPageSize(LARGE_NUM);
		imageSpecKeyUseTypeQo.setProjectId(project.getId());
		imageSpecKeyUseTypeQo.setEnvName(project.getEnvName());
		Pagination useTypePagination 
			= imageUseTypeService.queryImageSpecKeyUseTypePagination_1(imageSpecKeyUseTypeQo);
		if (useTypePagination.getList() != null)
			model.addAttribute("useTypeList", useTypePagination.getList());
		
		return PAGE_VIEW;
	}
	
	
	/**
	 * @方法功能说明：相册列表查询
	 * @修改者名字：zzb
	 * @修改时间：2014年11月7日下午5:40:42
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param albumQo
	 * @参数：@return
	 * @return:Pagination
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/albumlist")
	public Pagination albumlist(HttpServletRequest request, @ModelAttribute AlbumQo albumQo) {
		
		HgLogger.getInstance().info("zzb", "进入相册列表查询方法:albumQo【" + JSON.toJSONString(albumQo) + "】");
		
		Project project = HGSessionUserManager.getSessionProject(request);
		if (project == null)
			return null;
		
		// 1. 设置属性值
		albumQo.setTitleLike(true);
		albumQo.setUseType(new ImageUseTypeQo());
		albumQo.setProjectId(project.getId());
		albumQo.setEnvName(project.getEnvName());
		
		// 2. 设置pagination并查询
		Pagination pagination = albumService.queryAlbumPagination_1(albumQo);
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
			Model model, @ModelAttribute AlbumQo albumQo) {

		HgLogger.getInstance().info("zzb", "进入相册添加跳转方法:albumQo【" + JSON.toJSONString(albumQo, true) + "】");
		
		Project project = HGSessionUserManager.getSessionProject(request);
		if (project == null)
			return null;
		
		AlbumQo allAlbumQo = new AlbumQo();
		allAlbumQo.setPageNo(START_PAGE);
		allAlbumQo.setPageSize(LARGE_NUM);
		allAlbumQo.setProjectId(project.getId());
		allAlbumQo.setEnvName(project.getEnvName());
		Pagination pagination = albumService.queryAlbumPagination_1(allAlbumQo);
		
//		model.addAttribute("parentId", albumQo.getParentId());
		
		ImageUseTypeQo imageSpecKeyUseTypeQo = new ImageUseTypeQo();
		imageSpecKeyUseTypeQo.setProjectId(project.getId());
		imageSpecKeyUseTypeQo.setEnvName(project.getEnvName());
		imageSpecKeyUseTypeQo.setPageNo(START_PAGE);
		imageSpecKeyUseTypeQo.setPageSize(LARGE_NUM);
		Pagination useTypePagination 
			= imageUseTypeService.queryImageSpecKeyUseTypePagination_1(imageSpecKeyUseTypeQo);
		if (pagination.getList() != null)
			model.addAttribute("albumList", pagination.getList());
		if (useTypePagination.getList() != null)
			model.addAttribute("useTypeList", useTypePagination.getList());
		
		return PAGE_ADD;
	}
	
	
	/**
	 * @方法功能说明：新增保存
	 * @修改者名字：zzb
	 * @修改时间：2014年11月17日下午6:39:12
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param album
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value="/save")
	public String save(HttpServletRequest request, HttpServletResponse response, Model model,
			@ModelAttribute CreateAlbumCommand album) {
		
		HgLogger.getInstance().info("zzb", "进入相册新增保存方法:album【" + JSON.toJSONString(album, true) + "】");

		// 1. 默认返回值
		String statusCode = DwzJsonResultUtil.STATUS_CODE_200;
		String message = "保存成功";
		String callbackType = DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT;

		// 2. 保存
		Project project = HGSessionUserManager.getSessionProject(request);
		if (project == null) {
			HgLogger.getInstance().error("zzb", "相册新增保存失败:album【" + JSON.toJSONString(album, true) + "】");
			statusCode = DwzJsonResultUtil.STATUS_CODE_500;
			message = "没有管理的工程, 不能新建相册！";
			return DwzJsonResultUtil.createJsonString(statusCode, message, callbackType, "spiAlbum");
		}
		try {
			// 2.1 设置属性
			album.setFromProjectId(project.getId());
			album.setFromProjectEnvName(project.getEnvName());
			
			// 2.2 保存
			albumService.createAlbum_1(album);
		} catch (Exception e) {
			HgLogger.getInstance().error("zzb", "相册新增保存失败:album【" + JSON.toJSONString(album, true) + "】");
			e.printStackTrace();
			statusCode = DwzJsonResultUtil.STATUS_CODE_500;
			message = "保存失败";
		}
		
		return DwzJsonResultUtil.createJsonString(statusCode, message, callbackType, "spiAlbum");
	}

	
	/**
	 * @方法功能说明：相册编辑跳转
	 * @修改者名字：zzb
	 * @修改时间：2014年11月17日下午5:58:07
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param albumQo
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/edit")
	public String edit(HttpServletRequest request, HttpServletResponse response, 
			Model model, @ModelAttribute AlbumQo albumQo) {

		HgLogger.getInstance().info("zzb", "进入相册编辑跳转方法:albumQo【" + JSON.toJSONString(albumQo, true) + "】");
		
		Project project = HGSessionUserManager.getSessionProject(request);
		if (project == null)
			return null;
		
		// 1. 空值判断
		if (albumQo == null || StringUtils.isBlank(albumQo.getId()))
			return null;
		
		// 2. 查询该对象
		AlbumDTO albumDTO = albumService.queryUniqueAlbum_1(albumQo);
		
		// 3. 查询所有相册 及 用途
		AlbumQo allAlbumQo = new AlbumQo();
		allAlbumQo.setPageNo(START_PAGE);
		allAlbumQo.setPageSize(LARGE_NUM);
		allAlbumQo.setProjectId(project.getId());
		allAlbumQo.setEnvName(project.getEnvName());
		Pagination pagination = albumService.queryAlbumPagination_1(allAlbumQo);
		
		ImageUseTypeQo imageSpecKeyUseTypeQo = new ImageUseTypeQo();
		imageSpecKeyUseTypeQo.setProjectId(project.getId());
		imageSpecKeyUseTypeQo.setEnvName(project.getEnvName());
		imageSpecKeyUseTypeQo.setPageNo(START_PAGE);
		imageSpecKeyUseTypeQo.setPageSize(LARGE_NUM);
		Pagination useTypePagination 
			= imageUseTypeService.queryImageSpecKeyUseTypePagination_1(imageSpecKeyUseTypeQo);
		
		// 4. 设置返回值
		model.addAttribute("album", albumDTO);
		if (pagination.getList() != null)
			model.addAttribute("albumList", pagination.getList());
		if (useTypePagination.getList() != null)
			model.addAttribute("useTypeList", useTypePagination.getList());
		
		return PAGE_EDIT;
	}
	
	
	/**
	 * @方法功能说明：相册编辑保存
	 * @修改者名字：zzb
	 * @修改时间：2014年11月17日下午6:43:10
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param album
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value="/update")
	public String update(HttpServletRequest request, HttpServletResponse response, Model model,
			@ModelAttribute ModifyAlbumCommand command) {
		
		HgLogger.getInstance().info("zzb", "进入相册编辑保存方法:album【" + JSON.toJSONString(command, true) + "】");

		// 1. 默认返回值
		String statusCode = DwzJsonResultUtil.STATUS_CODE_200;
		String message = "保存成功";
		String callbackType = DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT;

		Project project = HGSessionUserManager.getSessionProject(request);
		if (project == null)
			return null;
		
		// 2. 保存
		try {
			command.setFromProjectId(project.getId());
			command.setFromProjectEnvName(project.getEnvName());
			albumService.modifyAlbum_1(command);
		} catch (Exception e) {
			HgLogger.getInstance().error("zzb", "相册编辑保存失败:album【" + JSON.toJSONString(command, true) + "】");
			e.printStackTrace();
			statusCode = DwzJsonResultUtil.STATUS_CODE_500;
			message = "保存失败";
		}
		
		return DwzJsonResultUtil.createJsonString(statusCode, message, callbackType, "spiAlbum");
	}
	
	
	/**
	 * @方法功能说明：相册删除
	 * @修改者名字：zzb
	 * @修改时间：2014年11月19日下午1:56:39
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
			@ModelAttribute DeleteAlbumCommand command) {
		
		HgLogger.getInstance().info("zzb", "进入相册删除方法:command【" + JSON.toJSONString(command, true) + "】");
		
		try {
			command.setDeleteSubAlbum(true);
			albumService.deleteAlbum_1(command);
			return DwzJsonResultUtil.createJsonString("200", "删除成功!", null, "spiAlbum");
		} catch (Exception e) {
			HgLogger.getInstance().error("zzb", "相册删除失败:command【" + JSON.toJSONString(command, true) + "】");
			e.printStackTrace();
			return DwzJsonResultUtil.createJsonString("300", e.getMessage(), null, "");
	
		}
	}
	
}
