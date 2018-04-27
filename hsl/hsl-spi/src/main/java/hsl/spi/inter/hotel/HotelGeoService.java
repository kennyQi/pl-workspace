package hsl.spi.inter.hotel;


import hsl.pojo.command.HotelGeoCommand;
import hsl.pojo.dto.hotel.util.HotelGeoDTO;
import hsl.pojo.qo.hotel.HotelGeoQO;
import hsl.spi.inter.BaseSpiService;

/**
 * 
 * @类功能说明：商圈接口
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhaows
 * @创建时间：2015-7-2上午9:06:48
 * @类修改者：
 * @修改日期：
 * @修改说明：
 */
public interface HotelGeoService extends BaseSpiService<HotelGeoDTO,HotelGeoQO>{
	public void create(HotelGeoCommand command);
}
