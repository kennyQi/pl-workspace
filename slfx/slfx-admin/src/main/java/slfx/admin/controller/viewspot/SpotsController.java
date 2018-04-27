package slfx.admin.controller.viewspot;

import hg.common.page.Pagination;
import hg.common.util.JsonUtil;
import hg.log.util.HgLogger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import slfx.admin.controller.BaseController;
import slfx.api.v1.request.qo.mp.MPPolicyQO;
import slfx.api.v1.response.mp.MPQueryPolicyResponse;
import slfx.mp.pojo.dto.platformpolicy.DatePriceDTO;
import slfx.mp.pojo.dto.platformpolicy.DateSalePriceDTO;
import slfx.mp.pojo.dto.scenicspot.ScenicSpotDTO;
import slfx.mp.pojo.dto.scenicspot.ScenicSpotsDTO;
import slfx.mp.pojo.dto.supplierpolicy.TCSupplierPolicySnapshotDTO;
import slfx.mp.qo.PlatformSpotsQO;
import slfx.mp.spi.inter.AdminSupplierPolicyService;
import slfx.mp.spi.inter.DateSalePriceService;
import slfx.mp.spi.inter.PlatformSpotService;
import slfx.mp.spi.inter.ScenicSpotUpdateService;
import slfx.mp.spi.inter.api.ApiMPPolicyService;

import com.alibaba.fastjson.JSON;

/**
 * 景点查询
 * @author liujz
 *
 */
@Controller
@RequestMapping(value = "/viewspot/spots")
public class SpotsController extends BaseController {

	@Autowired
	private PlatformSpotService platformSpotService;
	@Autowired
	private AdminSupplierPolicyService policyService;
	@Autowired
	private ApiMPPolicyService apiMPPolicyService;
	@Autowired
	private DateSalePriceService dateSalePriceService;
	@Autowired
	private ScenicSpotUpdateService scenicSpotUpdateService;
	@Autowired
	private HgLogger hgLogger;
	/**
	 * 景点列表
	 * @param request
	 * @param model
	 * @param platformSpotsQO
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/list")
	public  String querySpotsList(HttpServletRequest request, Model model,
			@ModelAttribute PlatformSpotsQO platformSpotsQO,
			@RequestParam(value="pageNum",required=false) Integer pageNo, 
			@RequestParam(value="numPerPage",required=false) Integer pageSize){
		hgLogger.info("wuyg", "admin查询景点列表");
		ScenicSpotDTO scenicSpotDTO=new ScenicSpotDTO();
		
		Pagination pagination=new Pagination();
		pageNo=pageNo==null?new Integer(1):pageNo;
		pageSize=pageSize==null?new Integer(20):pageSize;
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		
		List<TCSupplierPolicySnapshotDTO> list=new ArrayList<TCSupplierPolicySnapshotDTO>(); 
		
		if(StringUtils.isNotBlank(platformSpotsQO.getName())){
			scenicSpotDTO=platformSpotService.queryUnique(platformSpotsQO);
			if(scenicSpotDTO!=null){
				MPPolicyQO mpPolicyQO=new MPPolicyQO();
				mpPolicyQO.setScenicSpotId(scenicSpotDTO.getId());
				MPQueryPolicyResponse mpPolicyResponse=apiMPPolicyService.apiQueryPolicy(mpPolicyQO);
				list=mpPolicyResponse.getPolicies();
			}
		}
		
		model.addAttribute("policyDTOList", list);
		model.addAttribute("pagination", pagination);
		model.addAttribute("platformSpotsQO", platformSpotsQO);
		return "/viewspot/spots/spots_list.html";
	}
	
	/**
	 * 景区提示框匹配
	 * @param request
	 * @param model
	 * @param spotname
	 * @return
	 */
	@RequestMapping(value="/match")
	@ResponseBody
	public String matchName(HttpServletRequest request, Model model,
			@RequestParam(value="spotname",required=false)String spotname){
		PlatformSpotsQO platformSpotsQO =new PlatformSpotsQO();
		platformSpotsQO.setScenicSpotsNameLike(true);
		platformSpotsQO.setName(spotname);
		List<ScenicSpotDTO>  scenicSpotDTOList=platformSpotService.queryList(platformSpotsQO);
		List<ScenicSpotsDTO> list=new ArrayList<ScenicSpotsDTO>();
		for (ScenicSpotDTO scenicSpotDTO : scenicSpotDTOList) {
			ScenicSpotsDTO dto=new ScenicSpotsDTO();
			dto.setName(scenicSpotDTO.getScenicSpotBaseInfo().getName());
			list.add(dto);
		}
		return JSON.toJSONString(list);
	}
	
	/**
	 * 景点详情
	 * @param request
	 * @param model
	 * @param platformSpotsQO
	 * @return
	 */
	@RequestMapping(value="/detail")
	public String querySpotsDetail(HttpServletRequest request, Model model,
			@RequestParam(value="id",required=false) String id,
			@RequestParam(value="policyId",required=false) String policyId){
		hgLogger.info("wuyg", "admin查询景点详情");
		PlatformSpotsQO platformSpotsQO=new PlatformSpotsQO();
		platformSpotsQO.setId(id);
		ScenicSpotDTO scenicSpotDTO=platformSpotService.queryScenicSpotById(platformSpotsQO);
		MPPolicyQO mpPolicyQO=new MPPolicyQO();
		mpPolicyQO.setPolicyId(policyId);
		MPQueryPolicyResponse mpPolicyResponse=apiMPPolicyService.apiQueryPolicy(mpPolicyQO);
		model.addAttribute("policyDTO", mpPolicyResponse.getPolicies().get(0));
		model.addAttribute("scenicSpotDTO", scenicSpotDTO);
		return "/viewspot/spots/spots_detail.html";
	}
	
	/**
	 * 跳转景点价格日历页面
	 * @return
	 */
	@RequestMapping(value="/calendar")
	public String queryPriceCalendar(HttpServletRequest request, Model model,
			@ModelAttribute MPPolicyQO mpPolicyQO){
		model.addAttribute("id", mpPolicyQO.getPolicyId());
		return "/viewspot/spots/spots_price_calendar.html";
	}
	
	/**
	 * 价格日历
	 * @param request
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/dateprice")
	@ResponseBody
	public String queryDatePrice(HttpServletRequest request, Model model,
			@RequestParam(value="id",required=false) String id){
		hgLogger.info("wuyg", "admin查询价格日历");
		//查询价格日历
		List<DateSalePriceDTO> dateSalePriceDTOList=dateSalePriceService.getDateSalePrice(id, "0003");
		List<DatePriceDTO> datePriceDTOList=new ArrayList<DatePriceDTO>();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		//DatePriceDTO转换成DateSalePriceDTO
		if (dateSalePriceDTOList != null && dateSalePriceDTOList.size()>0) {
			for (DateSalePriceDTO dateSalePriceDTO : dateSalePriceDTOList) {
				DatePriceDTO datePriceDTO = new DatePriceDTO();
				datePriceDTO.setSaleDate(format.format(dateSalePriceDTO
						.getSaleDate()));
				datePriceDTO.setSalePrice("" + dateSalePriceDTO.getSalePrice());
				datePriceDTO.setNum(dateSalePriceDTO.getStock());
				datePriceDTOList.add(datePriceDTO);
			}
			return JSON.toJSONString(datePriceDTOList);
		}
		
		return null;
	}
	
	/**
	 * 异步查询景点列表
	 * @return
	 */
	@RequestMapping(value="/name")
	@ResponseBody
	public String querySpotsName(HttpServletRequest request, Model model,
			@RequestParam(value="name",required=false) String name){
		hgLogger.info("wuyg", "admin异步查询景点列表");
		Pagination pagination=new Pagination();
		pagination.setPageNo(1);
		pagination.setPageSize(10);
		
		PlatformSpotsQO platformSpotsQO=new PlatformSpotsQO();
		platformSpotsQO.setName(name);
		
		//添加查询条件
		pagination.setCondition(platformSpotsQO);
		//分页查询
		pagination=platformSpotService.queryPagination(pagination);
		
		return JsonUtil.parseObject(pagination.getList(), true);

	}
	/**
	 * 同步景点
	 * @return
	 */
	@RequestMapping(value="/scenicSpotUpdate")
	@ResponseBody
	public void scenicSpotUpdate(){
		scenicSpotUpdateService.scenicSpotUpdateJob();
	}
}
