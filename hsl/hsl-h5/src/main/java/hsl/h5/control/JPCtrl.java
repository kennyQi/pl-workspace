package hsl.h5.control;

import hg.log.util.HgLogger;
import hsl.h5.base.result.jp.JPQueryFlightResult;
import hsl.h5.base.utils.OpenidTracker;
import hsl.pojo.dto.jp.CityAirCodeDTO;
import hsl.pojo.dto.jp.FlightDTO;
import hsl.spi.inter.api.jp.JPService;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import slfx.api.v1.request.qo.jp.JPAirCodeQO;
import slfx.api.v1.request.qo.jp.JPFlightQO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
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
	/**
	 * 机票首页
	 */
	@RequestMapping("init")
	public ModelAndView init() {
		ModelAndView mav = new ModelAndView("jp/init");
		try {
			mav.addObject("openid", OpenidTracker.get());//获取微信跟踪器
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//默认显示当前时间
			mav.addObject("date", sdf.format(new Date()));
			mav.addObject("cities",this.getCityByCroup());
			String[] strs={"热门城市","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
			mav.addObject("groups",strs);
		} catch (Exception e) {
			log.error("hsl.err", e);
			mav.setViewName("error");
		}
		return mav;
	}
	
	/**
	 * 机票搜索
	 * @param command 航班查询的参数
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("list")
	public ModelAndView list(JPFlightQO command, String startCityName, String endCityName) {
		ModelAndView mav = new ModelAndView("jp/list");
		try {
			String params = "from=" + command.getFrom() + "&arrive=" + command.getArrive() +
					"&startCityName=" + startCityName + "&endCityName=" + endCityName;
			mav.addObject("params", params);
			startCityName = startCityName.replaceAll("-", "%");
			endCityName = endCityName.replaceAll("-", "%");
			mav.addObject("startCityName", URLDecoder.decode(startCityName, "UTF-8"));
			mav.addObject("endCityName", URLDecoder.decode(endCityName, "UTF-8"));
			mav.addObject("openid", OpenidTracker.get());
			mav.addObject("condition", command);
		} catch (Exception e) {
			HgLogger.getInstance().info("liusong","JPCtrl->list[机票搜索失败！]："+HgLogger.getStackTrace(e));
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
	public void ajaxList(JPFlightQO command,PrintWriter out, String sort, Boolean desc) {
		JPQueryFlightResult   jpQueryFlightResult  = new  JPQueryFlightResult();//返回的结果集result，包含航班的list结果集以及航班总数
		List<FlightDTO> jpFlightList = new  ArrayList<FlightDTO>();//调用slfx查询航班返回的结果集
	
		try {
		    // 航班列表
			jpFlightList = jpService.queryFlight(command);
			
			if(jpFlightList==null){
			    HgLogger.getInstance().info("liusong","JPCtrl->ajaxList->queryFlight[查询航班列表失败！查询QO]："+ JSON.toJSONString(command));
				jpQueryFlightResult.setMessage("失败！");
				jpQueryFlightResult.setResult(JPQueryFlightResult.RESULT_CODE_FAIL);
			}else{
				HgLogger.getInstance().info("liusong","JPCtrl->ajaxList->queryFlight[查询航班列表成功！]："+ JSON.toJSONString(jpFlightList));
				jpQueryFlightResult.setFlightList(jpFlightList);
				if (success(jpQueryFlightResult)) {
					List<FlightDTO> flightList = jpQueryFlightResult.getFlightList();//从result结果集中取得航班列表
					
					//航班列表临时变量
					List<FlightDTO> flights = new ArrayList<FlightDTO>();
					
					//将航班列表中票面价是0元起的航班筛选出来并放入航班列表临时变量flights当中
					for (int i = 0; flightList != null && i < flightList.size(); i++) {
						if (flightList.get(i).getCheapestFlightClass() == null || flightList.get(i).getFlightClassList() == null || flightList.get(i).getFlightClassList().size() == 0) {
							 flights.add(flightList.get(i));
						}
					}
					
					//遍历result结果集中的航班列表，将flights中的对象移除掉
					for (int i = 0; flights != null && i < flights.size(); i++) {
						flightList.remove(flights.get(i));
					}
					
					if (flightList != null && flightList.size() > 0) {
						//航班列表展示按价格排序
						if ("price".equals(sort)) {
							Collections.sort(flightList, new Comparator<FlightDTO>() {
								public int compare(FlightDTO o1, FlightDTO o2) {
									Double price1 = o1.getCheapestFlightClass().getSettleAccounts();
									Double price2 = o2.getCheapestFlightClass().getSettleAccounts();
									return price1.compareTo(price2);
								}
							});
						} else {//航班列表展示按时间排序
							Collections.sort(flightList, new Comparator<FlightDTO>() {
								public int compare(FlightDTO o1, FlightDTO o2) {
									String date1 = o1.getStartTime();
									String date2 = o2.getStartTime();
									String[] s1 = date1.split(":");
									String[] s2 = date2.split(":");
									Integer d1 = Integer.parseInt(s1[0] + s1[1]);
									Integer d2 = Integer.parseInt(s2[0] + s2[1]);
									return d1.compareTo(d2);
								}
							});
						}
						//降序
						if (desc) {
							Collections.reverse(flightList);
						}
						//最终赋值给result的航班列表是做过筛选的并且排过序的
						jpQueryFlightResult.setFlightList(flightList);
						jpQueryFlightResult.setTotalCount(flightList.size());
					}
				}
			}
		} catch (Exception e) {
			HgLogger.getInstance().info("liusong","JPCtrl->ajaxList->queryFlight[查询航班列表失败！查询QO]："+ JSON.toJSONString(command)+HgLogger.getStackTrace(e));
			log.error("hsl.err", e);
			jpQueryFlightResult.setResult("0");
		}
		
		//返回json字符串
		out.print(JSON.toJSONString(jpQueryFlightResult));
	}
	
	/**
	 *舱位列表
	 *参数：@param flightNo  航班号
	 *结果：@return
	 */
	@RequestMapping("clazz")
	public ModelAndView clazz(JPFlightQO command,String startCityName, String endCityName) {
		ModelAndView mav = new ModelAndView("jp/clazz");
		mav.addObject("openid", OpenidTracker.get());//设置微信跟踪器
		mav.addObject("condition", command);
		String params = "from=" + command.getFrom() + "&arrive=" + command.getArrive() +
				"&startCityName=" + startCityName + "&endCityName=" + endCityName;
		mav.addObject("params", params);
		
		//返回视图模型对象
		return mav;
	}
	
	/**
	 * 航班详细信息
	 * 参数：@param flightNo 航班号
	 * 结果：@return
	 */
	@RequestMapping("detail")
	public void detail(JPFlightQO command, PrintWriter out) {
		JPQueryFlightResult   jpQueryFlightResult  = new  JPQueryFlightResult();//返回的结果集result，包含航班的list结果集以及航班总数
		List<FlightDTO> jpFlightList = new  ArrayList<FlightDTO>();//调用slfx查询航班返回的结果集
		try{
			jpFlightList = jpService.queryFlight(command);//查询得到航班列表
			
			if (jpFlightList == null) {//如果查询得到的航班列表
				jpQueryFlightResult.setMessage("失败！");
				jpQueryFlightResult.setResult(JPQueryFlightResult.RESULT_CODE_FAIL);
				HgLogger.getInstance().info("liusong", "JPCtrl->detail->queryFlight[航班查询失败！查询的QO]:" +JSON.toJSONString(command));
			} else {
				//日志
				HgLogger.getInstance().info("liusong", "JPCtrl->detail->queryFlight[航班查询成功！]:" +JSON.toJSONString(jpFlightList));
				jpQueryFlightResult.setFlightList(jpFlightList);
				jpQueryFlightResult.setTotalCount(jpFlightList.size());
			}
			
			//返回json字符串
			out.print(JSON.toJSONString(jpQueryFlightResult));
		}catch(Exception e){
			HgLogger.getInstance().info("liusong", "JPCtrl->detail->queryFlight[航班查询失败！查询的QO]:" +JSON.toJSONString(command)+HgLogger.getStackTrace(e));
			jpQueryFlightResult.setResult("0");
		}
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
	private HashMap<String, List<CityAirCodeDTO>> getCityByCroup() {
		//初始化城市QO
		JPAirCodeQO jpAirCodeQO = JSON.parseObject("{}", JPAirCodeQO.class);
		//日志
		HgLogger.getInstance().info("liusong", "JPCtrl->init->queryCityAirCode[查询全部城市三字码,查询参数QO]:" + JSON.toJSONString(jpAirCodeQO));
		//查询所有的城市
		List<CityAirCodeDTO> cityAirCodeDTOs = jpService.queryCityAirCode(jpAirCodeQO);
		HashMap<String, List<CityAirCodeDTO>> groupCity = new HashMap<String, List<CityAirCodeDTO>>();
		String[] hotCitys = {"北京", "上海", "广州", "成都", "重庆", "西安", "昆明", "深圳", "杭州", "厦门", "长沙", "海口", "武汉", "乌鲁木齐", "郑州", "三亚", "贵阳", "南京", "青岛", "哈尔滨"};
		List<String> hc = Arrays.asList(hotCitys);
		//根据城市的首字母对城市分组
		for (CityAirCodeDTO city : cityAirCodeDTOs) {
			//得到城市的首字母的前缀
			String prefix = city.getCityJianPin().substring(0, 1);
			if (groupCity.size() > 0) {
				Set<String> keys = groupCity.keySet();
				//热门城市不添加分组中
				if (!hc.contains(city.getCityName().trim())) {
					//如果Map中含有当前的首字母的时候。直接获得该首字母的List，进行添加
					if (keys.contains(prefix)) {
						List<CityAirCodeDTO> cs = groupCity.get(prefix);
						cs.add(city);
					} else {
						List<CityAirCodeDTO> cs = new ArrayList<CityAirCodeDTO>();
						cs.add(city);
						groupCity.put(prefix, cs);
					}
				}
			} else {
				List<CityAirCodeDTO> cs = new ArrayList<CityAirCodeDTO>();
				cs.add(city);
				groupCity.put(prefix, cs);
			}
			//添加热门城市
			if (hc.contains(city.getCityName().trim())) {
				List<CityAirCodeDTO> hotC = groupCity.get("热门城市");
				if (hotC == null) {
					List<CityAirCodeDTO> h = new ArrayList<CityAirCodeDTO>();
					h.add(city);
					groupCity.put("热门城市", h);
				} else {
					hotC.add(city);
				}
			}
		}
		return groupCity;
	}
}