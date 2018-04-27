package hg.dzpw.app.service.api.dealer;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hg.dzpw.dealer.client.api.v1.request.RegionQO;
import hg.dzpw.dealer.client.api.v1.response.RegionResponse;
import hg.dzpw.dealer.client.common.ApiRequest;
import hg.dzpw.dealer.client.common.api.DealerApiAction;
import hg.dzpw.dealer.client.common.api.DealerApiService;
import hg.dzpw.dealer.client.dto.meta.RegionDTO;
import hg.system.model.meta.Area;
import hg.system.model.meta.City;
import hg.system.model.meta.Province;
import hg.system.qo.AreaQo;
import hg.system.qo.CityQo;
import hg.system.qo.ProvinceQo;
import hg.system.service.impl.AreaServiceImpl;
import hg.system.service.impl.CityServiceImpl;
import hg.system.service.impl.ProvinceServiceImpl;

/**
 * @类功能说明：经销商接口省市区查询服务
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yangkang
 * @创建时间：2015-4-20下午3:26:36
 * @版本：V1.0
 *
 */
@Service
public class ProvinceCityDealerApiService implements DealerApiService {
	/**
	 * 行政区域_省_service
	 */
	@Autowired
	private ProvinceServiceImpl provinceService;
	/**
	 * 行政区域_县市_service
	 */
	@Autowired
	private CityServiceImpl cityService;
	/**
	 * 行政区域_区_service
	 */
	@Autowired
	private AreaServiceImpl areaServiceImpl;
	
	
	@DealerApiAction(DealerApiAction.QueryRegion)
	public RegionResponse queryRegion(ApiRequest<RegionQO> request) {
		return queryRegion(request.getBody());
	}
	
	public RegionResponse queryRegion(RegionQO qo){
		
		RegionResponse resp = new RegionResponse();
		if(qo!=null){
			
			if(qo.getType()==null){
				resp.setResult(RegionResponse.RESULT_REQUIRED_PARAM);
				resp.setMessage("缺少必填参数[type]");
				return resp;
			}
			
			if(qo.getType()!=1 && qo.getType()!=2 && qo.getType()!=3){
				resp.setResult(RegionResponse.RESULT_QUERY_FAIL);
				resp.setMessage("行政区类型错误");
				return resp;
			}
			
			if(RegionQO.TYPE_PROVINCE == qo.getType()){
				List<RegionDTO> pl = this.queryProvince(qo);
				resp.setMessage("查询成功");
				resp.setRegions(pl);
				resp.setResult(RegionResponse.RESULT_SUCCESS);
			}
			
			if(RegionQO.TYPE_CITY == qo.getType()){
				List<RegionDTO> cl = this.queryCity(qo);
				resp.setMessage("查询成功");
				resp.setRegions(cl);
				resp.setResult(RegionResponse.RESULT_SUCCESS);
			}
			
			if(RegionQO.TYPE_AREA == qo.getType()){
				List<RegionDTO> al = this.queryArea(qo);
				resp.setMessage("查询成功");
				resp.setRegions(al);
				resp.setResult(RegionResponse.RESULT_SUCCESS);
			}
		}else{
			resp.setResult(RegionResponse.RESULT_SUCCESS);
			resp.setRegions(null);
		}
		
		return resp;
	}

	
	/**
	 * @方法功能说明：查询省
	 * @修改者名字：yangkang
	 * @修改时间：2014-12-9下午5:26:09
	 * @参数：@param pq
	 * @参数：@param cq
	 * @return:List<RegionDTO>
	 */
	private List<RegionDTO> queryProvince(RegionQO rq){
		
		List<RegionDTO> list = new ArrayList<RegionDTO>();
		ProvinceQo qo = new ProvinceQo();
		
		if(StringUtils.isNotBlank(rq.getCode()))
			qo.setCode(rq.getCode());
		
		List<Province> l = this.provinceService.queryList(qo);
		if(l!=null && l.size()>0){
			for(Province p : l){
				RegionDTO r = new RegionDTO();
				r.setId(p.getId());
				r.setCode(p.getCode());
				r.setName(p.getName());
				r.setParentCode(p.getParentCode());
				r.setType(RegionDTO.TYPE_PROVINCE);
				list.add(r);
			}
		}
		return list;
	}
	
	
	 /**
	 * @方法功能说明：查询市
	 * @修改者名字：yangkang
	 * @修改时间：2014-12-15上午11:27:48
	 * @参数：@param qo
	 * @return:List<RegionDTO>
	 */
	private List<RegionDTO> queryCity(RegionQO rq){
		
		 List<RegionDTO> list = new ArrayList<RegionDTO>();
		 CityQo cq = new CityQo(); 
		 
		 if(StringUtils.isNotBlank(rq.getParentCode()))
			 cq.setProvinceCode(rq.getParentCode());
		 
		 List<City> l = this.cityService.queryList(cq);
		 if(l!=null && l.size()>0){
			 for(City c : l){
				 RegionDTO r = new RegionDTO();
				 r.setId(c.getId());
				 r.setCode(c.getCode());
				 r.setName(c.getName());
				 r.setParentCode(c.getParentCode());
				 r.setType(RegionDTO.TYPE_PROVINCE);
				 list.add(r);
			 }
		 }
		 return list;
	}
	
	
	/**
	 * @方法功能说明：查询区
	 * @修改者名字：yangkang
	 * @修改时间：2015-4-20上午11:21:21
	 * @参数：@param rq
	 * @参数：@return
	 * @return:List<RegionDTO>
	 */
	private List<RegionDTO> queryArea(RegionQO rq){
		
		List<RegionDTO> list = new ArrayList<RegionDTO>();
		AreaQo aq = new AreaQo();
		
		if(StringUtils.isNotBlank(rq.getParentCode()))
			aq.setCityCode(rq.getParentCode());
		
		List<Area> l = this.areaServiceImpl.queryList(aq);
		if(l!=null && l.size()>0){
			for(Area a : l){
				RegionDTO r = new RegionDTO();
				r.setId(a.getId());
				r.setCode(a.getCode());
				r.setName(a.getName());
				r.setParentCode(a.getParentCode());
				r.setType(RegionQO.TYPE_AREA);
				list.add(r);
			}
		}
		return list;
	}
	
}
