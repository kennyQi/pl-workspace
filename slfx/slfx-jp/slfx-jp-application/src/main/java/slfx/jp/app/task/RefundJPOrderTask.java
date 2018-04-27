package slfx.jp.app.task;

import hg.log.util.HgLogger;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import slfx.jp.app.alipay.services.AlipayService;
import slfx.jp.app.service.local.JPOrderLocalService;
import slfx.jp.domain.model.order.JPOrder;
import slfx.jp.pojo.dto.order.JPOrderStatusConstant;
import slfx.jp.qo.admin.PlatformOrderQO;

import com.ibm.icu.text.DecimalFormat;

/**
 * @类功能说明：计划任务：对可退款的订单执行退款任务
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：zhangka
 * @创建时间：2014年11月12日上午16:26:30
 * @版本：V1.0
 * 
 */
public class RefundJPOrderTask {
	@Autowired
	private JPOrderLocalService jpOrderLocalService;
	
	@Autowired
	private JedisPool jedisPool;
	
	DecimalFormat df = new DecimalFormat("0.00");

	public void run() {

		HgLogger.getInstance().info("zhangka", "RefundJPOrderTask->run->Task beginning of:" + this.getDateString("yyyy-MM-dd HH:mm:ss"));
		
		//退款记录数
		int count = 0;

		/** 废票成功待退款 */
		PlatformOrderQO qo = new PlatformOrderQO();
		qo.setStatus(Integer.parseInt(JPOrderStatusConstant.SLFX_TICKET_REFUND_SUCC));
		qo.setPayStatus(Integer.parseInt(JPOrderStatusConstant.SLFX_TICKET_REBACK_WAIT));
		List<JPOrder> jpDiscardOrders = jpOrderLocalService.queryList(qo);
		/** 退票成功待退款 */
		qo.setStatus(Integer.parseInt(JPOrderStatusConstant.SLFX_TICKET_REFUND_SUCC));
		qo.setPayStatus(Integer.parseInt(JPOrderStatusConstant.SLFX_TICKET_REBACK_WAIT));
		List<JPOrder> jpBackOrders = jpOrderLocalService.queryList(qo);
		/** 出票失败待退款 */
		qo.setStatus(Integer.parseInt(JPOrderStatusConstant.SLFX_TICKET_FAIL));
		qo.setPayStatus(Integer.parseInt(JPOrderStatusConstant.SLFX_TICKET_REBACK_WAIT));
		List<JPOrder> jpTicketFailOrders = jpOrderLocalService.queryList(qo);
		
		StringBuilder sb = new StringBuilder();
		
		/** 废票成功待退款 */
		if (jpDiscardOrders != null && jpDiscardOrders.size() > 0) {
			
			for (JPOrder jpOrder : jpDiscardOrders) {
				
				if (jpOrder == null || jpOrder.getPayTradeNo() == null || jpOrder.getUserPayAmount() == null) {
					continue;
				}
				
				//单次批量退款最多1000笔
				if (count >= 1000) break;
				
				sb.append(jpOrder.getPayTradeNo());
				sb.append("^");
				sb.append(df.format(Math.abs(jpOrder.getUserPayAmount() + jpOrder.getDisparity() + jpOrder.getServiceCharge() +1)));
				sb.append("^");
				sb.append("原路退款");
				sb.append("#");
				
				count++;
			}
		}
		
		/** 退票成功待退款 */
		if (jpBackOrders != null && jpBackOrders.size() > 0) {
			
			for (JPOrder jpOrder : jpDiscardOrders) {
				
				if (jpOrder == null || jpOrder.getPayTradeNo() == null || jpOrder.getUserPayAmount() == null) {
					continue;
				}
				
				//单次批量退款最多1000笔
				if (count >= 1000) break;
				
				sb.append(jpOrder.getPayTradeNo());
				sb.append("^");
				sb.append(df.format(Math.abs(jpOrder.getUserPayAmount() + jpOrder.getDisparity() + jpOrder.getServiceCharge())));
				sb.append("^");
				sb.append("原路退款");
				sb.append("#");
				
				count++;
			}
		}
		
		/** 出票失败待退款 */
		if (jpTicketFailOrders != null && jpTicketFailOrders.size() > 0) {
			
			for (JPOrder jpOrder : jpTicketFailOrders) {
				
				if (jpOrder == null || jpOrder.getPayTradeNo() == null || jpOrder.getUserPayAmount() == null) {
					continue;
				}
				
				//单次批量退款最多1000笔
				if (count >= 1000) break;
				
				sb.append(jpOrder.getPayTradeNo());
				sb.append("^");
				sb.append(df.format(Math.abs(jpOrder.getUserPayAmount() + jpOrder.getDisparity() + jpOrder.getServiceCharge())));
				sb.append("^");
				sb.append("原路退款");
				sb.append("#");
				
				count++;
			}
		}
		
		HgLogger.getInstance().info("zhangka", "RefundJPOrderTask->run->count:" + count);
		
		if (count == 0) return;
		
		// 退款批次号。格式为：退款日期（8位当天日期）+流水号（3～24位，不能接受“000”，但是可以接受英文字符）
		String batch_no = this.getBatchNo();
		// 退款请求时间
		String refund_date = this.getDateString("yyyy-MM-dd HH:mm:ss");
		// 退款总笔数，即参数detail_data的值中，“#”字符出现的数量加1，最大支持1000笔（即“#”字符出现的数量999个）
		String batch_num = "";
		// 单笔数据集
		String detail_data = "";

		//去掉最有一个‘#’
		detail_data = sb.toString();
		if (detail_data.endsWith("#")) {
			detail_data = detail_data.substring(0, detail_data.length() - 1);
		}
		
		batch_num = String.valueOf(count);
		
		HgLogger.getInstance().info("zhangka", "RefundJPOrderTask->run->batch_no:" + batch_no + ",refund_date:" + refund_date + ",batch_num:" + batch_num + ", detail_data:" + detail_data);
		
		//把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
        sParaTemp.put("batch_no", batch_no);
        sParaTemp.put("refund_date", refund_date);
        sParaTemp.put("batch_num", batch_num);
        sParaTemp.put("detail_data", detail_data);
		 
        //批量退款操作
		try {
			String sHtmlText = AlipayService.refund_fastpay_by_platform_nopwd(sParaTemp);
			HgLogger.getInstance().info("zhangka", "RefundJPOrderTask->run->sHtmlText:" + sHtmlText);
		} catch (Exception e) {
			HgLogger.getInstance().error("zhangka", "RefundJPOrderTask->run->exception:" + HgLogger.getStackTrace(e));
		}
	}
	
	// 获取批次号
	private String getBatchNo() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		String refundDate = df.format(Calendar.getInstance().getTime());
		String sequence = String.format("%012d", this.getSequence());
		
		return refundDate + sequence;
	}
	
	// 获取给定格式的日期字符串
	private String getDateString(String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(Calendar.getInstance().getTime());
	}

	// 获取流水号
	public long getSequence() {
		Jedis jedis = null;
		String value = "1";
		try {
			jedis = jedisPool.getResource();
			value = jedis.get("jp_sequence");
			String date = jedis.get("jp_sequence_date");
			String today = this.getDateString("yyyyMMdd");

			if (StringUtils.isBlank(value)
					|| StringUtils.isBlank(date)
					|| !date.equals(today)) {
				value = "1";
			}
			
			long v = Long.parseLong(value);
			v++;
			
			jedis.set("jp_sequence", String.valueOf(v));
			jedis.set("jp_sequence_date", today);
		} finally {
			jedisPool.returnResource(jedis);
		}

		return Long.parseLong(value);
	}

	
	//测试方法
	public static void main(String[] args) {
		/*SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(df.format(Calendar.getInstance().getTime()));
		System.out.println(String.format("%012d", 999999));
		
		int discardOrderSize = 0;
		int backOrderSize = 0;
		String batch_num = discardOrderSize + backOrderSize + "";
		System.out.println(batch_num);
		
		double userPayAmount = -89.00;
		DecimalFormat df2 = new DecimalFormat("0.00");
		System.out.println(df2.format(Math.abs(userPayAmount)));
		
		String detail_data = "20211212121^89.00^原路退款#";
		if (detail_data.endsWith("#")) {
			detail_data = detail_data.substring(0, detail_data.length() - 1);
		}
		System.out.println(detail_data);*/
		System.out.println(String.valueOf(Calendar.getInstance().getTime().getTime()));
	}

}
