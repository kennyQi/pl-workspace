package slfx.jp.app.service.spi;

import hg.log.util.HgLogger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import slfx.jp.app.component.FlightCacheManager;
import slfx.jp.pojo.dto.flight.PlatformQueryWebFlightsDTO;
import slfx.jp.pojo.dto.flight.SlfxFlightClassDTO;
import slfx.jp.pojo.dto.flight.SlfxFlightDTO;
import slfx.jp.pojo.dto.supplier.abe.ABEPatFlightDTO;
import slfx.jp.pojo.dto.supplier.yg.QueryWebFlightsDTO;
import slfx.jp.pojo.dto.supplier.yg.YGFlightDTO;
import slfx.jp.pojo.exception.JPException;
import slfx.jp.qo.api.JPFlightSpiQO;
import slfx.jp.qo.client.PatFlightQO;
import slfx.jp.qo.client.QueryWebFlightsQO;
import slfx.jp.spi.inter.WebFlightService;
import slfx.yg.open.inter.YGFlightService;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @类功能说明：平台航班SERVICE实现
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年7月31日下午4:49:53
 * @版本：V1.0
 *
 */
@Service("webFlightService")
public class WebFlightServiceImpl implements WebFlightService{
	
	/**
	 * 易购的机票服务类
	 */
	@Autowired
	private YGFlightService ygFlightService;
	
	/**
	 * 平台缓存航班信息
	 */
	@Autowired
	private FlightCacheManager flightCacheManager;
	
	/**
	 * 查询航班列表--运价云
	 * @param qo	 查询航班的 query object
	 * @return	符合当前航班的DTO	
	 */
	@Override
	public PlatformQueryWebFlightsDTO queryFlights(JPFlightSpiQO qo) {
		HgLogger.getInstance().info("tandeng","WebFlightServiceImpl->queryFlights->JPFlightQO[查询航班列表]:"+ JSON.toJSONString(qo));		
		if(qo == null){
			return null;
		}
		//将"商城QO"转化"平台QO"
		QueryWebFlightsQO queryWebFlightsQO = new QueryWebFlightsQO();
		queryWebFlightsQO.setFrom(qo.getFrom());
		queryWebFlightsQO.setArrive(qo.getArrive());
		queryWebFlightsQO.setDateStr(qo.getDate());
		
		//调用易购查询接口查询数据
		QueryWebFlightsDTO queryWebFlightsDTO = ygFlightService.queryFlights(queryWebFlightsQO);
		
		//过滤掉出发时间在当前时刻之前的航班
		List<YGFlightDTO> tempFlightList = queryWebFlightsDTO.getFlightList();
		if (null != tempFlightList) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date current = new Date();
			Date start = null;
			YGFlightDTO yGFlightDTO = null;
			for (int i = tempFlightList.size() - 1; i >= 0; i--) {
				yGFlightDTO = tempFlightList.get(i);
				try {
					if (null != yGFlightDTO) {
						String startDate = yGFlightDTO.getStartDate();
						String startTime = yGFlightDTO.getStartTime();
						if (startDate.contains("/")) {
							startDate = startDate.replaceAll("/", "-");
						}

						start = sdf.parse(startDate + " " + startTime);
						if (start.before(current)) {
							tempFlightList.remove(i);
						}

					}
				} catch (Exception e) {
					HgLogger.getInstance().error("tandeng", "WebFlightServiceImpl->queryFlights->exception[查询航班列表]:" + HgLogger.getStackTrace(e));
				}
			}
		}
		
		//将查询的航班信息缓存到redis-20140722-add
		if(queryWebFlightsDTO != null 
				&& queryWebFlightsDTO.getFlightList() != null
				&& queryWebFlightsDTO.getFlightList().size() >0){
			flightCacheManager.refresh(queryWebFlightsDTO.getFlightList());
		}
		
		PlatformQueryWebFlightsDTO platformQueryWebFlightsDTO = new PlatformQueryWebFlightsDTO();
		List<SlfxFlightDTO> flightList = new ArrayList<SlfxFlightDTO>();
		if(queryWebFlightsDTO != null && queryWebFlightsDTO.getTgNoteMap() != null){
			platformQueryWebFlightsDTO.setTgNoteMap(queryWebFlightsDTO.getTgNoteMap());			
		}else{
			platformQueryWebFlightsDTO.setTgNoteMap(new HashMap<String, String>());
		}
		
		SlfxFlightDTO slfxFlightDTO = null;
		
		String flightNo = qo.getFlightNo();
		String flightDateStr = qo.getDate();;
		if(qo.getFlightNo() != null 
				&& flightCacheManager.checkBYFlightKey(flightNo,flightDateStr)){
			//指定航班号查询
			YGFlightDTO ygFlightDTO = flightCacheManager.getFlightDTO(flightNo,flightDateStr);
			
			//DTO转化
			slfxFlightDTO = reventFlightDTO(ygFlightDTO);

			flightList.add(slfxFlightDTO);
		}else{
			//查询全部
			List<YGFlightDTO> ygFlightDTOList = new ArrayList<YGFlightDTO>();
			
			if(queryWebFlightsDTO != null && queryWebFlightsDTO.getFlightList() != null 
					&& queryWebFlightsDTO.getFlightList().size() > 0){
				ygFlightDTOList = queryWebFlightsDTO.getFlightList();
			}
			for (YGFlightDTO ygFlightDTO : ygFlightDTOList) {
				//DTO转化
				slfxFlightDTO = reventFlightDTO(ygFlightDTO);
				
				flightList.add(slfxFlightDTO);
			}
			
		}
		platformQueryWebFlightsDTO.setFlightList(flightList);
		HgLogger.getInstance().info("tandeng","WebFlightServiceImpl->queryFlights->JPFlightQO[查询航班列表结束]");
		return platformQueryWebFlightsDTO;
	}
	
	/**
	 * 查询指定航班信息--缓存中获取
	 * @param qo	 查询航班的 query object
	 * @return	符合当前航班的DTO
	 * @throws JPException 
	*/
	@Override
	public PlatformQueryWebFlightsDTO queryFlightsByFlightNo(JPFlightSpiQO qo) throws JPException {
		
		if(qo == null || qo.getFlightNo() == null || qo.getDate() == null){
			throw new JPException(JPException.FLIGHT_QUERY_QO_NULL,"查询条件为空");
		}
		
		PlatformQueryWebFlightsDTO platformQueryWebFlightsDTO = new PlatformQueryWebFlightsDTO();
		List<SlfxFlightDTO> flightList = new ArrayList<SlfxFlightDTO>();
		
		SlfxFlightDTO slfxFlightDTO = null;
		
		String flightNo = qo.getFlightNo();
		String flightDateStr = qo.getDate();
		
		if(flightCacheManager.checkBYFlightKey(flightNo,flightDateStr)){
			try{
				//指定航班号查询
				YGFlightDTO ygFlightDTO = flightCacheManager.getFlightDTO(flightNo,flightDateStr);
				
				//DTO转化
				slfxFlightDTO = this.reventFlightDTO(ygFlightDTO);
				
				flightList.add(slfxFlightDTO);
				
				platformQueryWebFlightsDTO.setFlightList(flightList);
			}catch(Exception e){
				HgLogger.getInstance().error("tandeng", "redis服务器中没有这个条记录："+flightNo+"-"+flightDateStr);
				throw new JPException(JPException.FLIGHT_NOT_IN_CACHE,"redis缓存中没有该记录"); 
			}
			
		}
		return platformQueryWebFlightsDTO;
	}

	/**
	 * 
	 * @方法功能说明：DTO转换
	 * @修改者名字：tandeng
	 * @修改时间：2014年8月25日下午8:23:35
	 * @修改内容：
	 * @参数：@param ygFlightDTO
	 * @参数：@return
	 * @return:SlfxFlightDTO
	 * @throws
	 */
	private SlfxFlightDTO reventFlightDTO(YGFlightDTO ygFlightDTO){
		SlfxFlightDTO slfxFlightDTO = new SlfxFlightDTO();
		
		slfxFlightDTO.setAirCompName(ygFlightDTO.getAirCompName());
		slfxFlightDTO.setAircraftCode(ygFlightDTO.getAircraftCode());
		slfxFlightDTO.setAircraftName(ygFlightDTO.getAircraftName());
		slfxFlightDTO.setAirportTax(ygFlightDTO.getAirportTax());
		slfxFlightDTO.setCarrier(ygFlightDTO.getCarrier());
		
		slfxFlightDTO.setCheapestFlightClass(
				getCheapestFlightClassDTO(ygFlightDTO.getFlightClassList())
				);
		
		slfxFlightDTO.setEndCity(ygFlightDTO.getEndPortName());
		slfxFlightDTO.setEndCityCode(ygFlightDTO.getEndCityCode());
		slfxFlightDTO.setEndDate(ygFlightDTO.getEndDate());
		slfxFlightDTO.setEndPort(ygFlightDTO.getEndPort());
		slfxFlightDTO.setEndPortName(ygFlightDTO.getEndPortName());
		slfxFlightDTO.setEndTerminal(ygFlightDTO.getEndTerminal());
		slfxFlightDTO.setEndTime(ygFlightDTO.getEndTime());
		
		slfxFlightDTO.setFlightClassList(ygFlightDTO.getFlightClassList());
		
		slfxFlightDTO.setFlightNo(ygFlightDTO.getFlightNo());
		slfxFlightDTO.setFlyTime(ygFlightDTO.getFlyTime());
		slfxFlightDTO.setFuelSurTax(ygFlightDTO.getFuelSurTax());
		slfxFlightDTO.setId(ygFlightDTO.getId());
		slfxFlightDTO.setIsFood(ygFlightDTO.getIsFood());
		slfxFlightDTO.setMileage(ygFlightDTO.getMileage());
		slfxFlightDTO.setPolicyRemark("");
		slfxFlightDTO.setRefundTime(null);
		slfxFlightDTO.setShareFlightNum(ygFlightDTO.getShareFlightNum());
		slfxFlightDTO.setStartCity(ygFlightDTO.getStartPortName());
		slfxFlightDTO.setStartCityCode(ygFlightDTO.getStartCityCode());
		slfxFlightDTO.setStartDate(ygFlightDTO.getStartDate());
		slfxFlightDTO.setStartPort(ygFlightDTO.getStartPort());
		slfxFlightDTO.setStartPortName(ygFlightDTO.getStartPortName());
		slfxFlightDTO.setStartTerminal(ygFlightDTO.getStartTerminal());
		slfxFlightDTO.setStartTime(ygFlightDTO.getStartTime());
		slfxFlightDTO.setStopNum(ygFlightDTO.getStopNum());
		slfxFlightDTO.setTaxAmount(ygFlightDTO.getTaxAmount());
		slfxFlightDTO.setYPrice(ygFlightDTO.getYPrice());
		
		return slfxFlightDTO;
	}
	
	/**
	 * 
	 * @方法功能说明：获取最低仓位dto
	 * @修改者名字：tandeng
	 * @修改时间：2014年8月25日下午8:21:44
	 * @修改内容：
	 * @参数：@param list
	 * @参数：@return
	 * @return:SlfxFlightClassDTO
	 * @throws
	 */
	private SlfxFlightClassDTO getCheapestFlightClassDTO(List<SlfxFlightClassDTO> list) {
		SlfxFlightClassDTO resultDTO = new SlfxFlightClassDTO();
		double originalPrice = 0.0;
		
		int index = 0;
		for (SlfxFlightClassDTO slfxFlightClassDTO : list) {
			if (null == slfxFlightClassDTO) {
				continue;
			}
			if (index == 0) {
				originalPrice = slfxFlightClassDTO.getOriginalPrice();
				index++;
			}
			if (originalPrice >= slfxFlightClassDTO.getOriginalPrice()) {
				originalPrice = slfxFlightClassDTO.getOriginalPrice();
				//resultDTO = slfxFlightClassDTO;
				BeanUtils.copyProperties(slfxFlightClassDTO, resultDTO);
			}
		}
		return resultDTO;
	}
	
	public static void main(String[] args) {
//		List<SlfxFlightClassDTO> list = new ArrayList<SlfxFlightClassDTO>();
//		SlfxFlightClassDTO dto1 = new SlfxFlightClassDTO();
//		dto1.setOriginalPrice(10.00);
//		list.add(null);
//		list.add(dto1);
//		
//		SlfxFlightClassDTO dto2 = new SlfxFlightClassDTO();
//		dto2.setOriginalPrice(5.00);
//		list.add(dto2);
//		list.add(null);
//		
//		SlfxFlightClassDTO dto3 = new SlfxFlightClassDTO();
//		dto3.setOriginalPrice(11.00);
//		list.add(dto3);
//		
//		SlfxFlightClassDTO temp = getCheapestFlightClassDTO(list);
//		System.out.println(temp.getOriginalPrice());
		
	}
	
	@Override
	public HashMap<String, ABEPatFlightDTO> patByFlights(PatFlightQO qo) {
		return ygFlightService.patByFlights(qo);
	}

	@Override
	public YGFlightDTO queryCacheFlightInfo(String flightNo,String flightDateStr) {
		return flightCacheManager.getFlightDTO(flightNo,flightDateStr);
	}
	
}
