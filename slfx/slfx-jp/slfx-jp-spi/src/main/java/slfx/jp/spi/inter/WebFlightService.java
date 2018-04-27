package slfx.jp.spi.inter;

import java.util.HashMap;

import slfx.jp.pojo.dto.flight.PlatformQueryWebFlightsDTO;
import slfx.jp.pojo.dto.supplier.abe.ABEPatFlightDTO;
import slfx.jp.pojo.dto.supplier.yg.YGFlightDTO;
import slfx.jp.pojo.exception.JPException;
import slfx.jp.qo.api.JPFlightSpiQO;
import slfx.jp.qo.client.PatFlightQO;

/**
 * 
 * @类功能说明：平台航班查询SERVICE
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年7月31日下午5:12:50
 * @版本：V1.0
 *
 */
public interface WebFlightService {
	
	/**
	 * 查询航班列表--运价云
	 * 实现时需要将"商城QO"转化"平台QO"
	 * @param 商城QO
	 * @return	DTO
	 * @author tandeng
	 * @updateDate 2014-07-25
	 */
	PlatformQueryWebFlightsDTO queryFlights(JPFlightSpiQO qo);
	
	/**
	 * 查询航班指定航班--运价云
	 * 实现时需要将"商城QO"转化"平台QO"
	 * @param 商城QO
	 * @return	DTO
	 * @author tandeng
	 * @throws JPException 
	 * @updateDate 2014-08-26
	 */
	PlatformQueryWebFlightsDTO queryFlightsByFlightNo(JPFlightSpiQO qo) throws JPException;
	
	
	/**
	 * PAT报价（验价接口）--webABE 单个航班舱位验价
	 * 此方法一次校验全部乘客类型报价 即： ADT成人   CHD儿童   INF婴儿
	 * 数据格式XML
	 */
	public HashMap<String, ABEPatFlightDTO> patByFlights(PatFlightQO qo);

	/**
	 * 从缓存获取航班信息（平台下单用到）
	 * @param flightNo
	 * @param flightDateStr
	 * @return
	 */
	public YGFlightDTO queryCacheFlightInfo(String flightNo,String flightDateStr);
}
