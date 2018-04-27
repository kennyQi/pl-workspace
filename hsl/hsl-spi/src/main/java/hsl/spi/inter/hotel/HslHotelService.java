package hsl.spi.inter.hotel;

import java.util.List;

import hg.common.page.Pagination;
import hsl.pojo.dto.hotel.HotelDTO;
import hsl.pojo.dto.hotel.JDLocalHotelDetailDTO;
import hsl.pojo.exception.HotelException;
import hsl.pojo.qo.hotel.JDHotelDetailQO;
import hsl.pojo.qo.hotel.JDLocalHotelDetailQO;
import hsl.pojo.qo.log.PLZXClickRecordQo;

public interface HslHotelService {
	
	/**
	 * @方法功能说明：从分销查询酒店信息
	 * @修改者名字：renfeng
	 * @修改时间：2015年7月1日上午11:11:50
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@return
	 * @return:HotelDTO
	 * @throws
	 */
	public HotelDTO queryHotelDetail(JDHotelDetailQO qo) throws HotelException;
	
	
	/**
	 * @方法功能说明：分页查询酒店信息
	 * @修改者名字：renfeng
	 * @修改时间：2015年7月1日下午2:19:18
	 * @修改内容：
	 * @参数：@param pagination
	 * @参数：@return
	 * @return:Pagination
	 * @throws
	 */
	public Pagination queryPagination(Pagination pagination)throws HotelException ;
	
	
	/**
	 * @方法功能说明：从分销查询酒店信息(本地查询)
	 * @修改者名字：renfeng
	 * @修改时间：2015年7月21日下午2:42:41
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@return
	 * @参数：@throws HotelException
	 * @return:JDLocalHotelDetailDTO
	 * @throws
	 */
	public JDLocalHotelDetailDTO queryHotelLocalDetail(JDLocalHotelDetailQO qo) throws HotelException;
	
	/**
	 * @方法功能说明：查询用户酒店浏览记录
	 * @修改者名字：renfeng
	 * @修改时间：2015年7月23日下午3:14:16
	 * @修改内容：
	 * @参数：@param plzxClickRecordQo
	 * @参数：@return
	 * @return:List<HotelDTO>
	 * @throws
	 */
	public List<HotelDTO> queryUserClickRecord(PLZXClickRecordQo plzxClickRecordQo)throws HotelException;
}
