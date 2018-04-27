package pay.record.spi.inter.pay;

import java.util.List;

import pay.record.pojo.command.ModifyLinePayReocrdSpiCommand;
import pay.record.pojo.command.line.CreateLineUTJPayReocrdSpiCommand;
import pay.record.pojo.dto.LinePayRecordDTO;
import pay.record.pojo.qo.pay.LinePayRecordQO;
import pay.record.spi.inter.BasePayRecordSpiService;

public interface LinePayRecordService extends BasePayRecordSpiService<LinePayRecordDTO, LinePayRecordQO>{

	/**
	 * 
	 * @方法功能说明：新增线路支付记录
	 * @修改者名字：yuqz
	 * @修改时间：2015年12月11日上午9:37:13
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:Boolean
	 * @throws
	 */

//	public Boolean createLinePayRecord(CreateLineUTJPayReocrdSpiCommand command);

	/****
	 * 
	 * @方法功能说明：修改线路支付记录
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年12月11日下午6:22:33
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:Boolean
	 * @throws
	 */
//	public Boolean modifyLinePayRecord(ModifyLinePayReocrdSpiCommand command);

	/****
	 * 
	 * @方法功能说明：导出线路支付记录
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年12月14日上午9:27:52
	 * @修改内容：
	 * @参数：@param linePayRecordQO
	 * @参数：@return
	 * @return:List<AirPayRecordDTO>
	 * @throws
	 */
//	public List<LinePayRecordDTO> queryLinePaymentRecordList(LinePayRecordQO linePayRecordQO);
	


}
