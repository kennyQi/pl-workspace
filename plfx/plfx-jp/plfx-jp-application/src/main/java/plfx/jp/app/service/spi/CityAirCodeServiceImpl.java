package plfx.jp.app.service.spi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import plfx.jp.app.service.local.CityAirCodeLocalService;
import plfx.jp.pojo.dto.flight.CityAirCodeDTO;
import plfx.jp.spi.inter.CityAirCodeService;

/**
 * 
 * @类功能说明：城市机场三字码SERVICE
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年8月4日下午3:59:41
 * @版本：V1.0
 *
 */
@Service("cityAirCodeService")
public class CityAirCodeServiceImpl implements CityAirCodeService {
	
	@Autowired
	private CityAirCodeLocalService cityAirCodeLocalService;

	@Override
	public List<CityAirCodeDTO> queryCityAirCodeList() {
		return cityAirCodeLocalService.queryCityAirCodeList();
	}

}
