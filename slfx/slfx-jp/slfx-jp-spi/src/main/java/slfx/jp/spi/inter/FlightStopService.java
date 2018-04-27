package slfx.jp.spi.inter;

import java.util.List;

import slfx.jp.pojo.dto.flight.FlightStopInfoDTO;

/**
 * 
 * @类功能说明：航班经停信息SERVICE
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：renfeng
 * @创建时间：2014年7月31日下午5:10:01
 * @版本：V1.0
 *
 */
public interface FlightStopService {
	/**
	 * 查询航班经停信息--运价云
	 * 数据格式JSON
	 * @param flightNo 航班号   格式：CA8904
	 * @param DateStr 航班日期 格式：2014-04-11
	 */
	public List<FlightStopInfoDTO> queryFlightStop(String flightNo, String DateStr);
}
