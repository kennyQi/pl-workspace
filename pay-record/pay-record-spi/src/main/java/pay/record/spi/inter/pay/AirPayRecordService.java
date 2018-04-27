package pay.record.spi.inter.pay;

import pay.record.pojo.dto.AirPayRecordDTO;
import pay.record.pojo.qo.pay.AirPayRecordQO;
import pay.record.spi.inter.BasePayRecordSpiService;

public interface AirPayRecordService extends BasePayRecordSpiService<AirPayRecordDTO, AirPayRecordQO>{
	/****"
	 * 
	 * @方法功能说明：查询机票支付记录列表
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年12月3日下午2:54:07
	 * @修改内容：添加接口
	 * @参数：@param pagination
	 * @参数：@return
	 * @return:Pagination
	 * @throws
	 */
//	public Pagination queryJPPaymentOrderList(Pagination pagination);

	/**
	 * 
	 * @方法功能说明：查询机票List
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年12月4日下午3:14:34
	 * @修改内容：添加接口
	 * @参数：@param payRecordQO
	 * @参数：@return
	 * @return:List<AirPayRecordDTO>
	 * @throws
	 */
//	public List<AirPayRecordDTO> queryAirPaymentRecordList(AirPayRecordQO payRecordQO);

	/****
	 * 
	 * @方法功能说明：添加机票支付记录
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年12月10日下午5:34:42
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:Boolean
	 * @throws
	 */
//	public Boolean createAirRecord(CreateAirPayReocrdSpiCommand command);

	/****
	 * 
	 * @方法功能说明：修改机票支付记录
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年12月11日上午10:33:50
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:Boolean
	 * @throws
	 */
//	public Boolean modifyAirRecord(ModifyAirPayReocrdSpiCommand command);
	
	
}
