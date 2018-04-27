package hg.payment.domain.common.util;

import hg.payment.domain.model.payorder.PayOrderProcessor;
import hg.payment.domain.model.refund.RefundProcessor;
import hg.payment.pojo.command.admin.dto.ModifyRefundDTO;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.httpclient.util.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
public class RefundUtils {

	@Autowired
	private JedisPool jedisPool;
	
	/**
	 *  获取退款批次号
	 * @return
	 */
	public String getBatchNo() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String refundDate = df.format(Calendar.getInstance().getTime());
		String sequence = String.format("%012d", this.getSequence());
		
		return refundDate + sequence;
	}
	
	/**
	 *  获取流水号
	 * @return
	 */
	public long getSequence() {
		Jedis jedis = null;
		String value = "1";
		try {
			jedis = jedisPool.getResource();
			value = jedis.get("alipay_refund_sequence");
			String date = jedis.get("alipay_refund_sequence_date");
			String today = DateUtil.formatDate(new Date(), "yyyyMMdd");

			if (StringUtils.isBlank(value)
					|| StringUtils.isBlank(date)
					|| !date.equals(today)) {
				value = "1";
			}
			
			long v = Long.parseLong(value);
			v++;
			
			jedis.set("alipay_refund_sequence", String.valueOf(v));
			jedis.set("alipay_refund_sequence_date", today);
		} finally {
			jedisPool.returnResource(jedis);
		}

		return Long.parseLong(value);
	}
	
	/**
	 * 获取退款记录ID
	 * @return
	 */
	public String createRefundNo(String batchNo,int index){
		String pattern = "000";
		DecimalFormat df = new DecimalFormat(pattern);
		return batchNo + df.format(index);
	}
	
	/**
	 * 解析支付宝退款异步回调处理详情result_details
	 * @param detail
	 * @return
	 */
	public List<ModifyRefundDTO> paraseRefundNotify(String detail){
		
		List<ModifyRefundDTO> dtoList = new ArrayList<ModifyRefundDTO>();
		
		String[] tradeCollection = detail.split("#"); //交易信息数组
		for(String tradeRefundData:tradeCollection){
			
			ModifyRefundDTO dto = new ModifyRefundDTO();
			
			String[] tradeRefundDataArr = tradeRefundData.split("\\$");
			String refundData = tradeRefundDataArr[0]; //交易退款数据集
			String[] refundDataDetail = refundData.split("\\^");
			
			dto.setThirdPartyTradeNo(refundDataDetail[0]);
			if("SUCCESS".equalsIgnoreCase(refundDataDetail[2])) {
				dto.setRefundSuccess(RefundProcessor.REFUND_SUCCESS);
			}else{
				dto.setRefundSuccess(RefundProcessor.REFUND_FAIL);
				dto.setRefundRemark(refundDataDetail[2]); //退款失败的错误信息码
			}
			
			if(StringUtils.isNotBlank(tradeRefundDataArr[1])){
				String[] lastDataArr = tradeRefundDataArr[1].split("\\|");
				String chargeData = lastDataArr[0]; //收费退款数据集
			    String[] chargeDataDetail = chargeData.split("\\^");
			    dto.setRefundAccount(chargeDataDetail[0]);
			    dto.setServiceCharge(Double.valueOf(chargeDataDetail[2]));
			}
			
			dtoList.add(dto);
		}
		return dtoList;
		
	}
	
	public static void main(String args[]){
		RefundUtils util = new RefundUtils();
		util.paraseRefundNotify("2014112100001000820036361952^0.01^TRADE_HAS_CLOSED$zjhg_jiesuan@163.com^2088701074577516^0.02^#2014111200001000820035611523^0.01^SUCCESS$zjhg_jiesuan@163.com^2088701074577516^0.00^SUCCESS");
	}
	
	
}
