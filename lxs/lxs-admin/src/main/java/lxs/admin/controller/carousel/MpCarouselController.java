
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
import lxs.app.service.mp.ScenicSpotService;
import lxs.domain.model.app.Carousel;
import lxs.domain.model.mp.ScenicSpot;
import lxs.pojo.command.app.AddCarouselCommand;
import lxs.pojo.command.app.UpdateCarouselCommand;
import lxs.pojo.dto.app.CarouselDTO;
import lxs.pojo.dto.mp.ScenicSpotDTO;
import lxs.pojo.qo.app.CarouselQO;
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
 * 门票轮播图
 * @author DONGDA
 *
 */
@Controller
@RequestMapping(value = "/mpCarousel")
public class MpCarouselController {
	@Autowired
	private CarouselService carouselService;

	@Autowired
	private ImageService_1 spiImageServiceImpl_1;

	@Autowired
	private ScenicSpotService scenicSpotService;

	/**
	 * @Title: queryCarouselList 
	 * @author guok
	 * @Description: 景区轮播图列表
	 * @Time 2016年3月8日下午5:16:57
	 * @param request
	 * @param model
	 * @param carouselQO
	 * @param pageNo
	 * @param pageSize
	 * @return String 设定文件
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
		carouselQO.setCarouselLevel(Carousel.MENPIAO);
		Pagination pagination = new Pagination();
		pagination.setCondition(carouselQO);
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		pagination = carouselService.queryPagination(pagination);
		
		List<Carousel> carousels= (List<Carousel>) pagination.getList();
		List<CarouselDTO> dtos =new ArrayList<CarouselDTO>();
		for (Carousel car : carousels) {
			CarouselDTO dto= new CarouselDTO();
			dto.setLinename(car.getCarouselAction());
			if (car.getCarouselType()==Carousel.MENPIAO) {
				ScenicSpot scenicSpot=scenicSpotService.get(car.getCarouselAction());
				if (scenicSpot!=null) {
					dto.setLinename(scenicSpot.getBaseInfo().getName());
				}
				
			}
			dto.setCarouselAction(car.getCarouselAction());
			
			dto.setCarouselType(car.getCarouselType());
			dto.setId(car.getId());
			dto.setImageURL(SysProperties.getInstance().get("fileUploadPath")+car.getImageURL());
			dto.setNote(car.getNote());
			dto.setStatus(car.getStatus());
			dtos.add(dto);
		}
		model.addAttribute("pagination", dtos);
		model.addAttribute("carouselQO", carouselQO);
		return "/mpCarousel/mpCarouselList.html";
	}

	/**
	 * @Title: addCarouselView 
	 * @author guok
	 * @Description: 跳转景区轮播图添加页
	 * @Time 2016年3月8日下午5:17:19
	 * @param model
	 * @param pageNo
	 * @param pageSize
	 * @return String 设定文件
	 * @throws
	 */
	@RequestMapping(value = "/addCarouselView")
	public String addCarouselView(Model model,@RequestParam(value = "pageNum", required = false) Integer pageNo,
			@RequestParam(value = "numPerPage", required = false) Integer pageSize) {
		
		return "/mpCarousel/mpCarouselAdd.html";

	}

	/**
	 * 添加数据
	 * 
	 * @param model
	 * @param command
	 * @return
	 */
	@RequestMapping(value = "/carouselAdd")
	@ResponseBody
	public String carouselAdd(Model model, AddCarouselCommand command) {
		
		String statusCode = DwzJsonResultUtil.STATUS_CODE_200;
		String message = "保存成功";
		String callbackType = DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT;
		
		if (StringUtils.isBlank(command.getCarouselAction())&&StringUtils.isBlank(command.getCarouselActionCheck())) {
			return DwzJsonResultUtil.createJsonString("300", "请选择线路!",
					null, "carouselList");
		}
		
		
		CarouselQO carouselQO = new CarouselQO();
		carouselQO.setCarouselLevel(Carousel.MENPIAO);

		Pagination pagination = new Pagination();
		pagination.setCondition(carouselQO);
		pagination = carouselService.queryPagination(pagination);

		
		if (pagination.getList()!=null&&pagination.getList().size()>=10) {
			return DwzJsonResultUtil.createJsonString("300", "最多上传10张!",
					null, "carouselList");
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

		command.setCarouselLevel(Carousel.MENPIAO);
		carouselService.addCarouse(command);
		model.addAttribute("pagination", pagination);

		return DwzJsonResultUtil.createJsonString(statusCode, message,
				callbackType, "pageView");

	}

	/**
	 * @Title: carouselEdit 
	 * @author guok
	 * @Description: 修改景区轮播图
	 * @Time 2016年3月8日下午5:18:13
	 * @param model
	 * @param command
	 * @return String 设定文件
	 * @throws
	 */
	@RequestMapping(value = "/mpCarouselEdit")
	@ResponseBody
	public String carouselEdit(Model model, UpdateCarouselCommand command) {
		String statusCode = DwzJsonResultUtil.STATUS_CODE_200;
		String message = "修改成功";
		String callbackType = DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT;
		if (StringUtils.isBlank(command.getCarouselAction())&&StringUtils.isBlank(command.getCarouselActionCheck())) {
			return DwzJsonResultUtil.createJsonString("300", "请选择景区!",
					null, "pageView");
		}
		
		if (StringUtils.isBlank(command.getFileName())) {

		}else {
			
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

		}
		
		carouselService.editCarouse(command);

		CarouselQO carouselQO = new CarouselQO();

		Pagination pagination = new Pagination();
		pagination.setCondition(carouselQO);
		pagination = carouselService.queryPagination(pagination);
		model.addAttribute("pagination", pagination);

		return DwzJsonResultUtil.createJsonString(statusCode, message,
				callbackType, "pageView");

	}

	/**
	 * @Title: modifyCarouselStatus 
	 * @author guok
	 * @Description: 禁用，启用轮播图片
	 * @Time 2016年3月8日下午5:18:57
	 * @param carouselID
	 * @param isUse
	 * @return String 设定文件
	 * @throws
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
				DwzJsonResultUtil.STATUS_CODE_200, "状态修改成功!", null, "pageView");

	}

	/**
	 * @Title: deleteCarousel 
	 * @author guok
	 * @Description: 删除轮播图
	 * @Time 2016年3月8日下午5:19:06
	 * @param carouselID
	 * @return String 设定文件
	 * @throws
	 */
	@RequestMapping(value = "/deleteCarousel")
	@ResponseBody
	public String deleteCarousel(@RequestParam(value = "id") String carouselID) {
		carouselService.deleteById(carouselID);
		return DwzJsonResultUtil.createJsonString(
				DwzJsonResultUtil.STATUS_CODE_200, "删除成功!", null, "pageView");

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
		carousel.setImageURL(SysProperties.getInstance().get("fileUploadPath")+carousel.getImageURL());

		model.addAttribute("carousel", carousel);

		return "/mpCarousel/mpCarouselEdit.html";
	}
	
	/**
	 * @Title: queryMpList 
	 * @author guok
	 * @Description: 景区列表
	 * @Time 2016年3月8日下午5:19:23
	 * @param request
	 * @param model
	 * @param response
	 * @param scenicSpotQO
	 * @param pageNo
	 * @param pageSize
	 * @param carouselID
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
			@RequestParam(value = "numPerPage", required = false) Integer pageSize,@RequestParam(value = "id1",required = false) String carouselID,@RequestParam(value = "handleType", required = false) String handleType) throws IOException {
		if (pageNo == null) {
			pageNo = 1;
		}
		if (pageSize == null) {
			pageSize = 20;
		}
		
		CarouselQO carouselQO = new CarouselQO();
		carouselQO.setId(carouselID);
		Carousel carousel = carouselService.queryUnique(carouselQO);
		if (carousel==null) {
			carousel=new Carousel();
		}
		
		model.addAttribute("carousel", carousel);

		
//		scenicSpotQO.setLocalStatus(ScenicSpot.SHOW);
		Pagination pagination = new Pagination();
		pagination.setCondition(scenicSpotQO);
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		pagination = scenicSpotService.queryPagination(pagination);
		model.addAttribute("scenicSpotQO",scenicSpotQO);
		response.setCharacterEncoding("UTF-8"); 
		List<ScenicSpotDTO> scenicSpots = EntityConvertUtils.convertEntityToDtoList(
				(List<ScenicSpotDTO>) pagination.getList(), ScenicSpotDTO.class);
		
		if (StringUtils.isNotBlank(carouselID)&&scenicSpots!=null&&scenicSpots.size()>1) {
			for (ScenicSpotDTO lineDTO : scenicSpots) {
				if (carouselID.equals(lineDTO.getId())) {
					scenicSpots.set(0, lineDTO);
				}
			}
		}
		model.addAttribute("scenicSpots", scenicSpots);
		model.addAttribute("pagination", pagination);
		
		model.addAttribute("handleType", handleType);
		return "/mpCarousel/mpLists.html";
	}

}

