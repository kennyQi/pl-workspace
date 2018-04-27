package pl.admin.controller.scenic;
import hg.common.page.Pagination;
import hg.common.util.DwzJsonResultUtil;
import hg.common.util.SysProperties;
import hg.log.util.HgLogger;
import hg.service.image.pojo.dto.ImageDTO;
import hg.service.image.pojo.qo.AlbumQo;
import hg.service.image.pojo.qo.ImageQo;
import hg.service.image.pojo.qo.ImageUseTypeQo;
import hg.service.image.spi.inter.ImageService_1;
import hg.system.model.meta.City;
import hg.system.model.meta.Province;
import hg.system.qo.CityQo;
import hg.system.qo.ProvinceQo;
import hg.system.service.CityService;
import hg.system.service.ProvinceService;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.admin.controller.BaseController;
import pl.app.service.ScenicServiceImpl;
import pl.cms.domain.entity.scenic.Scenic;
import pl.cms.pojo.command.scenic.CreateScenicCommand;
import pl.cms.pojo.command.scenic.ModifyScenicCommand;
import pl.cms.pojo.qo.ScenicQO;

import com.alibaba.fastjson.JSON;
@Controller
@RequestMapping("/scenic")
public class ScenicController extends BaseController {

	@Autowired
	private ScenicServiceImpl scenicServiceImpl;
	@Resource
	private ProvinceService provinceService;
	@Resource
	private CityService cityService;
	@Autowired
	private ImageService_1 imageService_1;
	/**
	 * @方法功能说明：景区列表
	 * @修改者名字：chenxy
	 * @修改时间：2015年3月13日下午5:35:24
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param qo
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/list")
	public String list(HttpServletRequest request, Model model,
			@ModelAttribute ScenicQO qo) {
		model.addAttribute("param", qo);
		qo.setNameLike(true);	
		qo.setOrderByCreateDate(-1);
		Pagination pagination = new Pagination();
		pagination.setPageNo(qo.getPageNo());
		pagination.setPageSize(qo.getPageSize());
		pagination.setCondition(qo);
		pagination = scenicServiceImpl.queryPagination(pagination);
		model.addAttribute("pagination", pagination);

		return "/scenic/list.html";
	}

	@RequestMapping(value = "/add")
	public String add(HttpServletRequest request, Model model)
			throws IOException {
		ProvinceQo provinceQo = new ProvinceQo();
		List<Province> provinceList = provinceService.queryList(provinceQo);
		model.addAttribute("provinces", provinceList);
		model.addAttribute("grades", ScenicQO.SCENIC_GRADE_LIST);
		return "/scenic/add.html";
	}

	@RequestMapping(value = "/edit/{scenicId}")
	public String edit(HttpServletRequest request, Model model,
			@PathVariable String scenicId) {
		ProvinceQo provinceQo = new ProvinceQo();
		List<Province> provinceList = provinceService.queryList(provinceQo);
		model.addAttribute("provinces", provinceList);
		ScenicQO qo = new ScenicQO();
		qo.setId(scenicId);
		qo.setFetchCity(true);
		qo.setFetchProvince(true);
		Scenic scenic = scenicServiceImpl.queryUnique(qo);
		model.addAttribute("scenic", scenic);
		model.addAttribute("grades", ScenicQO.SCENIC_GRADE_LIST);
		model.addAttribute("fileUploadPath", SysProperties.getInstance().get("fileUploadPath"));
		
		ImageQo imageQo = new ImageQo();
		imageQo.setProjectId(SysProperties.getInstance().get("imageServiceProjectId"));
		imageQo.setEnvName(SysProperties.getInstance().get("imageServiceProjectEnvName"));
		imageQo.setId(scenic.getScenicSpotBaseInfo().getTitleImage().getImageId());
		ImageUseTypeQo useType=new ImageUseTypeQo();
		useType.setId("PL_CMS_SCENIC_TITLE_IMAGE");
		imageQo.setUseType(useType);
		AlbumQo albumQo=new AlbumQo();
		albumQo.setId("PL_CMS_SCENIC_TITLE_IMAGE");
		imageQo.setAlbumQo(albumQo);
		ImageDTO imageDTO = imageService_1.queryUniqueImage_1(imageQo);
		
		model.addAttribute("fdfsFileInfoJSON", imageDTO.getSpecImageMap().get("default").getFileInfo());
		return "/scenic/edit.html";
	}

	@RequestMapping(value = "/update")
	@ResponseBody
	public String update(HttpServletRequest request, Model model,
			@ModelAttribute ModifyScenicCommand command) {

		if (command != null) {
			try {
				scenicServiceImpl.modifyScenic(command);
			} catch (Exception e) {
				e.printStackTrace();
				return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_500, "修改失败",DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "scenicList");
			}
		}

		return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "修改成功",DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "scenicList");
	}

	@ResponseBody
	@RequestMapping(value = "/delete/{scenicId}")
	public String delete(HttpServletRequest request, Model model,
			@PathVariable String scenicId) {
		scenicServiceImpl.deleteById(scenicId);
		return DwzJsonResultUtil.createSimpleJsonString(DwzJsonResultUtil.STATUS_CODE_200, "删除成功");
	}

	@ResponseBody
	@RequestMapping(value = "/create")
	public String create(HttpServletRequest request, Model model,
			@ModelAttribute CreateScenicCommand command) {
		
		if (command != null) {
			try {
				scenicServiceImpl.createScenic(command);
			} catch (Exception e) {
				e.printStackTrace();
				return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_500, "添加失败",DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "scenicList");
			}
		}
	 return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "添加成功","openNew", "scenicList");

	}
	/**
	 * @方法功能说明：根据省份查询相应的城市
	 * @修改者名字：chenxy
	 * @修改时间：2014年10月10日下午2:55:08
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param province
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/searchCity")
	@ResponseBody
	public String searchCity(HttpServletRequest request,Model model,
			@RequestParam(value = "province", required = false) String province){
		// 查询市
		CityQo cityQo = new CityQo();
		cityQo.setProvinceCode(province);
		List<City> cityList = cityService.queryList(cityQo);
		HgLogger.getInstance().info("chenxy", "根据省份查询城市成功");
		return JSON.toJSONString(cityList);
	}
}
