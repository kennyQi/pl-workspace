package plfx.admin.controller.traveline;

import hg.common.page.Pagination;
import hg.common.util.DateUtil;
import hg.system.model.meta.City;
import hg.system.model.meta.Province;
import hg.system.qo.CityQo;
import hg.system.qo.ProvinceQo;
import hg.system.service.CityService;
import hg.system.service.ProvinceService;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import plfx.admin.controller.BaseController;
import plfx.xl.pojo.dto.CityOfCountryDTO;
import plfx.xl.pojo.dto.CountryDTO;
import plfx.xl.pojo.dto.finance.LineFinanceDTO;
import plfx.xl.pojo.dto.line.LineDTO;
import plfx.xl.pojo.dto.line.LineSnapshotDTO;
import plfx.xl.pojo.dto.order.LineOrderDTO;
import plfx.xl.pojo.qo.LineCityQo;
import plfx.xl.pojo.qo.LineCountryQo;
import plfx.xl.pojo.qo.LineFinanceQO;
import plfx.xl.pojo.qo.LineQO;
import plfx.xl.pojo.qo.LineSnapshotQO;
import plfx.xl.pojo.system.LineConstants;
import plfx.xl.pojo.system.XLOrderStatusConstant;
import plfx.xl.spi.inter.CityOfCountryService;
import plfx.xl.spi.inter.CountryService;
import plfx.xl.spi.inter.LineFinanceService;
import plfx.xl.spi.inter.LineService;
import plfx.xl.spi.inter.LineSnapshotService;

/**
 * @类功能说明：财务管理Controller
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年12月22日下午4:40:29
 * @版本：V1.0
 * 
 */
@Controller
@RequestMapping("/traveline/finance")
public class LineFinanceController extends BaseController {

	@Autowired
	private LineFinanceService lineFinanceService;
	
	@Autowired
	private ProvinceService provinceService;
	
	@Autowired
	private CityService cityService;
	
	@Autowired
	private CityOfCountryService cityOfCountryService;
	@Autowired
	private CountryService countryService;
	
	@Autowired
	private LineSnapshotService lineSnapshotService;
	
	@Autowired
	private LineService lineService;

	/**
	 * 
	 * @方法功能说明：财务管理列表查询
	 * @修改者名字：tandeng
	 * @修改时间：2014年12月25日下午5:24:57
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param lineQO
	 * @参数：@param pageNo
	 * @参数：@param pageSize
	 * @参数：@param beginTimeStr
	 * @参数：@param endTimeStr
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("/list")
	public String queryLineList(
			HttpServletRequest request,
			HttpServletResponse response,
			Model model,
			@ModelAttribute LineFinanceQO lineFinanceQO,
			@RequestParam(value = "pageNum", required = false) Integer pageNo,
			@RequestParam(value = "numPerPage", required = false) Integer pageSize,
			@RequestParam(value = "beginTimeStr", required = false) String beginTimeStr,
			@RequestParam(value = "endTimeStr", required = false) String endTimeStr) {

		Pagination pagination = new Pagination();
		pagination.setPageNo(pageNo == null ? new Integer(1) : pageNo);
		pagination.setPageSize(pageSize == null ? new Integer(20) : pageSize);

		if (lineFinanceQO == null) {
			lineFinanceQO = new LineFinanceQO();
		}

		if (null != beginTimeStr && !"".equals(beginTimeStr)) {
			lineFinanceQO.setCreateDateFrom(DateUtil.dateStr2BeginDate(beginTimeStr));
		}
		if (null != endTimeStr && !"".equals(endTimeStr)) {
			lineFinanceQO.setCreateDateTo(DateUtil.dateStr2EndDate(endTimeStr));
		}
		if (StringUtils.isBlank(lineFinanceQO.getStartingDepart())) {
			lineFinanceQO.setStartingProvinceID(null);
			lineFinanceQO.setStartingCityID(null);
		}
		
		if (StringUtils.isBlank(lineFinanceQO.getDomesticLine())) {
			lineFinanceQO.setCityOfType(null);
			lineFinanceQO.setProvinceOfType(null);
		}
		pagination.setCondition(lineFinanceQO);
		pagination = lineFinanceService.queryPagination(pagination);
        //做处理国内国外
		
		List<LineFinanceDTO> lineFinanceList = new ArrayList<LineFinanceDTO>();
	  
		for(LineFinanceDTO lineFinanceDTO :(List<LineFinanceDTO>)pagination.getList()){
			LineSnapshotQO qo = new LineSnapshotQO();
			qo.setId(lineFinanceDTO.getLineSnapshot().getId());
			LineSnapshotDTO lineSnapshotDTO = lineSnapshotService.queryUnique(qo);
			lineFinanceDTO.setLineSnapshot(lineSnapshotDTO);
			LineQO lineQO =new LineQO();
			lineQO.setId(lineSnapshotDTO.getLine().getId());
			LineDTO lineDTO = lineService.queryUnique(lineQO);
			lineFinanceDTO.getLineSnapshot().setLine(lineDTO);
			if(StringUtils.isNotBlank(lineFinanceQO.getStartingDepart()) || StringUtils.isNotBlank(lineFinanceQO.getDomesticLine())){
				//出发地
				if((StringUtils.isNotBlank(lineDTO.getBaseInfo().getStartGrade()) && lineFinanceQO.getStartingDepart().equals(lineDTO.getBaseInfo().getStartGrade()))){
					if(StringUtils.isNotBlank(lineFinanceQO.getDomesticLine())){
						if(lineFinanceQO.getDomesticLine().equals(lineDTO.getBaseInfo().getTypeGrade())){
							lineFinanceList.add(lineFinanceDTO);
						}
					}else{
						lineFinanceList.add(lineFinanceDTO);
					}
					
				}else if(StringUtils.isNotBlank(lineFinanceQO.getDomesticLine()) && lineFinanceQO.getDomesticLine().equals(lineDTO.getBaseInfo().getTypeGrade())){
					//线路类型
					lineFinanceList.add(lineFinanceDTO);
				}
			}else {
				lineFinanceList.add(lineFinanceDTO);
			}
			
		}
		pagination.setList(lineFinanceList);
		
		CityQo cityQo = new CityQo();
		List<City> cityList = cityService.queryList(cityQo);
				
		//查询所有省信息
		ProvinceQo province = new ProvinceQo();
		List<Province> provinceList = provinceService.queryList(province);
		
        //查询国家
		LineCountryQo lineCountryQo = new LineCountryQo();
		List<CountryDTO> countryList = countryService.queryLineCountryList(lineCountryQo);
		model.addAttribute("countryList", countryList);
		
		//有出发城市查询条件则查询该城市所在省份的所有城市(出发地  国内线)
		if(StringUtils.isNotBlank(lineFinanceQO.getStartingDepart()) && lineFinanceQO.getStartingDepart().equals(LineConstants.HOME)){
			if(StringUtils.isNotBlank(lineFinanceQO.getStartingCityID())){
				CityQo startCityQo = new CityQo();
				startCityQo.setProvinceCode(lineFinanceQO.getStartingProvinceID());
				List<City> startCityList = cityService.queryList(startCityQo);
				model.addAttribute("startCityList", startCityList);
			}	
		}else{//国外线
			if(StringUtils.isNotBlank(lineFinanceQO.getStartingCityID())){
				LineCityQo lineCityQo1 = new LineCityQo();
				lineCityQo1.setParentCode(lineFinanceQO.getProvinceOfType());
				List<CityOfCountryDTO> startCityList = cityOfCountryService.queryList(lineCityQo1);
				model.addAttribute("startCityList", startCityList);
			}	
		}
		
						
		//有线路类型城市编号查询条件则查询该城市所在省份的所有城市
		if(StringUtils.isNotBlank(lineFinanceQO.getDomesticLine()) && lineFinanceQO.getDomesticLine().equals(LineConstants.HOME)){
			if(StringUtils.isNotBlank(lineFinanceQO.getCityOfType())){
				CityQo typeCityQo = new CityQo();
				typeCityQo.setProvinceCode(lineFinanceQO.getProvinceOfType());
				List<City> typeCityList = cityService.queryList(typeCityQo);
				model.addAttribute("typeCityList", typeCityList);
			}
		}else{
			LineCityQo lineCityQo2 = new LineCityQo();
			lineCityQo2.setParentCode(lineFinanceQO.getProvinceOfType());
			List<CityOfCountryDTO> typeCityList = cityOfCountryService.queryList(lineCityQo2);
			model.addAttribute("typeCityList", typeCityList);
		}
		

		model.addAttribute("pagination", pagination);
		model.addAttribute("lineFinanceQO", lineFinanceQO);
		model.addAttribute("cityList", cityList);
		model.addAttribute("provinceList",provinceList);
		//订单状态
		model.addAttribute("statusMap", XLOrderStatusConstant.SLFX_XLORDER_STATUS_MAP); 
		//订单支付状态
		model.addAttribute("payStatusMap", XLOrderStatusConstant.SLFX_XLORDER_PAY_STATUS_MAP); 
		//线路类型
		model.addAttribute("typeMap", LineConstants.typeMap); 
		//线路级别(1国内,2国外)
	    model.addAttribute("gradeMap", LineConstants.gradeMap);
	    
		return "/traveline/finance/finance_list.html";
	}
	
}