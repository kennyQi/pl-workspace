package hgria.admin.controller.hgCommon.spi.image;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import hg.common.page.Pagination;
import hg.common.util.DwzJsonResultUtil;
import hg.common.util.SysProperties;
import hg.common.util.file.DownloadUtil;
import hg.common.util.file.FdfsFileInfo;
import hg.common.util.file.FdfsFileUtil;
import hg.common.util.image.ImageUtil;
import hg.log.util.HgLogger;
import hg.service.image.command.image.spec.DeleteImageSpecCommand;
import hg.service.image.command.image.spec.ModifyImageSpecCommand;
import hg.service.image.pojo.dto.ImageDTO;
import hg.service.image.pojo.dto.ImageSpecDTO;
import hg.service.image.pojo.exception.ImageException;
import hg.service.image.pojo.qo.ImageQo;
import hg.service.image.pojo.qo.ImageSpecQo;
import hg.service.image.pojo.qo.ImageUseTypeQo;
import hg.service.image.spi.inter.ImageService_1;
import hgria.admin.common.hgUtil.HGSystemProperties;
import hgria.admin.controller.BaseController;
import hgria.admin.manager.HGSessionUserManager;
import hgria.domain.model.project.Project;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * @类功能说明：图片附件_控制器
 * @类修改者：zzb
 * @修改日期：2014年9月4日下午1:39:57
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzb
 * @创建时间：2014年9月4日下午1:39:57
 */
@Controller
@RequestMapping(value = "/hg/spiAlbum/spiImageSpec")
public class SpiImageSpecController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory
			.getLogger(SpiImageSpecController.class);

	/**
	 * 图片service
	 */
	@Resource(name = "spiImageServiceImpl_1")
	private ImageService_1 imageService;

	public final static Integer LARGE_NUM = 99999;

	/**
	 * 系统配置参数
	 */
	@Resource(name = "hgSystemProperties")
	HGSystemProperties hgSystemProperties;

	/**
	 * 图片附件管理页面
	 */
	public final static String PAGE_VIEW = "/content/spiImage/imageSpec.html";

	/**
	 * 图片附件裁剪页面
	 */
	public final static String PAGE_CUT = "/content/spiImage/imageSpecCut.html";

	/**
	 * @方法功能说明：图片裁剪跳转
	 * @修改者名字：zzb
	 * @修改时间：2014年11月19日下午2:06:03
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param imageSpecQo
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/view")
	public String view(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@ModelAttribute ImageSpecQo imageSpecQo) {

		HgLogger.getInstance().info(
				"zzb",
				"进入图片附件裁剪跳转方法:imageSpecQo【"
						+ JSON.toJSONString(imageSpecQo, true) + "】");

		Project project = HGSessionUserManager.getSessionProject(request);
		if (project == null)
			return null;

		// 1. 空值判断
		if (imageSpecQo == null || imageSpecQo.getImage() == null)
			return null;

		// 2. 查出 所有附件
		imageSpecQo.setPageSize(LARGE_NUM);
		imageSpecQo.setProjectId(project.getId());
		imageSpecQo.setEnvName(project.getEnvName());
		Pagination pagination = imageService
				.queryImageSpecPagination_1(imageSpecQo);
		String imageStaticUrl = hgSystemProperties.getImageStaticUrl();

		// 3. 查询出 图片, useType;
		ImageQo imageQo = new ImageQo();
		imageQo.setUseType(new ImageUseTypeQo());
		imageQo.setId(imageSpecQo.getImage().getId());
		imageQo.setProjectId(project.getId());
		imageQo.setEnvName(project.getEnvName());
		ImageDTO imageDTO = imageService.queryUniqueImage_1(imageQo);

		// 4. 设置返回值
		if (pagination.getList() != null)
			model.addAttribute("imageSpecList", pagination.getList());
		model.addAttribute("imageStaticUrl", imageStaticUrl);
		model.addAttribute("image", imageDTO);

		return PAGE_VIEW;
	}

	/**
	 * @方法功能说明：附件裁剪跳转
	 * @修改者名字：zzb
	 * @修改时间：2014年11月10日下午4:22:00
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param imageSpecQo
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/cut")
	public String cut(HttpServletRequest request, HttpServletResponse response,
			Model model, @ModelAttribute ImageSpecQo imageSpecQo) {

		HgLogger.getInstance().info(
				"zzb",
				"进入图片附件裁剪跳转方法:imageSpecQo【"
						+ JSON.toJSONString(imageSpecQo, true) + "】");

		Project project = HGSessionUserManager.getSessionProject(request);
		if (project == null)
			return null;

		// 1. 空值判断
		// if (imageSpecQo == null
		// || StringUtils.isBlank(imageSpecQo.getId())
		// || StringUtils.isBlank(imageSpecQo.getImageId()))
		// return null;

		// 2. 查询属性值
		ImageSpecDTO imageSpecDTO = imageService
				.queryUniqueImageSpec_1(imageSpecQo);
		if (imageSpecDTO == null)
			return null;
		String imageStaticUrl = hgSystemProperties.getImageStaticUrl();

		// 3. 查询出 图片, useType;
		ImageQo imageQo = new ImageQo();
		imageQo.setId(request.getParameter("imageId"));
		imageQo.setUseType(new ImageUseTypeQo());
		ImageDTO imageDTO = imageService.queryUniqueImage_1(imageQo);

		// 4. 设置返回值
		model.addAttribute("imageStaticUrl", imageStaticUrl);
		model.addAttribute("imageSpec", imageSpecDTO);
		model.addAttribute("image", imageDTO);
		return PAGE_CUT;
	}

	/**
	 * @方法功能说明：裁剪保存
	 * @修改者名字：zzb
	 * @修改时间：2014年11月10日下午6:32:00
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
	@RequestMapping(value = "/cutSave")
	public String cutSave(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@ModelAttribute ModifyImageSpecCommand command) {

		HgLogger.getInstance().info(
				"zzb",
				"进入图片附件裁剪保存方法:command【" + JSON.toJSONString(command, true)
						+ "】");

		// 1. 默认返回值
		String statusCode = DwzJsonResultUtil.STATUS_CODE_200;
		String message = "裁剪成功!";
		String callbackType = DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT;

		// 查询规格原图
		ImageSpecQo qo = new ImageSpecQo();
		qo.setId(command.getImageSpecId());
		ImageSpecDTO imageSpecDTO = imageService.queryUniqueImageSpec_1(qo);

		// 下载
		FdfsFileInfo fileInfo = JSON.parseObject(imageSpecDTO.getFileInfo(),
				FdfsFileInfo.class);
		String url = SysProperties.getInstance().get("fileUploadPath")
				+ fileInfo.getUri();
		File srcFile = null;
		try {
			srcFile = DownloadUtil.saveToFile(url);
		} catch (IOException e1) {
			e1.printStackTrace();
			
		}

		int left, top, width, height;
		left = Integer.parseInt(getParam(request, "_left"));
		top = Integer.parseInt(getParam(request, "_top"));
		width = Integer.parseInt(getParam(request, "_width"));
		height = Integer.parseInt(getParam(request, "_height"));

		// 裁剪
		try {
			ImageUtil.cutImage(srcFile, srcFile.getAbsolutePath(), left, top,
					width, height);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		Image src = null;
		try {
			src = ImageIO.read(srcFile);
		} catch (IOException e1) {
			e1.printStackTrace();
			HgLogger.getInstance().error(
					"zzb",
					"图片读取失败:command【" + JSON.toJSONString(command, true)
							+ "】");
			e1.printStackTrace();
			statusCode = DwzJsonResultUtil.STATUS_CODE_500;
			message = e1.getMessage();
			return DwzJsonResultUtil.createJsonString(statusCode, message,
					callbackType, "imageSpec");
		}

		String imageName = srcFile.getName();
		Map<String, String> metaMap = new HashMap<String, String>();
		metaMap.put("title", imageName);
		metaMap.put("height", String.valueOf(src.getHeight(null)));
		metaMap.put("width", String.valueOf(src.getWidth(null)));
		FdfsFileUtil.init();
		//	原图文件信息换成了新图文件信息
		fileInfo = FdfsFileUtil.upload(srcFile, metaMap);
		if (fileInfo == null) {
			HgLogger.getInstance().error(
					"zzb",
					"新图片上传失败:command【" + JSON.toJSONString(command, true)
							+ "】");
			statusCode = DwzJsonResultUtil.STATUS_CODE_500;
			message = "新图片上传失败:command【" + JSON.toJSONString(command, true)
					+ "】";
			return DwzJsonResultUtil.createJsonString(statusCode, message,
					callbackType, "imageSpec");
		}
		
		// 3. 更新
		Project project = HGSessionUserManager.getSessionProject(request);
		qo = new ImageSpecQo(project.getId(), project.getEnvName());
		qo.setKey(getParam(request, "key"));
		ImageQo image = new ImageQo();
		image.setId(JSON.parseObject(imageSpecDTO.getFileInfo(), FdfsFileInfo.class).getImageId());
		qo.setImage(image);
		ImageSpecDTO imageSpecDTONew = imageService.queryUniqueImageSpec_1(qo);
		command.setImageSpecId(imageSpecDTONew.getId());
		command.setFileInfo(JSON.toJSONString(fileInfo));
		try {
			imageService.modifyImageSpec_1(command);
		} catch (Exception e) {
			HgLogger.getInstance().error(
					"zzb",
					"图片裁剪保存失败:command【" + JSON.toJSONString(command, true)
							+ "】");
			e.printStackTrace();
			statusCode = DwzJsonResultUtil.STATUS_CODE_500;
			message = e.getMessage();
		}

		return DwzJsonResultUtil.createJsonString(statusCode, message,
				callbackType, "imageSpec");
	}

	/**
	 * @方法功能说明：图片附件删除
	 * @修改者名字：zzb
	 * @修改时间：2014年11月19日下午6:21:49
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
	public String del(HttpServletRequest request, HttpServletResponse response,
			Model model, @ModelAttribute DeleteImageSpecCommand command) {

		HgLogger.getInstance().info("zzb",
				"进入图片附件删除方法:command【" + JSON.toJSONString(command, true) + "】");

		try {
			imageService.deleteImageSpec_1(command);
			return DwzJsonResultUtil.createJsonString("200", "删除成功!", null,
					"spiImageSpec");
		} catch (ImageException e) {
			HgLogger.getInstance().error(
					"zzb",
					"图片附件删除失败:command【" + JSON.toJSONString(command, true)
							+ "】");
			e.printStackTrace();
			return DwzJsonResultUtil.createJsonString("300", e.getMessage(),
					null, "");
		}

	}
}
