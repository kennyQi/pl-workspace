package plfx.mp.tcclient.tc.service;

import plfx.mp.tcclient.tc.dto.base.CityListByProvinceIdDto;
import plfx.mp.tcclient.tc.dto.base.CountyListByCityIdDto;
import plfx.mp.tcclient.tc.dto.base.DivisionInfoByIdDto;
import plfx.mp.tcclient.tc.dto.base.DivisionInfoByNameDto;
import plfx.mp.tcclient.tc.dto.base.ProvinceListDto;
import plfx.mp.tcclient.tc.dto.jd.NearbySceneryDto;
import plfx.mp.tcclient.tc.dto.jd.PriceCalendarDto;
import plfx.mp.tcclient.tc.dto.jd.SceneryDetailDto;
import plfx.mp.tcclient.tc.dto.jd.SceneryDto;
import plfx.mp.tcclient.tc.dto.jd.SceneryImageListDto;
import plfx.mp.tcclient.tc.dto.jd.SceneryPriceDto;
import plfx.mp.tcclient.tc.dto.jd.SceneryTrafficInfoDto;
import plfx.mp.tcclient.tc.dto.order.CancelSceneryOrderDto;
import plfx.mp.tcclient.tc.dto.order.SceneryOrderDetailDto;
import plfx.mp.tcclient.tc.dto.order.SceneryOrderListDto;
import plfx.mp.tcclient.tc.dto.order.SubmitSceneryOrderDto;
import plfx.mp.tcclient.tc.pojo.Response;
import plfx.mp.tcclient.tc.pojo.ResultNearbyScenery;
import plfx.mp.tcclient.tc.pojo.ResultPriceCalendar;
import plfx.mp.tcclient.tc.pojo.ResultSceneryDetail;
import plfx.mp.tcclient.tc.pojo.ResultSceneryImageList;
import plfx.mp.tcclient.tc.pojo.ResultSceneryList;
import plfx.mp.tcclient.tc.pojo.ResultSceneryPrice;
import plfx.mp.tcclient.tc.pojo.ResultSceneryTrafficInfo;
import plfx.mp.tcclient.tc.pojo.base.response.ResultCityListByProvinceId;
import plfx.mp.tcclient.tc.pojo.base.response.ResultCountyListByCityId;
import plfx.mp.tcclient.tc.pojo.base.response.ResultDivisionInfoById;
import plfx.mp.tcclient.tc.pojo.base.response.ResultDivisionInfoByName;
import plfx.mp.tcclient.tc.pojo.base.response.ResultProvinceList;
import plfx.mp.tcclient.tc.pojo.order.response.ResultCancelSceneryOrder;
import plfx.mp.tcclient.tc.pojo.order.response.ResultSceneryOrderDetail;
import plfx.mp.tcclient.tc.pojo.order.response.ResultSceneryOrderList;
import plfx.mp.tcclient.tc.pojo.order.response.ResultSubmitSceneryOrder;

/**
 * 同城接口服务
 * @author zhangqy
 *
 */
public interface TcClientService {
	/**
	 * 1.1.1	获取景点列表
	 * @param request
	 * @return
	 */
	public Response<ResultSceneryList> getSceneryList(SceneryDto dto);
	/**
	 * 1.1.2	获取景点详细信息
	 * @param dto
	 * @return
	 */
	public Response<ResultSceneryDetail> getSceneryDetail(SceneryDetailDto dto);
	/**
	 * 1.1.3	获取景点交通指南信息
	 * @param dto
	 * @return
	 */
	public Response<ResultSceneryTrafficInfo> getSceneryTrafficInfo(SceneryTrafficInfoDto dto);
	/**
	 * 1.1.4	获取景点图片列表
	 * @param dto
	 * @return
	 */
	public Response<ResultSceneryImageList> getSceneryImageList(SceneryImageListDto dto);
	/**
	 * 1.1.5	获取周边景点
	 * @param dto
	 * @return
	 */
	public Response<ResultNearbyScenery> getNearbyScenery(NearbySceneryDto dto);
	/**
	 * 1.1.6	获取价格搜索接口 
	 * @param dto
	 * @return
	 */
	public Response<ResultSceneryPrice> getSceneryPrice(SceneryPriceDto dto);
	/**
	 * 1.1.7	获取价格日历接口
	 * @param dto
	 * @return
	 */
	public Response<ResultPriceCalendar> getPriceCalendar(PriceCalendarDto dto);
	//===============================行政区划===============================
	/**
	 * 2.1.1	获取省列表
	 * @param dto
	 * @return
	 */
	public Response<ResultProvinceList> getProvinceList(ProvinceListDto dto);
	/**
	 * 2.1.2	查询城市列表
	 * @param dto
	 * @return
	 */
	public Response<ResultCityListByProvinceId> getCityListByProvinceId(CityListByProvinceIdDto dto);
	/**
	 * 2.1.3	查询行政区列表 
	 * @param dto
	 * @return
	 */
	public Response<ResultCountyListByCityId> getCountyListByCityId(CountyListByCityIdDto dto);
	/**
	 * 2.1.4	根据名称查询区划信息 
	 * @param dto
	 * @return
	 */
	public Response<ResultDivisionInfoByName> getDivisionInfoByName(DivisionInfoByNameDto dto);
	/***
	 * 2.1.5	根据ID查询区划信息
	 * @param dto
	 * @return
	 */
	public Response<ResultDivisionInfoById> getDivisionInfoById(DivisionInfoByIdDto dto);
	///==========================订单=============================
	/**
	 * 3.1.1	提交订单接口
	 * @param dto
	 * @return
	 */
	public Response<ResultSubmitSceneryOrder> submitSceneryOrder(SubmitSceneryOrderDto dto);
	/**
	 * 3.1.2	景区取消订单
	 * @param dto
	 * @return
	 */
	public Response<ResultCancelSceneryOrder> cancelSceneryOrder(CancelSceneryOrderDto dto);
	/**
	 * 3.1.3	景区同步单列表
	 * @param dto
	 * @return
	 */
	public Response<ResultSceneryOrderList> getSceneryOrderList(SceneryOrderListDto dto);
	/**
	 * 3.1.4	景区同步单列表
	 * @param dto
	 * @return
	 */
	public Response<ResultSceneryOrderDetail> getSceneryOrderDetail(SceneryOrderDetailDto dto);
}
