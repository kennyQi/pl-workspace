package hg.payment.domain.model.refund;

import hg.log.util.HgLogger;
import hg.payment.domain.Pay;
import hg.payment.domain.common.util.alipay.util.AlipayRefundUtil;
import hg.payment.domain.model.channel.alipay.AlipayPayChannel;
import hg.payment.pojo.command.dto.AlipayRefundRequestDTO;
import hg.payment.pojo.command.spi.payorder.alipay.AlipayRefundCommand;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.apache.commons.httpclient.util.DateUtil;

/**
 * 
 * 
 *@类功能说明：支付宝退款记录
 *@类修改者：
 *@修改日期：
 *@修改说明：
 *@公司名称：浙江汇购科技有限公司
 *@部门：技术部
 *@作者：luoyun
 *@创建时间：2014年11月28日上午8:48:41
 *
 */
@Entity
@Table(name = Pay.TABLE_PREFIX + "ALIPAY_REFUND")
@PrimaryKeyJoinColumn(name="ALIPAY_REFUND_ID")
public class AlipayRefund extends Refund{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 退款批次号
	 */
	@Column(name="REFUND_BATCH_NO")
	private String refundBatchNo;
	
	/**
	 * 合作商户唯一ID
	 */
	@Column(name="PARTNER")
	private String partner;

	/**
	 * 支付宝商户唯一密钥
	 */
	@Column(name="ALIPAY_KEYS")
	private String keys;
	
	
	
	public void createAlipayRefund(AlipayRefundCommand command,AlipayRefundRequestDTO dto,String batchNo){
		setAmount(dto.getAmount());
		setId(dto.getId());
		setRefundDate(new Date());
		setRefundMessage(dto.getRefundMessage());
		setRefundBatchNo(batchNo);
		setPartner(command.getPartner());
		setKeys(command.getKeys());
		RefundProcessor refundProcessor = new RefundProcessor();
		setRefundProcessor(refundProcessor);
	}
	
	
	
	public String buildRefundRequestParam(List<AlipayRefund> refundList) {
		
		AlipayRefund refund = refundList.get(0);
		AlipayPayChannel payChannel = (AlipayPayChannel)refund.getPayOrder().getPayChannel();
		AlipayRefundUtil alipayRefundUtil = new AlipayRefundUtil();
		alipayRefundUtil.setAlipayRefund(refund);
		
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", payChannel.getRefundServiceName());
        sParaTemp.put("partner", refund.getPartner());
        sParaTemp.put("_input_charset", payChannel.getInputCharset());
        sParaTemp.put("notify_url", payChannel.getRefundNotifyUrl());
        sParaTemp.put("dback_notify_url","");
        sParaTemp.put("batch_no", refund.getRefundBatchNo());//批次号
        sParaTemp.put("refund_date", DateUtil.formatDate(refund.getRefundDate(), "yyyy-MM-dd hh:mm:ss"));//退款时间
        sParaTemp.put("detail_data", buildRefundDetailData(refundList));//退款集
        sParaTemp.put("batch_num", refundList.size() + "");//退款笔数
        
		String submitUrl = alipayRefundUtil.buildRefundRequest(sParaTemp);
//		String submitHtml = alipayRefundUtil.buildRefundRequest(sParaTemp,"get","确认");
		
		HgLogger.getInstance().debug("luoyun", "【支付宝】支付宝批量退款请求参数：" + submitUrl);
		return submitUrl;
	}
	
	
	/**
	 * 组装支付宝批量退款请求中的单笔数据集参数
	 * @param refundList
	 * @return
	 */
	private String buildRefundDetailData(List<AlipayRefund> refundList){
		StringBuffer detailBuffer = new StringBuffer();
		for(Refund refund:refundList){
			detailBuffer = detailBuffer.append(refund.getPayOrder().getThirdPartyTradeNo() + "^"
					+ refund.getAmount() + "^" + refund.getRefundMessage() + "#");
		}
		String refundDetailData = detailBuffer.toString();
		refundDetailData = refundDetailData.substring(0,refundDetailData.length()-1);
		return refundDetailData;
	}
	
	
	public String getRefundBatchNo() {
		return refundBatchNo;
	}

	public void setRefundBatchNo(String refundBatchNo) {
		this.refundBatchNo = refundBatchNo;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getKeys() {
		return keys;
	}

	public void setKeys(String keys) {
		this.keys = keys;
	}
	
	
	
	
	
}
