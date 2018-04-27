package lxs.api.action.line;

import hg.common.page.Pagination;
import hg.common.util.SysProperties;
import hg.log.util.HgLogger;
import hg.system.model.meta.City;
import hg.system.qo.CityQo;
import hg.system.service.CityService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lxs.api.action.LxsAction;
import lxs.api.base.ApiRequest;
import lxs.api.base.ApiResponse;
import lxs.api.v1.dto.line.LineInfoDTO;
import lxs.api.v1.dto.line.LineListDTO;
import lxs.api.v1.request.qo.line.LineQO;
import lxs.api.v1.response.line.QueryLineResponse;
import lxs.app.service.line.DateSalePriceService;
import lxs.app.service.line.LineSelectiveService;
import lxs.app.service.line.LineService;
import lxs.app.service.line.LineSubjectService;
import lxs.domain.model.line.DayRoute;
import lxs.domain.model.line.Line;
import lxs.domain.model.line.LineImage;
import lxs.domain.model.line.LineRouteInfo;
import lxs.domain.model.line.LineSelective;
import lxs.domain.model.line.LineSubject;
import lxs.pojo.exception.line.LineException;
import lxs.pojo.qo.line.LineSelectiveQO;
import lxs.pojo.qo.line.LineSubjectQO;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Component("QueryLineAction")
public class QueryLineAction implements LxsAction {

	@Autowired
	private LineService lineService;
	@Autowired
	private LineSelectiveService lineSelectiveService;
	@Autowired
	private HgLogger hgLogger;
	@Autowired
	private CityService cityService;
	@Autowired
	private LineSubjectService lineSubjectService;
	@Autowired
	private DateSalePriceService dateSalePriceService;
	
	@Override
	public String execute(ApiRequest apiRequest) {
		HgLogger.getInstance().info("lxs_dev", "【QueryLineAction】"+"进入action");
		LineQO apilineQO = JSON.parseObject(apiRequest.getBody().getPayload(),LineQO.class);
		//线路查询qo
		lxs.pojo.qo.line.LineQO lineQO = new lxs.pojo.qo.line.LineQO();
		lineQO.setIsSale("1");
		//精选线路查询qo
		LineSelectiveQO lineSelectiveQO = new LineSelectiveQO();
		lineSelectiveQO.setForSale(1);
		//定义response
		QueryLineResponse queryLineResponse = new QueryLineResponse();
		if((apilineQO.getLineID()!=null&&StringUtils.isNotBlank(apilineQO.getLineID()))||(apilineQO.getLineSelectiveID()!=null&&StringUtils.isNotBlank(apilineQO.getLineSelectiveID()))){
			if (apilineQO.isQuerySelectiveLine()) {
				//查询精选线路详情
				lineSelectiveQO.setId(apilineQO.getLineSelectiveID());
				HgLogger.getInstance().info("lxs_dev", "【QueryLineAction】"+"精选线路ID："+apilineQO.getLineSelectiveID());
				try{
					if(lineSelectiveService.queryCount(lineSelectiveQO)==0){
						throw new LineException(LineException.RESULT_DATA_NOT_FOUND, "线路不存在");
					}
					Line line = lineSelectiveService.queryUnique(lineSelectiveQO).getLine();
					HgLogger.getInstance().info("lxs_dev", "【QueryLineAction】"+"查询成功，开始转化");
					LineInfoDTO lineInfoDTO = new LineInfoDTO();
					if(line.getBaseInfo()!=null){
						if(line.getBaseInfo().getName()!=null){
							//线路名称
							lineInfoDTO.setLineName(line.getBaseInfo().getName());
						}
						if(line.getBaseInfo().getNumber()!=null){
							//线路编号
							lineInfoDTO.setLineNO(line.getBaseInfo().getNumber());
						}
						if(line.getBaseInfo().getSales()!=null){
							//销量
							lineInfoDTO.setSales(line.getBaseInfo().getSales().toString());
						}
						if(line.getBaseInfo().getDestinationCity()!=null){
							//行程概况
							String[] strings=line.getBaseInfo().getDestinationCity().split(",");
							String destinationCity = "";
							for(int i=0;i<strings.length;i++){
								destinationCity=destinationCity+cityService.get(strings[i]).getName();
								if(i+1<strings.length){
									destinationCity=destinationCity+",";
								}
							}
							lineInfoDTO.setDestinationCity(destinationCity);
						}
						if(line.getBaseInfo().getType()!=null){
							//线路类型
							lineInfoDTO.setType(line.getBaseInfo().getType().toString());
						}
						if(line.getBaseInfo().getRecommendationLevel()!=null){
							//线路推荐
							lineInfoDTO.setRecommendationLevel(line.getBaseInfo().getRecommendationLevel().toString());
						}
					}
					if(line.getMinPrice()!=null){
						//价格
						lineInfoDTO.setMinPrice(line.getMinPrice().toString());
					}
					if(line!=null){
						//id
						lineInfoDTO.setLineID(line.getId());
					}
					
					//轮播图
					List<String> uriList = new ArrayList<String>();
					if(line.getLineImageList()!=null){
						for(LineImage lineImage:line.getLineImageList()){
							if(lineImage.getUrlMapsJSON()!=null){
								Map lineMap = JSON.parseObject(lineImage.getUrlMapsJSON(),Map.class);
								if(lineMap.get(SysProperties.getInstance().get("slfxImageAPPDetailType"))==null){
									if(lineMap.get("default")!=null){
										uriList.add(SysProperties.getInstance().get("slfxImageUrl")+lineMap.get("default"));
									}
								}else{
									uriList.add(SysProperties.getInstance().get("slfxImageUrl")+lineMap.get(SysProperties.getInstance().get("slfxImageAPPDetailType")));
								}
							}
						}
					}
					if(line.getRouteInfo()!=null){
						LineRouteInfo lineRouteInfo = line.getRouteInfo();
						if(lineRouteInfo.getDayRouteList()!=null){
							for(DayRoute dayRoute:lineRouteInfo.getDayRouteList()){
								if(dayRoute.getLineImageList()!=null){
									for(LineImage lineImage:dayRoute.getLineImageList()){
										if(lineImage.getUrlMapsJSON()!=null){
											Map lineMap = JSON.parseObject(lineImage.getUrlMapsJSON(),Map.class);
											if(lineMap.get(SysProperties.getInstance().get("slfxImageAPPDetailType"))==null){
												if(lineMap.get("default")!=null){
													uriList.add(SysProperties.getInstance().get("slfxImageUrl")+lineMap.get("default"));
												}
											}else{
												uriList.add(SysProperties.getInstance().get("slfxImageUrl")+lineMap.get(SysProperties.getInstance().get("slfxImageAPPDetailType")));
											}
										}
									}
								}
							}
						}
					}
					lineInfoDTO.setUriList(uriList);
					if(line.getPayInfo()!=null){
						if(line.getPayInfo().getDownPayment()!=null){
							//订金支付比例
							lineInfoDTO.setDownPayment(line.getPayInfo().getDownPayment().toString());
						}
						if(line.getPayInfo().getPayTotalDaysBeforeStart()!=null){
							// 需付全款提前天数
							lineInfoDTO.setPayTotalDaysBeforeStart(line.getPayInfo().getPayTotalDaysBeforeStart().toString());
						}
						if(line.getPayInfo().getLastPayTotalDaysBeforeStart()!=null){
							//最晚付款时间出发日期前
							lineInfoDTO.setLastPayTotalDaysBeforeStart(line.getPayInfo().getLastPayTotalDaysBeforeStart().toString());
						}
					}
					queryLineResponse.setLine(lineInfoDTO);
					queryLineResponse.setResult(ApiResponse.RESULT_CODE_OK);
					queryLineResponse.setMessage("查询精选成功");
					HgLogger.getInstance().info("lxs_dev", "【QueryLineAction】"+"线路精选查询成功");
				}catch(LineException e){
					HgLogger.getInstance().info("lxs_dev", "【QueryLineAction】"+"异常，"+HgLogger.getStackTrace(e));
					queryLineResponse.setResult(e.getCode());
					queryLineResponse.setMessage(e.getMessage());
				}
			}else{
				//查询线路详情
				lineQO.setId(apilineQO.getLineID());
				HgLogger.getInstance().info("lxs_dev", "【QueryLineAction】"+"线路ID："+apilineQO.getLineID());
				try{
					if(lineService.queryCount(lineQO)==0){
						throw new LineException(LineException.RESULT_DATA_NOT_FOUND, "线路不存在");
					}
					Line line = lineService.queryUnique(lineQO);
					HgLogger.getInstance().info("lxs_dev", "【QueryLineAction】"+"查询成功，开始转化");
					LineInfoDTO lineInfoDTO = new LineInfoDTO();
					if(line.getBaseInfo()!=null){
						if(line.getBaseInfo().getName()!=null){
							//线路名称
							lineInfoDTO.setLineName(line.getBaseInfo().getName());
						}
						if(line.getBaseInfo().getNumber()!=null){
							//线路编号
							lineInfoDTO.setLineNO(line.getBaseInfo().getNumber());
						}
						if(line.getBaseInfo().getSales()!=null){
							//销量
							lineInfoDTO.setSales(line.getBaseInfo().getSales().toString());
						}
						if(line.getBaseInfo().getDestinationCity()!=null){
							//行程概况
							String[] strings=line.getBaseInfo().getDestinationCity().split(",");
							String destinationCity = "";
							for(int i=0;i<strings.length;i++){
								destinationCity=destinationCity+cityService.get(strings[i]).getName();
								if(i+1<strings.length){
									destinationCity=destinationCity+",";
								}
							}
							lineInfoDTO.setDestinationCity(destinationCity);
						}
						if(line.getBaseInfo().getType()!=null){
							//线路类型
							lineInfoDTO.setType(line.getBaseInfo().getType().toString());
						}
						if(line.getBaseInfo().getRecommendationLevel()!=null){
							//线路推荐
							lineInfoDTO.setRecommendationLevel(line.getBaseInfo().getRecommendationLevel().toString());
						}
					}
					if(line.getMinPrice()!=null){
						//价格
						lineInfoDTO.setMinPrice(line.getMinPrice().toString());
					}
					if(line!=null){
						//id
						lineInfoDTO.setLineID(line.getId());
					}
					//轮播图
					List<String> uriList = new ArrayList<String>();
					if(line.getLineImageList()!=null){
						for(LineImage lineImage:line.getLineImageList()){
							if(lineImage.getUrlMapsJSON()!=null){
								Map lineMap = JSON.parseObject(lineImage.getUrlMapsJSON(),Map.class);
								if(lineMap.get(SysProperties.getInstance().get("slfxImageAPPDetailType"))==null){
									if(lineMap.get("default")!=null){
										uriList.add(SysProperties.getInstance().get("slfxImageUrl")+lineMap.get("default"));
									}
								}else{
									uriList.add(SysProperties.getInstance().get("slfxImageUrl")+lineMap.get(SysProperties.getInstance().get("slfxImageAPPDetailType")));
								}
							}
						}
					}
					if(line.getRouteInfo()!=null){
						LineRouteInfo lineRouteInfo = line.getRouteInfo();
						if(lineRouteInfo.getDayRouteList()!=null){
							for(DayRoute dayRoute:lineRouteInfo.getDayRouteList()){
								if(dayRoute.getLineImageList()!=null){
									for(LineImage lineImage:dayRoute.getLineImageList()){
										if(lineImage.getUrlMapsJSON()!=null){
											Map lineMap = JSON.parseObject(lineImage.getUrlMapsJSON(),Map.class);
											if(lineMap.get(SysProperties.getInstance().get("slfxImageAPPDetailType"))==null){
												if(lineMap.get("default")!=null){
													uriList.add(SysProperties.getInstance().get("slfxImageUrl")+lineMap.get("default"));
												}
											}else{
												uriList.add(SysProperties.getInstance().get("slfxImageUrl")+lineMap.get(SysProperties.getInstance().get("slfxImageAPPDetailType")));
											}
										}
									}
								}
							}
						}
					}
					lineInfoDTO.setUriList(uriList);
					if(line.getPayInfo()!=null){
						if(line.getPayInfo().getDownPayment()!=null){
							//订金支付比例
							lineInfoDTO.setDownPayment(line.getPayInfo().getDownPayment().toString());
						}
						if(line.getPayInfo().getPayTotalDaysBeforeStart()!=null){
							// 需付全款提前天数
							lineInfoDTO.setPayTotalDaysBeforeStart(line.getPayInfo().getPayTotalDaysBeforeStart().toString());
						}
						if(line.getPayInfo().getLastPayTotalDaysBeforeStart()!=null){
							//最晚付款时间出发日期前
							lineInfoDTO.setLastPayTotalDaysBeforeStart(line.getPayInfo().getLastPayTotalDaysBeforeStart().toString());
						}
					}
					queryLineResponse.setLine(lineInfoDTO);
					queryLineResponse.setResult(ApiResponse.RESULT_CODE_OK);
					queryLineResponse.setMessage("查询成功");
					HgLogger.getInstance().info("lxs_dev", "【QueryLineAction】"+"线路查询成功");
				}catch(LineException e){
					HgLogger.getInstance().info("lxs_dev", "【QueryLineAction】"+"查询线路失败，"+e.getMessage());
					queryLineResponse.setResult(e.getCode());
					queryLineResponse.setMessage(e.getMessage());
				}
			}
		}else{
			Pagination pagination = new Pagination();
			// 判断是否查询精选线路
			if (apilineQO.isQuerySelectiveLine()) {
				//分页 查询精选线路
				HgLogger.getInstance().info("lxs_dev", "【QueryLineAction】"+"开始查询精选线路");
				HgLogger.getInstance().info("lxs_dev", "【QueryLineAction】"+"开始查询第"+apilineQO.getPageNO()+"页，每页"+apilineQO.getPageSize()+"条记录");
				lineSelectiveQO.setName(apilineQO.getName());
				lineSelectiveQO.setType(apilineQO.getType());
				lineSelectiveQO.setStartingCity(apilineQO.getStartingCity());
				lineSelectiveQO.setOrder(apilineQO.getOrder());
				lineSelectiveQO.setOrderType(apilineQO.getOrderType());
				
				String[] provinces=SysProperties.getInstance().get("startingProvinces").split(",");
				List<String> cityList = new ArrayList<String>();
				for(int i=0;i<provinces.length;i++){
					CityQo cityQo = new CityQo();
					cityQo.setProvinceCode(provinces[i]);
					List<City> cities = cityService.queryList(cityQo);
					for(City city:cities){
						cityList.add(city.getCode());
					}
				}
				lineSelectiveQO.setStaringCities(cityList);
				
				pagination.setPageNo(apilineQO.getPageNO());
				pagination.setPageSize(apilineQO.getPageSize());
				pagination.setCondition(lineSelectiveQO);
				pagination.setCheckPassLastPageNo(true);
				pagination = lineSelectiveService.queryPagination(pagination);
				HgLogger.getInstance().info("lxs_dev", "【QueryLineAction】"+"开始查询该页是否为最后一页");
				int totalPage = 0;
				if(pagination.getTotalCount()<=apilineQO.getPageSize()){
					totalPage = 1;
				}else{
					totalPage = new BigDecimal(Double.valueOf(pagination.getTotalCount())/Double.valueOf(apilineQO.getPageSize())).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
				}
				List<LineListDTO> lineListDTOs = new ArrayList<LineListDTO>();
				if (apilineQO.getPageNO() <= totalPage) {
					HgLogger.getInstance().info("lxs_dev", "【QueryLineAction】"+"查询精选线路成功，开始转化");
					for(LineSelective lineSelective:(List<LineSelective>)pagination.getList()){
						LineListDTO lineListDTO = new LineListDTO();
						//线路ID
						lineListDTO.setLineID(lineSelective.getLine().getId());
						if(lineSelective.getLine().getBaseInfo()!=null){
							if(lineSelective.getLine().getBaseInfo().getName()!=null){
								//线路名称
								lineListDTO.setLineName(lineSelective.getLine().getBaseInfo().getName());
							}
							if(lineSelective.getLine().getBaseInfo().getNumber()!=null){
								//线路编号
								lineListDTO.setLineNO(lineSelective.getLine().getBaseInfo().getNumber());
							}
							if(lineSelective.getLine().getBaseInfo().getDestinationCity()!=null){
								//线路详情
								String[] strings=lineSelective.getLine().getBaseInfo().getDestinationCity().split(",");
								String destinationCity = "";
								for(int i=0;i<strings.length;i++){
									destinationCity=destinationCity+cityService.get(strings[i]).getName();
									if(i+1<strings.length){
										destinationCity=destinationCity+",";
									}else{
										
									}
								}
								lineListDTO.setLineDescription(destinationCity);
							}
						}
						//线路价格
						if(dateSalePriceService.getMin("adultPrice",lineSelective.getLine().getId())==0){
							lineListDTO.setMinPrice(lineSelective.getLine().getMinPrice().toString());
						}else{
							lineListDTO.setMinPrice(String.valueOf(dateSalePriceService.getMin("adultPrice",lineSelective.getLine().getId())));
						}
						//推荐指数
						if(lineSelective.getLine().getBaseInfo().getRecommendationLevel()!=null){
							lineListDTO.setRecommendationLevel(lineSelective.getLine().getBaseInfo().getRecommendationLevel());
						}
						//出游类型
						//1  跟团游 2 自助游
						if(lineSelective.getLine().getBaseInfo().getType()!=null){
							lineListDTO.setType(lineSelective.getLine().getBaseInfo().getType());
						}
						//出发地
						String destinationCity = "";
						if(lineSelective.getLine().getBaseInfo()!=null){
							if(lineSelective.getLine().getBaseInfo().getStarting()!=null&&StringUtils.isNotBlank(lineSelective.getLine().getBaseInfo().getStarting())){
								String[] strings=lineSelective.getLine().getBaseInfo().getStarting().split(",");
								for(int i=0;i<strings.length;i++){
									destinationCity=destinationCity+cityService.get(strings[i]).getName();
									if(i+1<strings.length){
										destinationCity=destinationCity+",";
									}
								}
								lineListDTO.setStarting(destinationCity);
							}
						}
						
						//首页图
						lineListDTO.setPictureUri("#");
						if(lineSelective.getLine().getLineImageList()!=null){
							for(LineImage lineImage:lineSelective.getLine().getLineImageList()){
								if(lineImage.getUrlMapsJSON()!=null){
									Map lineMap = JSON.parseObject(lineImage.getUrlMapsJSON(), Map.class);
									if(lineMap.get(SysProperties.getInstance().get("slfxImageAPPListType"))==null){
										if(lineMap.get("default")!=null){
											lineListDTO.setPictureUri(SysProperties.getInstance().get("slfxImageUrl")+lineMap.get("default"));
										}
									}else{
										lineListDTO.setPictureUri(SysProperties.getInstance().get("slfxImageUrl")+lineMap.get(SysProperties.getInstance().get("slfxImageAPPListType")));
									}
									break;
								}
							}
						}
						lineListDTOs.add(lineListDTO);
					}
					queryLineResponse.setLineList(lineListDTOs);
					if(apilineQO.getPageNO()<totalPage){
						queryLineResponse.setIsLastPage("n");
					}else{
						queryLineResponse.setIsLastPage("y");
					}
				} else {
					queryLineResponse.setLineList(lineListDTOs);
					queryLineResponse.setIsLastPage("y");
				}
				queryLineResponse.setResult(ApiResponse.RESULT_CODE_OK);
				queryLineResponse.setMessage("查询成功");
				HgLogger.getInstance().info("lxs_dev", "【QueryLineAction】"+"精选线路查询成功");
			} else {
				// 分页查询线路
				HgLogger.getInstance().info("lxs_dev", "【QueryLineAction】"+"开始查询线路");
				HgLogger.getInstance().info("lxs_dev", "【QueryLineAction】"+"开始查询第"+apilineQO.getPageNO()+"页，每页"+apilineQO.getPageSize()+"条记录");
				lineQO.setSearchName(apilineQO.getName());
				lineQO.setType(apilineQO.getType());
				lineQO.setStartCity(apilineQO.getStartingCity());
				lineQO.setOrder(apilineQO.getOrder());
				lineQO.setOrderType(apilineQO.getOrderType());
				lineQO.setGetDateSalePrice(true);
				lineQO.setGetPictures(true);
				lineQO.setLocalStatus(lxs.pojo.qo.line.LineQO.SHOW);
				lineQO.setSort(lxs.pojo.qo.line.LineQO.QUERY_WITH_TOP);
				if(apilineQO.getLowPrice()!=0&&apilineQO.getHighPrice()!=0){
					lineQO.setLowPrice(apilineQO.getLowPrice());
					lineQO.setHighPrice(apilineQO.getHighPrice());
				}
				if(apilineQO.getRouteDays()!=null&&apilineQO.getRouteDays()!=0){
					/**
					 * 与手机端约定好的如果大于5天就传入99
					 */
					if(apilineQO.getRouteDays()==99){
						lineQO.setRouteDays5More("yes");
					}else{
						lineQO.setRouteDays5More("no");
						lineQO.setRouteDays(apilineQO.getRouteDays());
					}
				}
				if(apilineQO.getSubjectId()!=null&&StringUtils.isNotBlank(apilineQO.getSubjectId())){
					LineSubjectQO lineSubjectQO = new LineSubjectQO();
					lineSubjectQO.setSubjectID(apilineQO.getSubjectId());
					List<LineSubject> lineSubjects = lineSubjectService.queryList(lineSubjectQO);
					List<String> IDs = new ArrayList<String>();
					for (LineSubject lineSubject : lineSubjects) {
						IDs.add(lineSubject.getLineID());
					}
					lineQO.setLineIDs(IDs);
				}
				if(apilineQO.getSaleDate()!=null&&StringUtils.isNotBlank(apilineQO.getSaleDate())){
					lineQO.setSaleDate(apilineQO.getSaleDate());
				}
				String[] provinces=SysProperties.getInstance().get("startingProvinces").split(",");
				List<String> cityList = new ArrayList<String>();
				for(int i=0;i<provinces.length;i++){
					CityQo cityQo = new CityQo();
					cityQo.setProvinceCode(provinces[i]);
					List<City> cities = cityService.queryList(cityQo);
					for(City city:cities){
						cityList.add(city.getCode());
					}
				}
				lineQO.setStartingProvince(cityList);
				
				/**
				 * 目的地
				 */
				if(apilineQO.getDestinationCity()!=null&&StringUtils.isNotBlank(apilineQO.getDestinationCity())){
					lineQO.setDestinationCity(apilineQO.getDestinationCity());
				}
				pagination.setCheckPassLastPageNo(true);
				pagination.setPageNo(apilineQO.getPageNO());
				pagination.setPageSize(apilineQO.getPageSize());
				pagination.setCondition(lineQO);
				pagination.setCheckPassLastPageNo(true);
				pagination = lineService.queryPagination(pagination);
				int totalPage = 0;
				if(pagination.getTotalCount()<=apilineQO.getPageSize()){
					totalPage = 1;
				}else{
					totalPage = new BigDecimal(Double.valueOf(pagination.getTotalCount())/Double.valueOf(apilineQO.getPageSize())).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
				}
				HgLogger.getInstance().info("lxs_dev", "【QueryLineAction】"+"开始查询该页是否为最后一页");
				List<LineListDTO> lineListDTOs = new ArrayList<LineListDTO>();
				if (apilineQO.getPageNO() <= totalPage) {
					HgLogger.getInstance().info("lxs_dev", "【QueryLineAction】"+"查询线路成功，开始转化");
					for(Line line:(List<Line>)pagination.getList()){
						LineListDTO lineListDTO = new LineListDTO();
						//线路ID
						lineListDTO.setLineID(line.getId());
						if(line.getBaseInfo()!=null){
							if(line.getBaseInfo().getName()!=null){
								//线路名称
								lineListDTO.setLineName(line.getBaseInfo().getName());
							}
							if(line.getBaseInfo().getNumber()!=null){
								//线路名称
								lineListDTO.setLineNO(line.getBaseInfo().getNumber());
							}
							if(line.getBaseInfo().getDestinationCity()!=null){
								//线路详情
								String[] strings=line.getBaseInfo().getDestinationCity().split(",");
								String destinationCity = "";
								for(int i=0;i<strings.length;i++){
									destinationCity=destinationCity+cityService.get(strings[i]).getName();
									if(i+1<strings.length){
										destinationCity=destinationCity+",";
									}
								}
								lineListDTO.setLineDescription(destinationCity);
							}
						}
						//线路价格
						if(dateSalePriceService.getMin("adultPrice",line.getId())==0){
							lineListDTO.setMinPrice(line.getMinPrice().toString());
						}else{
							lineListDTO.setMinPrice(String.valueOf(dateSalePriceService.getMin("adultPrice",line.getId())));
						}
						//推荐指数
						if(line.getBaseInfo().getRecommendationLevel()!=null){
							lineListDTO.setRecommendationLevel(line.getBaseInfo().getRecommendationLevel());
						}
						//出游类型
						//1  跟团游 2 自助游
						if(line.getBaseInfo().getType()!=null){
							lineListDTO.setType(line.getBaseInfo().getType());
						}
						//出发地
						String destinationCity = "";
						if(line.getBaseInfo()!=null){
							if(line.getBaseInfo().getStarting()!=null&&StringUtils.isNotBlank(line.getBaseInfo().getStarting())){
								String[] strings=line.getBaseInfo().getStarting().split(",");
								for(int i=0;i<strings.length;i++){
									destinationCity=destinationCity+cityService.get(strings[i]).getName();
									if(i+1<strings.length){
										destinationCity=destinationCity+",";
									}
								}
								lineListDTO.setStarting(destinationCity);
							}
						}
						//首页图
						lineListDTO.setPictureUri("#");
						if(line.getLineImageList()!=null){
							for(LineImage lineImage:line.getLineImageList()){
								if(lineImage.getUrlMapsJSON()!=null){
									Map lineMap = JSON.parseObject(lineImage.getUrlMapsJSON(), Map.class);
									if(lineMap.get(SysProperties.getInstance().get("slfxImageAPPListType"))==null){
										if(lineMap.get("default")!=null){
											lineListDTO.setPictureUri(SysProperties.getInstance().get("slfxImageUrl")+lineMap.get("default"));
										}
									}else{
										lineListDTO.setPictureUri(SysProperties.getInstance().get("slfxImageUrl")+lineMap.get(SysProperties.getInstance().get("slfxImageAPPListType")));
									}
									break;
								}
							}
						}
						lineListDTOs.add(lineListDTO);
					}
					queryLineResponse.setLineList(lineListDTOs);
					if(apilineQO.getPageNO()<totalPage){
						queryLineResponse.setIsLastPage("n");
					}else{
						queryLineResponse.setIsLastPage("y");
					}
				} else {
					queryLineResponse.setLineList(lineListDTOs);
					queryLineResponse.setIsLastPage("y");
				}
				queryLineResponse.setResult(ApiResponse.RESULT_CODE_OK);
				queryLineResponse.setMessage("查询成功");
				HgLogger.getInstance().info("lxs_dev", "【QueryLineAction】"+"线路查询成功");
			}
		}
		HgLogger.getInstance().info("lxs_dev", "【QueryLineAction】"+"查询线路结果"+JSON.toJSONString(queryLineResponse));
		return JSON.toJSONString(queryLineResponse);
	}
}
