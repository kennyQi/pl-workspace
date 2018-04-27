package hgria.admin.controller.hgCommon.spi.image;


import hg.common.component.RemoteConfigurer;
import hg.common.page.Pagination;
import hg.common.util.DwzJsonResultUtil;
import hg.common.util.Md5FileUtil;
import hg.common.util.UUIDGenerator;
import hg.common.util.file.FdfsFileInfo;
import hg.common.util.file.FdfsFileUtil;
import hg.log.util.HgLogger;
import hg.service.image.command.image.CreateImageCommand;
import hg.service.image.command.image.DeleteImageCommand;
import hg.service.image.command.image.ModifyImageCommand;
import hg.service.image.pojo.dto.ImageDTO;
import hg.service.image.pojo.qo.AlbumQo;
import hg.service.image.pojo.qo.ImageQo;
import hg.service.image.pojo.qo.ImageUseTypeQo;
import hg.service.image.spi.inter.AlbumService_1;
import hg.service.image.spi.inter.ImageService_1;
import hg.service.image.spi.inter.ImageUseTypeService_1;
import hg.system.exception.HGException;
import hgria.admin.common.hgUtil.FileUploadUtils;
import hgria.admin.common.hgUtil.HGSystemProperties;
import hgria.admin.common.hgUtil.ImageInfo;
import hgria.admin.controller.BaseController;
import hgria.admin.manager.HGSessionUserManager;
import hgria.domain.model.project.Project;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.aspectj.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @类功能说明：图片控制器_spi
 * @类修改者：zzb
 * @修改日期：2014年11月18日上午10:45:13
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzb
 * @创建时间：2014年11月18日上午10:45:13
 */
@Controller
@RequestMapping(value="hg/spiAlbum/spiImage")
public class SpiImageController extends BaseController {
	
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(SpiImageController.class);
	
	/**
	 * 图片service
	 */
	@Resource(name="spiImageServiceImpl_1")
	private ImageService_1 imageService;
	
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
	
	/**
	 * 系统配置参数
	 */
	@Resource(name="hgSystemProperties")
	HGSystemProperties hgSystemProperties;
	
	public final static Integer START_PAGE 	= 1;
	
	public final static Integer LARGE_NUM 	= 99999;
	
	/**
	 * 相册主页面(包含树节点 和 list页面)
	 */
	public final static String PAGE_VIEW 	= "/content/spiImage/image.html";
	
	/**
	 * 相册添加页面
	 */
	public final static String PAGE_ADD 	= "/content/spiImage/imageAdd.html";
	
	/**
	 * 相册编辑页面
	 */
	public final static String PAGE_EDIT 	= "/content/spiImage/imageEdit.html";
	
	
	/**
	 * @方法功能说明：图片页面跳转方法
	 * @修改者名字：zzb
	 * @修改时间：2014年11月7日下午5:39:57
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
			@ModelAttribute ImageQo imageQo) {
		
		Project project = HGSessionUserManager.getSessionProject(request);
		if (project == null)
			return null;
		
		model.addAttribute("imageQo", imageQo);
		return PAGE_VIEW;
	}
	
	
	/**
	 * @方法功能说明：图片列表查询
	 * @修改者名字：zzb
	 * @修改时间：2014年11月10日上午9:07:05
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param imageQo
	 * @参数：@return
	 * @return:Pagination
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/imagelist")
	public Pagination imagelist(HttpServletRequest request, @ModelAttribute ImageQo imageQo) {
		
		HgLogger.getInstance().info("zzb", "进入图片列表查询方法:imageQo【" + JSON.toJSONString(imageQo) + "】");

		Project project = HGSessionUserManager.getSessionProject(request);
		if (project == null)
			return null;
		
		imageQo.setUseType(new ImageUseTypeQo());
		imageQo.setTitleLike(true);
		imageQo.setProjectId(project.getId());
		imageQo.setEnvName(project.getEnvName());
		imageQo.setFetchAlbum(true);
		
		Pagination pagination = imageService.queryPaginationImage_1(imageQo);

		if (pagination.getList() == null) 
			return null;

		pagination.setCondition(null);
		return pagination;
	}
	
	
	/**
	 * @方法功能说明：图片添加跳转
	 * @修改者名字：zzb
	 * @修改时间：2014年11月10日上午10:57:12
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param imageQo
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/add")
	public String add(HttpServletRequest request, HttpServletResponse response, 
			Model model, @ModelAttribute ImageQo imageQo) {

		HgLogger.getInstance().info("zzb", "进入图片添加跳转方法:imageQo【" 
				+ JSON.toJSONString(imageQo, true) + "】");

		Project project = HGSessionUserManager.getSessionProject(request);
		if (project == null)
			return null;
		
		// 1. 查询所有相册 和 用途
		AlbumQo albumQo = new AlbumQo();
		albumQo.setPageNo(START_PAGE);
		albumQo.setPageSize(LARGE_NUM);
		albumQo.setProjectId(project.getId());
		albumQo.setEnvName(project.getEnvName());
		Pagination pagination = albumService.queryAlbumPagination_1(albumQo);

		ImageUseTypeQo imageSpecKeyUseTypeQo = new ImageUseTypeQo();
		imageSpecKeyUseTypeQo.setPageNo(START_PAGE);
		imageSpecKeyUseTypeQo.setPageSize(LARGE_NUM);
		imageSpecKeyUseTypeQo.setProjectId(project.getId());
		imageSpecKeyUseTypeQo.setEnvName(project.getEnvName());
		Pagination useTypePagination 
			= imageUseTypeService.queryImageSpecKeyUseTypePagination_1(imageSpecKeyUseTypeQo);
		
		// 2. 设置返回值
		if (pagination.getList() != null)
			model.addAttribute("albumList", pagination.getList());
		if (useTypePagination.getList() != null)
			model.addAttribute("useTypeList", useTypePagination.getList());
		model.addAttribute("imageQo", imageQo);
		return PAGE_ADD;
	}
	
	
	/**
	 * @方法功能说明：图片新增保存
	 * @修改者名字：zzb
	 * @修改时间：2014年11月10日上午10:58:36
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
	@RequestMapping(value="/save")
	public String save(HttpServletRequest request, HttpServletResponse response, Model model,
			@ModelAttribute CreateImageCommand command) {
		
		HgLogger.getInstance().info("zzb", "进入图片新增保存方法:command【" + JSON.toJSONString(command, true) + "】");

		// 1. 默认返回值
		String statusCode = DwzJsonResultUtil.STATUS_CODE_200;
		String message = "保存成功";
		String callbackType = DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT;

		// 2. 保存
		Project project = HGSessionUserManager.getSessionProject(request);
		if (project == null) {
			HgLogger.getInstance().error("zzb", "图片新增保存失败:album【" + JSON.toJSONString(command, true) + "】");
			statusCode = DwzJsonResultUtil.STATUS_CODE_500;
			message = "没有管理的工程, 不能新建图片！";
			return DwzJsonResultUtil.createJsonString(statusCode, message, callbackType, "spiImage");
		}
		try {
			// 2.1 设置属性
			command.setFromProjectId(project.getId());
			command.setFromProjectEnvName(project.getEnvName());
			
			// 2.2 保存
			imageService.createImage_1(command);
		} catch (Exception e) {
			HgLogger.getInstance().error("zzb", "图片新增保存失败:command【" + JSON.toJSONString(command, true) + "】");
			e.printStackTrace();
			statusCode = DwzJsonResultUtil.STATUS_CODE_500;
			message = e.getMessage();
		}

		return DwzJsonResultUtil.createJsonString(statusCode, message, callbackType, "spiImage");
	}
	
	
	/**
	 * @方法功能说明：图片编辑跳转
	 * @修改者名字：zzb
	 * @修改时间：2014年11月19日上午10:05:04
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param imageQo
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/edit")
	public String edit(HttpServletRequest request, HttpServletResponse response, 
			Model model, @ModelAttribute ImageQo imageQo) {

		HgLogger.getInstance().info("zzb", "进入图片编辑跳转方法:imageQo【" 
				+ JSON.toJSONString(imageQo, true) + "】");
		
		Project project = HGSessionUserManager.getSessionProject(request);
		if (project == null)
			return null;
		
		// 1. 空值判断
		if (imageQo == null || StringUtils.isBlank(imageQo.getId()))
			return null;
		
		// 2. 查询该对象
		imageQo.setAlbumQo(new AlbumQo());
		imageQo.setUseType(new ImageUseTypeQo());
		ImageDTO imageDTO = imageService.queryUniqueImage_1(imageQo);
		if (imageDTO == null)
			return null;
		
		// 3. 查询所有相册, 本对象, 用途
		AlbumQo albumQo = new AlbumQo();
		albumQo.setPageNo(START_PAGE);
		albumQo.setPageSize(LARGE_NUM);
		albumQo.setProjectId(project.getId());
		albumQo.setEnvName(project.getEnvName());
		Pagination pagination = albumService.queryAlbumPagination_1(albumQo);
		ImageUseTypeQo imageSpecKeyUseTypeQo = new ImageUseTypeQo();
		
		imageSpecKeyUseTypeQo.setPageNo(START_PAGE);
		imageSpecKeyUseTypeQo.setPageSize(LARGE_NUM);
		imageSpecKeyUseTypeQo.setProjectId(project.getId());
		imageSpecKeyUseTypeQo.setEnvName(project.getEnvName());
		Pagination useTypePagination 
			= imageUseTypeService.queryImageSpecKeyUseTypePagination_1(imageSpecKeyUseTypeQo);
		
		// 4. 设置返回值
		model.addAttribute("image", imageDTO);
		if (pagination.getList() != null)
			model.addAttribute("albumList", pagination.getList());
		if (useTypePagination.getList() != null)
			model.addAttribute("useTypeList", useTypePagination.getList());
		model.addAttribute("imageQo", imageQo);
		return PAGE_EDIT;
	}
	
	
	/**
	 * @方法功能说明：图片编辑保存
	 * @修改者名字：zzb
	 * @修改时间：2014年11月19日上午10:35:01
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
	@RequestMapping(value="/update")
	public String update(HttpServletRequest request, HttpServletResponse response, Model model,
			@ModelAttribute ModifyImageCommand command) {

		HgLogger.getInstance().info("zzb", "进入图片编辑保存方法:command【" + JSON.toJSONString(command, true) + "】");

		// 1. 默认返回值
		String statusCode = DwzJsonResultUtil.STATUS_CODE_200;
		String message = "保存成功";
		String callbackType = DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT;

		Project project = HGSessionUserManager.getSessionProject(request);
		if (project == null)
			return null;
		
		// 2. 保存
		try {
			// 2.2 保存
			command.setFromProjectId(project.getId());
			command.setFromProjectEnvName(project.getEnvName());
			imageService.modifyImage_1(command);
		} catch (Exception e) {
			HgLogger.getInstance().error("zzb", "图片编辑保存失败:command【" + JSON.toJSONString(command, true) + "】");
			e.printStackTrace();
			statusCode = DwzJsonResultUtil.STATUS_CODE_500;
			message = e.getMessage();
		}

		return DwzJsonResultUtil.createJsonString(statusCode, message, callbackType, "spiImage");
	}
	
	
	/**
	 * @方法功能说明：图片删除
	 * @修改者名字：zzb
	 * @修改时间：2014年11月19日下午1:57:06
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
			@ModelAttribute DeleteImageCommand command) {
		
		HgLogger.getInstance().info("zzb", "进入图片删除方法:command【" + JSON.toJSONString(command, true) + "】");
		
		try {
			imageService.deleteImage_1(command);
			return DwzJsonResultUtil.createJsonString("200", "删除成功!", null, "spiImage");
		} catch (Exception e) {
			HgLogger.getInstance().error("zzb", "图片删除失败:command【" + JSON.toJSONString(command, true) + "】");
			e.printStackTrace();
			return DwzJsonResultUtil.createJsonString("300", e.getMessage(), null, "");
	
		}
	}
	
	
	/**
	 * @方法功能说明：
	 * @修改者名字：zzb
	 * @修改时间：2014年11月18日下午5:47:29
	 * @修改内容：
	 * @参数：@param file
	 * @参数：@param maxHeight
	 * @参数：@param maxWidth
	 * @参数：@param maxSize
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/imgUpload")
	public String imgUpload(@RequestParam("file") CommonsMultipartFile file,
			String maxHeight, String maxWidth, String maxSize) {

		HgLogger.getInstance().info("zzb", "进入图片上传方法:file【" + JSON.toJSONString(file, true) + "】");

		// 1. 验证图片格式, 尺寸, 大小
		JSONObject result = new JSONObject();

		ImageInfo imgInfo = null;
		try {
			imgInfo = FileUploadUtils.validate(file, maxHeight, maxWidth, maxSize);
		} catch (HGException e) {
			HgLogger.getInstance().error("zzb", "图片上传文件失败:file【" + JSON.toJSONString(file, true) + "】");
			e.printStackTrace();
			result.put("status", "false");
			result.put("msg", e.getMessage());

			return result.toJSONString();
		}

		// 2. 上传图片
		FdfsFileInfo fileInfo = null;
		String ctxPath = null;
		try {
			
			// 2.1 保存图片到本地
			String realFileName = file.getOriginalFilename();
			ctxPath = System.getProperty("user.dir") + File.separator
					+ UUIDGenerator.getUUID() + File.separator;
			// 创建文件夹
			File dirPath = new File(ctxPath);
			if (!dirPath.exists()) {
				dirPath.mkdir();
			}
			// 创建文件
			String filePath = ctxPath + realFileName;
			File uploadFile = new File(filePath);
			// Copy文件
			FileCopyUtils.copy(file.getBytes(), uploadFile);
			
			// 2.2 设置meta值
			String md5 = null;
			Map<String, String> metaMap = new HashMap<String, String>();
			try {
				md5 = Md5FileUtil.getMD5(new FileInputStream(uploadFile));
				metaMap.put("md5", md5);
				metaMap.put("width", String.valueOf(imgInfo.getWidth()));
				metaMap.put("height", String.valueOf(imgInfo.getHeight()));
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				throw new HGException(HGException.CODE_IMAGESPE_MD5_ERROR, "图片MD5解析异常！");
			}
			
			// 2.3 上传
			FdfsFileUtil.init();
			try {
				fileInfo = FdfsFileUtil.upload(new FileInputStream(uploadFile), imgInfo.getFileType(), null);
				fileInfo.setMetaMap(metaMap);
			} catch (Exception e) {
				e.printStackTrace();
				throw new HGException(HGException.CODE_IMAGESPE_UPLOAD_ERROR, "图片上传异常！");
			}
		} catch (IOException e1) {
			HgLogger.getInstance().error("zzb", "图片上传文件失败:file【" + JSON.toJSONString(file, true) + "】");
			e1.printStackTrace();
			result.put("status", "false");
			result.put("msg", "图片上传异常！");
			
			return result.toJSONString();
		} catch (HGException e) {
			HgLogger.getInstance().error("zzb", "图片上传文件失败:file【" + JSON.toJSONString(file, true) + "】");
			e.printStackTrace();
			result.put("status", "false");
			result.put("msg", e.getMessage());
			
			return result.toJSONString();
		} finally {
			if (ctxPath != null) {
				FileUtil.deleteContents(new File(ctxPath));
				new File(ctxPath).delete();
			}
		}
		

		// 3. 设置返回值
		String imageUrl = hgSystemProperties.getImageStaticUrl()
				+ fileInfo.getUri();

		result.put("status", "success");
		result.put("imageUrl", imageUrl);
		result.put("imageName", imgInfo.getFileName());
		result.put("fileInfo", JSON.toJSONString(fileInfo));

		HgLogger.getInstance().debug("renfeng", "图片上传成功");

		return result.toJSONString();
	}
	
}
