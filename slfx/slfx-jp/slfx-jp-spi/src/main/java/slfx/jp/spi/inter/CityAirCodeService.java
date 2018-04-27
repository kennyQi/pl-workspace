package slfx.jp.spi.inter;

import java.util.List;

import slfx.jp.pojo.dto.flight.CityAirCodeDTO;

/**
 * 
 * @类功能说明：城市机场三字码SERVICE
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年8月4日下午2:18:47
 * @版本：V1.0
 *
 */
public interface CityAirCodeService {
	
	/**
	 * @方法功能说明：查询所有城市机场三字
	 * @修改者名字：tandeng
	 * @修改时间：2014年8月4日下午2:35:19
	 * @修改内容：
	 * @参数：@return
	 * @return:List<CityAirCodeDTO>
	 * @throws
	 */
	public List<CityAirCodeDTO> queryCityAirCodeList();

}
