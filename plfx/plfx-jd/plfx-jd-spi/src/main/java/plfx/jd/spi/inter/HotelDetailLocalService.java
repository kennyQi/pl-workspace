package plfx.jd.spi.inter;

import plfx.jd.pojo.dto.plfx.hotel.YLHotelDTO;
import plfx.jd.pojo.qo.YLHotelQO;
import plfx.jd.spi.BaseJDSpiService;

/**
 * 
 * @类功能说明：酒店service
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江票量云数据科技股份有限公司
 * @部门：技术部
 * @作者：caizhenghao
 * @创建时间：2015年2月12日下午3:31:44
 * @版本：V1.0
 *
 */
public interface HotelDetailLocalService extends BaseJDSpiService<YLHotelDTO, YLHotelQO>{

	/****
	 * 
	 * @方法功能说明：获取酒店静态信息saveOrUpdate数据库
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年7月21日上午10:36:08
	 * @修改内容：
	 * @参数：
	 * @return:void
	 * @throws
	 */
	public boolean saveOrUpdateHotelList();
	
	/***
	 * 
	 * @方法功能说明：获取单个酒店saveOrUpdate数据库
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年7月21日上午10:36:20
	 * @修改内容：
	 * @参数：
	 * @return:void
	 * @throws
	 */
	public boolean saveOrUpdateHotelDetail(boolean flag);
	
	/***
	 * 
	 * @方法功能说明：根据酒店id查询本地酒店详情数据
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年7月20日下午3:33:02
	 * @修改内容：
	 * @参数：@return
	 * @return:YLHotelDTO
	 * @throws
	 */
	public YLHotelDTO searchByHotelId(YLHotelQO qo);
	


}
