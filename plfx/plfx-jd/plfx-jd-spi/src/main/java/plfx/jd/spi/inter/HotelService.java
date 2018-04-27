package plfx.jd.spi.inter;



import plfx.jd.pojo.dto.ylclient.hotel.HotelDataInventoryResultDTO;
import plfx.jd.pojo.dto.ylclient.hotel.HotelDataValidateResultDTO;
import plfx.jd.pojo.dto.ylclient.hotel.HotelListResultDTO;
import plfx.jd.pojo.qo.HotelDTO;
import plfx.jd.pojo.qo.YLHotelListQO;
import plfx.jd.pojo.qo.ylclient.JDHotelDetailQO;
import plfx.jd.pojo.qo.ylclient.JDHotelListQO;
import plfx.jd.pojo.qo.ylclient.JDInventoryQO;
import plfx.jd.pojo.qo.ylclient.JDValidateQO;
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
public interface HotelService extends BaseJDSpiService<HotelDTO, YLHotelListQO>{


/*---------------------admin使用上面，shop使用下面----------------------*/
	public HotelListResultDTO queryHotelDetail(JDHotelDetailQO qo);

	public HotelListResultDTO queryHotelList(JDHotelListQO qo);

	public HotelDataInventoryResultDTO queryHotelInventory(JDInventoryQO qo);

	public HotelDataValidateResultDTO queryHotelValidate(JDValidateQO qo);
	
}
