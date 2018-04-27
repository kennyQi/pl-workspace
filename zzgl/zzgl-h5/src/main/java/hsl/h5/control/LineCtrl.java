package hsl.h5.control;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.PropertyPreFilter;

import hg.common.page.Pagination;
import hg.common.util.SysProperties;
import hg.log.util.HgLogger;
import hg.system.model.meta.City;
import hg.system.qo.CityQo;
import hg.system.service.CityService;
import hsl.h5.base.result.api.ApiResult;
import hsl.h5.base.result.line.PullUpListResult;
import hsl.pojo.dto.line.DateSalePriceDTO;
import hsl.pojo.dto.line.DayRouteDTO;
import hsl.pojo.dto.line.HotelInfoDTO;
import hsl.pojo.dto.line.LineDTO;
import hsl.pojo.dto.line.LinePictureDTO;
import hsl.pojo.dto.line.LinePictureFolderDTO;
import hsl.pojo.qo.jp.plfx.JPFlightGNQO;
import hsl.pojo.qo.line.HslLineQO;
import hsl.spi.inter.line.HslLineService;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @类功能说明：线路控制器
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年1月30日上午10:06:40
 * @版本：V1.0
 *
 */
@Controller
@RequestMapping("line")
public class LineCtrl extends HslCtrl{
	
	@Autowired
	private HslLineService hslLineService;
	@Autowired
	private JedisPool jedisPool;
	@Resource
	private CityService cityService;
	/**
	 * @方法功能说明：查询线路列表
	 * @修改者名字：yuqz
	 * @修改时间：2015年1月30日上午10:04:08
	 * @修改内容：
	 * @参数：@param pageNo
	 * @参数：@param pageSize
	 * @参数：@param hslLineSearchQO
	 * @参数：@return
	 * @return:ModelAndView
	 * @throws
	 */
	@RequestMapping("list")
	public ModelAndView list(HslLineQO hslLineQO){
		hslLineQO.setGetPictures(true);
		if(null != hslLineQO.getHavePrice() && StringUtils.isNotBlank(hslLineQO.getHavePrice())){
			
			HgLogger.getInstance().info("yuqz", "split:"+Integer.parseInt(hslLineQO.getHavePrice().split("-")[0]));
			
			Double minPrice = Double.parseDouble(hslLineQO.getHavePrice().split("-")[0]);
			Double maxPrice = Double.parseDouble(hslLineQO.getHavePrice().split("-")[1]);
			if(maxPrice == 1){
				hslLineQO.setMinPrice(0.0);
				hslLineQO.setMaxPrice(minPrice);//有以上以下的，第一个数是值，第二数是判断(1以下，2以上)
			}else if(maxPrice == 2){
				hslLineQO.setMinPrice(minPrice);
			}else{
				hslLineQO.setMinPrice(minPrice);
				hslLineQO.setMaxPrice(maxPrice);
			}
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String beginDateTime = "";
		String endDateTime = "";
		if(null != hslLineQO.getBeginDateTime()){
			Date begin = hslLineQO.getBeginDateTime();
			beginDateTime = sdf.format(begin);
		}
		if(null != hslLineQO.getEndDateTime()){
			Date end = hslLineQO.getEndDateTime();
			endDateTime = sdf.format(end);
		}
		if(hslLineQO.getDayCount()!=null&&hslLineQO.getDayCount() == 10){
			hslLineQO.setScope(1);
		}
		ModelAndView mav = new ModelAndView("line/list");
		Pagination pagination = new Pagination();
		pagination.setPageNo(1);
		pagination.setPageSize(20);
		pagination.setCondition(hslLineQO);
		pagination.setCheckPassLastPageNo(false);
		hslLineQO.setGetDateSalePrice(true);
		pagination = hslLineService.queryPagination(pagination);
		if(pagination.getList().size() < 20){
			mav.addObject("haveMore", false);
		}else{
			mav.addObject("haveMore", true);
		}
		
		String image_host=SysProperties.getInstance().get("image_host");
		mav.addObject("image_host", image_host);
		mav.addObject("pagination", pagination);
		mav.addObject("hslLineQO", hslLineQO);
		mav.addObject("cityMap", getCityMap());
		mav.addObject("beginDateTime", beginDateTime);
		mav.addObject("endDateTime", endDateTime);
		return mav;
	}
	
	/**
	 * @方法功能说明：上拉加载更多线路信息
	 * @修改者名字：yuqz
	 * @修改时间：2015年2月4日下午5:39:21
	 * @修改内容：
	 * @参数：@param pageNo
	 * @参数：@param pageSize
	 * @参数：@param hslLineQO
	 * @return:void
	 * @throws
	 */
	@ResponseBody
	@RequestMapping("pullUpList")
	public String pullUpList(@RequestParam(value="pageNo",required=false) Integer pageNo,
			@RequestParam(value="pageSize",required=false) Integer pageSize, HslLineQO hslLineQO, PrintWriter out){
		PullUpListResult pullUpListResult = new PullUpListResult();
		pullUpListResult.setHaveMore(true);//是否还有数据
		if(hslLineQO.getDayCount()!=null&&hslLineQO.getDayCount() == 10){
			hslLineQO.setScope(1);
		}
		Pagination pagination = new Pagination();
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		pagination.setCondition(hslLineQO);
		pagination.setCheckPassLastPageNo(false);
		pagination = hslLineService.queryPagination(pagination);
		if(hslLineQO.getPageSize() > pagination.getList().size()){
			pullUpListResult.setHaveMore(false);
		}
		pullUpListResult.setResult(ApiResult.RESULT_CODE_OK);
		pullUpListResult.setPagination(pagination);
		pullUpListResult.setPageNo(pageNo);
		pullUpListResult.setPageSize(pageSize);
		pullUpListResult.setCityMap(getCityMap());
		pullUpListResult.setHslLineQO(hslLineQO);

		final List<String> nameFilterList = Arrays.asList("urlMapsJSON", "schedulingDescription",
				"favorableDescription", "bookDescription", "feeDescription", "featureDescription",
				"noticeDescription");
		return JSON.toJSONString(pullUpListResult, new PropertyPreFilter() {
			@Override
			public boolean apply(JSONSerializer serializer, Object source, String name) {
				if (nameFilterList.indexOf(name) != -1)
					return false;
				return true;
			}
		});
//		return JSON.toJSONString(pullUpListResult);
	}
	
	
	/**
	 * @方法功能说明：查询线路详情
	 * @修改者名字：yuqz
	 * @修改时间：2015年1月30日上午10:22:03
	 * @修改内容：
	 * @参数：@param hslLineDetailQO
	 * @参数：@return
	 * @return:Object
	 * @throws
	 */
	@RequestMapping("detail")
	public Object detail(HslLineQO hslLineQO){
		hslLineQO.setGetPictures(true);
		hslLineQO.setGetDateSalePrice(true);
		ModelAndView mav = new ModelAndView("line/detail");
		LineDTO lineDTO = hslLineService.queryUnique(hslLineQO);
		if(null == lineDTO){
			RedirectView rv= new RedirectView("list",true);
			return rv;
		}
		//转化价格日历
		List<Map<String,Object>> dtos=new ArrayList<Map<String,Object>>();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		if(lineDTO.getDateSalePriceList()!=null){
			for (DateSalePriceDTO dateSalePriceDTO : lineDTO.getDateSalePriceList()) {
				Map<String,Object> map=new HashMap<String,Object>();
				map.put("saleDate",format.format(dateSalePriceDTO.getSaleDate()));
				map.put("adultPrice",dateSalePriceDTO.getAdultPrice().intValue());
				map.put("childPrice",dateSalePriceDTO.getChildPrice().intValue());
				dtos.add(map);
			}
		}
		try{
			List<DayRouteDTO> dayRoutes = lineDTO.getRouteInfo().getDayRouteList();
			for(DayRouteDTO dayDto: dayRoutes){
				List<HotelInfoDTO> list = JSON.parseArray(dayDto.getHotelInfoJson(), HotelInfoDTO.class);
				dayDto.setHotelList(list);
			}
		}catch(NullPointerException e){
			HgLogger.getInstance().error("yuqz", JSON.toJSONString(e));
		}
		//定义图片list
		List<String> pictureSiteLists = new ArrayList<String>();
		//获取图片文件夹
		List<LinePictureFolderDTO> folderList = lineDTO.getFolderList();
		outer: if(null != folderList){
			for(LinePictureFolderDTO linePictureFolderDTO : folderList){
				//文件夹中的图片
				List<LinePictureDTO> pictureList = linePictureFolderDTO.getPictureList();
				if(null != pictureList){
					for(LinePictureDTO LinePictureDTO : pictureList){
						if(null != LinePictureDTO.getSite() && StringUtils.isNotBlank(LinePictureDTO.getSite())){
							if(pictureSiteLists.size() < 5){
								pictureSiteLists.add(LinePictureDTO.getSite());
							}else{
								break outer;
							}
						}
					}
				}
			}
		}
		String image_host=SysProperties.getInstance().get("image_host");
		mav.addObject("image_host", image_host);
		mav.addObject("datePrices",JSON.toJSONString(dtos));
		mav.addObject("line", lineDTO);
		mav.addObject("pictureSiteLists", pictureSiteLists);
		mav.addObject("cityMap", getCityMap());
		return mav;
	}
	
	/**
	 * @方法功能说明：跳转到优惠活动
	 * @修改者名字：yuqz
	 * @修改时间：2015年2月3日下午2:16:00
	 * @修改内容：
	 * @参数：@param id
	 * @参数：@return
	 * @return:ModelAndView
	 * @throws
	 */
	@RequestMapping("favorable")
	public ModelAndView favorable(@RequestParam(value="id",required=true) String id){
		ModelAndView mav = new ModelAndView("line/favorable");
		HslLineQO hslLineQO = new HslLineQO();
		hslLineQO.setId(id);
		LineDTO lineDTO = hslLineService.queryUnique(hslLineQO);
		mav.addObject("line", lineDTO);
		return mav;
	}
	
	/**
	 * @方法功能说明：跳转到线路特色
	 * @修改者名字：yuqz
	 * @修改时间：2015年2月3日下午2:27:44
	 * @修改内容：
	 * @参数：@param id
	 * @参数：@return
	 * @return:ModelAndView
	 * @throws
	 */
	@RequestMapping("feature")
	public ModelAndView feature(@RequestParam(value="id",required=true) String id){
		ModelAndView mav = new ModelAndView("line/feature");
		HslLineQO hslLineQO = new HslLineQO();
		hslLineQO.setId(id);
		LineDTO lineDTO = hslLineService.queryUnique(hslLineQO);
		mav.addObject("line", lineDTO);
		return mav;
	}
	
	/**
	 * @方法功能说明：跳转到交通信息
	 * @修改者名字：yuqz
	 * @修改时间：2015年2月3日下午2:32:35
	 * @修改内容：
	 * @参数：@param id
	 * @参数：@return
	 * @return:ModelAndView
	 * @throws
	 */
	@RequestMapping("traffic")
	public ModelAndView traffic(@RequestParam(value="id",required=true) String id){
		ModelAndView mav = new ModelAndView("line/traffic");
		HslLineQO hslLineQO = new HslLineQO();
		hslLineQO.setId(id);
		LineDTO lineDTO = hslLineService.queryUnique(hslLineQO);
		mav.addObject("line", lineDTO);
		return mav;
	}
	
	/**
	 * @方法功能说明：跳转到提示信息
	 * @修改者名字：yuqz
	 * @修改时间：2015年2月3日下午2:42:00
	 * @修改内容：
	 * @参数：@param id
	 * @参数：@return
	 * @return:ModelAndView
	 * @throws
	 */
	@RequestMapping("notice")
	public ModelAndView notice(@RequestParam(value="id",required=true) String id){
		ModelAndView mav = new ModelAndView("line/notice");
		HslLineQO hslLineQO = new HslLineQO();
		hslLineQO.setId(id);
		LineDTO lineDTO = hslLineService.queryUnique(hslLineQO);
		mav.addObject("line", lineDTO);
		return mav;
	}
	
	/**
	 * @方法功能说明：跳转到费用说明
	 * @修改者名字：yuqz
	 * @修改时间：2015年2月3日下午2:42:20
	 * @修改内容：
	 * @参数：@param id
	 * @参数：@return
	 * @return:ModelAndView
	 * @throws
	 */
	@RequestMapping("fee")
	public ModelAndView fee(@RequestParam(value="id",required=true) String id){
		ModelAndView mav = new ModelAndView("line/fee");
		HslLineQO hslLineQO = new HslLineQO();
		hslLineQO.setId(id);
		LineDTO lineDTO = hslLineService.queryUnique(hslLineQO);
		mav.addObject("line", lineDTO);
		return mav;
	}
	
	/**
	 * @方法功能说明：跳转到预定须知
	 * @修改者名字：yuqz
	 * @修改时间：2015年2月3日下午2:42:33
	 * @修改内容：
	 * @参数：@param id
	 * @参数：@return
	 * @return:ModelAndView
	 * @throws
	 */
	@RequestMapping("book")
	public ModelAndView book(@RequestParam(value="id",required=true) String id){
		ModelAndView mav = new ModelAndView("line/book");
		HslLineQO hslLineQO = new HslLineQO();
		hslLineQO.setId(id);
		LineDTO lineDTO = hslLineService.queryUnique(hslLineQO);
		mav.addObject("line", lineDTO);
		return mav;
	}
	
	/**
	 * @方法功能说明：跳转到全部行程
	 * @修改者名字：yuqz
	 * @修改时间：2015年2月3日下午4:46:54
	 * @修改内容：
	 * @参数：@param id
	 * @参数：@return
	 * @return:ModelAndView
	 * @throws
	 */
	@RequestMapping("allDayRoute")
	public ModelAndView allDayRoute(@RequestParam(value="id",required=true) String id){
		ModelAndView mav = new ModelAndView("line/allDayRoute");
		HslLineQO hslLineQO = new HslLineQO();
		hslLineQO.setId(id);
		LineDTO lineDTO = hslLineService.queryUnique(hslLineQO);
		try{
			List<DayRouteDTO> dayRoutes = lineDTO.getRouteInfo().getDayRouteList();
			for(DayRouteDTO dayDto: dayRoutes){
				List<HotelInfoDTO> list = JSON.parseArray(dayDto.getHotelInfoJson(), HotelInfoDTO.class);
				dayDto.setHotelList(list);
			}
		}catch(NullPointerException e){
			HgLogger.getInstance().error("yuqz", JSON.toJSONString(e));
		}
		mav.addObject("line", lineDTO);
		mav.addObject("cityMap", getCityMap());
		return mav;
	}
	
	/**
	 * @方法功能说明：跳转到筛选页面
	 * @修改者名字：yuqz
	 * @修改时间：2015年2月3日下午5:32:37
	 * @修改内容：
	 * @参数：@param id
	 * @参数：@return
	 * @return:ModelAndView
	 * @throws
	 */
	@RequestMapping("searchView")
	public ModelAndView searchView(HslLineQO hslLineQO){
		ModelAndView mav = new ModelAndView("line/searchView");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String beginDateTime = "";
		String endDateTime = "";
		if(null != hslLineQO.getBeginDateTime()){
			Date begin = hslLineQO.getBeginDateTime();
			beginDateTime = sdf.format(begin);
		}
		if(null != hslLineQO.getEndDateTime()){
			Date end = hslLineQO.getEndDateTime();
			endDateTime = sdf.format(end);
		}
		mav.addObject("cities",this.getCityByCroup());
		String[] strs={"热门城市","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
		mav.addObject("groups",strs);
		mav.addObject("hslLineQO",hslLineQO);
		mav.addObject("beginDateTime",beginDateTime);
		mav.addObject("endDateTime",endDateTime);
		return mav;
	}
	
	/**
	 * @方法功能说明：从分销获得含有三字码的城市以及把城市分组放入redis中
	 * @修改者名字：chenxy
	 * @修改时间：2015年1月9日上午9:48:20
	 * @修改内容：
	 * @参数：@return
	 * @return:HashMap<String,List<CityAirCodeDTO>>
	 * @throws
	 */
	private HashMap<String, List<City>> getCityByCroup(){
		Jedis jedis = null;
		jedis = jedisPool.getResource();
		String cityGroup = jedis.get("zzpl_citylineGroup");
		@SuppressWarnings("unchecked")
		HashMap<String, List<City>> cityGroupMap=JSON.parseObject(cityGroup, HashMap.class);
		if(cityGroupMap!=null&&cityGroupMap.size()>0){
			//将jedis连接返回给jedisPool中
			jedisPool.returnResource(jedis);
			return cityGroupMap;
		}else{
			//查询所有的城市
			HashMap<String, List<City>> groupCity=new HashMap<String, List<City>>();
			CityQo cityQo = new CityQo();
			List<City> cityList = cityService.queryList(cityQo);
			String[] hotCitys={"北京","上海","广州","成都","重庆","西安","昆明","深圳","杭州","厦门","长沙","海口","武汉","乌鲁木齐","郑州","三亚","贵阳","南京","青岛","哈尔滨"};
			List<String> hc=Arrays.asList(hotCitys);
			//根据城市的首字母对城市分组
			for(City city:cityList){
				//得到城市的首字母的前缀
				String prefix=city.getCityJianPin().substring(0, 1);
				if(groupCity.size()>0){
					Set<String> keys=groupCity.keySet();
					//如果Map中含有当前的首字母的时候。直接获得该首字母的List，进行添加
					if(!hc.contains(city.getName().trim())){
						if(keys.contains(prefix)){
							List<City> cs=groupCity.get(prefix);
							cs.add(city);
						}else{
							List<City> cs =new ArrayList<City>();
							cs.add(city);
							groupCity.put(prefix,cs);
						}
					}
				}else{
					List<City> cs =new ArrayList<City>();
					cs.add(city);
					groupCity.put(prefix,cs);
				}
				//添加热门城市
				if(hc.contains(city.getName().trim())){
					List<City> hotC=groupCity.get("热门城市");
					if(hotC==null){
						List<City> h=new ArrayList<City>();
						h.add(city);
						groupCity.put("热门城市",h);
					}else{
						hotC.add(city);
					}
				}
			}
			jedis.set("zzpl_citylineGroup",JSON.toJSONString(groupCity,SerializerFeature.DisableCircularReferenceDetect));
			HgLogger.getInstance().info("chenxy", "LineCtrl->init->queryCityAirCode[查询全部城市三字码成功！]:"+ JSON.toJSONString(groupCity));
			//将jedis连接返回给jedisPool中
			jedisPool.returnResource(jedis);
			//设置城市列表，供页面使用
			return groupCity;
		}
	}
}
