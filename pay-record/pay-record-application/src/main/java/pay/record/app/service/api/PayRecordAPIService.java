package pay.record.app.service.api;

import hg.log.util.HgLogger;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pay.record.api.client.api.v1.pay.record.request.air.CreateAirFTGPayReocrdCommand;
import pay.record.api.client.api.v1.pay.record.request.air.CreateAirGTFPayReocrdCommand;
import pay.record.api.client.api.v1.pay.record.request.air.CreateAirJTUPayReocrdCommand;
import pay.record.api.client.api.v1.pay.record.request.air.CreateAirUTJPayReocrdCommand;
import pay.record.api.client.api.v1.pay.record.request.line.CreateLineUTJPayReocrdCommand;
import pay.record.api.client.common.ApiResponse;
import pay.record.api.client.common.api.PayRecordApiAction;
import pay.record.api.client.common.api.PayReocrdApiService;
import pay.record.app.service.local.pay.AirPayRecordLocalService;
import pay.record.app.service.local.pay.LinePayRecordLocalService;
import pay.record.domain.model.pay.AirPayRecord;
import pay.record.domain.model.pay.LinePayRecord;
import pay.record.pojo.command.air.CreateAirFTGPayReocrdSpiCommand;
import pay.record.pojo.command.air.CreateAirGTFPayReocrdSpiCommand;
import pay.record.pojo.command.air.CreateAirJTUPayReocrdSpiCommand;
import pay.record.pojo.command.air.CreateAirUTJPayReocrdSpiCommand;
import pay.record.pojo.command.line.CreateLineUTJPayReocrdSpiCommand;
import pay.record.pojo.system.BasePayRecordConstants;

/**
 * 
 * @类功能说明：支付记录接口服务
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2016年1月7日下午5:01:27
 * @版本：V1.0
 *
 */
@Service
public class PayRecordAPIService implements PayReocrdApiService{
	@Autowired
	private AirPayRecordLocalService airPayRecordLocalService;
	
	@Autowired
	private LinePayRecordLocalService linePayRecordLocalService;
	
	/**
	 * 
	 * @方法功能说明：新增机票支付记录->用户到经销商
	 * @修改者名字：yuqz
	 * @修改时间：2015年11月24日下午1:55:22
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:CreatePayRecordResponse
	 * @throws
	 */
	@PayRecordApiAction(PayRecordApiAction.PAY_RECORD_AIR_UTJ_CREATE)
	public ApiResponse createAirUTJPayRecord(CreateAirUTJPayReocrdCommand command) {
		ApiResponse apiResponse = new ApiResponse();
		try{
			CreateAirUTJPayReocrdSpiCommand spiCommand = new CreateAirUTJPayReocrdSpiCommand();
			//客户端command转换成服务端command
			spiCommand = dealCreateAirUTJPayReocrdCommand(command);
			AirPayRecord airPayRecord = new AirPayRecord(spiCommand);
			airPayRecordLocalService.save(airPayRecord);
			apiResponse.setResult(ApiResponse.RESULT_SUCCESS);
			apiResponse.setMessage("成功");
		}catch(Exception e){
			apiResponse.setResult(ApiResponse.RESULT_FAILURE);
			apiResponse.setMessage("失败");
			HgLogger.getInstance().error("yuqz", "新增机票支付记录->用户到经销商异常：" + HgLogger.getStackTrace(e));
		}
		return apiResponse;
	}
	
	/**
	 * 
	 * @方法功能说明：新增机票支付记录->分销到供应商
	 * @修改者名字：yuqz
	 * @修改时间：2016年1月15日下午2:55:35
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:ApiResponse
	 * @throws
	 */
	@PayRecordApiAction(PayRecordApiAction.PAY_RECORD_AIR_FTG_CREATE)
	public ApiResponse createAirFTGPayRecord(CreateAirFTGPayReocrdCommand command) {
		ApiResponse apiResponse = new ApiResponse();
		try{
			CreateAirFTGPayReocrdSpiCommand spiCommand = new CreateAirFTGPayReocrdSpiCommand();
			//客户端command转换成服务端command
			spiCommand = dealCreateAirFTGPayReocrdCommand(command);
			AirPayRecord airPayRecord = new AirPayRecord(spiCommand);
			airPayRecordLocalService.save(airPayRecord);
			apiResponse.setResult(ApiResponse.RESULT_SUCCESS);
			apiResponse.setMessage("成功");
		}catch(Exception e){
			apiResponse.setResult(ApiResponse.RESULT_FAILURE);
			apiResponse.setMessage("失败");
			HgLogger.getInstance().error("yuqz", "新增机票支付记录->经销商到供应商异常：" + HgLogger.getStackTrace(e));
		}
		return apiResponse;
	}
	
	/**
	 * 
	 * @方法功能说明：新增机票支付记录->供应商到分销
	 * @修改者名字：yuqz
	 * @修改时间：2016年1月18日下午2:31:55
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:ApiResponse
	 * @throws
	 */
	@PayRecordApiAction(PayRecordApiAction.PAY_RECORD_AIR_GTF_CREATE)
	public ApiResponse createAirGTFPayRecord(CreateAirGTFPayReocrdCommand command) {
		ApiResponse apiResponse = new ApiResponse();
		try{
			CreateAirGTFPayReocrdSpiCommand spiCommand = new CreateAirGTFPayReocrdSpiCommand();
			//客户端command转换成服务端command
			spiCommand = dealCreateAirGTFPayReocrdCommand(command);
			AirPayRecord airPayRecord = new AirPayRecord(spiCommand);
			airPayRecordLocalService.save(airPayRecord);
			apiResponse.setResult(ApiResponse.RESULT_SUCCESS);
			apiResponse.setMessage("成功");
		}catch(Exception e){
			apiResponse.setResult(ApiResponse.RESULT_FAILURE);
			apiResponse.setMessage("失败");
			HgLogger.getInstance().error("yuqz", "新增机票支付记录->经销商到供应商异常：" + HgLogger.getStackTrace(e));
		}
		return apiResponse;
	}

	/**
	 * 
	 * @方法功能说明：新增机票支付记录->经销商到用户
	 * @修改者名字：yuqz
	 * @修改时间：2016年1月18日上午11:19:54
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:ApiResponse
	 * @throws
	 */
	@PayRecordApiAction(PayRecordApiAction.PAY_RECORD_AIR_JTU_CREATE)
	public ApiResponse createAirJTUPayRecord(CreateAirJTUPayReocrdCommand command) {
		ApiResponse apiResponse = new ApiResponse();
		try{
			CreateAirJTUPayReocrdSpiCommand spiCommand = new CreateAirJTUPayReocrdSpiCommand();
			//客户端command转换成服务端command
			spiCommand = dealCreateAirJTUPayReocrdCommand(command);
			AirPayRecord airPayRecord = new AirPayRecord(spiCommand);
			airPayRecordLocalService.save(airPayRecord);
			apiResponse.setResult(ApiResponse.RESULT_SUCCESS);
			apiResponse.setMessage("成功");
		}catch(Exception e){
			apiResponse.setResult(ApiResponse.RESULT_FAILURE);
			apiResponse.setMessage("失败");
			HgLogger.getInstance().error("yuqz", "新增机票支付记录->经销商到供应商异常：" + HgLogger.getStackTrace(e));
		}
		return apiResponse;
	}
	
	/**
	 * 
	 * @方法功能说明：新增线路支付记录->用户到经销商
	 * @修改者名字：yuqz
	 * @修改时间：2016年1月19日下午3:06:54
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:ApiResponse
	 * @throws
	 */
	@PayRecordApiAction(PayRecordApiAction.PAY_RECORD_LINE_UTJ_CREATE)
	public ApiResponse createAirUTJPayRecord(CreateLineUTJPayReocrdCommand command) {
		ApiResponse apiResponse = new ApiResponse();
		try{
			CreateLineUTJPayReocrdSpiCommand spiCommand = new CreateLineUTJPayReocrdSpiCommand();
			//客户端command转换成服务端command
			spiCommand = dealCreateLineUTJPayReocrdCommand(command);
			LinePayRecord linePayRecord = new LinePayRecord(spiCommand);
			linePayRecordLocalService.save(linePayRecord);
			apiResponse.setResult(ApiResponse.RESULT_SUCCESS);
			apiResponse.setMessage("成功");
		}catch(Exception e){
			apiResponse.setResult(ApiResponse.RESULT_FAILURE);
			apiResponse.setMessage("失败");
			HgLogger.getInstance().error("yuqz", "新增机票支付记录->用户到经销商异常：" + HgLogger.getStackTrace(e));
		}
		return apiResponse;
	}

	private CreateLineUTJPayReocrdSpiCommand dealCreateLineUTJPayReocrdCommand(
			CreateLineUTJPayReocrdCommand command) {
		CreateLineUTJPayReocrdSpiCommand spiCommand = new CreateLineUTJPayReocrdSpiCommand();
		BeanUtils.copyProperties(command, spiCommand);
//		spiCommand.setFromProjectIP(command.getFromProjectIP());
		//设置记录类型为用户到经销商
		spiCommand.setRecordType(BasePayRecordConstants.RECORD_TYEP_UTJ);
		return spiCommand;
	}

	/**
	 * 
	 * @方法功能说明：客户端command转换成服务端command
	 * @修改者名字：yuqz
	 * @修改时间：2016年1月13日下午3:20:02
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:CreateAirUTJPayReocrdSpiCommand
	 * @throws
	 */
	private CreateAirUTJPayReocrdSpiCommand dealCreateAirUTJPayReocrdCommand(CreateAirUTJPayReocrdCommand command) {
		CreateAirUTJPayReocrdSpiCommand spiCommand = new CreateAirUTJPayReocrdSpiCommand();
		BeanUtils.copyProperties(command, spiCommand);
//		spiCommand.setFromProjectIP(command.getFromProjectIP());
		//设置记录类型为用户到经销商
		spiCommand.setRecordType(BasePayRecordConstants.RECORD_TYEP_UTJ);
		return spiCommand;
	}
	
	private CreateAirFTGPayReocrdSpiCommand dealCreateAirFTGPayReocrdCommand(
			CreateAirFTGPayReocrdCommand command) {
		CreateAirFTGPayReocrdSpiCommand spiCommand = new CreateAirFTGPayReocrdSpiCommand();
		BeanUtils.copyProperties(command, spiCommand);
		//设置记录类型为分销到供应商
		spiCommand.setRecordType(BasePayRecordConstants.RECORD_TYEP_FTG);
		return spiCommand;
	}
	
	private CreateAirGTFPayReocrdSpiCommand dealCreateAirGTFPayReocrdCommand(
			CreateAirGTFPayReocrdCommand command) {
		CreateAirGTFPayReocrdSpiCommand spiCommand = new CreateAirGTFPayReocrdSpiCommand();
		BeanUtils.copyProperties(command, spiCommand);
		//设置记录类型为分销到供应商
		spiCommand.setRecordType(BasePayRecordConstants.RECORD_TYEP_GTF);
		return spiCommand;
	}
	
	private CreateAirJTUPayReocrdSpiCommand dealCreateAirJTUPayReocrdCommand(
			CreateAirJTUPayReocrdCommand command) {
		CreateAirJTUPayReocrdSpiCommand spiCommand = new CreateAirJTUPayReocrdSpiCommand();
		BeanUtils.copyProperties(command, spiCommand);
		//设置记录类型为分销到供应商
		spiCommand.setRecordType(BasePayRecordConstants.RECORD_TYEP_JTU);
		return spiCommand;
	}
}
