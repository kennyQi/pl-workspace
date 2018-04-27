//package hg.dzpw.app.service.local;
//
//import hg.common.component.BaseServiceImpl;
//import hg.common.util.SpringContextUtil;
//import hg.dzpw.app.dao.DealerDao;
//import hg.dzpw.app.dao.HJBTransferRecordDao;
//import hg.dzpw.app.pojo.qo.HJBTransferRecordQo;
//import hg.dzpw.app.service.api.hjb.HJBPayService;
//import hg.dzpw.domain.model.dealer.Dealer;
//import hg.dzpw.domain.model.pay.HJBTransferRecord;
//import hg.dzpw.pojo.api.hjb.HJBSignQueryRequestDto;
//import hg.dzpw.pojo.api.hjb.HJBSignQueryResponseDto;
//import hg.dzpw.pojo.api.hjb.HJBTransferRequestDto;
//import hg.dzpw.pojo.api.hjb.HJBTransferResponseDto;
//import hg.dzpw.pojo.command.pay.DealerTransferToDzpwCommand;
//import hg.dzpw.pojo.command.pay.DzpwTransferToDealerCommand;
//import hg.dzpw.pojo.command.pay.DzpwTransferToMerchantCommand;
//import hg.dzpw.pojo.common.HjbPayServiceConfig;
//import hg.dzpw.pojo.exception.DZPWException;
//
//import org.apache.commons.lang.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
//
///**
// * @类功能说明：汇金宝转账记录服务
// * @类修改者：
// * @修改日期：2015-5-6下午4:17:23
// * @修改说明：
// * @公司名称：浙江汇购科技有限公司
// * @作者：zhurz
// * @创建时间：2015-5-6下午4:17:23
// */
//@Service
//public class HJBTransferRecordLocalService extends BaseServiceImpl<HJBTransferRecord, HJBTransferRecordQo, HJBTransferRecordDao> {
//
//	@Autowired
//	private HjbPayServiceConfig config;
//	
//	@Autowired
//	private HJBTransferRecordDao dao;
//	
//	@Autowired
//	private DealerDao dealerDao;
//	
//	@Autowired
//	private HJBPayService hjbPayService;
//	
//	@Override
//	protected HJBTransferRecordDao getDao() {
//		return dao;
//	}
//
//	private HJBTransferRecordLocalService service;
//	private HJBTransferRecordLocalService thisService() {
//		if (service == null) {
//			try {
//				service = SpringContextUtil.getApplicationContext().getBean(this.getClass());
//			} catch (Exception e) {
//				return this;
//			}
//		}
//		return service;
//	}
//
//	/**
//	 * @方法功能说明：记录请求
//	 * @修改者名字：zhurz
//	 * @修改时间：2015-5-6下午6:29:16
//	 * @修改内容：
//	 * @参数：@param requestDto
//	 * @参数：@param type
//	 * @参数：@param targetId
//	 * @参数：@return
//	 * @return:HJBTransferRecord
//	 * @throws
//	 */
//	@Transactional(propagation = Propagation.REQUIRES_NEW, noRollbackFor = Exception.class)
//	public HJBTransferRecord recordRequest(HJBTransferRequestDto requestDto, Integer type,
//			String targetId) {
//		HJBTransferRecord record = new HJBTransferRecord();
//		record.create(requestDto, type, targetId);
//		getDao().save(record);
//		return record;
//	}
//
//	/**
//	 * @方法功能说明：记录响应
//	 * @修改者名字：zhurz
//	 * @修改时间：2015-5-6下午6:29:22
//	 * @修改内容：
//	 * @参数：@param responseDto
//	 * @参数：@param recordId
//	 * @参数：@return
//	 * @return:HJBTransferRecord
//	 * @throws
//	 */
//	@Transactional(propagation = Propagation.REQUIRES_NEW, noRollbackFor = Exception.class)
//	public HJBTransferRecord recordResponse(HJBTransferResponseDto responseDto, String recordId) {
//		HJBTransferRecord record = getDao().get(recordId);
//		if (record == null)
//			return null;
//		record.recordResponse(responseDto);
//		getDao().update(record);
//		return record;
//	}
//	
//	
//	/**
//	 * @方法功能说明：经销商 转账给 DZPW
//	 * @修改者名字：
//	 * @修改时间：2015-7-21下午5:14:29
//	 * @参数：@param command
//	 * @参数：@return
//	 * @参数：@throws DZPWException
//	 * @return:HJBTransferResponseDto
//	 */
//	@Transactional(rollbackFor = Exception.class)
//	public HJBTransferResponseDto transferToDzpw(DealerTransferToDzpwCommand command) throws DZPWException {
//
//		Dealer dealer = dealerDao.get(command.getDealerId());
//
//		if (dealer == null)
//			throw new DZPWException(DZPWException.MESSAGE_ONLY, "经销商不存在");
//
//		if (!Integer.valueOf(1).equals(dealer.getAccountInfo().getAccountType()))
//			throw new DZPWException(DZPWException.MESSAGE_ONLY, "经销商未设置汇金宝结算账户");
//
//		if (StringUtils.isBlank(dealer.getAccountInfo().getAccountNumber()))
//			throw new DZPWException(DZPWException.MESSAGE_ONLY, "经销商未设置结算账户");
//
//		dealer.getAccountInfo().getAccountNumber();
//		HJBTransferRequestDto requestDto = new HJBTransferRequestDto();
//		requestDto.setVersion(config.getVersion());
//		requestDto.setMerchantId(config.getMerchantId());
//		requestDto.setPayCstNo(dealer.getAccountInfo().getAccountNumber());
//		requestDto.setRcvCstNo(config.getDzpwCstNo());
//		requestDto.setUserId(dealer.getAccountInfo().getOperatorNo());
//		requestDto.setOriginalOrderNo(System.currentTimeMillis()+command.getOriginalOrderNo());//毫秒数+DZPW订单号 13位+15位
//		requestDto.setTrxAmount(String.valueOf(Integer.valueOf((int) (command.getAmount() * 100))));
//		
//		// 记录请求
//		HJBTransferRecord record = thisService().recordRequest(requestDto,
//				HJBTransferRecord.TYPE_DEALER_TO_DZPW, command.getDzpwOrderId());
//
//		// 发起请求
//		HJBTransferResponseDto responseDto = hjbPayService.signTransfer(requestDto);
//
//		// 记录返回
//		thisService().recordResponse(responseDto, record.getId());
//		
//		// 订单号存在
//		if (responseDto != null && StringUtils.equals("err_023", responseDto.getErrCode())) {
//			// 如果订单号已经存在，则调用查证接口
//			HJBSignQueryRequestDto queryRequestDto = new HJBSignQueryRequestDto();
//			queryRequestDto.setVersion(requestDto.getVersion());
//			queryRequestDto.setMerchantId(requestDto.getMerchantId());
//			queryRequestDto.setOrderNo(responseDto.getOriginalOrderNo());
//			HJBSignQueryResponseDto dto = hjbPayService.signQuery(queryRequestDto);
//			// 如果金额不同
//			if (!StringUtils.equals(dto.getAmount(), requestDto.getTrxAmount()))
//				return null;
//			responseDto.setStatus(dto.getOrderStatus());
//			responseDto.setOrderNo(dto.getOrderNo());
//			responseDto.setOrderAmt(dto.getAmount());
//			if (StringUtils.isBlank(dto.getErrorMessage()))
//				responseDto.setMessage("单笔订单查证");
//			else
//				responseDto.setMessage(dto.getErrorMessage());
//			responseDto.setOriginalOrderNo(dto.getOriginalOrderNo());
//			// 记录查证
//			thisService().recordResponse(responseDto, record.getId());
//		}
//		return responseDto;
//	}
//	
//	
//	/**
//	 * @方法功能说明：DZPW 转账给 景区
//	 * @修改者名字：
//	 * @修改时间：2015-7-21下午5:14:29
//	 * @参数：@param command
//	 * @参数：@return
//	 * @参数：@throws DZPWException
//	 * @return:HJBTransferResponseDto
//	 */
//	@Transactional(rollbackFor = Exception.class)
//	public HJBTransferResponseDto transferToMerchant(DzpwTransferToMerchantCommand command){
//		
//		HJBTransferRequestDto request = new HJBTransferRequestDto();
//		request.setMerchantId(config.getMerchantId());
//		request.setOriginalOrderNo(System.currentTimeMillis()+command.getTicketOrderId());//毫秒数+DZPW订单号 13位+15位
//		request.setPayCstNo(config.getDzpwCstNo());
//		request.setRcvCstNo(command.getRcvCstNo());//收款CST_NO 
//		request.setTrxAmount(String.valueOf(Integer.valueOf((int) (command.getAmount() * 100))));
////		request.setUserId(config.getDzpwUserId());//电子票务付款方  不用设userId
//		request.setVersion(config.getVersion());
//		
//		//记录请求
//		HJBTransferRecord record = thisService().recordRequest(request, HJBTransferRecord.TYPE_DZPW_TO_SCENICSPOT, command.getSingleTicketId());
//		
//		HJBTransferResponseDto respDto = hjbPayService.signTransfer(request);
//		
//		//记录返回
//		thisService().recordResponse(respDto, record.getId());
//		
//		//重复支付  订单存在 需查证
//		if (respDto != null && StringUtils.equals("err_023", respDto.getErrCode())) {
//			HJBSignQueryRequestDto queryReqDto = new HJBSignQueryRequestDto();
//			queryReqDto.setMerchantId(request.getMerchantId());
//			queryReqDto.setOrderNo(request.getOriginalOrderNo());
//			queryReqDto.setVersion(request.getVersion());
//			//查证
//			HJBSignQueryResponseDto qureyRespDto = hjbPayService.signQuery(queryReqDto);
//			
//			respDto.setOrderAmt(qureyRespDto.getAmount());
//			respDto.setOrderNo(qureyRespDto.getOrderNo());
//			respDto.setStatus(qureyRespDto.getStatus());
//			
//			if (StringUtils.isBlank(qureyRespDto.getErrorMessage()))
//				respDto.setMessage("单笔订单查证");
//			else
//				respDto.setMessage(qureyRespDto.getErrorMessage());
//			
//			//记录查证结果
//			thisService().recordResponse(respDto, record.getId());
//		}
//		
//		return respDto;
//	}
//	
//	/**
//	 * @方法功能说明：DZPW 转账给 经销商
//	 * @修改者名字：
//	 * @修改时间：2015-7-21下午5:14:29
//	 * @参数：@param command
//	 * @参数：@return
//	 * @参数：@throws DZPWException
//	 * @return:HJBTransferResponseDto
//	 */
//	@Transactional(rollbackFor = Exception.class)
//	public HJBTransferResponseDto transferToDealer(DzpwTransferToDealerCommand command){
//		
//		HJBTransferRequestDto request = new HJBTransferRequestDto();
//		request.setMerchantId(config.getMerchantId());
//		request.setOriginalOrderNo(System.currentTimeMillis()+command.getOrderId());//毫秒数+DZPW订单号 13位+15位
//		request.setPayCstNo(config.getDzpwCstNo());
//		request.setRcvCstNo(command.getRcvCstNo());//经销商的 cst_no
//		request.setTrxAmount(String.valueOf(Integer.valueOf((int) (command.getAmount() * 100))));
////		request.setUserId(config.getDzpwUserId());//电子票务付款方  不用设userId
//		request.setVersion(config.getVersion());
//		
//		//记录请求
//		HJBTransferRecord record = thisService().recordRequest(request, HJBTransferRecord.TYPE_DZPW_TO_DEALER, command.getOrderId());
//		
//		HJBTransferResponseDto respDto = hjbPayService.signTransfer(request);
//		
//		//记录返回
//		thisService().recordResponse(respDto, record.getId());
//		
//		if (respDto != null && StringUtils.equals("err_023", respDto.getErrCode())) {
//			
//			HJBSignQueryRequestDto queryReqDto = new HJBSignQueryRequestDto();
//			queryReqDto.setMerchantId(request.getMerchantId());
//			queryReqDto.setOrderNo(request.getOriginalOrderNo());
//			queryReqDto.setVersion(request.getVersion());
//			//查证
//			HJBSignQueryResponseDto qureyRespDto = hjbPayService.signQuery(queryReqDto);
//			
//			respDto.setOrderAmt(qureyRespDto.getAmount());
//			respDto.setOrderNo(qureyRespDto.getOrderNo());
//			respDto.setStatus(qureyRespDto.getStatus());
//			
//			if (StringUtils.isBlank(qureyRespDto.getErrorMessage()))
//				respDto.setMessage("单笔订单查证");
//			else
//				respDto.setMessage(qureyRespDto.getErrorMessage());
//			
//			//记录查证结果
//			thisService().recordResponse(respDto, record.getId());
//		}
//		
//		return respDto;
//	}
//	
//}
