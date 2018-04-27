package lxs.admin.controller.carousel;

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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lxs.app.service.app.CarouselService;
import lxs.app.service.line.LineService;
import lxs.app.service.mp.ScenicSpotService;
import lxs.domain.model.app.Carousel;
import lxs.domain.model.line.Line;
import lxs.domain.model.mp.ScenicSpot;
import lxs.pojo.command.app.AddCarouselCommand;
import lxs.pojo.command.app.UpdateCarouselCommand;
import lxs.pojo.dto.app.CarouselDTO;
import lxs.pojo.dto.line.LineDTO;
import lxs.pojo.dto.mp.ScenicSpotDTO;
import lxs.pojo.qo.app.CarouselQO;
import lxs.pojo.qo.line.LineQO;
import lxs.pojo.qo.mp.ScenicSpotQO;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @类功能说明：轮播图控制器
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：jinyy
 * @创建时间：2015年5月7日下午2:12:11
 */
@Controller
@RequestMapping(value = "/carousel")
public class CarouselController {
	@Autowired
	private CarouselService carouselService;

	@Autowired
	private ImageService_1 spiImageServiceImpl_1;

	@Autowired
	private LineService lineService;
	
	@Autowired
	private ScenicSpotService scenicSpotService;

	/**
	 * 
	 * 
	 * @方法功能说明：查询轮播图列表
	 * @修改者名字：jinyy
	 * @修改时间：2015-10-26 上午11:27:41
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/carouselList")
	public String queryCarouselList(
			HttpServletRequest request,
			Model model,
			@ModelAttribute CarouselQO carouselQO,
			@RequestParam(value = "pageNum", required = false) Integer pageNo,
			@RequestParam(value = "numPerPage", required = false) Integer pageSize) {
		if (pageNo == null) {
			pageNo = 1;
		}
		if (pageSize == null) {
			pageSize = 20;
		}
		carouselQO.setCarouselLevel(Carousel.HOMGPAGE);
		Pagination pagination = new Pagination();
		pagination.setCondition(carouselQO);
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		pagination = carouselService.queryPagination(pagination);

		List<Carousel> carousels = (List<Carousel>) pagination.getList();
		List<CarouselDTO> dtos = new ArrayList<CarouselDTO>();
		for (Carousel car : carousels) {
			CarouselDTO dto = new CarouselDTO();
			dto.setLinename(car.getCarouselAction());
			if (car.getCarouselType() == Carousel.LINE) {
				Line line = lineService.get(car.getCarouselAction());
				if (line != null) {
					dto.setLinename(line.getBaseInfo().getName());
				}

			}else if (car.getCarouselType() == Carousel.MENPIAO) {
				ScenicSpot scenicSpot = scenicSpotService.get(car.getCarouselAction());
				if (scenicSpot != null) {
					dto.setLinename(scenicSpot.getBaseInfo().getName());
				}
			}
			dto.setCarouselAction(car.getCarouselAction());

			dto.setCarouselType(car.getCarouselType());
			dto.setId(car.getId());
			dto.setImageURL(SysProperties.getInstance().get("fileUploadPath")
					+ car.getImageURL());// 加上cc地址
			dto.setNote(car.getNote());
			dto.setStatus(car.getStatus());
			dtos.add(dto);
		}
		model.addAttribute("pagination", dtos);
		model.addAttribute("carouselQO", carouselQO);
		return "/carousel/carouselList.html";
	}

	/**
	 * 
	 * 
	 * @方法功能说明：跳转到轮播图添加页面
	 * @修改者名字：jinyy
	 * @修改时间：2015-10-26 上午11:28:03
	 * @修改内容：
	 * @参数：@param 线路分页
	 * @return:void
	 * @throws
	 */
	@RequestMapping(value = "/addCarouselView")
	public String addCarouselView(
			Model model,
			@RequestParam(value = "pageNum", required = false) Integer pageNo,
			@RequestParam(value = "numPerPage", required = false) Integer pageSize) {

		List<Line> lines = lineService.queryList(new LineQO());
		model.addAttribute("lines", lines);
		return "/carousel/carouselAdd.html";

	}

	/**
	 * 
	 * 
	 * @方法功能说明：添加轮播图
	 * @修改者名字：jinyy
	 * @修改时间：2015-10-26 上午11:28:41
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	@RequestMapping(value = "/carouselAdd")
	@ResponseBody
	public String carouselAdd(Model model, AddCarouselCommand command) {

		String statusCode = DwzJsonResultUtil.STATUS_CODE_200;
		String message = "保存成功";
		String callbackType = DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT;

		if (StringUtils.isBlank(command.getCarouselAction())
				&& StringUtils.isBlank(command.getCarouselActionCheck())) {
			return DwzJsonResultUtil.createJsonString("300", "请选择线路!", null,
					"carouselList");
		}

		CarouselQO carouselQO = new CarouselQO();
		carouselQO.setCarouselLevel(Carousel.HOMGPAGE);

		Pagination pagination = new Pagination();
		pagination.setCondition(carouselQO);
		pagination = carouselService.queryPagination(pagination);

		if (pagination.getList() != null && pagination.getList().size() >= 10) {
			return DwzJsonResultUtil.createJsonString("300", "最多上传10张!", null,
					"carouselList");
		}
		CreateImageCommand createImageCommand = new CreateImageCommand();
		// 利旧URL存储file的json
		createImageCommand.setFileInfo(command.getImageInfo());
		createImageCommand.setFileName(command.getFileName());
		/* createImageCommand.setRemark("旅行社APP广告"); */
		createImageCommand.setUseTypeId(SysProperties.getInstance().get(
				"imageUseTypeId"));
		createImageCommand.setFromProjectEnvName(SysProperties.getInstance()
				.get("imageEnvName"));
		createImageCommand.setFromProjectId(SysProperties.getInstance().get(
				"imageProjectId"));
		createImageCommand.setAlbumId(SysProperties.getInstance().get(
				"imageUseTypeId"));

		try {
			String imageID = spiImageServiceImpl_1
					.createImage_1(createImageCommand);
			HgLogger.getInstance().info("lxs_dev",
					"【addAd】" + "新增轮播图片在图片服务中的ID：" + imageID);
			ImageQo imageQo = new ImageQo();
			imageQo.setId(imageID);
			ImageDTO imageDTO = spiImageServiceImpl_1
					.queryUniqueImage_1(imageQo);
			HgLogger.getInstance().info(
					"lxs_dev",
					"【addCarousel】" + "查询图片服务里得到结果："
							+ JSON.toJSONString(imageDTO));
			Map<String, String> map = new HashMap<String, String>();
			for (String key : imageDTO.getSpecImageMap().keySet()) {
				FdfsFileInfo fdfsFileInfo = JSON.parseObject(imageDTO
						.getSpecImageMap().get(key).getFileInfo(),
						FdfsFileInfo.class);
				map.put(key, fdfsFileInfo.getUri());
			}
			command.setImagePath(map.get(SysProperties.getInstance().get(
					"adImageType")));

		} catch (ImageException e) {
			HgLogger.getInstance().info(
					"lxs_dev",
					"【addCarousel】" + "上传轮播图片，图片服务报错"
							+ HgLogger.getStackTrace(e));
			e.printStackTrace();
		} catch (IOException e) {
			HgLogger.getInstance().info(
					"lxs_dev",
					"【addCarousel】" + "上传轮播图片，图片服务IO报错"
							+ HgLogger.getStackTrace(e));
			e.printStackTrace();
		}

		command.setCarouselLevel(Carousel.HOMGPAGE);
		carouselService.addCarouse(command);
		model.addAttribute("pagination", pagination);

		return DwzJsonResultUtil.createJsonString(statusCode, message,
				callbackType, "pageView");

	}

	/**
	 * 
	 * 
	 * @方法功能说明：修改
	 * @修改者名字：jinyy
	 * @修改时间：2015-10-26 上午11:29:44
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	@RequestMapping(value = "/carouselEdit")
	@ResponseBody
	public String carouselEdit(Model model, UpdateCarouselCommand command) {
		String statusCode = DwzJsonResultUtil.STATUS_CODE_200;
		String message = "保存成功";
		String callbackType = DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT;
		if (StringUtils.isBlank(command.getCarouselAction())
				&& StringUtils.isBlank(command.getCarouselActionCheck())) {
			return DwzJsonResultUtil.createJsonString("300", "请选择线路!", null,
					"carouselList");
		}

		if (StringUtils.isBlank(command.getFileName())) {

		} else {

			CreateImageCommand createImageCommand = new CreateImageCommand();
			// 利旧URL存储file的json
			createImageCommand.setFileInfo(command.getImageInfo());
			createImageCommand.setFileName(command.getFileName());
			/* createImageCommand.setRemark("旅行社APP广告"); */
			createImageCommand.setUseTypeId(SysProperties.getInstance().get(
					"imageUseTypeId"));
			createImageCommand.setFromProjectEnvName(SysProperties
					.getInstance().get("imageEnvName"));
			createImageCommand.setFromProjectId(SysProperties.getInstance()
					.get("imageProjectId"));
			createImageCommand.setAlbumId(SysProperties.getInstance().get(
					"imageUseTypeId"));

			try {
				String imageID = spiImageServiceImpl_1
						.createImage_1(createImageCommand);
				HgLogger.getInstance().info("lxs_dev",
						"【addAd】" + "新增轮播图片在图片服务中的ID：" + imageID);
				ImageQo imageQo = new ImageQo();
				imageQo.setId(imageID);
				ImageDTO imageDTO = spiImageServiceImpl_1
						.queryUniqueImage_1(imageQo);

				HgLogger.getInstance().info(
						"lxs_dev",
						"【addCarousel】" + "查询图片服务里得到结果："
								+ JSON.toJSONString(imageDTO));
				Map<String, String> map = new HashMap<String, String>();
				for (String key : imageDTO.getSpecImageMap().keySet()) {
					FdfsFileInfo fdfsFileInfo = JSON.parseObject(imageDTO
							.getSpecImageMap().get(key).getFileInfo(),
							FdfsFileInfo.class);
					map.put(key, fdfsFileInfo.getUri());
				}
				command.setImagePath(map.get(SysProperties.getInstance().get(
						"adImageType")));
			} catch (ImageException e) {
				HgLogger.getInstance().info(
						"lxs_dev",
						"【addCarousel】" + "上传轮播图片，图片服务报错"
								+ HgLogger.getStackTrace(e));
				e.printStackTrace();
			} catch (IOException e) {
				HgLogger.getInstance().info(
						"lxs_dev",
						"【addCarousel】" + "上传轮播图片，图片服务IO报错"
								+ HgLogger.getStackTrace(e));
				e.printStackTrace();
			}

		}

		carouselService.editCarouse(command);

		/*
		 * CarouselQO carouselQO = new CarouselQO();
		 * 
		 * Pagination pagination = new Pagination();
		 * pagination.setCondition(carouselQO); pagination =
		 * carouselService.queryPagination(pagination);
		 * model.addAttribute("pagination", pagination);
		 */

		return DwzJsonResultUtil.createJsonString(statusCode, message,
				callbackType, "pageView");

	}

	/**
	 * 禁用，启用轮播图片
	 */

	@RequestMapping(value = "/modifyCarouselStatus")
	@ResponseBody
	public String modifyCarouselStatus(
			@RequestParam(value = "id") String carouselID,
			@RequestParam(value = "isUse") String isUse) {
		UpdateCarouselCommand updateCarouselCommand = new UpdateCarouselCommand();
		updateCarouselCommand.setId(carouselID);
		if ("true".equals(isUse)) {
			updateCarouselCommand.setStatus(2);
		} else {
			updateCarouselCommand.setStatus(1);
		}
		carouselService.modifyCarouselStatus(updateCarouselCommand);
		return DwzJsonResultUtil.createJsonString(
				DwzJsonResultUtil.STATUS_CODE_200, "状态修改成功!", null, "");

	}

	@RequestMapping(value = "/deleteCarousel")
	@ResponseBody
	public String deleteCarousel(@RequestParam(value = "id") String carouselID) {
		carouselService.deleteById(carouselID);
		return DwzJsonResultUtil.createSimpleJsonString("200", "删除成功！");

	}

	/**
	 * 跳转到修改页面
	 * 
	 * @param model
	 * @param carouselID
	 * @return
	 */
	@RequestMapping(value = "/editCarouselView")
	public String editCarousel(Model model,
			@RequestParam(value = "id") String carouselID) {

		CarouselQO carouselQO = new CarouselQO();
		carouselQO.setId(carouselID);
		Carousel carousel = carouselService.queryUnique(carouselQO);
		carousel.setImageURL(SysProperties.getInstance().get("fileUploadPath")
				+ carousel.getImageURL());

		model.addAttribute("carousel", carousel);

		List<Line> lines = lineService.queryList(new LineQO());

		model.addAttribute("lines", lines);
		return "/carousel/carouselEdit.html";
	}

	/**
	 * 
	 * 
	 * @方法功能说明：线路列表
	 * @修改者名字：jinyy
	 * @修改时间：2015-10-26 上午11:30:40
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/lineList")
	public String queryLineList(
			HttpServletRequest request,
			Model model,
			HttpServletResponse response,
			@ModelAttribute LineQO lineQO,
			@RequestParam(value = "pageNum", required = false) Integer pageNo,
			@RequestParam(value = "numPerPage", required = false) Integer pageSize,
			@RequestParam(value = "id1", required = false) String carouselID,
			@RequestParam(value = "handleType", required = false) String handleType)
			throws IOException {
		if (pageNo == null) {
			pageNo = 1;
		}
		if (pageSize == null) {
			pageSize = 20;
		}
		if (lineQO.getForSale() == 0) {
			lineQO.setForSaletype("yes");
		}

		CarouselQO carouselQO = new CarouselQO();
		carouselQO.setId(carouselID);
		Carousel carousel = carouselService.queryUnique(carouselQO);
		if (carousel == null) {
			carousel = new Carousel();
		}

		model.addAttribute("carousel", carousel);

		lineQO.setLocalStatus(LineQO.NOT_DEL);
		lineQO.setSort(LineQO.QUERY_WITH_TOP);
		Pagination pagination = new Pagination();
		pagination.setCondition(lineQO);
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		pagination = lineService.queryPagination(pagination);
		model.addAttribute("lineQO", lineQO);
		response.setCharacterEncoding("UTF-8");
		List<LineDTO> lines = EntityConvertUtils.convertEntityToDtoList(
				(List<LineDTO>) pagination.getList(), LineDTO.class);

		if (StringUtils.isNotBlank(carouselID) && lines != null
				&& lines.size() > 1) {
			for (LineDTO lineDTO : lines) {
				if (carouselID.equals(lineDTO.getId())) {
					lines.set(0, lineDTO);
				}
			}
		}
		model.addAttribute("lines", lines);
		model.addAttribute("pagination", pagination);
		model.addAttribute("handleType", handleType);
		return "/carousel/lineLists.html";
	}
	
	/**
	 * @Title: queryMpList 
	 * @author guok
	 * @Description: 景区门票列表
	 * @Time 2016年3月3日下午2:55:17
	 * @param request
	 * @param model
	 * @param response
	 * @param scenicSpotQO
	 * @param pageNo
	 * @param pageSize
	 * @param carouselID
	 * @param handleType
	 * @return String 设定文件
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/mpList")
	public String queryMpList(
			HttpServletRequest request,
			Model model,
			HttpServletResponse response,
			@ModelAttribute ScenicSpotQO scenicSpotQO,
			@RequestParam(value = "pageNum", required = false) Integer pageNo,
			@RequestParam(value = "numPerPage", required = false) Integer pageSize,
			@RequestParam(value = "id1", required = false) String carouselID,
			@RequestParam(value = "handleType", required = false) String handleType){
		if (pageNo == null) {
			pageNo = 1;
		}
		if (pageSize == null) {
			pageSize = 20;
		}
		CarouselQO carouselQO = new CarouselQO();
		carouselQO.setId(carouselID);
		Carousel carousel = carouselService.queryUnique(carouselQO);
		if (carousel == null) {
			carousel = new Carousel();
		}

		model.addAttribute("carousel", carousel);
//		scenicSpotQO.setLocalStatus(ScenicSpot.SHOW);
		Pagination pagination = new Pagination();
		pagination.setCondition(scenicSpotQO);
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		pagination = scenicSpotService.queryPagination(pagination);
		List<ScenicSpotDTO> scenicSpotList = EntityConvertUtils.convertEntityToDtoList(
				(List<ScenicSpotDTO>) pagination.getList(), ScenicSpotDTO.class);
		
		if (StringUtils.isNotBlank(carouselID) && scenicSpotList != null
				&& scenicSpotList.size() > 1) {
			for (ScenicSpotDTO scenicSpotDTO : scenicSpotList) {
				if (carouselID.equals(scenicSpotDTO.getId())) {
					scenicSpotList.set(0, scenicSpotDTO);
				}
			}
		}
		
		model.addAttribute("scenicSpotList", scenicSpotList);
		
		model.addAttribute("scenicSpotQO", scenicSpotQO);
		model.addAttribute("pagination", pagination);
		model.addAttribute("handleType", handleType);
		
		return "/carousel/mpLists.html";
	}

}
