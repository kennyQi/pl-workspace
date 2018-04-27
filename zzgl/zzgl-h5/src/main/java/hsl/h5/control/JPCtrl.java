package hsl.h5.control;
import com.alibaba.fastjson.serializer.SerializerFeature;

import hg.log.util.HgLogger;
import hsl.h5.base.utils.OpenidTracker;
import hsl.pojo.command.jp.BookGNFlightCommand;
import hsl.pojo.dto.ad.HslAdPositionDTO;
import hsl.pojo.dto.jp.CityAirCodeDTO;
import hsl.pojo.dto.jp.FlightGNDTO;
import hsl.pojo.dto.jp.plfx.JPQueryFlightListGNDTO;
import hsl.pojo.qo.ad.HslAdPositionQO;
import hsl.pojo.qo.jp.plfx.JPFlightGNQO;
import hsl.spi.inter.ad.HslAdPositionService;
import hsl.spi.inter.ad.HslAdService;
import hsl.spi.inter.jp.JPService;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import hsl.spi.qo.sys.CityAirCodeQO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.alibaba.fastjson.JSON;
/**
 * 机票管理Action
 * @author 胡永伟
 */
@Controller
@RequestMapping("jp")
public class JPCtrl extends HslCtrl {

	@Autowired
	private JPService jpService;
	@Autowired
	private JedisPool jedisPool;
	@Resource
	private HslAdService hslAdService;
	@Resource
	private HslAdPositionService hslAdPositionService;
	/**
	 * 机票首页
	 */
	@RequestMapping("init")
	public ModelAndView init(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("jp/init");
		try {
			mav.addObject("openid", OpenidTracker.get());//获取微信跟踪器
			
			mav.addObject("openid", OpenidTracker.get());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//默认显示当前时间
			mav.addObject("date", sdf.format(new Date()));
			if(request.getSession().getAttribute("user")!=null){
				mav.addObject("user",request.getSession().getAttribute("user"));
			}
			HgLogger.getInstance().info("renfeng","init--查询城市列表");
			HashMap<String, List<CityAirCodeDTO>> CityAirCodeDTOMap=this.getCityByCroup();
			mav.addObject("cities", CityAirCodeDTOMap);
			String[] strs={"热门城市","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
			HgLogger.getInstance().info("renfeng","JPCtrl->查询三字码："+JSON.toJSONString(CityAirCodeDTOMap));
			mav.addObject("groups",strs);
		} catch (Exception e) {
			e.printStackTrace();
			HgLogger.getInstance().error("renfeng", "init--查询城市列表--->>>出错-->>" + HgLogger.getStackTrace(e));
			mav.setViewName("error");
		}
		return mav;
	}

	/**
	 * 机票搜索
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("list")
	public ModelAndView list(JPFlightGNQO qo, String startCityName, String endCityName) {
		ModelAndView mav = new ModelAndView("jp/list");
		try {
			String params = "orgCity=" + qo.getOrgCity() + "&dstCity=" + qo.getDstCity() +
					"&startCityName=" + startCityName + "&endCityName=" + endCityName;
			mav.addObject("params", params);
			startCityName = startCityName.replaceAll("-", "%");
			endCityName = endCityName.replaceAll("-", "%");
			mav.addObject("startCityName", URLDecoder.decode(startCityName, "UTF-8"));
			mav.addObject("endCityName", URLDecoder.decode(endCityName, "UTF-8"));
			mav.addObject("openid", OpenidTracker.get());
			mav.addObject("condition", qo);
		} catch (Exception e) {
			HgLogger.getInstance().info("renfeng","JPCtrl->list[机票搜索失败！]："+HgLogger.getStackTrace(e));
			mav.setViewName("error");
			e.printStackTrace();
		}
		return mav;
	}

	/**
	 * 获取航班列表
	 *参数： @param command 查询航班列表所需参数
	 *参数：@param out  输出
	 */
	@RequestMapping("ajaxList")
	public void ajaxList(JPFlightGNQO jPFlightGNQO,PrintWriter out, String sort, Boolean desc) {
		List<FlightGNDTO> jpFlightList = new  ArrayList<FlightGNDTO>();//调用slfx查询航班返回的结果集
		JPQueryFlightListGNDTO flightDTOs=new JPQueryFlightListGNDTO();
		Map<String,String> map = new HashMap<String,String>();
		try {
			// 航班列表
			flightDTOs = jpService.queryFlight(jPFlightGNQO);
			jpFlightList=flightDTOs.getFlightList();
			if(jpFlightList==null||jpFlightList.size()==0){
				map.put("result", "-1");
				HgLogger.getInstance().info("liusong","JPCtrl->ajaxList->queryFlight[查询航班列表失败！查询QO]："+ JSON.toJSONString(jPFlightGNQO));
			}else{
				
				//设置航班最低价格
				for( FlightGNDTO flight:jpFlightList){
					flight.setMinPic(flight.getCabinList().get(flight.getCabinList().size()-1).getIbePrice());
				}
				
			  //按时间和价格排序
			   if (jpFlightList.size()>0) {
				  	 List<FlightGNDTO> flights = new  ArrayList<FlightGNDTO>();
                     //将航班列表中票面价是0元起的航班筛选出来并放入航班列表临时变量flights当中
                     for (int i = 0; jpFlightList != null && i < jpFlightList.size(); i++) {
                         if ( jpFlightList.get(i).getCabinList() == null || jpFlightList.get(i).getCabinList().size() == 0) {
                             flights.add(jpFlightList.get(i));
                         }
                     }
 
                     //遍历result结果集中的航班列表，将flights中的对象移除掉
                     for (int i = 0; flights != null && i < flights.size(); i++) {
                    	 jpFlightList.remove(flights.get(i));
                     }
 
                     if (jpFlightList != null && jpFlightList.size() > 0) {
                         //航班列表展示按价格排序
                         if ("price".equals(sort)) {
                             Collections.sort(jpFlightList, new Comparator<FlightGNDTO>() {
                                 public int compare(FlightGNDTO o1, FlightGNDTO o2) {
                                     Double price1 =Double.parseDouble(o1.getCabinList().get(o1.getCabinList().size()-1).getIbePrice()) ;
                                     Double price2 =Double.parseDouble(o2.getCabinList().get(o2.getCabinList().size()-1).getIbePrice()) ;
                                     return price1.compareTo(price2);
                                 }
                             });
                         } else {//航班列表展示按时间排序
                             Collections.sort(jpFlightList, new Comparator<FlightGNDTO>() {
                                 public int compare(FlightGNDTO o1, FlightGNDTO o2) {
                                     Date date1 = o1.getStartTime();
                                     Date date2 = o2.getStartTime();
                                     return date1.compareTo(date2);
                                 }
                             });
                         }
                         //降序
                         if (desc) {
                             Collections.reverse(jpFlightList);
                         }
                       
                     }
                 }
			   
			    map.put("flightCount", jpFlightList.size()+"");
				String jsonStri=JSON.toJSONString(jpFlightList);
				map.put("flightList", jsonStri);
				System.out.println(jsonStri);
				map.put("result", "1");
				HgLogger.getInstance().info("liusong", "JPCtrl->ajaxList->queryFlight[查询航班列表成功！]：" + JSON.toJSONString(jpFlightList));

			}
		} catch (Exception e) {
			map.put("result", "-1");
			e.printStackTrace();
			HgLogger.getInstance().info("liusong","JPCtrl->ajaxList->queryFlight[查询航班列表失败！查询QO]："+ JSON.toJSONString(jPFlightGNQO)+HgLogger.getStackTrace(e));
			
		}


		
		if  (flightDTOs!=null&&flightDTOs.getBuildFee()!=null&&flightDTOs.getOilFee()!=null){
			map.put("buildFeeAndOilFee", (flightDTOs.getBuildFee()+flightDTOs.getOilFee())+"");
		}else{
			map.put("buildFeeAndOilFee", "0");
		}

		//返回json字符串
		out.print(JSON.toJSONString(map));
	}

	/**
	 *舱位列表
	 *参数：@param flightNo  航班号
	 *结果：@return
	 */
	@RequestMapping("clazz")
	public ModelAndView clazz(JPFlightGNQO qo,String startCityName, String endCityName,BookGNFlightCommand command) {
		ModelAndView mav = new ModelAndView("jp/clazz");
		mav.addObject("openid", OpenidTracker.get());//设置微信跟踪器
		mav.addObject("condition", qo);
		String params = "orgCity=" + qo.getOrgCity() + "&dstCity=" + qo.getDstCity() +
				"&startCityName=" + startCityName + "&endCityName=" + endCityName;
		mav.addObject("params", params);
		mav.addObject("command", command);
		//返回视图模型对象
		return mav;
	}

	/**
	 * 航班详细信息
	 * 参数：@param flightNo 航班号
	 * 结果：@return
	 */
	@RequestMapping("detail")
	public void detail(JPFlightGNQO qo, PrintWriter out) {
		Map<String,String> map = new HashMap<String,String>();

		JPQueryFlightListGNDTO jpQueryFlightListGNDTO ;//调用slfx查询航班返回的结果集
		try{
			jpQueryFlightListGNDTO = jpService.queryFlight(qo);//查询得到航班列表
			List<FlightGNDTO> jpFlightList =jpQueryFlightListGNDTO.getFlightList();
			if (jpFlightList == null) {//如果查询得到的航班列表
				HgLogger.getInstance().info("liusong", "JPCtrl->detail->queryFlight[航班查询失败！查询的QO]:" +JSON.toJSONString(qo));
			} else if (jpFlightList.size()==0){
				//日志
				HgLogger.getInstance().info("liusong", "JPCtrl->detail->queryFlight[航班查询成功！]:" + JSON.toJSONString(jpQueryFlightListGNDTO));
				map.put("result", "-1");
			}else if (jpFlightList.size()>0){
				map.put("result", "1");
				for(FlightGNDTO flight:jpFlightList){
					if(flight.getFlightno().equals(qo.getFlightNo())){
						map.put("flight", JSON.toJSONString(flight));
						break;
					}

				}

			}


		}catch(Exception e){
			HgLogger.getInstance().info("liusong", "JPCtrl->detail->queryFlight[航班查询失败！查询的QO]:" +JSON.toJSONString(qo)+HgLogger.getStackTrace(e));
		}

		//返回json字符串
		out.print(JSON.toJSONString(map));
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
	private HashMap<String, List<CityAirCodeDTO>> getCityByCroup(){
		HgLogger.getInstance().info("chenxy","查询城市列表0————>>redis");
		Jedis jedis = null;
		jedis = jedisPool.getResource();
		HgLogger.getInstance().info("chenxy","查询城市列表1————>>redis");
		String cityGroup = jedis.get("zzpl_cityGroup");
		@SuppressWarnings("unchecked")
		HashMap<String, List<CityAirCodeDTO>> cityGroupMap=JSON.parseObject(cityGroup, HashMap.class);
		if(cityGroupMap!=null&&cityGroupMap.size()>0){
			//将jedis连接返回给jedisPool中
			HgLogger.getInstance().info("liusong", "JPCtrl->getCityByCroup从分销获得含有三字码的城市以及把城市分组放入redis中");
			jedisPool.returnResource(jedis);
			return cityGroupMap;
		}else{
			//初始化城市QO
			CityAirCodeQO jpAirCodeQO = JSON.parseObject("{}", CityAirCodeQO.class);
			//日志
			HgLogger.getInstance().info("liusong", "JPCtrl->init->queryCityAirCode[查询全部城市三字码,查询参数QO]:"+ JSON.toJSONString(jpAirCodeQO));
			//查询所有的城市
			List<CityAirCodeDTO> cityAirCodeDTOs = jpService.queryCityAirCode(jpAirCodeQO);
			HashMap<String, List<CityAirCodeDTO>> groupCity=new HashMap<String, List<CityAirCodeDTO>>();
			String[] hotCitys={"北京","上海","广州","成都","重庆","西安","昆明","深圳","杭州","厦门","长沙","海口","武汉","乌鲁木齐","郑州","三亚","贵阳","南京","青岛","哈尔滨"};
			List<String> hc=Arrays.asList(hotCitys);
			//根据城市的首字母对城市分组
			for(CityAirCodeDTO city:cityAirCodeDTOs){
				//得到城市的首字母的前缀
				String prefix=city.getCityJianPin().substring(0, 1);
				if(groupCity.size()>0){
					Set<String> keys=groupCity.keySet();
					//热门城市不添加分组中
					if(!hc.contains(city.getName().trim())) {
						//如果Map中含有当前的首字母的时候。直接获得该首字母的List，进行添加
						if(keys.contains(prefix)){
							List<CityAirCodeDTO> cs=groupCity.get(prefix);
							cs.add(city);
						}else{
							List<CityAirCodeDTO> cs =new ArrayList<CityAirCodeDTO>();
							cs.add(city);
							groupCity.put(prefix,cs);
						}
					}
				}else{
					List<CityAirCodeDTO> cs =new ArrayList<CityAirCodeDTO>();
					cs.add(city);
					groupCity.put(prefix,cs);
				}
				//添加热门城市
				if(hc.contains(city.getName().trim())){
					List<CityAirCodeDTO> hotC=groupCity.get("热门城市");
					if(hotC==null){
						List<CityAirCodeDTO> h=new ArrayList<CityAirCodeDTO>();
						h.add(city);
						groupCity.put("热门城市",h);
					}else{
						hotC.add(city);
					}
				}
			}
			jedis.set("zzpl_cityGroup",JSON.toJSONString(groupCity,SerializerFeature.DisableCircularReferenceDetect));
			System.out.println(JSON.toJSONString(groupCity, SerializerFeature.DisableCircularReferenceDetect));
			HgLogger.getInstance().info("liusong", "JPCtrl->init->queryCityAirCode[查询全部城市三字码成功！]:"+ JSON.toJSONString(groupCity));
			//将jedis连接返回给jedisPool中
			jedisPool.returnResource(jedis);
			//设置城市列表，供页面使用
			return groupCity;
		}
	}
	private int getShowNo(String positionId){
		HslAdPositionQO hslAdPositionQO = new HslAdPositionQO();
		hslAdPositionQO.setPositionId(positionId);
		HslAdPositionDTO hslAdPositionDTO = hslAdPositionService.queryAdPosition(hslAdPositionQO);
		if(null == hslAdPositionDTO){
			HgLogger.getInstance().info("yuqz", "IndexController->getShowNo->查询广告位信息失败:" + JSON.toJSONString(hslAdPositionDTO));
			return 0;
		}else{
			return hslAdPositionDTO.getShowInfo().getShowNo();
		}
		
	}
}