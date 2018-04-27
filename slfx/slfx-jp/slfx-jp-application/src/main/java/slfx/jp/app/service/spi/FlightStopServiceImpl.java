package slfx.jp.app.service.spi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import slfx.jp.pojo.dto.flight.FlightStopInfoDTO;
import slfx.jp.spi.inter.FlightStopService;
import slfx.yg.open.inter.YGFlightService;

/**
 * 
 * @类功能说明：平台航班经停SERVICE实现
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年7月31日下午4:46:14
 * @版本：V1.0
 *
 */
@Service("jpFlightStopService")
public class FlightStopServiceImpl implements FlightStopService {
	
	@Autowired
	private YGFlightService ygFlightService;

	@Override
	public List<FlightStopInfoDTO> queryFlightStop(String flightNo,
			String DateStr) {
		
		return this.ygFlightService.queryFlightStop(flightNo, DateStr);
	}

}
