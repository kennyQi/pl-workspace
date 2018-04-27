package hg.jxc.admin.controller.system;

import hg.jxc.admin.controller.BaseController;
import hg.system.model.meta.Area;
import hg.system.model.meta.City;
import hg.system.model.meta.Province;
import hg.system.qo.AreaQo;
import hg.system.qo.CityQo;
import hg.system.service.AreaService;
import hg.system.service.CityService;
import hg.system.service.ProvinceService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/system/address")
public class AddressController extends BaseController {

	@Autowired
	ProvinceService provinceService;

	@Autowired
	CityService cityService;

	@Autowired
	AreaService areaService;

	@RequestMapping("/city_select")
	@ResponseBody
	public String queryCitySelectHTML(@RequestParam String provinceId) {
		Province province = provinceService.get(provinceId);
		CityQo qo = new CityQo();
		qo.setProvinceCode(province.getCode());
		List<City> cityList = cityService.queryList(qo);

		StringBuffer sb = new StringBuffer();
		for (City city : cityList) {
			sb.append("<option value=");
			sb.append(city.getId());
			sb.append(">");
			sb.append(city.getName());
			sb.append("</option>");

		}
		String ret = sb.toString();
		return ret;

	}

	@RequestMapping("/area_select")
	@ResponseBody
	public String queryAreaSelectHTML(@RequestParam String cityId) {
		City city = cityService.get(cityId);
		AreaQo qo = new AreaQo();
		qo.setCityCode(city.getCode());
		List<Area> areaList = areaService.queryList(qo);

		StringBuffer sb = new StringBuffer();
		for (Area area : areaList) {
			sb.append("<option value=");
			sb.append(area.getId());
			sb.append(">");
			sb.append(area.getName());
			sb.append("</option>");
		}
		return sb.toString();
	}

}
